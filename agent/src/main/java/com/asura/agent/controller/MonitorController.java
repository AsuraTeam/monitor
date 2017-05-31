package com.asura.agent.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.agent.conf.MonitorCacheConfig;
import com.asura.agent.configure.Configure;
import com.asura.agent.entity.MonitorConfigureEntity;
import com.asura.agent.entity.MonitorContactGroupEntity;
import com.asura.agent.entity.MonitorContactsEntity;
import com.asura.agent.entity.MonitorItemEntity;
import com.asura.agent.entity.MonitorLockEntity;
import com.asura.agent.entity.MonitorMessagesEntity;
import com.asura.agent.entity.MonitorScriptsEntity;
import com.asura.agent.entity.MonitorSystemScriptsEntity;
import com.asura.agent.entity.MonitorTemplateEntity;
import com.asura.agent.entity.PushEntity;
import com.asura.agent.monitor.AgentMonitor;
import com.asura.agent.monitor.AgentSystemInfo;
import com.asura.agent.thread.AgentPingThread;
import com.asura.agent.thread.RunScriptThread;
import com.asura.agent.util.Base64Util;
import com.asura.agent.util.CommandUtil;
import com.asura.agent.util.DateUtil;
import com.asura.agent.util.FileIoUtil;
import com.asura.agent.util.HttpSendUtil;
import com.asura.agent.util.IpUtil;
import com.asura.agent.util.MonitorUtil;
import com.asura.agent.util.RedisUtil;
import com.asura.agent.util.SocketSendUtil;
import com.asura.agent.util.network.Ping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import static com.asura.agent.conf.MonitorCacheConfig.cacheContactGroupKey;
import static com.asura.agent.conf.MonitorCacheConfig.cacheContactKey;
import static com.asura.agent.util.MonitorUtil.getAdminGroup;
import static com.asura.agent.util.MonitorUtil.getGroupData;
import static com.asura.agent.util.MonitorUtil.getIsValidHosts;
import static com.asura.agent.util.MonitorUtil.getScripts;
import static com.asura.agent.util.MonitorUtil.sendPostMessages;
import static com.asura.agent.util.RedisUtil.app;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 *
 *          监控程序上报，监控脚本执行
 *          2016-09-16 16:50:00 创建
 *          20170117 修改某类报警人为空报错异常
 *          修改配置修改由400秒改为200秒
 *          修改如果重试次数为0，报警不受时间限制，有问题就发送报警
 *
 *          20170120 修改ip地址太短，不能上报数据错误
 *          20170201 修改不能初始化监控数据bug
 *          修改缓存文件过期失败bug
 *          20170203 优化agent信息上传，每半小时上传一次数据
 *          优化报警发送信息时添加配置的描述信息添加到报警消息里
 *          修改获取脚本为安全考虑不从redis获取，从接口获取
 *          修改读取文件时文件内容太少获取不到数据问题
 *
 *          20170204 修改agent无监控不能自动恢复问题
 *          20170211 修改配置监控的时间和默认脚本时间冲突
 *          20170215 修改监控配置丢失报错
 *          20170218 添加对执行脚本时间的限制，防止卡死程序耗尽资源
 *          20170223 添加agent检查ping时添加hostname功能
 *          20170426 agent数据上报错误修改
 *          20170426 agent监控多线程支持
 *          20170515 修改报警重复
 *          20170517 报警不需要发送恢复修复
 *          20170526 修复redis检查bug
 */
@RestController
@EnableAutoConfiguration
public class MonitorController {

    // 版本号
    private final String VERSION = "1.0.0.20";

    private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    private static RedisUtil redisUtil;
    private static Gson gson;

    // 获取临时文件目录
    public static final String separator = System.getProperty("file.separator");
    private static final String tempDir = System.getProperty("java.io.tmpdir") + separator + "monitor" + separator;
    // 存放使用udp发送数据开关
    private static Long udpSendNumber;
    // 报警的开关
    private static boolean IS_MONITOR = false;
    // 存放本机的IP地址
    private static HashSet<String> LOCAL_IP; //IpUtil.getHostIP();
    // 全局的本机拥有的hostID
    private static String HOST_IDS;
    // 全局拥有的组的ID
    private static HashSet<String> GROUPS_IDS;
    // 存放本机的监控配置数据
    private static Map<String, HashSet<MonitorConfigureEntity>> HOST_CONFIGS;
    // 存放属于每个组的配置
    private static Map<String, HashSet<MonitorConfigureEntity>> GROUP_CONFIGS;
    // 存放配置文件
    private static Map<String, MonitorConfigureEntity> CONFIGS;
    // 存放本机所在的组
    private static Map<String, HashSet> HOST_GROUPS;
    // 存放host拥有的配置文件id
    private static HashSet<String> hostConfigs;
    // 存放group拥有的配置文件id
    private static HashSet<String> groupsConfigs;
    // 存放本机拥有的所有项目id
    private static HashSet<String> HOST_ITEMS;
    // 保存本机所有组的项目ID
    private static HashSet<String> GROUP_ITEMS;
    // 保存所有项目的配置信息
    private static Map<String, HashSet<MonitorItemEntity>> ITEM_CONFIGS;
    // 获取所有脚本的id
    private static HashSet<String> SCRIPTS;
    // 获取所有脚本的配置
    private static Map<String, MonitorScriptsEntity> SCRIPT_CONFIGS;
    // 存放所有要执行的时间
    private static HashSet<String> SCRIPT_TIME;
    // 存放每个时间对应的脚本
    private static Map<String, HashSet> SCRIPT_TIME_MAP;
    // 存放每个脚本上次执行的时间
    private static Map<String, Long> SCRIPT_RUNTIME;
    // 存放每个脚本的参数
    private static Map<String, String> SCRIPT_ARGV;
    // 保存监控检查初始化时间
    private static long INIT_TIME;
    // 存放自己所属的业务线
    private static String HOST_GROUP;
    // 存放成功的接口
    private static final String successApiUrl = "/monitor/api/success";
    // 存放失败的接口
    private static final String faildApiUrl = "/monitor/api/faild";
    // 报警，如果redis队列失败，直接调接口发送
    // 报警判断，存放每个脚本存放的报警次数，达到几次后发送报警消息到队列
    // 每次发送报警的数据记录到库里
    private static Map<String, Integer> ALARM_MAP;
    // 记录报警信息的次数
    private static Map<String, Integer> ALARM_COUNT;
    // 记录报警的间隔
    private static Map<String, Long> ALARM_INTERVAL;
    // 存储每个脚本对应的项目ID
    private static Map<String, String> SCRIPT_ITEM;
    // 存储每个item对应的config
    private static Map<String, MonitorConfigureEntity> SCRIPT_ITEM_CONFIG;
    // 记录每个脚本对应的报警状态
    private static Map<String, PushEntity> SCRIPT_STATUS;
    // 存放本地报警队列，每10秒检查一次,有的话就发送
    private static LinkedTransferQueue<PushEntity> queue;
    // 存放脚本运行间隔
    private static Map<String, Long> MONITOR_LOCK;
    // 存放最近报警时间，判断是否为闪断, 如果恢复和报警时间太短，不再发送恢复消息
    // 存储系统调用资源失败,比如Redis失败,连续10次失败后程序先停止执行,等一会后继续
    private static Map<String, Long> ALARM_LAST_TIME;
    // 存放参数中IP地址的
    private static Map<String, String> ARGV_HOST_MAP;
    // 存放每个脚本的检查间隔
    private static Map<String, String> SCRIPT_CHECK_INTERVAL;
    // 每个脚本的重试间隔
    private static Map SCRIPT_RETRY_MAP;
    // 存放各种执行时间
    private static Map<String, Long> timeMap;
    private static Long PUSH_SERVER_INFO_TIME;
    // 存放自己是否是分布式检查AGENT的机器
    private static boolean CACHE_CHECK_MONITOR_LOCK;
    // 存放每个脚本对应的ITEM配置
    private static Map<String, MonitorItemEntity> SCRIPT_TO_ITEM;

    // 存放是否有除4默认配置外的其他配置, true 是有的， false是没有的
    private static boolean IS_DEFAULT = false;

    // 存放实时执行的时间
    private static Map<String, Map> REAL_TIME_CACHE;

    // 设置agent报警次数
    private static Map<String, String> AGENT_ALARM_MAP;

    // 存放是否打开Debug
    private static boolean isDebug = false;

    private static ExecutorService executor ;
    private static int threadPoolNumber ;
    private Random random;


    /**
     * 初始化系统变量
     */
    void init() {
        logger.info("初始化系统变量.....");
        checkAgentRedis();
        redisUtil = new RedisUtil();
        gson = new Gson();
        hostConfigs = new HashSet<>();
        groupsConfigs = new HashSet<>();
        HOST_IDS = "";
        GROUPS_IDS = new HashSet<>();
        HOST_ITEMS = new HashSet<>();
        HOST_CONFIGS = new HashMap<>();
        HOST_GROUPS = new HashMap<>();
        GROUPS_IDS = new HashSet<>();
        GROUP_ITEMS = new HashSet<>();
        GROUP_CONFIGS = new HashMap<>();
        SCRIPT_CONFIGS = new HashMap<>();
        ITEM_CONFIGS = new HashMap<>();
        SCRIPTS = new HashSet<>();
        LOCAL_IP = new HashSet<>();
        SCRIPT_TIME = new HashSet();
        SCRIPT_TIME_MAP = new HashMap<>();
        SCRIPT_RUNTIME = new HashMap<>();
        SCRIPT_ARGV = new HashMap<>();
        CONFIGS = new HashMap<>();
        ALARM_MAP = new HashMap<>();
        ALARM_COUNT = new HashMap<>();
        ALARM_INTERVAL = new HashMap<>();
        SCRIPT_ITEM = new HashMap<>();
        SCRIPT_ITEM_CONFIG = new HashMap<>();
        queue = new LinkedTransferQueue();
        SCRIPT_STATUS = new HashMap<>();
        MONITOR_LOCK = new HashMap<>();
        if (ALARM_LAST_TIME == null) {
            ALARM_LAST_TIME = new HashMap<>();
        }
        ARGV_HOST_MAP = new HashMap<>();
        SCRIPT_CHECK_INTERVAL = new HashMap<>();
        SCRIPT_RETRY_MAP = new HashMap<>();
        SCRIPT_TO_ITEM = new HashMap<>();
        timeMap = new HashMap<>();
        CACHE_CHECK_MONITOR_LOCK = false;
        IS_DEFAULT = false;
        setIsDebug();
        if (udpSendNumber == null) {
            udpSendNumber = 1L;
        }
        if (timeMap == null){
            timeMap = new HashMap<>();
        }
        if (REAL_TIME_CACHE == null) {
            REAL_TIME_CACHE = new HashMap<>();
        }
        if (PUSH_SERVER_INFO_TIME == null) {
            pushServerInfo();
            PUSH_SERVER_INFO_TIME = DateUtil.getCurrTime();
        }

        setAgentServerInfo();
    }

    /**
     * @param messages
     */
    static void info(String messages) {
        if (null != messages ) {
            logger.info(messages);
        }
    }

    /**
     * 缓存服务启动端口和主机名
     */
    void setAgentServerInfo() {
        Map map = new HashMap();
        map.put("port", IpUtil.getServerPort());
        map.put("hostname", IpUtil.getHostname());
        redisUtil.set(MonitorCacheConfig.cacheAgentServerInfo.concat(HOST_IDS), gson.toJson(map));
    }

    /**
     *
     * @return
     */
  public static boolean getErrorNumber(){
       String key = "monitor_check_redis_active";
       if (ALARM_LAST_TIME == null){
           ALARM_LAST_TIME = new HashMap<>();
       }
       if (!ALARM_LAST_TIME.containsKey(key)){
           info(isDebug ? "检查redis错误次数: " + ALARM_LAST_TIME.get(key) : null);
           ALARM_LAST_TIME.put(key, 0L);
       }
       info(isDebug ? "检查redis错误次数: " + ALARM_LAST_TIME.get(key) : null);
       checkAgentRedis();
        if (ALARM_LAST_TIME.get(key) > 3){
            logger.error("获取redis失败，程序终止执行");
            return true;
        }
        return false;
    }


    /**
     * 每8秒检查一次，连续10次失败，程序先停止
     * 主要针对redis用域名连接域名失败的情况
     */
    public static void checkAgentRedis(){
        String key = "monitor_check_redis_active";
        initTimeMap(key);
        info(isDebug ? "开始检查redis可用" : null);
        if (checkTimeMap(key, 6)) {
            String result = redisUtil.get(key);
            if (result == null || result.equals("") || result.length() < 1 ) {
                if (ALARM_LAST_TIME.get(key) > 10){
                    logger.error( "redis  失败, 不执行set操作 " + ALARM_LAST_TIME.get(key));
                    return;
                }
                String set = redisUtil.set(key, DateUtil.getCurrTime() + "");
                if (set.equals("err")) {
                    if (ALARM_LAST_TIME.containsKey(key)) {
                        logger.error( "redis  失败 " + ALARM_LAST_TIME.get(key));
                        ALARM_LAST_TIME.put(key, ALARM_LAST_TIME.get(key) + 1);
                    }else{
                        ALARM_LAST_TIME.put(key, 1L);
                    }
                }
                putTimeMap(key);
                return;
            }
            putTimeMap(key);
            ALARM_LAST_TIME.put(key, 0L);
        }
    }

    /**
     * 获取IP地址的ID
     * @param ip
     * @return
     */
    static String getHostId(String ip) {
        ip = ip.replace(",", "");
        ip = ip.replace("'", "");
        ip = ip.replace("\"", "");
        return redisUtil.get(MonitorCacheConfig.hostsIdKey .concat(ip));
    }


    /**
     * 获取自己是否可上线
     */
    void getHostConfig() {
        if (GROUPS_IDS == null) {
            init();
        }
        // 获取自己是否有监控项目
        HashSet hosts = getIsValidHosts();
        for (String ip : IpUtil.getHostIP()) {
            String result = getHostId(ip);
            if (result != null && result.length() > 0) {
                HOST_IDS = result;
                logger.info("获取到本机IP地址 " + ip + " id: " + result);
                LOCAL_IP.add(ip);
                // 上报版本和cpu数量
                redisUtil.set(MonitorCacheConfig.cacheAgentVersion.concat(ip), VERSION);
                redisUtil.set(MonitorCacheConfig.cacheAgentCpu.concat(ip), CommandUtil.getCpuNumber());
                if (hosts.contains(result)) {
                    IS_DEFAULT = true;
                }
            }
        }
        boolean isMonitor = false;
        // 去redis获取自己的host的ID
        for (String ip : LOCAL_IP) {
            if (ip.length() < 7) {
                continue;
            }
            IS_MONITOR = true;
            String group = redisUtil.get(MonitorCacheConfig.getCacheHostGroupsKey + ip);
            if (group != null && group.length() > 0) {
                info(isDebug ? "set host groups is:" + group : null);
                HOST_GROUP = group;
                isMonitor = true;
                info(isDebug ? "HOST_GROUP: " + HOST_GROUP : null);
            } else if (!isMonitor) {
                IS_DEFAULT = false;
                IS_MONITOR = false;
            }
        }
    }

    /**
     * 获取系统基础信息
     * 2017-03-13
     */
    @RequestMapping("/api/info")
    @ResponseBody
    public String getSystemInfo() {
        return AgentSystemInfo.getSystemInfo();
    }

    /**
     * 上传cmdb数据
     */
    void pushCmdb(){
        try {
            for (String ip : IpUtil.getHostIP()) {
                String result = getHostId(ip);
                if (result != null && result.length() > 0) {
                    return;
                }
                logger.error("ip没有到cmdb, 请保证至少一个agent的地址在cmdb系统", ip);
            }
        }catch (Exception e){
            timeMap.remove("setAgentServerInfoCron");
            return;
        }
        String url = "/resource/configure/server/auto";
        Properties props = System.getProperties();
        StringBuilder sb = new StringBuilder();
        sb.append("os=")
                .append(props.getProperty("os.name"))
                .append(props.getProperty("os.version"))
                .append("&cpu=")
                .append(CommandUtil.getCpuNumber())
                .append("&memory=")
                .append(CommandUtil.getMemorySize())
                .append("&disk=")
                .append(CommandUtil.getDiskSize());
        HttpSendUtil.sendPost(url, sb.toString());
        logger.info("上传数据到cmdb" + sb.toString());
    }

    /**
     * 获取实时数据
     * 主要是性能参数的获取，传递脚本ID
     */
    @RequestMapping("/api/realtime")
    @ResponseBody
    public String realtime(int scriptId) throws Exception {
        String result = "";
        Map dataMap = new HashMap();
        String id = String.valueOf(scriptId);
        boolean isCache = false;
        if (REAL_TIME_CACHE.containsKey(id)) {
            Long time = (Long) REAL_TIME_CACHE.get(id).get("time");
            if (DateUtil.getCurrTime() - time < 5) {
                isCache = true;
            }
        }
        if (isCache) {
            result = (String) REAL_TIME_CACHE.get(id).get("data");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(tempDir).append(separator).append(String.valueOf(scriptId));
            result = gson.toJson(run(stringBuilder.toString(), 10));
            dataMap.put("time", DateUtil.getCurrTime());
            dataMap.put("data", result);
            REAL_TIME_CACHE.put(id, dataMap);
        }
        return result;
    }

    /**
     * 获取是否拥有监控
     */
    @RequestMapping("/monitor/init")
    void initMonitor() {
        checkAgentRedis();
        init();
        // 获取自己是否有监控项目
        // 去redis获取自己的host的ID
        getHostConfig();

        if (IS_MONITOR) {
            setMonitorGroups();
            getHostGroupsConfigs();
            getMonitorConfigure();
            getItemConfigure();
            setItemConfigs();
            setScripts();
            setScriptConfigs();
            getScriptTime();
            getScriptArgv();
        }
    }

    /**
     * 获取参数中的IP地址的ID
     * 所有配置的IP地址必须在资源库中
     *
     * @return
     */
    static String getServerId(PushEntity pushEntity) {
        if (pushEntity.getIp() == null) {
            return getHosts();
        }
        String ip = pushEntity.getIp();
        if (!ARGV_HOST_MAP.containsKey(ip)) {
            String result = getHostId(ip);
            if (result != null && result.length() > 0) {
                ARGV_HOST_MAP.put(ip, result);
            } else {
                ARGV_HOST_MAP.put(ip, IpUtil.ipToLong(ip));
            }
        }
        info(isDebug ? "ARGV_HOST_MAP " + ip + " " + ARGV_HOST_MAP.get(ip) : null);
        return ARGV_HOST_MAP.get(ip);
    }

    /**
     * 任务计划管理
     */
    void scheduledStart(){
        logger.info(isDebug ? "任务调度启动" : null);
        checkAgentRedis();
        if (getErrorNumber()){
            return;
        }
        // 设置主机正在运行状态  10秒一次
        setHostActive();
        // checkAlarmQueue 10秒钟一次
        checkAlarmQueue();
        // 检查配置文件修改
        checkConfigChange();
        // 设置是否打开DEBUG
        setIsDebug();
        // 清除无效数据
        clearRealTimeCACHE();
        checkIsMonitor();
        setAgentServerInfoCron();
        // agent数据上报
        agentMonitor();
        // 上报服务器信息
        pushServerInfoCron();
        // 获取上报服务器的地址
        setPushServer();
        // 检查监控配置
        checkMonitorIsValid();
        //checkAgentStatus 1分钟
        checkAgentStatus();
        // 每天初始化数据
        setClearMonitorTime();
        logger.info(isDebug ? "任务调度结束" : null);
    }

    /**
     * 清除实时查看的内存
     * 每5分钟清除一次，避免垃圾数据
     */
    void clearRealTimeCACHE(){
        initTimeMap("clearRealTimeCACHE");
        if (checkTimeMap("clearRealTimeCACHE", 300)) {
            REAL_TIME_CACHE = new HashMap<>();
            putTimeMap("clearRealTimeCACHE");
        }
    }

    /**
     * 每一分钟检查是否加到组里，主要防止没有加到cmdb的机器
     */
    void checkIsMonitor() {
        if (LOCAL_IP == null) {
            return;
        }
        initTimeMap("checkIsMonitor");
        if (checkTimeMap("checkIsMonitor", 120)) {
            if (!IS_MONITOR && !IS_DEFAULT) {
                for (String ip : LOCAL_IP) {
                    String group = redisUtil.get(MonitorCacheConfig.getCacheHostGroupsKey + ip);
                    if (group != null && group.length() > 0) {
                        logger.info("初始化加到cmdb的机器...");
                        initMonitor();
                    }
                }
            }
            putTimeMap("checkIsMonitor");
        }
    }

    /**
     * 设置是否打开debug
     * 每一分钟跑一次
     */
    void setIsDebug() {
        initTimeMap("setIsDebug");
        if (checkTimeMap("setIsDebug", 60)) {
            if (Configure.get("DEBUG").equals("true")) {
                isDebug = true;
            } else {
                isDebug = false;
            }
            putTimeMap("setIsDebug");
        }
    }

    /**
     * agent自身资源使用上报,每1分钟上报一次
     */
    void agentMonitor() {
        if (LOCAL_IP == null) return;
        initTimeMap("agentMonitor");
        if (checkTimeMap("agentMonitor", 70)) {
            putTimeMap("agentMonitor");
            String ip = "";
            for (String ips : LOCAL_IP) {
                ip = ips;
            }
            ArrayList<PushEntity> list = AgentMonitor.setPushEntitys(ip);
            info(isDebug ? "agent data:" + gson.toJson(list) : null);
            pushMonitor(list, successApiUrl, true);

        }
    }



    /**
     * 检查配置更新,任何配置更新将重置监控
     * 主要是监控默认的配置
     */
    void checkConfigChange() {
        if (LOCAL_IP == null) {
            return;
        }
        if(timeMap.containsKey("checkConfigChange")) {
            if (checkTimeMap("checkConfigChange", 60)) {
                String result = redisUtil.rpop(MonitorCacheConfig.cacheDefaultChangeQueue.concat(HOST_IDS));
                if (result != null && result.length() > 0) {
                    logger.info("检查到配置更新，开始更新监控...");
                    initMonitor();
                }
                putTimeMap("checkConfigChange");
            }
        }else{
            putTimeMap("checkConfigChange");
        }
    }

    /**
     * 检查是否有脚本要执行
     * 每5秒检查一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    void checkExecScript() throws Exception {

        checkAgentRedis();

        if (getErrorNumber()){
            return;
        }

        // 检查是否初始化过
        if (INIT_TIME == 0) {
            // 初始化监控
            INIT_TIME = DateUtil.getCurrTime();
            info(isDebug ? "init monitor ...." : null);
            checkAgentRedis();
            initMonitor();
        }

        if (!IS_MONITOR) {
            initMonitor();
            logger.error("没有监控项目退出监控1");
            return;
        }

        // 如果没有开启监控，则不执行任何东西
        if (!IS_MONITOR  || SCRIPT_TIME == null )  {
            logger.error("没有监控项目退出监控2");
            return;
        }
        info(isDebug ? "cron 启动任务计划 " + DateUtil.getCurrTime(): null);
        // 防止停止监控 2017-02-04
        if (SCRIPT_TIME.size() == 0) {
            initMonitor();
            return;
        }
        try {
            scheduledStart();
        }catch (Exception e){
            logger.error("scheduledStart", e);
        }
        RunScriptThread runScriptThread = new RunScriptThread(isDebug, SCRIPT_TIME);
        if (executor == null){
            threadPoolNumber = 30;
            executor = Executors.newFixedThreadPool(threadPoolNumber);
        }
        try {
            executor.execute(runScriptThread);
        }catch (Exception e){
            logger.info("线程池失败扩大线程池");
            executor = Executors.newFixedThreadPool(threadPoolNumber * 2);
        }
    }

    /**
     * 删除MAP
     *
     * @param id
     */
   static void removeMap(String id) {
        Iterator<String> iterator = ALARM_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(id)) {
                info(isDebug ? "remove ALARM_MAP " + id : null);
                iterator.remove();
            }
        }
    }

    /**
     * 每30分钟设置一次
     */
    void setAgentServerInfoCron() {
        initTimeMap("setAgentServerInfoCron");
        if (!timeMap.containsKey("setAgentServerInfoCron")) {
            pushCmdb();
            initTimeMap("setAgentServerInfoCron");
            return;
        }
        if (checkTimeMap("setAgentServerInfoCron", 1800)) {
            setAgentServerInfo();
            putTimeMap("setAgentServerInfoCron");
        }
    }

    /**
     * 上传客户端的信息到服务端
     */
    void pushServerInfo() {
        if (getErrorNumber()){
            return;
        }
        String pushUrl = "monitor/api/sysInfo";
        String os = System.getProperty("os.name");
        String scriptUrl = "monitor/scripts/api/scripts";
        String script = HttpSendUtil.sendPost(scriptUrl, "os=" + os);
        if (script != null && script.length() > 1) {
            MonitorSystemScriptsEntity scriptsEntity = gson.fromJson(script, MonitorSystemScriptsEntity.class);
            script = scriptsEntity.getScriptsContent();
        } else {
            return;
        }
        String file = tempDir + "sysInfo";
        FileIoUtil.makeDir(tempDir);
        FileIoUtil.writeFile(file, script, false);
        MonitorUtil.setFileExec(file);
        String result = runScript(file, 10);
        info(isDebug ? "系统信息获取到: " + result : null);
        String data = HttpSendUtil.sendPost(pushUrl,"sysInfo=" + Base64Util.encode(result));
        logger.info(data);
    }

    /**
     * 每30分钟上传一次数据
     */
    void pushServerInfoCron() {
        initTimeMap("pushServerInfoCron");
        if (checkTimeMap("pushServerInfoCron", 1800)) {
            pushServerInfo();
            putTimeMap("pushServerInfoCron");
        }
    }

    /**
     * 每10分钟获取一次push服务器信息
     */
    void setPushServer() {
        checkAgentRedis();
        initTimeMap("setPushServer");
        if (!timeMap.containsKey("setPushServer")){
            putTimeMap("setPushServer");
            SocketSendUtil.setServer();
        }
        if (checkTimeMap("setPushServer",180)) {
            putTimeMap("setPushServer");
            SocketSendUtil.setServer();
        }
    }

    /**
     * 报警重试和发送报警
     * 检查和发送消息
     * 检查报警重试的次数，到达后发送报警
     */
    @Scheduled(cron = "0/5 * * * * ?")
    void checkMonitorAlarm() {
        if (!IS_MONITOR) {
            return;
        }

        if (ALARM_MAP == null) {
            return;
        }

        if (getErrorNumber()){
            return;
        }

        info(isDebug ? "报警发送检查...." : null);
        String alarmMapString = gson.toJson(ALARM_MAP);
        Map<String, Double> ALARM_NEW_MAP = gson.fromJson(alarmMapString, HashMap.class);
        info(isDebug ? "alarmMapString " + gson.toJson(ALARM_NEW_MAP) : null);
        for (Map.Entry<String, Double> entits : ALARM_NEW_MAP.entrySet()) {
            if (entits.getKey() == null) {
                info(isDebug ? "ALARM_MAP key is null ... " : null);
                continue;
            }
            String[] key = entits.getKey().split("_");
            Integer value = entits.getValue().intValue();

            // 获取配置文件的ID
            String configId = key[0];
            MonitorConfigureEntity entity = CONFIGS.get(configId);
            if (entity == null) {
                info(isDebug ? "获取到空数据 COFNIGS.get" + configId + gson.toJson(key): null);
                continue;
            }
            if (key.length < 6) {
                info(isDebug ? "获取到key小于6... " + gson.toJson(key) : null);
                continue;
            }
            info(isDebug ? "ALARM_NEW_MAP " + entits.getKey() + " " + key[5] : null);
            if (key[5].equals("ok")) {
                continue;
            }
            if (key[5].equals("warning")) {
                continue;
            }
            if (key[5].equals("unknown")) {
                continue;
            }
            String scriptId = key[1];
            String id = configId + "_" + scriptId;
            StringBuilder alarmIdString = new StringBuilder();
            alarmIdString.append(id).append("_").append(key[2]).append("_").append(key[3]).append("_").append(key[4]);
            String alarmId = alarmIdString.toString();
            // 如果小于配置文件的重试次数，则执行重试

            // 获取重试次数
            int retry = 0;
            if (entity.getMonitorConfigureTp().equals("item")) {
                retry = entity.getRetry();
            } else {
                MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(id);
                retry = itemEntity.getRetry();
            }

            if (retry == 0) {
                logger.info("获取到重试次数为0,删除报警信息");
                ALARM_MAP.remove(entits.getKey());
            }

            String alarmIdTime = alarmId.concat(".time");

            // 如果retry为0则不执行重试
            if (value >= 1 && value <= retry + 1 && retry > 0) {
                if (!ALARM_INTERVAL.containsKey(alarmIdTime)){
                    ALARM_INTERVAL.put(alarmIdTime, DateUtil.getCurrTime());
                }
                if (DateUtil.getCurrTime() - ALARM_INTERVAL.get(alarmIdTime) < 10 ){
                    info(isDebug ? "跳出重试,间隔太小了" + gson.toJson(entits) : null);
                    continue;
                }
                info(isDebug ? "获取到重试数据" + gson.toJson(entits) : null);
                StringBuilder command = new StringBuilder();
                command.append(tempDir).append(scriptId).append(SCRIPT_ARGV.get(id));

                info(isDebug ? "checkMonitorAlarm " + command.toString() + " " + alarmId + " retry -> " + value : null);
                List<PushEntity> pushEntities = run(command.toString(), getTimeout(scriptId));
                for (PushEntity pushEntity : pushEntities) {
                    if (pushEntity != null && Integer.valueOf(pushEntity.getStatus()) > 1) {
                        setStatus(pushEntity, alarmId);
                        pushEntity.setScriptId(scriptId);
                        pushEntity.setConfigId(configId);
                        // 脚本数据如果没有指定IP参数,就为本机的脚本内容
                        if (pushEntity.getIp() == null) {
                            pushEntity.setServer(getHosts());
                        } else {
                            pushEntity.setServer(getServerId(pushEntity));
                        }
                        ALARM_MAP.put(alarmId, value + 1);
                        ALARM_INTERVAL.put(alarmIdTime, DateUtil.getCurrTime());
                        info(isDebug ? "ALARM_INTERVAL " + command.toString() + " " + alarmId + " retry put time -> " + DateUtil.getCurrTime() : null);
                        SCRIPT_STATUS.put(alarmId, pushEntity);
                    }
                }
            } else {

                // 判断报警间隔
                long currTime = DateUtil.getCurrTime();

                if (!ALARM_INTERVAL.containsKey(alarmId)) {
                    logger.info("初始化 ALARM_INTERVAL ... ");
                    ALARM_INTERVAL.put(alarmId, currTime);
                }

                int alarmInterval;
                if (entity.getMonitorConfigureTp().equals("item")) {
                    alarmInterval = entity.getAlarmInterval() * 60;
                } else {
                    MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(id);
                    alarmInterval = itemEntity.getAlarmInterval() * 60;
                }
                if (ALARM_COUNT == null || ALARM_COUNT.size() < 1) {
                    info(isDebug ? "没有ALARM_COUNT, 退出ALARM_COUNT" : null);
                    return;
                }

                info(isDebug ? "获取到报警间隔为:" + alarmInterval : null);
                if (ALARM_COUNT.get(alarmId) == null) {
                    info(isDebug ? "ALARM_COUNT is null ... " + alarmId : null);
                    continue;
                }
                long lastSendTime;
                lastSendTime = ALARM_INTERVAL.get(alarmId);
                info(isDebug ? "获取到ALARM_COUNT 是" + ALARM_COUNT.get(alarmId) + "" : null);
                currTime = DateUtil.getCurrTime();
                if (currTime - lastSendTime < alarmInterval && currTime > lastSendTime) {
                    // 第一次不记数，直接发送
                    if (alarmInterval > 0 && retry > 0) {
                        info(isDebug ? "不能发送报警信息,报警间隔太短:" + alarmId + " 还差 " + (alarmInterval - (currTime - lastSendTime)) : null);
                        continue;
                    }
                }
//                currTime = DateUtil.getCurrTime();
//                lastSendTime = ALARM_INTERVAL.get(alarmId);
//                if (currTime - lastSendTime > alarmInterval) {
//                    info(isDebug ? "初始化时间报警记数器:" + alarmId : null);
//                    ALARM_INTERVAL.put(alarmId, currTime);
//                }
//
                // 报警次数记数, 每次加一
                if (ALARM_COUNT.containsKey(alarmId) && ALARM_COUNT.get(alarmId) >= 1) {
                    info(isDebug ? "ALARM_COUNT add number " + ALARM_COUNT.get(alarmId) : null);
                    ALARM_COUNT.put(alarmId, ALARM_COUNT.get(alarmId) + 1);
                }

                if (ALARM_COUNT.get(alarmId) == 0) {
                    info(isDebug ? "ALARM_COUNT id is continue " + ALARM_COUNT.get(alarmId) : null);
                    continue;
                }

                info(isDebug ? "alarm count to number " + ALARM_COUNT.get(alarmId) : null);
                int alarmCount = 1;
                if (entity.getMonitorConfigureTp().equals("item")) {
                    alarmCount = entity.getAlarmCount();
                } else {
                    MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(id);
                    alarmCount = itemEntity.getAlarmCount();
                }

                // 判断报警次数
                if (ALARM_COUNT.get(alarmId) >= alarmCount + 2) {
                    if (alarmCount > 1) {
                        info(isDebug ? "alarm count to max " : null);
                        continue;
                    }
                }

                if (retry == 0 && alarmCount > 0 ) {
                    logger.info("获取到重试次数为0，删除ALARM_COUNT,跳出检查");
                    ALARM_COUNT.remove(alarmId);
                    continue;
                }

                if (SCRIPT_STATUS.containsKey(alarmId)) {
                    //  发送报警信息
                    info(isDebug ? "添加报警到队列啦... " + alarmId : null);
                    info(isDebug ? "添加到报警队列script_status" + gson.toJson(SCRIPT_STATUS.get(alarmId)) : null);
                    info(isDebug ? "初始化时间报警记数器:" + alarmId : null);
                    ALARM_INTERVAL.put(alarmId, DateUtil.getCurrTime());
                    queue.add(SCRIPT_STATUS.get(alarmId));
                }

                if (retry == 0) {
                    logger.info("获取到重试次数为0，删除SCRIPT_STATUS");
                    SCRIPT_STATUS.remove(alarmId);
                } else {
                    ALARM_LAST_TIME.put(alarmId, DateUtil.getCurrTime());
                }
            }
        }
    }

    /**
     * 报警队列检查
     * 将报警的消息添加到redis队列中
     */
    void checkAlarmQueue() {
        if (!IS_MONITOR) {
            return;
        }
        if (queue == null) {
            return;
        }
        initTimeMap("checkAlarmQueue");
        if (checkTimeMap("checkAlarmQueue", 10)) {
            PushEntity entity = queue.poll();
            if (entity != null) {
                int scriptId = Integer.valueOf(entity.getScriptId());
                int status = Integer.valueOf(entity.getStatus());
                info(isDebug ? "开始设置队列啦 " + entity.getConfigId() + "_" + scriptId + " " + status : null);
                pushMessages(scriptId, status, entity);
            }
            putTimeMap("checkAlarmQueue");
        }
    }


    /**
     * 检查为加入到监控的机器，再配置完监控后，生效配置
     */
    void checkMonitorIsValid() {
        initTimeMap("checkMonitorIsValid");
        if (checkTimeMap("checkMonitorIsValid", 40)) {
            if (!IS_DEFAULT) {
                info(isDebug ? "开始检查是否有监控" : null);
                getHostConfig();
                if (IS_DEFAULT) {
                    info(isDebug ? "检查到该主机可以监控" : null);
                    initMonitor();
                }
            }
            putTimeMap("checkMonitorIsValid");
        }
    }

    /**
     * 每一天初始化一次监控数据
     */
    void setClearMonitorTime() {
        initTimeMap("setClearMonitorTime");
        if (!timeMap.containsKey("setClearMonitorTime")) {
            putTimeMap("setClearMonitorTime");
            init();
        }
        if (checkTimeMap("setClearMonitorTime", 86400)) {
            logger.info("初始化监控数据");
            initMonitor();
            putTimeMap("setClearMonitorTime");
        }
    }

    /**
     * 每5分钟
     * 重新设置redis里的数据
     * 检查脚本是否有更新
     */
    @Scheduled(cron = "0 */5 * * * ?")
    void resetMonitorTime() throws Exception {
        if (!IS_MONITOR) {
            return;
        }

        info(isDebug ? "resetMonitorTime .. start" : null);
        Random random = new Random();
        // 防止重复执行
        Thread.sleep(random.nextInt(100) * 300);
        Jedis jedis = redisUtil.getJedis();
        String queueKey = app + "_" + MonitorCacheConfig.cacheHostUpdateQueue + getHosts();
        info(isDebug ? "queue key" +  queueKey : null);
        long len = jedis.llen(queueKey);

        info(isDebug ? "获取到队列大小 " + len : null);
        for (int i = 0; i < len; i++) {
            String data = jedis.rpop(queueKey);
            info(isDebug ? "获取到更新状态 " + data : null);
            if (data != null) {
                // 有更新的化就更新所有信息
                initMonitor();
            }
        }
        jedis.close();
        info(isDebug ? "resetMonitorTime .. end" : null);
    }

    /**
     * 初始化时间
     * @param key
     */
   static void initTimeMap(String key){
        if (timeMap == null){
            timeMap = new HashMap<>();
        }
        if (!timeMap.containsKey(key)){
            timeMap.put(key, DateUtil.getCurrTime());
        }
    }

    /**
     * 设置时间
     * @param key
     */
    static void putTimeMap(String key){
        timeMap.put(key, DateUtil.getCurrTime());
    }

    /**
     * 判断时间是否到期
     * @param key
     * @param expired
     * @return
     */
    static boolean checkTimeMap(String key, int expired){
        if (DateUtil.getCurrTime() - timeMap.get(key) >= expired){
            return true;
        }
        return false;
    }

    /**
     * 每15秒更新一次
     * 设置agent是存活的
     */
//    @Scheduled(cron = "*/5 * * * * ?")
    void setHostActive() {
        if (!IS_MONITOR && !IS_DEFAULT) {
            return;
        }
        initTimeMap("setHostActive");
        if (checkTimeMap("setHostActive", 10)) {
            String key = MonitorCacheConfig.cacheHostIsUpdate.concat(getHosts());
            redisUtil.setex(key, 86400, DateUtil.getCurrTime().toString());
            putTimeMap("setHostActive");
        }
    }

    /**
     * @param hosts
     *
     * @return
     */
    static List<String> getHostStatus(List<String> hosts, boolean isAlarm) {
        return MonitorUtil.getHostStatus(hosts, isAlarm);
    }

    /**
     * 设置每个agent的报警信息, map
     *
     * @param hosts
     */
    static void setAgentAlarmStatus(List<String> hosts) {
        if (AGENT_ALARM_MAP == null) {
            logger.info("初始化AGENT_ALARM_MAP");
            AGENT_ALARM_MAP = new HashMap<>();
        }
        List<String> list = getHostStatus(hosts, true);
        int count = 0;
        String host;
        for (String alarm : list) {
            host = hosts.get(count);
            count += 1;
            if (alarm != null && alarm.length() > 0) {
                logger.info(host + "is alarmis alar " + alarm);
                AGENT_ALARM_MAP.put(host, alarm);
            }
        }
    }


    /**
     * @param messages
     */
    static void pushMessages(String messages) {

        // 报警开关
        String alarmSwitch = redisUtil.get(MonitorCacheConfig.cacheAlarmSwitch);
        if (alarmSwitch != null && alarmSwitch.length() > 0 && !alarmSwitch.equals("1")) {
            logger.info("报警开关关闭,不能发送报警信息...");
            return;
        }

        logger.info("发送报警消息为: " + messages);
        // 报警发送到redis队列里
        boolean isOk = false;
        for (int i = 0; i < 4; i++) {
            Long push = redisUtil.lpush(MonitorCacheConfig.cacheAlarmQueueKey, messages);
            if (push > 0) {
                isOk = true;
                break;
            }
        }
        // 通知服务端做报警处理
        String url = "/monitor/api/send/messages";
        if (isOk) {
            logger.info("发送报警消息" + HttpSendUtil.sendPost(url, "") + " " + url);
        } else {
            sendPostMessages(messages, url);
        }
    }

    /**
     * 获取agent报警的信息
     *
     * @param host
     *
     * @return
     */
    static MonitorMessagesEntity getAgentMessages(String host) {
        MonitorMessagesEntity messagesEntity = new MonitorMessagesEntity();
        messagesEntity.setServerId(Integer.valueOf(host));
        messagesEntity.setMessagesTime(DateUtil.getTimeStamp());
        messagesEntity.setScriptsId(0);
        String alarmGroup = getAdminGroup();
        messagesEntity.setMobile(getContact(alarmGroup, "mobile"));
        messagesEntity.setWeixin(getContact(alarmGroup, "weixin"));
        messagesEntity.setDing(getContact(alarmGroup, "ding"));
        String emailAddress = getContact(alarmGroup, "email");
        messagesEntity.setEmail(emailAddress);
        return messagesEntity;
    }


    /**
     * 发送agent报警信息
     *
     * @param host
     */
    static void sendAgentAlarm(String host) {

        String server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + host);

        // 如果ping失败，属于严重，发送到所有联系方式到管理员组
        String ping = Ping.execPing(server);

        if (!ping.toUpperCase().contains("TTL")) {
            String alarmKey = MonitorCacheConfig.cacheAgentAlarmNumber + host;
            String alarmTimeKey = MonitorCacheConfig.cacheAgentAlarmNumber + host + "_time";
            String alarmNumber = redisUtil.get(alarmKey);
            String alarmTimeData = redisUtil.get(alarmTimeKey);
            MonitorMessagesEntity messagesEntity = getAgentMessages(host);
            messagesEntity.setSevertityId(2);
            messagesEntity.setAlarmCount(1);
            messagesEntity.setIp(server);
            if (alarmNumber != null) {
                if (Integer.valueOf(alarmNumber) >= 4) {
                    info(isDebug ? "agent check 报警超过4次" : null);
                    return;
                } else {
                    if (alarmTimeData != null && DateUtil.getCurrTime() - Integer.valueOf(alarmTimeData) > 600) {
                        messagesEntity.setAlarmCount(Integer.valueOf(alarmNumber) + 1);
                        alarmNumber = Integer.valueOf(alarmNumber) + 1 + "";
                        redisUtil.setex(alarmKey, 86400, alarmNumber);
                        redisUtil.setex(alarmTimeKey, 86400, DateUtil.getCurrTime() + "");
                    } else {
                        info(isDebug ? "agent check 报警间隔不够" : null);
                        return;
                    }
                }
            } else {
                alarmNumber = "1";
                redisUtil.setex(alarmKey, 86400, 1 + "");
                redisUtil.setex(alarmTimeKey, 86400, DateUtil.getCurrTime() + "");
            }
            String hostname = MonitorUtil.getIpHostName(server);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("agent check 报警:").append(alarmNumber).append(" ping ").append(server).append(" ").append(hostname).append(" ").append(ping).append(" ").append(DateUtil.getDate("yyyy-MM-dd HH:mm:ss")).append("检查服务器:").append(IpUtil.getHostname());
            messagesEntity.setMessages(stringBuilder.toString());
            pushMessages(gson.toJson(messagesEntity));
        }
    }


    /**
     * @param groupsId
     * @param jedis
     *
     * @throws Exception
     */
    static void checkHostIsUpdate(String groupsId, Jedis jedis) throws Exception {
        Map<String, String> okMap = new HashMap<>();
        Map<String, String> faildMap = new HashMap<>();
        List<String> hostList = MonitorUtil.getHosts(groupsId);
        setAgentAlarmStatus(hostList);
        String host;
        int count = 0;
        info(isDebug ? "hostList: " + gson.toJson(hostList) : null);
        boolean skip = false;
        for (String date : getHostStatus(hostList, false)) {
            host = hostList.get(count);
            count += 1;
            info(isDebug ? host + " " + date : null);
            if (date == null || date.length() < 2) {
                continue;
            }
            Long currDate = DateUtil.getCurrTime();
            Long lastDate = Long.valueOf(date);
            // 防止本机时间差距太大
            if (currDate - lastDate >= 240) {
                if (skip){
                    info(isDebug ? "跳出agent检查 "  : null);
                    continue;
                }

                // 重试8次，每隔5秒
                String key = RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIsUpdate + host;
                for (int retry = 0; retry < 8; retry++) {
                    String hostDate = jedis.get(key);
                    logger.info("重试检查agent " + retry + " " + host);
                    if (hostDate != null && hostDate.length() > 1) {
                        if (DateUtil.getCurrTime() - Long.valueOf(hostDate) > 300) {
                            faildMap.put(host, date);
                        } else {
                            okMap.put(host, date);
                            faildMap.remove(host);
                            continue;
                        }
                    }
                    Thread.sleep(5000);
                }

                info(isDebug ? "host date " + date : null);
                faildMap.put(host, date);

                logger.info("获取到监控超时服务..." + host);
                // 异常数据放到web页面
                // 报警信息生成
                logger.info("检查到agent失败了，开始检查ping");
                sendAgentAlarm(host);
                // 每次就检查一个报错
                skip = true;
                continue;
            } else {
                info(isDebug ? "set is ok " + host : null);
                okMap.put(host, date);
                info(isDebug ?  "agent_alarm_map :" + gson.toJson(AGENT_ALARM_MAP) : null);
                if (AGENT_ALARM_MAP.containsKey(host)) {
                    AGENT_ALARM_MAP.remove(host);
                    String server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + host);
                    String hostname = MonitorUtil.getIpHostName(server);
                    if (server == null || server.length() < 5) {
                        continue;
                    }
                    MonitorMessagesEntity messagesEntity = getAgentMessages(host);
                    messagesEntity.setSevertityId(1);
                    messagesEntity.setAlarmCount(0);
                    messagesEntity.setIp(server);
                    String key = MonitorCacheConfig.cacheAgentAlarmNumber + host;
                    String result = redisUtil.get(key);
                    if (result != null && result.length() > 0) {
                        redisUtil.del(key);
                        messagesEntity.setMessages("agent 检查 ping 恢复  " + server + " " + hostname + " " + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + " agent server " + IpUtil.getHostname());
                        pushMessages(gson.toJson(messagesEntity));
                    }
                }
            }
        }
        redisUtil.setex(MonitorCacheConfig.cacheAgentIsOk + "_" + groupsId, 300, gson.toJson(okMap));
        redisUtil.setex(MonitorCacheConfig.cacheAgentUnreachable + "_" + groupsId, 300, gson.toJson(faildMap));
    }


    /**
     * agent检查 60分钟一次
     */
    void checkAgentStatus(){

        initTimeMap("checkAgentStatus");
        if (checkTimeMap("checkAgentStatus", 60)) {
            if (executor != null){
                info(isDebug ? "开始检查agent线程启动" : null);
                AgentPingThread agentPingThread = new AgentPingThread();
                executor.execute(agentPingThread);
                info(isDebug ? "开始检查agent线程结束" : null);
            }
            putTimeMap("checkAgentStatus");
        }
    }

    /**
     * 分布式检查监控agent是否存活，如果5分钟内没有
     * 执行数据上报，就判断agent已经死亡，并将报警信息
     * 发送给web页面，恢复后从web页面删除, 报警信息发送
     * 给系统管理员
     */
    public static void checkMonitorUpdate() throws Exception {
        if (!IS_MONITOR && !IS_DEFAULT) {
            return;
        }
        initTimeMap("agentCheckTime");
        if (checkTimeMap("agentCheckTime", 60)) {
            putTimeMap("agentCheckTime");
        } else {
            return;
        }
        String isCheck = Configure.get("agentActiveCheck");
        if (isCheck.equals("false")) {
            return;
        }
        Map<String, String> groupsMap = MonitorUtil.getGroups();
        List<String> groupsList = new ArrayList<>();
        String[] groupsKey = new String[groupsMap.size()];
        int groupsCount = 0;
        for (Map.Entry<String, String> entry : groupsMap.entrySet()) {
            groupsList.add(entry.getKey());
            groupsKey[groupsCount] = RedisUtil.app + "_" + MonitorCacheConfig.cacheCheckMonitorLock + entry.getKey();
            groupsCount += 1;
        }
        Jedis jedis = redisUtil.getJedis();
        logger.info("mget " + gson.toJson(groupsKey));
        List<String> groupsData = jedis.mget(groupsKey);
        long start = DateUtil.getCurrTime();
        groupsCount = 0;
        for (String result : groupsData) {
            info(isDebug ? "开始检查agent是否存活" : null);
            String groupsId = groupsList.get(groupsCount);
            groupsCount += 1;
            CACHE_CHECK_MONITOR_LOCK = false;
            String lockKey = MonitorCacheConfig.cacheCheckMonitorLock + groupsId;
            if (result == null || result.length() < 3) {
                MonitorLockEntity monitorLockEntity = new MonitorLockEntity();
                monitorLockEntity.setServerId(getHosts());
                monitorLockEntity.setCheckTime(DateUtil.getCurrTime());
                redisUtil.setex(lockKey, 60, gson.toJson(monitorLockEntity));
                CACHE_CHECK_MONITOR_LOCK = true;
            }
            if (!CACHE_CHECK_MONITOR_LOCK) {
                continue;
            }
            if (DateUtil.getCurrTime() - start > 60) {
                return;
            }
            checkHostIsUpdate(groupsId, jedis);
        }
        jedis.close();
    }

    /**
     * 获取和设置本机所属的组
     */
    void setMonitorGroups() {
        String result;
        HashSet<Double> groups;
        String id = HOST_IDS;
        result = redisUtil.get(MonitorCacheConfig.cacheHostGroupsKey + id);

        info(isDebug ? "get groups data " + result : null);
        if (result != null && result.length() > 0) {
            groups = gson.fromJson(result, HashSet.class);
            HOST_GROUPS.put(id, groups);
            try {
                for (Double g : groups) {
                    GROUPS_IDS.add(new Double(g).intValue() + "");
                }
            } catch (Exception e) {
                logger.error("groups id error", e);
            }
        } else {
            logger.error("host id " + id + " groups  not found from redis");
        }
    }

    /**
     * 获取每个host和group拥有的所有配置文件的id
     */
    HashSet<String> setHostGroupsConfigs(String key, String id, HashSet hostConfigs) {
        hostConfigs.add("0");
        info(isDebug ? "获取配置文件..." : null);
        if (!IS_DEFAULT) {
            info(isDebug ? "除默认配置外，没有找到已配置的项目,删除配置.." : null);
            if (!id.equals("0")) {
                redisUtil.del(key + id);
            }
        } else {
            String result = redisUtil.get(key + id);
            info(isDebug ? "setHostGroupsConfigs" + result : null);
            if (result != null && result.length() > 0) {
                HashSet<String> configs = gson.fromJson(result, HashSet.class);
                if (configs != null) {
                    for (String c : configs) {
                        hostConfigs.add(c);
                    }
                }
            }
        }
        return hostConfigs;
    }

    /**
     * 获取每个host和group拥有的所有配置文件的id
     */
    void getHostGroupsConfigs() {
        hostConfigs = setHostGroupsConfigs(MonitorCacheConfig.cacheHostConfigKey, HOST_IDS, hostConfigs);
        for (String id : GROUPS_IDS) {
            groupsConfigs = setHostGroupsConfigs(MonitorCacheConfig.cacheGroupConfigKey, id, groupsConfigs);
        }
    }

    /**
     * 设置默认配置
     *
     * @return
     */
    MonitorConfigureEntity getMonitorConfig() {
        return MonitorUtil.getMonitorConfig();
    }

    /**
     * @param hConfigs
     * @param id
     * @param key
     * @param hostConfigs
     *
     * @return
     */
    Map setMonitorConfigs(Map<String, HashSet<MonitorConfigureEntity>> hConfigs, String id, String key, HashSet<String> hostConfigs) {
        HashSet<MonitorConfigureEntity> monitorConfigureEntities = new HashSet<>();
        for (String cid : hostConfigs) {
            StringBuilder configString = new StringBuilder();
            configString.append(key).append(id).append("_").append(cid);
            String configId = redisUtil.get(configString.toString());
            info(isDebug ? "get config " + id + " " + cid + " " + configId : null);
            if (configId != null && configId.length() > 0) {
                if (!IS_DEFAULT) {
                    info(isDebug ? "除默认配置外，没有找到已配置的项目,删除配置.." : null);
                    redisUtil.del(MonitorCacheConfig.cacheHostConfigKey + id);
                    continue;
                }
                configId = configId.replace("\"", "");
                String config = redisUtil.get(MonitorCacheConfig.cacheConfigureKey + configId);
                if (config == null || config.length() < 1) {
                    info(isDebug ? "获取到失败的配置,跳出: " + configId : null);
                    continue;
                }

                info(isDebug ? "get config data configId " + configId + " " + config : null);
                MonitorConfigureEntity configureEntity = gson.fromJson(config, MonitorConfigureEntity.class);
                if (!hConfigs.containsKey(id)) {
                    monitorConfigureEntities = new HashSet<>();
                } else {
                    monitorConfigureEntities = hConfigs.get(id);
                }
                // 配置文件按id存放
                CONFIGS.put(configId, configureEntity);
                // 主机的所属业务线
                if (configureEntity != null && configureEntity.getGroupsId().length() > 0) {
                    HOST_GROUP = configureEntity.getGroupsId();
                }

                monitorConfigureEntities.add(configureEntity);
                hConfigs.put(id, monitorConfigureEntities);
            } else {
                logger.error("configs " + id + " " + cid + " not found from redis");
                // 添加默认配置
                if (!monitorConfigureEntities.contains(getMonitorConfig())) {
                    info(isDebug ? "添加默认配置..." : null);
                    monitorConfigureEntities.add(getMonitorConfig());
                    hConfigs.put(id, monitorConfigureEntities);
                }
            }
        }
        return hConfigs;
    }

    /**
     * 获取本机的监控数据
     */
    void getMonitorConfigure() {
        HOST_CONFIGS = setMonitorConfigs(HOST_CONFIGS, HOST_IDS, MonitorCacheConfig.cacheHostCnfigureKey, hostConfigs);
        for (String id : GROUPS_IDS) {
            GROUP_CONFIGS = setMonitorConfigs(GROUP_CONFIGS, id, MonitorCacheConfig.cacheGroupsConfigureKey, groupsConfigs);
        }
    }

    /**
     * @param temp
     * @param items
     * @param configId
     * @param monitorConfigureEntity
     */
    void setItems(String temp, HashSet items, int configId, MonitorConfigureEntity monitorConfigureEntity) {
        String templateResult = redisUtil.get(MonitorCacheConfig.cacheTemplateKey + temp);
        if (templateResult != null && templateResult.length() > 1) {
            MonitorTemplateEntity templateEntity = gson.fromJson(templateResult, MonitorTemplateEntity.class);
            String itemsList = templateEntity.getItems();
            for (String item2 : itemsList.split(",")) {
                info(isDebug ? "通过模板配置item..." : null);
                items.add(configId + "_" + item2);
                SCRIPT_ITEM_CONFIG.put(item2, monitorConfigureEntity);
            }
        }
        // 设置默认监控项目
        info(isDebug ? "获取默认项目" : null);
        String defaultItems = redisUtil.get(MonitorCacheConfig.cacheIsDefault);
        info(isDebug ? "获取到defaultItems " + defaultItems : null);
        if (defaultItems != null && defaultItems.length() > 0) {
            ArrayList<String> list = gson.fromJson(defaultItems, ArrayList.class);
            MonitorConfigureEntity configureEntity = getMonitorConfig();
            info(isDebug ? "添加默认配置..." : null);
            for (String item : list) {
                items.add("0_" + item);
                CONFIGS.put("0", configureEntity);
                SCRIPT_ITEM_CONFIG.put(item, configureEntity);
            }
        }
    }

    /**
     * 获取项目的配置信息
     */
    HashSet<String> setItemConfigure(Map<String, HashSet<MonitorConfigureEntity>> map, HashSet items) {
        String item;
        for (Map.Entry<String, HashSet<MonitorConfigureEntity>> entry : map.entrySet()) {
            HashSet<MonitorConfigureEntity> entities = entry.getValue();
            for (MonitorConfigureEntity monitorConfigureEntity : entities) {
                if (monitorConfigureEntity != null) {
                    int configId = monitorConfigureEntity.getConfigureId();
                    // 添加未使用模板的项目
                    String configType = monitorConfigureEntity.getMonitorConfigureTp();

                    if (configType.equals("item")) {
                        item = monitorConfigureEntity.getItemId();

                        info(isDebug ? "获取到监控项目, ID:" + configId + "_" + item : null);

                        items.add(configId + "_" + item);

                        // 存储每个ITEM的配置文件
                        SCRIPT_ITEM_CONFIG.put(item, monitorConfigureEntity);
                    }

                    // 添加模板里的项目
                    if (configType.equals("template")) {
                        String templates = monitorConfigureEntity.getTemplateId();
                        info(isDebug ? "获取到templates " + templates : null);
                        if (templates != null) {
                            String[] temps = templates.split(",");
                            for (String temp : temps) {
                                if (temp.length() > 0) {
                                    setItems(temp, items, configId, monitorConfigureEntity);
                                }
                            }
                        }
                    }
                }
            }
        }
        return items;
    }

    /**
     * 获取拥有的项目的信息
     */
    void getItemConfigure() {
        HOST_ITEMS = setItemConfigure(HOST_CONFIGS, HOST_ITEMS);
        GROUP_ITEMS = setItemConfigure(GROUP_CONFIGS, GROUP_ITEMS);
    }

    /**
     * 获取每个项目的配置信息
     */
    Map<String, HashSet<MonitorItemEntity>> setItemConfigs(Map<String, HashSet<MonitorItemEntity>> itemConfigs, HashSet<String> items) {
        HashSet<MonitorItemEntity> itemsConfig;
        for (String ids : items) {
            String id = ids.split("_")[1];
            info(isDebug ? "get item configur " + id : null);
            String result = redisUtil.get(MonitorCacheConfig.cacheItemKey + id);
            if (result != null && result.length() > 0) {
                MonitorItemEntity monitorItemEntity = gson.fromJson(result, MonitorItemEntity.class);
                if (!itemConfigs.containsKey(id)) {
                    itemsConfig = new HashSet<>();
                } else {
                    itemsConfig = itemConfigs.get(id);
                }
                if (monitorItemEntity != null) {
                    itemsConfig.add(monitorItemEntity);
                    itemConfigs.put(id, itemsConfig);
                    // 脚本对应的项目ID，然后发信息时

                    info(isDebug ? "set SCRIPT_ITEM " + monitorItemEntity.getScriptId() + " " + gson.toJson(monitorItemEntity) : null);

                    SCRIPT_ITEM.put(monitorItemEntity.getScriptId() + "", gson.toJson(monitorItemEntity));
                }
            } else {
                logger.error("item " + id + " not found from redis");
            }
        }
        return itemConfigs;
    }

    /**
     * 获取每个项目的配置信息
     */
    void setItemConfigs() {
        ITEM_CONFIGS = setItemConfigs(ITEM_CONFIGS, HOST_ITEMS);
        ITEM_CONFIGS = setItemConfigs(ITEM_CONFIGS, GROUP_ITEMS);
    }

    /**
     * 获取脚本信息
     */
    void setScripts() {
        for (String item : HOST_ITEMS) {
            info(isDebug ? "set scripts " + item : null);
            // 获取项目ID和配置ID
            try {
                String[] items = item.split("_");
                int itemId = Integer.valueOf(items[1]);
                String configId = items[0];
                for (Map.Entry<String, HashSet<MonitorItemEntity>> entry : ITEM_CONFIGS.entrySet()) {
                    HashSet<MonitorItemEntity> entryValues = entry.getValue();
                    for (MonitorItemEntity monitorItemEntity : entryValues) {
                        if (monitorItemEntity != null && monitorItemEntity.getItemId().equals(itemId)) {
                            int scriptId = monitorItemEntity.getScriptId();
                            String key = configId + "_" + scriptId;
                            SCRIPTS.add(key);
                            SCRIPT_TO_ITEM.put(key, monitorItemEntity);

                            info(isDebug ? "SCRIPTS add " + configId + "_" + scriptId : null);

                        }
                    }
                }
            }catch (Exception e){
                logger.error("项目配置失败", e);
                continue;
            }
        }
    }


    /**
     * 获取拥有脚本的脚本内容
     */
    void setScriptConfigs() {
        FileIoUtil.makeDir(tempDir);
        MonitorScriptsEntity monitorScriptsEntity;
        for (String ids : SCRIPTS) {
            String id = ids.split("_")[1];
            monitorScriptsEntity = getScripts(id);
            if (monitorScriptsEntity.getScriptsId() != null) {
                SCRIPT_CONFIGS.put(id + "", monitorScriptsEntity);
                info(isDebug ? tempDir + "" + id : null);
                FileIoUtil.writeFile(tempDir + "" + id, monitorScriptsEntity.getContent(), false);
                MonitorUtil.setFileExec(tempDir.concat(id));
            } else {
                logger.error("script " + id + " not found from redis");
            }
        }
    }

    /**
     * @param arg
     *
     * @return
     */
    String getArg(String arg) {
        arg = arg.replace("'", "\\'");
        if (arg.contains(" ")) {
            return " '" + arg + "' ";
        } else {
            return " " + arg + " ";
        }
    }

    /**
     * 获取脚本的参数
     *
     * @return
     */
    String getArg(MonitorConfigureEntity configureEntity, MonitorItemEntity itemEntity) {
        Gson gson = new Gson();
        String entityString;
        if (configureEntity != null) {
            entityString = gson.toJson(configureEntity);
        } else {
            entityString = gson.toJson(itemEntity);
        }
        MonitorConfigureEntity entity = gson.fromJson(entityString, MonitorConfigureEntity.class);
        String args = "";
        if (entity.getArg1() != null && entity.getArg1().length() > 0) {
            args += getArg(entity.getArg1());
        }
        if (entity.getArg2() != null && entity.getArg2().length() > 0) {
            args += getArg(entity.getArg2());
        }
        if (entity.getArg3() != null && entity.getArg3().length() > 0) {
            args += getArg(entity.getArg3());
        }
        if (entity.getArg4() != null && entity.getArg4().length() > 0) {
            args += getArg(entity.getArg4());
        }
        if (entity.getArg5() != null && entity.getArg5().length() > 0) {
            args += getArg(entity.getArg5());
        }
        if (entity.getArg6() != null && entity.getArg6().length() > 0) {
            args += getArg(entity.getArg6());
        }
        if (entity.getArg7() != null && entity.getArg7().length() > 0) {
            args += getArg(entity.getArg7());
        }
        if (entity.getArg8() != null && entity.getArg8().length() > 0) {
            args += getArg(entity.getArg8());
        }
        // 最后一个参数为IP地址
        for (String ip : LOCAL_IP) {
            args += " ".concat(ip);
        }
        return args;
    }

    /**
     * 脚本执行时间归类
     * 脚本执行的时间归类
     * 脚本执行时间要执行的脚本归类
     */
    void getScriptTime() {
        HashSet checkTime;
        for (String item : HOST_ITEMS) {
            // 获取项目ID和配置ID
            String[] items = item.split("_");
            int itemId = Integer.valueOf(items[1]);
            String configId = items[0];
            for (Map.Entry<String, HashSet<MonitorItemEntity>> entityEntry : ITEM_CONFIGS.entrySet()) {
                HashSet<MonitorItemEntity> entities = entityEntry.getValue();
                for (MonitorItemEntity entity : entities) {
                    // 比较项目id是否一致，不一致退出
                    if (!entity.getItemId().equals(itemId)) {
                        continue;
                    }

                    MonitorConfigureEntity configureEntity = CONFIGS.get(configId);
                    // 为模板时从item获取配置
                    checkTime = new HashSet();
                    if (configureEntity.getMonitorConfigureTp().equals("template")) {
                        info(isDebug ? "通过模板获取到item配置" + itemId : null);
                        SCRIPT_CHECK_INTERVAL.put(configureEntity.getConfigureId() + "_" + entity.getScriptId() + "", entity.getCheckInterval());
                        // 监控的时间,单位为秒
                        SCRIPT_TIME.add(entity.getCheckInterval());
                        if (SCRIPT_TIME_MAP.containsKey(entity.getCheckInterval() + "")) {
                            checkTime = SCRIPT_TIME_MAP.get(entity.getCheckInterval() + "");
                        }
                        info(isDebug ? "checkTime add 1 " + configId + "_" + entity.getScriptId() : null);
                        checkTime.add(configId + "_" + entity.getScriptId() + "");
                        // 每个map里存放每个时间点要执行的脚本id
                        SCRIPT_TIME_MAP.put(entity.getCheckInterval() + "", checkTime);
                        SCRIPT_RETRY_MAP.put(configureEntity.getConfigureId() + "_" + entity.getScriptId() + "", entity);
                    }
                    checkTime = new HashSet();
                    if (configureEntity.getMonitorConfigureTp().equals("item")) {
                        info(isDebug ? "通过item去配置SCRIPT_TIME_MAP" : null);
                        SCRIPT_TIME.add(configureEntity.getCheckInterval() + "");
                        info(isDebug ? "SCTIPT_TIME set " + gson.toJson(SCRIPT_TIME) : null);
                        if (SCRIPT_TIME_MAP.containsKey(configureEntity.getCheckInterval() + "")) {
                            checkTime = SCRIPT_TIME_MAP.get(configureEntity.getCheckInterval() + "");
                            info(isDebug ? "SCRIPT_TIME_MAP 包含 " + gson.toJson(checkTime) : null);
                        }
                        SCRIPT_CHECK_INTERVAL.put(configureEntity.getConfigureId() + "_" + entity.getScriptId() + "", configureEntity.getCheckInterval() + "");
                        info(isDebug ? "checkTime add 2 " + configId + "_" + entity.getScriptId() : null);
                        checkTime.add(configId + "_" + entity.getScriptId() + "");
                        SCRIPT_TIME_MAP.put(configureEntity.getCheckInterval() + "", checkTime);
                        MonitorItemEntity monitorItemEntity = new MonitorItemEntity();
                        monitorItemEntity.setAlarmCount(configureEntity.getAlarmCount());
                        monitorItemEntity.setRetry(configureEntity.getRetry());
                        monitorItemEntity.setAlarmInterval(configureEntity.getAlarmInterval());
                        SCRIPT_RETRY_MAP.put(configureEntity.getConfigureId() + "_" + entity.getScriptId() + "", monitorItemEntity);
                    }
                }
            }
        }
    }

    /**
     * 设置脚本的参数
     */
    void getScriptArgv() {
        for (String item : HOST_ITEMS) {
            // 获取项目ID和配置ID
            String[] items = item.split("_");
            int itemId = Integer.valueOf(items[1]);
            String configId = items[0];
            for (Map.Entry<String, HashSet<MonitorItemEntity>> entityEntry : ITEM_CONFIGS.entrySet()) {
                HashSet<MonitorItemEntity> entities = entityEntry.getValue();
                for (MonitorItemEntity entity : entities) {
                    if (!entity.getItemId().equals(itemId)) {
                        continue;
                    }
                    String scriptId = configId + "_" + entity.getScriptId();
                    MonitorConfigureEntity configureEntity = CONFIGS.get(configId);
                    String args;
                    if (configureEntity.getMonitorConfigureTp().equals("item")) {
                        info(isDebug ? "获取通过item配置的参数...." : null);
                        args = getArg(configureEntity, null);
                    } else {
                        info(isDebug ? "获取通过template配置的参数...." : null);
                        args = getArg(null, entity);
                    }
                    SCRIPT_ARGV.put(scriptId, args);
                    info(isDebug ? "SCRIPT_ARGV put " + scriptId + " " + args : null);
                }
            }
        }
    }

    /**
     * 获取主机ID
     *
     * @return
     */
    static String getHosts() {
        if (HOST_IDS != null) {
            return HOST_IDS;
        }
        return "";
    }

    /**
     * 获取脚本超时时间
     * @param id
     * @return
     */
    static Integer getTimeout(String id){
        info(isDebug ? "开始获取到超时时间; 脚本id "  + id : null);
        MonitorScriptsEntity entity = SCRIPT_CONFIGS.get(id);
        if (entity != null && entity.getTimeOut() != null && entity.getTimeOut().length() > 0 ){
            try {
                info(isDebug ? "获取到超时时间" + entity.getTimeOut()  : null);
                int timeOut =  Integer.valueOf(entity.getTimeOut());
                if (timeOut == 0){
                    return 8;
                }
                return timeOut;
            }catch (Exception e){
                if (isDebug){
                    logger.error("脚本时间获取失败", e);
                }
            }
        }
        info(isDebug ? "获取到默认超时时间8秒"  : null);
        return 8;
    }


    /**
     * 运行时间
     *
     * @param time
     * @param success
     * @param faild
     */
    public static void runScript(String time, ArrayList<PushEntity> success, ArrayList<PushEntity> faild) {
        String command;
        List<PushEntity> entitys;
        HashSet<String> scriptTime;
        Long lastTime;
        // 获取这个时间保护的时间

        info(isDebug ? "runScript " + time + " " + gson.toJson(SCRIPT_TIME_MAP) : null);
        scriptTime = SCRIPT_TIME_MAP.get(time);
        String lockKey ;
        Long checkInterval ;
        for (String id : scriptTime) {
            String[] ids = id.split("_");
            String s = ids[1];

            // 获取脚本的最近运行时间
            lastTime = SCRIPT_RUNTIME.get(id);
            if (lastTime == null) {
                lastTime = 1L;
            }
            long nowTime = DateUtil.getCurrTime();

            checkInterval = Long.valueOf(SCRIPT_CHECK_INTERVAL.get(id));
            lockKey = "locked".concat(id);
            if ((nowTime - lastTime) >= checkInterval - 1 ) {
                info(isDebug ? "获取配置文件信息" + ids[0] : null);

                if (MONITOR_LOCK.containsKey(lockKey) ) {
                    info(isDebug ? "MONITOR IS LOCK ....  ".concat(id) +" " + MONITOR_LOCK.get(lockKey): null);
                    continue;
                }else{
                    info(isDebug ? time.concat(" 添加锁 .... ".concat(id)): null);
                    MONITOR_LOCK.put(lockKey, nowTime);
                }

                if(MONITOR_LOCK.containsKey(lockKey) && (nowTime - MONITOR_LOCK.get(lockKey)) >= (checkInterval * 2)) {
                    info(isDebug ? time.concat(" 锁时间超过删除锁 .... ".concat(id)): null);
                    MONITOR_LOCK.remove(lockKey);
                }

                info(isDebug ? "SCRIPT_RUNTIME put " + id : null);
                SCRIPT_RUNTIME.put(id, DateUtil.getCurrTime());
                command = tempDir + s + SCRIPT_ARGV.get(id);
                info(isDebug ? "start exec script " + command : null);
                entitys = run(command, getTimeout(s));
                info(isDebug ? time.concat(" 脚本完成删除锁 .... ".concat(id)): null);
                MONITOR_LOCK.remove(lockKey);
                if (entitys == null) {
                    logger.info("执行脚本失败" + command);
                    continue;
                }
                StringBuilder alarmIdString;
                String alarmId;
                for (PushEntity entity : entitys) {
                    alarmIdString = new StringBuilder();
                    alarmIdString.append(id)
                            .append("_")
                            .append(getServerId(entity))
                            .append("_")
                            .append( entity.getGroups())
                            .append("_")
                            .append(entity.getName());
                    alarmId = alarmIdString.toString();
                    if (entity != null) {
                        entity.setCommand(s);
                        entity.setScriptId(s);
                        // 设置业务线
                        entity.setConfigId(ids[0]);
                        entity.setGroupsName(HOST_GROUP);
                        if (entity.getIp() == null) {
                            entity.setServer(getHosts());
                        } else {
                            entity.setServer(getServerId(entity));
                        }
                        // 画图的使用的 不做报警处理
                        if (entity.getStatus().equals("0")) {
                            info(isDebug ? "画图使用数据:" +  gson.toJson(entity) : null);
                            success.add(entity);
                        }
                        // 判断脚本运行状态成功
                        if (entity.getStatus().equals("1")) {
                            info(isDebug ? "status 1 PushEntity " + gson.toJson(entity) : null);
                            success.add(entity);
                            if (ALARM_COUNT.containsKey(alarmId)) {
                                if (getAlarmStatus(alarmId)) {
                                    ALARM_COUNT.remove(alarmId);
                                    info(isDebug ? "获取到报警状态为 " + entity.getStatus() + " ALARM_COUNT.remove " + alarmId : null);

                                    if (!ALARM_LAST_TIME.containsKey(alarmId)) {
                                        info(isDebug ? "初始化 ALARM_LAST_TIME ..." : null);
                                        ALARM_LAST_TIME.put(alarmId, DateUtil.getCurrTime());
                                    }
                                    //  发送报警信息, 报警后40秒内恢复,不发送恢复信息, 去掉多余的报警
                                    if (ALARM_LAST_TIME != null && DateUtil.getCurrTime() - ALARM_LAST_TIME.get(alarmId) > 40) {
                                        MonitorItemEntity monitorItemEntity = SCRIPT_TO_ITEM.get(s + "_" + ids[0]);
                                        if (monitorItemEntity != null) {
                                            if (monitorItemEntity.getIsRecover() == 1) {
                                                queue.add(entity);
                                                info(isDebug ? "添加恢复报警到队列啦...1" : null);
                                            }
                                        } else {
                                            queue.add(entity);
                                            info(isDebug ? "添加恢复报警到队列啦...2" : null);
                                        }
                                    } else {
                                        logger.info("恢复时间太短,跳出恢复报警...");
                                    }
                                    info(isDebug ? "remove ALARM_COUNT: " + alarmId : null);
                                }
                            }
                        } else {
                            // 统计非正常状态的信息
                            if (Integer.valueOf(entity.getStatus()) > 1) {
                                info(isDebug ? "获取到失败状态 " + gson.toJson(entity) + " " + alarmId : null);
                                faild.add(entity);
                                if (!ALARM_COUNT.containsKey(alarmId)) {
                                    info(isDebug ? "获取到报警状态为 " + entity.getStatus() + " init ALARM_COUNT 1 " + alarmId : null);
                                    ALARM_COUNT.put(alarmId, 1);
                                } else if (ALARM_COUNT.get(alarmId) == 0) {
                                    info(isDebug ? "获取到报警状态为 " + entity.getStatus() + " init ALARM_COUNT 2 " + alarmId : null);
                                    ALARM_COUNT.put(alarmId, 1);
                                }
                                SCRIPT_STATUS.put(alarmId, entity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 报警状态设置
     * 当报警超过配置的数值后进行报警
     *
     * @param type
     * @param ids
     */
    static void setAlarmMap(String type, String ids) {
        String id = ids + "_" + type;
        int old = 0;
        if (ALARM_MAP.containsKey(id)) {
            old = ALARM_MAP.get(id);
        }
        old += 1;
        ALARM_MAP.put(id, old);
        info(isDebug ? "setAlarmMap: " + ids +"  type:"+ type: null);
        if (!type.equals("ok")) {
            removeMap(ids.concat("_ok"));
        }
        if (!type.equals("faild")) {
            if (!type.equals("warning")) {
                removeMap(ids.concat("_faild"));
            }
        }
        if (!type.equals("warning")) {
            if (!type.equals("faild")) {
                removeMap(ids.concat("_warning"));
            }
        }
        if (!type.equals("unknown")) {
            removeMap(ids.concat("_unknown"));
        }
    }

    /**
     * @return
     */
    static int getAlarmCount(String id) {
        if (ALARM_COUNT.get(id) == null) {
            ALARM_COUNT.put(id, 0);
        }
        return ALARM_COUNT.get(id);
    }

    /**
     * 判断是否需要发送报警恢复信息
     *
     * @param id
     *
     * @return
     */
    static boolean getAlarmStatus(String id) {
        if (getAlarmCount(id) > 1) {
            return true;
        }
        return false;
    }

    /**
     * @param url
     * @param data
     */
   static void pushData(String url, String data) {
       if (getErrorNumber()){
           return;
       }
        info(HttpSendUtil.sendPost(url,"lentity=" + Base64Util.encode(data)));
    }

    /**
     * 上报监控结果
     *
     * @param entity
     */
    public static void pushMonitor(ArrayList<PushEntity> entity, String url, boolean status) {

        if (getErrorNumber()){
            return;
        }

        ArrayList ok = new ArrayList();
        ArrayList faild = new ArrayList();
        ArrayList unknown = new ArrayList();
        ArrayList warning = new ArrayList();

        info(isDebug ? entity.size() + "" : null);
        if (entity.size() > 0) {
            String data = gson.toJson(entity);
            if (data.length() < 5) {
                info(isDebug ? "获取到数据为空，退出pushMonitor" : null);
                return;
            }
            // 发送监控数据
            if (status) {
                // 前30次走http方式发送数据
                if (udpSendNumber > 20) {
                    if (SocketSendUtil.getServerListSize() > 0) {
                        info(isDebug ? "通过udp的socket端口上传数据 " : null);
                        SocketSendUtil.sendData(data);
                    } else {
                        pushData(url, data);
                    }
                } else {
                    udpSendNumber += 1;
                    pushData(url, data);
                }
                if (udpSendNumber > 50) {
                    udpSendNumber = 19L;
                }
            } else {
                // 发送失败的数据
                info(isDebug ? "上传失败的数据 " + data : null);
                pushData(url, data);
            }
            info(isDebug ? "entity size: " + entity.size() : null);

            for (PushEntity pushEntity : entity) {
                StringBuilder idString = new StringBuilder();
                idString.append(pushEntity.getConfigId())
                        .append("_")
                        .append(pushEntity.getScriptId())
                        .append("_")
                        .append(pushEntity.getServer())
                        .append("_")
                        .append(pushEntity.getGroups())
                        .append("_")
                        .append(pushEntity.getName());
                String id = idString.toString();
                switch (pushEntity.getStatus()) {
                    case "1":
                        ok.add(id);
                        setAlarmMap("ok", id);
                        if (getAlarmStatus(id)) {
                            //  发送报警信息
                            info(isDebug ? "remove ALARM_COUNT: " + id : null);
                            ALARM_COUNT.remove(id);
                            info(isDebug ? "添加恢复报警到队列啦..." : null);
                            queue.add(pushEntity);
                        }
                        break;
                    case "2":
                        // 获取到老的值，然后加一
                        setAlarmMap("faild", id);
                        faild.add(id);
                        break;
                    case "3":
                        setAlarmMap("warning", id);
                        warning.add(id);
                        break;
                    case "4":
                        setAlarmMap("unknown", id);
                        unknown.add(id);
                        break;
                    default:
                        break;
                }

                //  执行重试，根据配置的信息

            }
        }

        if (ok.size() > 0) {
            info(isDebug ? "set ok data " : null);
            setGroupsData("ok", ok);
        }
        if (faild.size() > 0) {
            info(isDebug ? "set faild data " : null);
            setGroupsData("faild", faild);
        }
        if (warning.size() > 0) {
            info(isDebug ? "set warning data " : null);
            setGroupsData("warning", warning);
        }
        if (unknown.size() > 0) {
            info(isDebug ? "set unknown data " : null);
            setGroupsData("unknown", unknown);
        }
        ok.clear();
        unknown.clear();
        warning.clear();
        faild.clear();
    }

    /**
     * @param pushEntity
     * @param id
     */
    void setStatus(PushEntity pushEntity, String id) {
        info(isDebug ? "setStatus pushEntity " + gson.toJson(pushEntity) : null);
        switch (pushEntity.getStatus()) {
            case "1":
                setAlarmMap("ok", id);
                break;
            case "2":
                // 获取到老的值，然后加一
                setAlarmMap("faild", id);
                break;
            case "3":
                setAlarmMap("warning", id);
                break;
            case "4":
                setAlarmMap("unknown", id);
                break;
            default:
                break;
        }
    }

    /**
     * @param contactsEntity
     * @param type
     *
     * @return
     */
    static HashSet getContact(MonitorContactsEntity contactsEntity, String type, HashSet contactSet) {
        switch (type) {
            case "mobile":
                if(contactsEntity.getMobile() != null ) {
                    contactSet.add(contactsEntity.getMobile());
                }
                break;
            case "email":
                if (contactsEntity.getMail() != null) {
                    contactSet.add(contactsEntity.getMail());
                }
                break;
            case "weixin":
                if (contactsEntity.getMobile() != null) {
                    contactSet.add(contactsEntity.getMobile());
                }
                break;
            case "ding":
                if (contactsEntity.getNo() != null) {
                    contactSet.add(contactsEntity.getNo());
                }
                break;
        }
        return contactSet;
    }

    /**
     * 获取联系人
     *
     * @return
     */
    static String getContact(String groups, String type) {
        if (groups == null || groups.length() < 1) {
            return "";
        }
        String[] g = groups.split(",");
        String contact = "";
        for (String group : g) {
            if (group.length() < 1) {
                continue;
            }
            String result = redisUtil.get(cacheContactGroupKey + group);
            if (result != null && result.length() > 0) {
                    MonitorContactGroupEntity entity = gson.fromJson(result, MonitorContactGroupEntity.class);
                    contact += entity.getMember().concat(",");
            }

        }

        logger.info("获取到联系组 " + contact);
        HashSet<String> contactSet = new HashSet();
        String[] contacts = contact.split(",");
        info(isDebug ? contacts.toString() : null);
        MonitorContactsEntity entity = new MonitorContactsEntity();
        for (String c : contacts) {
            if (c == null || c.length() < 1) {
                continue;
            }
            String result = redisUtil.get(cacheContactKey + c);
            if (result != null && result.length() > 0) {
                entity = gson.fromJson(result, MonitorContactsEntity.class);
            }
            if (entity != null) {
                contactSet = getContact(entity, type, contactSet);
            }
        }
        String result = "";
        for (String c : contactSet) {
            result += c .concat(",");
        }
        logger.info("获取到联系人 " + result);
        if (result.length() > 2) {
            return result.substring(0, result.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * @param pushEntity
     * @param entity
     *
     * @return
     */
    String getMessages(PushEntity pushEntity, MonitorMessagesEntity entity) {
        String description = ".";
        // 给有配置的添加配置里的描述信息
        try {
            MonitorConfigureEntity configureEntity = CONFIGS.get(pushEntity.getConfigId());
            description = configureEntity.getDescription();
        } catch (Exception e) {
            logger.error("没有获取到配置文件信息", e);
        }
        String messages = entity.getMessages();
        messages = messages.replace("${message}", entity.getAlarmCount() - 1 + " " + pushEntity.getMessages());
        messages = messages.replace("${groups}", "");
        if (pushEntity.getCommand() != null) {
            messages = messages.replace("${command}", pushEntity.getCommand());
        }
        if (pushEntity.getIp() != null) {
            messages = messages.replace("${server}", pushEntity.getIp() + " " + description);
        } else {
            for (String ip : LOCAL_IP) {
                messages = messages.replace("${server}", ip + " " + IpUtil.getHostname() + " " + description);
            }
        }
        messages = messages.replace("${time}", DateUtil.getDay());
        return messages;
    }

    /**
     * 生成报警信息
     *
     * @param scriptId
     * @param status
     */
    void pushMessages(int scriptId, int status, PushEntity pushEntity) {
        MonitorMessagesEntity entity = new MonitorMessagesEntity();
        entity.setScriptsId(scriptId);
        entity.setSevertityId(status);
        entity.setMessagesTime(DateUtil.getTimeStamp());
        if (pushEntity.getIp() != null) {
            entity.setIp(pushEntity.getIp());
        } else {
            for (String ip : LOCAL_IP) {
                entity.setIp(ip);
            }
        }
        entity.setValue(pushEntity.getValue());
        entity.setIndexName(pushEntity.getName());

        // 报警服务器设置
        int serverId = 0;
        serverId = Integer.valueOf(HOST_IDS);
        StringBuilder alarmBuilder = new StringBuilder();
        alarmBuilder.append(pushEntity.getConfigId()).append("_").append(scriptId).append("_").append(serverId).append("_").append(pushEntity.getGroups()).append("_").append(pushEntity.getName());
        String alarmId = alarmBuilder.toString();

        info(isDebug ? "pushMessages 获取到ALARM_COUNT id: " + alarmId : null);

        if (ALARM_COUNT.containsKey(alarmId)) {
            entity.setAlarmCount(ALARM_COUNT.get(alarmId));
        } else {
            entity.setAlarmCount(1);
        }
        MonitorItemEntity itemEntity = gson.fromJson(SCRIPT_ITEM.get(scriptId + ""), MonitorItemEntity.class);

        try {
            // 报警不恢复处理
            if (itemEntity.getIsRecover() == 0 && status == 1 ) {
                logger.info("获取到不需 要发送恢复的监控项目,退出报警恢复" + gson.toJson(pushEntity));
                return;
            }
        }catch (Exception e){
            logger.error("获取恢复值失败", e);
        }

        String recover = "";
        String message = "";
        if (itemEntity != null) {
            info(isDebug ? "获取到script_item:" + gson.toJson(itemEntity) : null);
            recover = itemEntity.getRecoverMessages();
            message = itemEntity.getAlarmMessages();
        } else {
            recover = "报警恢复了";
            message = "报警啦";
        }

        MonitorConfigureEntity configureEntity = CONFIGS.get(pushEntity.getConfigId());

        // 在项目中配置发送给管理员的项目全部发报警给管理员
        String adminGroup = getAdminGroup(itemEntity);
        String mobileGroups = configureEntity.getMobileGroups() + "," + adminGroup;
        String emailGroups = configureEntity.getEmailGroups() + "," + adminGroup;
        String dingGroups = configureEntity.getDingGroups() + "," + adminGroup;
        String weixinGroups = configureEntity.getWeixinGroups() + "," + adminGroup;
        String all = configureEntity.getAllGroups();
        String mobile = "";
        String email = "";
        String ding = "";
        String weixin = "";
        if (all != null && all.length() > 0) {
            mobile = getContact(all + "," + adminGroup, "mobile");
            email = getContact(all + "," + adminGroup, "email");
            ding = getContact(all + "," + adminGroup, "ding");
            weixin = getContact(all + "," + adminGroup, "weixin");
        } else {
            if (mobileGroups != null && mobileGroups.length() > 0) {
                mobile = getContact(mobileGroups, "mobile");
            }
            if (emailGroups != null && emailGroups.length() > 0) {
                email = getContact(emailGroups, "email");
            }
            if (dingGroups != null && dingGroups.length() > 0) {
                ding = getContact(dingGroups, "ding");
            }
            if (weixinGroups != null && weixinGroups.length() > 0) {
                weixin = getContact(weixinGroups, "weixin");
            }
        }

        // 设置报警对象
        if (weixin.length() > 0) {
            entity.setWeixin(weixin);
        }
        if (mobile.length() > 0) {
            entity.setMobile(mobile);
        }
        if (ding.length() > 0) {
            entity.setDing(ding);
        }
        if (email.length() > 0) {
            entity.setEmail(email);
        }

        if (status == 1) {
            entity.setMessages(recover);
        } else {
            entity.setMessages(message);
        }

        // 将没有ID或者是非本机的写入ip地址，发送报警使用
        if (pushEntity.getIp() != null) {
            entity.setIpAddress(pushEntity.getIp());
        }

        // 业务线设置
        int groupsId = 0;
        for (String m : GROUPS_IDS) {
            groupsId = Integer.valueOf(m);
        }
        entity.setGroupsId(groupsId);
        entity.setServerId(serverId);
        String messages = getMessages(pushEntity, entity);
        entity.setMessages(messages);
        pushMessages(gson.toJson(entity));
    }

    /**
     * 20170218
     *
     * @param command
     *
     * @return
     */
    static String runScript(String command, int timeOut) {
        info(isDebug ? "执行脚本超时时间为 " + timeOut : null);
        return CommandUtil.runScript(command, timeOut, executor);
    }

    /**
     * 脚本执行
     *
     * @param command
     *
     * @return
     */
    static List<PushEntity> run(String command, int timeOut) {
        String result;
        try {
            result = runScript(command, timeOut);
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(result, type);
            info(isDebug ? "脚本执行结果 " + result : null);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(command + " run faild");
        }
        return null;
    }

    /**
     * 清除过期的报警数据
     *
     * @param dataMap
     *
     * @return
     */
    static Map<String, String> clearExpiredMap(Map<String, String> dataMap) {
        if (dataMap == null) {
            return new HashMap<>();
        }
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String id = entry.getKey();
            String time = entry.getValue();
            if (DateUtil.getCurrTime() - Long.valueOf(time) > 3600) {
                logger.info("移除过期的报警数据 " + id);
            } else {
                newMap.put(id, time);
            }
        }
        return newMap;
    }

    /**
     * 业务线告警状态设置
     *
     * @param type
     * @param ids
     */
    static void setGroupsData(String type, ArrayList<String> ids) {
        String result = redisUtil.get(MonitorCacheConfig.cacheHostStatusMap.concat(HOST_IDS));
        Map<String, String> redisData = new HashMap<>();
        Map<String, String> okData = new HashMap<>();
        Map<String, String> faildData = new HashMap<>();
        Map<String, String> warningData = new HashMap<>();
        Map<String, String> unknownData = new HashMap<>();
        if (result != null && result.length() > 0) {
            Map<String, String> map = gson.fromJson(result, HashMap.class);
            okData = gson.fromJson(map.get("ok"), HashMap.class);
            faildData = gson.fromJson(map.get("faild"), HashMap.class);
            warningData = gson.fromJson(map.get("warning"), HashMap.class);
            unknownData = gson.fromJson(map.get("unknown"), HashMap.class);
        }
        okData = clearExpiredMap(okData);
        faildData = clearExpiredMap(faildData);
        warningData = clearExpiredMap(warningData);
        unknownData = clearExpiredMap(unknownData);
        Map dataMap = getGroupData(faildData, okData, warningData, unknownData, type, ids);
        redisData.put("ok", gson.toJson(dataMap.get("ok")));
        redisData.put("faild", gson.toJson(dataMap.get("faild")));
        redisData.put("warning", gson.toJson(dataMap.get("warning")));
        redisData.put("unknown", gson.toJson(dataMap.get("unknown")));
        redisUtil.setex(MonitorCacheConfig.cacheHostStatusMap.concat(HOST_IDS), 3600, gson.toJson(redisData));
        dataMap.clear();
        okData.clear();
        redisData.clear();
        unknownData.clear();
        warningData.clear();
    }
}