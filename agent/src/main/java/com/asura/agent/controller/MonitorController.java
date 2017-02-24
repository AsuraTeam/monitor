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
import com.asura.agent.util.Base64Util;
import com.asura.agent.util.DateUtil;
import com.asura.agent.util.FileIoUtil;
import com.asura.agent.util.HttpUtil;
import com.asura.agent.util.IpUtil;
import com.asura.agent.util.RedisUtil;
import com.asura.agent.util.network.Ping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

import static com.asura.agent.conf.MonitorCacheConfig.cacheContactGroupKey;
import static com.asura.agent.conf.MonitorCacheConfig.cacheContactKey;
import static com.asura.agent.util.HttpUtil.sendPost;
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

 * 监控程序上报，监控脚本执行
 * 2016-09-16 16:50:00 创建
 * 20170117 修改某类报警人为空报错异常
 *          修改配置修改由400秒改为200秒
 *          修改如果重试次数为0，报警不受时间限制，有问题就发送报警
 *
 * 20170120 修改ip地址太短，不能上报数据错误
 * 20170201 修改不能初始化监控数据bug
 *          修改缓存文件过期失败bug
 */
@RestController
@EnableAutoConfiguration
public class MonitorController {
    public static String separator = System.getProperty("file.separator");

    private final Logger logger = LoggerFactory.getLogger(MonitorController.class);

    private static RedisUtil redisUtil = new RedisUtil();
    private static Gson gson = new Gson();

    // 获取临时文件目录
    private static String tempDir = System.getProperty("java.io.tmpdir") + separator + "monitor" + separator;
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
    private static String successApiUrl;
    // 存放失败的接口
    private static String faildApiUrl;
    private static Runtime runtime = Runtime.getRuntime();

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
    // 存储联系组的联系人
    private static Map<String, String> CONTACT_GROUP;
    // 存储联系人的数据
    private static Map<String, MonitorContactsEntity> CONTACT;
    // 存放本地报警队列，每10秒检查一次,有的话就发送
    private static LinkedTransferQueue<PushEntity> queue;
    // 存放脚本运行间隔
    private static Map<String, Long> MONITOR_LOCK;
    // 存放最近报警时间，判断是否为闪断, 如果恢复和报警时间太短，不再发送恢复消息
    private static Map<String, Long> ALARM_LAST_TIME;
    // 存放参数中IP地址的
    private static Map<String, String> ARGV_HOST_MAP;
    // 存放每个脚本的检查间隔
    private static Map<String, String> SCRIPT_CHECK_INTERVAL;
    // 每个脚本的重试间隔
    private static Map SCRIPT_RETRY_MAP;
    // 存放系统启动后的计时数据,
    private static Long CLEAR_MONITOR_TIME;
    // 存放检查是否要更新的计时数据,每5分钟检查一次
    private static Long CHECK_DEFAULT_TIME;
    private static Long CHECK_UPDATE_TIME;
    private static Long UPDATE_SERVER_TIME;
    private static Long PUSH_SERVER_INFO_TIME;
    private static Long AGENT_CHECK_TIME;
    // 存放自己是否是分布式检查AGENT的机器
    private static boolean CACHE_CHECK_MONITOR_LOCK;
    // 存放每个脚本对应的ITEM配置
    private static Map<String, MonitorItemEntity> SCRIPT_TO_ITEM;

    // 存放是否有除4默认配置外的其他配置, true 是有的， false是没有的
    private static boolean IS_DEFAULT = false;

    // 存放实时执行的时间
    private static Map<String, Map> REAL_TIME_CACHE ;

    // 设置agent报警次数
    private static Map<String ,String> AGENT_ALARM_MAP;


    /**
     * 初始化系统变量
     */
    void init() {
        info("初始化系统变量.....");
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
        successApiUrl = Configure.get("successApiUrl");
        faildApiUrl = Configure.get("faildApiUrl");
        ALARM_MAP = new HashMap<>();
        ALARM_COUNT = new HashMap<>();
        ALARM_INTERVAL = new HashMap<>();
        SCRIPT_ITEM = new HashMap<>();
        SCRIPT_ITEM_CONFIG = new HashMap<>();
        CONTACT_GROUP = new HashMap<>();
        CONTACT = new HashMap<>();
        queue = new LinkedTransferQueue();
        SCRIPT_STATUS = new HashMap<>();
        MONITOR_LOCK = new HashMap<>();
        ALARM_LAST_TIME = new HashMap<>();
        ARGV_HOST_MAP = new HashMap<>();
        SCRIPT_CHECK_INTERVAL = new HashMap<>();
        SCRIPT_RETRY_MAP = new HashMap<>();
        SCRIPT_TO_ITEM = new HashMap<>();
        CACHE_CHECK_MONITOR_LOCK = false;
        IS_DEFAULT = false;
        if (CLEAR_MONITOR_TIME == null) {
            CLEAR_MONITOR_TIME = DateUtil.getCurrTime();
        }
        if(CHECK_DEFAULT_TIME==null){
            CHECK_DEFAULT_TIME = DateUtil.getCurrTime();
        }
        if (CHECK_UPDATE_TIME==null){
            CHECK_UPDATE_TIME = DateUtil.getCurrTime();
        }
        if(REAL_TIME_CACHE==null){
            REAL_TIME_CACHE = new HashMap<>();
        }
        if (UPDATE_SERVER_TIME==null){
            UPDATE_SERVER_TIME = DateUtil.getCurrTime();
        }
        if (PUSH_SERVER_INFO_TIME==null){
            pushServerInfo();
            PUSH_SERVER_INFO_TIME = DateUtil.getCurrTime();
        }
        if (AGENT_CHECK_TIME==null){
            AGENT_CHECK_TIME = DateUtil.getCurrTime();
        }
        setAgentServerInfo();
    }

    void info(String messages) {
        if (Configure.get("DEBUG").equals("true")) {
            logger.info(messages);
        }
    }

    /**
     * 缓存服务启动端口和主机名
     */
    void setAgentServerInfo(){

            Map map = new HashMap();
            map.put("port", IpUtil.getServerPort());
            map.put("hostname", IpUtil.getHostname());
            redisUtil.set(MonitorCacheConfig.cacheAgentServerInfo + HOST_IDS, gson.toJson(map));

    }

    /**
     * 获取IP地址的ID
     *
     * @param ip
     *
     * @return
     */
    String getHostId(String ip) {
        ip = ip.replace(",", "");
        ip = ip.replace("'", "");
        ip = ip.replace("\"", "");
        String result = redisUtil.get(MonitorCacheConfig.hostsIdKey + ip);
        return result;
    }

    /**
     * 获取有效的监控主机
     */
    HashSet getIsValidHosts() {
        // 获取自己是否有监控项目
        String allHosts = redisUtil.get(MonitorCacheConfig.cacheAllHostIsValid);
        info("get is configure host " + allHosts);
        HashSet hosts = gson.fromJson(allHosts, HashSet.class);
        return hosts;
    }

    /**
     * 获取自己是否可上线
     */
    void getHostConfig() {
        if (GROUPS_IDS==null){
            init();
        }
        // 获取自己是否有监控项目
        HashSet hosts = getIsValidHosts();
        for (String ip : IpUtil.getHostIP()) {
            String result = getHostId(ip);
            if (result != null && result.length() > 0) {
                HOST_IDS = result;
                logger.info("获取到本机IP地址 "+ ip + " id: " + result);
                LOCAL_IP.add(ip);
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
                info("set host groups is:" + group);
                HOST_GROUP = group;
                isMonitor = true;
                info("HOST_GROUP: " + HOST_GROUP);
            } else if (!isMonitor) {
                IS_DEFAULT = false;
                IS_MONITOR = false;
            }
        }
    }

    /**
     * 获取实时数据
     * 主要是性能参数的获取，传递脚本ID
     */
    @RequestMapping("/api/realtime")
    @ResponseBody
    public String  realtime(int scriptId) throws Exception{
        String result = "";
        Map dataMap = new HashMap();
        String id = String.valueOf(scriptId);
        boolean isCache = false;
        if (REAL_TIME_CACHE.containsKey(id)){
            Long time = (Long)REAL_TIME_CACHE.get(id).get("time");
            if (DateUtil.getCurrTime()-time<5){
                isCache = true;
            }
        }
        if(isCache ){
            result = (String)REAL_TIME_CACHE.get(id).get("data");
        }else {
            String cmd = tempDir + separator + String.valueOf(scriptId);
            result =  gson.toJson(run(cmd));
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
    String getServerId(PushEntity pushEntity) {
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
        info("ARGV_HOST_MAP " + ip + " " + ARGV_HOST_MAP.get(ip));
        return ARGV_HOST_MAP.get(ip);
    }

    /**
     * 每一分钟检查是否加到组里，主要防止没有加到cmdb的机器
     */
    @Scheduled(cron = "0 */2 * * * ?")
    void checkIsMonitor() {
        if (LOCAL_IP == null) {
            return;
        }
        if (!IS_MONITOR && !IS_DEFAULT) {
            for (String ip : LOCAL_IP) {
                String group = redisUtil.get(MonitorCacheConfig.getCacheHostGroupsKey + ip);
                if (group != null && group.length() > 0) {
                    logger.info("初始化加到cmdb的机器...");
                    initMonitor();
                }
            }
        }
    }

    /**
     * 检查配置更新,任何配置更新将重置监控
     * 主要是监控默认的配置
     */
    @Scheduled(cron = "*/10 * * * * ?")
    void checkConfigChange() {
        if (LOCAL_IP == null) {
            return;
        }
        if(DateUtil.getCurrTime() - CHECK_DEFAULT_TIME > 60) {
            String result = redisUtil.rpop(MonitorCacheConfig.cacheDefaultChangeQueue + HOST_IDS);
            if (result != null && result.length() > 0) {
                logger.info("检查到配置更新，开始更新监控...");
                initMonitor();
            }
            CHECK_DEFAULT_TIME = DateUtil.getCurrTime();
        }
    }

    /**
     * 检查是否有脚本要执行
     * 每5秒检查一次
     */
    @Scheduled(cron = "0/5 * * * * ?")
    void checkExecScript() throws Exception {
        if (!IS_MONITOR) {
            return;
        }
        Random random = new Random();

        // 检查是否初始化过
        if (INIT_TIME == 0) {
            // 初始化监控
            INIT_TIME = DateUtil.getCurrTime() / 1000;
            info("init monitor ....");
            initMonitor();
        }
        // 防止重复执行
        int sleep = random.nextInt(10) * 300;
        Thread.sleep(sleep);
        if (MONITOR_LOCK.containsKey("locked") && DateUtil.getCurrTime() - MONITOR_LOCK.get("locked") < 20) {
            info("MONITOR IS LOCK .... ");
            return;
        } else {
            MONITOR_LOCK.remove("locked");
        }

        // 如果没有开启监控，则不执行任何东西
        if (!IS_MONITOR) {
            return;
        }
        if (SCRIPT_TIME == null) {
            return;
        }
        MONITOR_LOCK.put("locked", DateUtil.getCurrTime());
        ArrayList<PushEntity> success = new ArrayList<>();
        ArrayList<PushEntity> faild = new ArrayList<>();
        info("SCRIPT_TIME " + gson.toJson(SCRIPT_TIME));
        for (String time : SCRIPT_TIME) {
            info("String time " + time);
            runScript(time, success, faild);
        }
        if (faild.size() > 0) {
            pushMonitor(faild, faildApiUrl);
        }
        if (success.size() > 0) {
            pushMonitor(success, successApiUrl);

        }
        MONITOR_LOCK.remove("locked");
    }

    /**
     * 删除MAP
     *
     * @param id
     */
    void removeMap(String id) {
        Iterator<String> iterator = ALARM_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(id)) {
                info("remove ALARM_MAP " + id);
                iterator.remove();
            }
        }
    }

    /**
     * 每30分钟设置一次
     */
    @Scheduled(cron = "*/59 * * * * ?")
    void  setAgentServerInfoCron(){
        if (UPDATE_SERVER_TIME==null){
            return;
        }
        if (DateUtil.getCurrTime()-UPDATE_SERVER_TIME>1800) {
            setAgentServerInfo();
            UPDATE_SERVER_TIME = DateUtil.getCurrTime();
        }
    }

    /**
     * 上传客户端的信息到服务端
     */
    void pushServerInfo(){
        String pushUrl = Configure.get("sysInfoApiUrl");
        String os = System.getProperty("os.name");
        String scriptUrl = Configure.get("sysInfoScriptApi");
        String script = HttpUtil.sendPost(scriptUrl, "os="+os);
        if (script!=null&&script.length()>1){
            MonitorSystemScriptsEntity scriptsEntity = gson.fromJson(script, MonitorSystemScriptsEntity.class);
            script = scriptsEntity.getScriptsContent();
        }else{
            return;
        }
        String file  = tempDir + "sysInfo";
        FileIoUtil.makeDir(tempDir);
        FileIoUtil.writeFile(file, script, false);
        run("chmod a+x " + file);
        String result = runScript(file);
        info("系统信息获取到: "+ result);
        logger.info(sendPost(pushUrl, "sysInfo=" + Base64Util.encode(result)));
    }

    /**
     * 每30分钟上传一次数据
     */
    @Scheduled(cron = "*/59 * * * * ?")
    void  pushServerInfoCron(){
        if (PUSH_SERVER_INFO_TIME==null){
            return;
        }
        if (DateUtil.getCurrTime()-PUSH_SERVER_INFO_TIME>1800) {
            pushServerInfo();
            PUSH_SERVER_INFO_TIME = DateUtil.getCurrTime();
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
        info("报警发送检查....");
        String alarmMapString = gson.toJson(ALARM_MAP);
        Map<String, Double> ALARM_NEW_MAP = gson.fromJson(alarmMapString, HashMap.class);
//        info("alarmMapString " + gson.toJson(ALARM_NEW_MAP));
        for (Map.Entry<String, Double> entits : ALARM_NEW_MAP.entrySet()) {
            if (entits.getKey() == null) {
                info("ALARM_MAP key is null ... ");
                continue;
            }
            String[] key = entits.getKey().split("_");
            Integer value = entits.getValue().intValue();

            // 获取配置文件的ID
            String configId = key[0];
            MonitorConfigureEntity entity = CONFIGS.get(configId);
            if (entity == null) {
                info("获取到空数据 COFNIGS.get" + configId);
                continue;
            }
            if (key.length < 6) {
                info("获取到key小于6... " + gson.toJson(key));
                continue;
            }
//            info("ALARM_NEW_MAP " + entits.getKey() + " " + key[5]);
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
            String alarmId = id + "_" + key[2] + "_" + key[3] + "_" + key[4];
            // 如果小于配置文件的重试次数，则执行重试

            // 获取重试次数
            int retry = 0;
            if (entity.getMonitorConfigureTp().equals("item")) {
                retry = entity.getRetry();
            } else {
                MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(scriptId);
                retry = itemEntity.getRetry();
            }

            if (retry==0){
                logger.info("获取到重试次数为0,删除报警信息");
                ALARM_MAP.remove(entits.getKey());
            }

            // 如果retry为0则不执行重试
            if (value >= 1 && value <= retry + 1 && retry > 0) {
                String command = tempDir + scriptId + SCRIPT_ARGV.get(id);
                info("checkMonitorAlarm " + command + " " + alarmId + " retry -> " + value);
                List<PushEntity> pushEntities = run(command);
                for (PushEntity pushEntity : pushEntities) {
                    if (pushEntity != null) {
                        if (Integer.valueOf(pushEntity.getStatus()) > 1) {
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
                            SCRIPT_STATUS.put(alarmId, pushEntity);
                        }
                    }
                }
            } else {

                // 判断报警间隔
                long currTime = DateUtil.getCurrTime();
                long lastSendTime;
                if (!ALARM_INTERVAL.containsKey(alarmId)) {
                    logger.info("初始化 ALARM_INTERVAL ... ");
                    ALARM_INTERVAL.put(alarmId, currTime);
                }

                lastSendTime = ALARM_INTERVAL.get(alarmId);
                int alarmInterval = 30;
                if (entity.getMonitorConfigureTp().equals("item")) {
                    alarmInterval = entity.getAlarmInterval() * 60;
                } else {
                    MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(scriptId);
                    alarmInterval = itemEntity.getAlarmInterval() * 60;
                }

//                info("ALARM_COUNT ... " + gson.toJson(ALARM_COUNT));
                if (ALARM_COUNT == null || ALARM_COUNT.size() < 1) {
                    info("没有ALARM_COUNT, 退出ALARM_COUNT");
                    return;
                }

                info("获取到报警间隔为:" + alarmInterval);

                if (ALARM_COUNT.get(alarmId) == null) {
                    info("ALARM_COUNT is null ... " + alarmId);
                    continue;
                }
                info("获取到ALARM_COUNT 是" + ALARM_COUNT.get(alarmId) + "");

                if (currTime - lastSendTime < alarmInterval && currTime > lastSendTime) {
                    // 第一次不记数，直接发送
                    if (alarmInterval > 0 && retry > 0 ) {
                        info("不能发送报警信息,报警间隔太短:" + alarmId + " 还差 " + (alarmInterval - (currTime - lastSendTime)));
                        continue;
                    }
                }
                if (currTime - lastSendTime > alarmInterval) {
                    info("初始化时间报警记数器:" + alarmId);
                    ALARM_INTERVAL.put(alarmId, currTime);
                }
                // 报警次数记数, 每次加一
                if (ALARM_COUNT.containsKey(alarmId) && ALARM_COUNT.get(alarmId) >= 1) {
                    info("ALARM_COUNT add number " + ALARM_COUNT.get(alarmId));
                    ALARM_COUNT.put(alarmId, ALARM_COUNT.get(alarmId) + 1);
                }
                if (ALARM_COUNT.get(alarmId) == 0) {
                    info("ALARM_COUNT id is continue " + ALARM_COUNT.get(alarmId));
                    continue;
                }

                info("alarm count to number " + ALARM_COUNT.get(alarmId));
                int alarmCount = 1;
                if (entity.getMonitorConfigureTp().equals("item")) {
                    alarmCount = entity.getAlarmCount();
                } else {
                    MonitorItemEntity itemEntity = (MonitorItemEntity) SCRIPT_RETRY_MAP.get(scriptId);
                    alarmCount = itemEntity.getAlarmCount();
                }

                // 判断报警次数
                if (ALARM_COUNT.get(alarmId) >= alarmCount + 2) {
                    if (alarmCount > 1) {
                        info("alarm count to max ");
                        continue;
                    }
                }

                if (retry==0){
                    logger.info("获取到重试次数为0，删除ALARM_COUNT");
                    ALARM_COUNT.remove(alarmId);
                }

                //  发送报警信息
                info("添加报警到队列啦... " + alarmId);
                info(gson.toJson(SCRIPT_STATUS.get(alarmId)));
                if (SCRIPT_STATUS.containsKey(alarmId)) {
                    queue.add(SCRIPT_STATUS.get(alarmId));
                }
                if (retry==0){
                    logger.info("获取到重试次数为0，删除SCRIPT_STATUS");
                    SCRIPT_STATUS.remove(alarmId);
                }else {
                    ALARM_LAST_TIME.put(alarmId, currTime);
                }
            }
        }
    }

    /**
     * 报警队列检查
     * 将报警的消息添加到redis队列中
     */
    @Scheduled(cron = "0/15 * * * * ?")
    void checkAlarmQueue() {
        if (!IS_MONITOR) {
            return;
        }
        if (queue == null) {
            return;
        }
        PushEntity entity = queue.poll();
//        info("queue 获取到.." + gson.toJson(entity));
        if (entity != null) {
            int scriptId = Integer.valueOf(entity.getScriptId());
            int status = Integer.valueOf(entity.getStatus());
            info("开始设置队列啦 " + entity.getConfigId() + "_" + scriptId + " " + status);
            pushMessages(scriptId, status, entity);
        }
    }


    /**
     * 检查为加入到监控的机器，再配置完监控后，生效配置
     */
    @Scheduled(cron = "*/30 * * * * ?")
    void checkMonitorIsValid() {
        if (!IS_DEFAULT) {
            info("开始检查是否有监控");
            getHostConfig();
            if (IS_DEFAULT) {
                info("检查到该主机可以监控");
                initMonitor();
            }
        }
    }

    /**
     * 每一天初始化一次监控数据
     */
    @Scheduled(cron = "0 */10 * * * ?")
    void setClearMonitorTime() {
        if (CLEAR_MONITOR_TIME==null){
            init();
        }
        if (DateUtil.getCurrTime() - CLEAR_MONITOR_TIME >= 86400) {
            logger.info("初始化监控数据");
            initMonitor();
            CLEAR_MONITOR_TIME = DateUtil.getCurrTime();
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

        info("resetMonitorTime .. start");
        Random random = new Random();
        // 防止重复执行
        Thread.sleep(random.nextInt(100) * 300);
        info("resetMonitorTime .. end");
        Thread.sleep(random.nextInt(100) * 300);

        Jedis jedis = redisUtil.getJedis();
        String queueKey = app + "_" + MonitorCacheConfig.cacheHostUpdateQueue + getHosts();
        info(queueKey);
        long len = jedis.llen(queueKey);
        info("获取到队列大小 " + len);
        for (int i = 0; i < len; i++) {
            String data = jedis.rpop(queueKey);
            info("获取到更新状态 " + data);
            if (data != null) {
                // 有更新的化就更新所有信息
                initMonitor();
            }
        }
        jedis.close();
    }

    /**
     * 每30秒更新一次
     * 设置agent是存活的
     */
    @Scheduled(cron = "*/5 * * * * ?")
    void setHostActive() {
        if (!IS_MONITOR && !IS_DEFAULT) {
            return;
        }
        if(DateUtil.getCurrTime() - CHECK_UPDATE_TIME > 15) {
            String key = MonitorCacheConfig.cacheHostIsUpdate + getHosts();
            redisUtil.setex(key, 86400, DateUtil.getCurrTime() + "");
            CHECK_UPDATE_TIME = DateUtil.getCurrTime();
        }
    }

    /**
     * @param groupsId
     *
     * @return
     */
    HashSet<String> getGroupsHosts(String groupsId) {
        String fileName = tempDir + separator + "cache_groups_hosts_"+groupsId;
        if (checkCacheFileTime("cache_groups_hosts_"+groupsId, 1800)) {
            String result = redisUtil.get(MonitorCacheConfig.cacheGroupsHosts + groupsId);
            FileIoUtil.writeFile(fileName, result, false);
        }
        String result = FileIoUtil.readFile(fileName);
        if (result != null && result.length() > 0) {
            return gson.fromJson(result, HashSet.class);
        }
        return new HashSet<>();
    }

    /**
     * @param hosts
     *
     * @return
     */
    List<String> getHostStatus(List<String> hosts, boolean isAlarm) {
        String[] strings = new String[hosts.size()];
        int count = 0;
        String key = "";
        for (String host : hosts) {
            if (isAlarm) {
                 key = RedisUtil.app+"_"+ MonitorCacheConfig.cacheAgentAlarmNumber + host;
            }else{
                 key = RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIsUpdate + host;
            }
            strings[count] = key;
            count += 1;
        }
        if (strings.length > 0) {
            Jedis jedis = redisUtil.getJedis();
            List<String> result = jedis.mget(strings);
//            info("获取到 mget host status" + gson.toJson(result));
            return result;
        }
        return new ArrayList<>();
    }

    /**
     * 设置每个agent的报警信息, map
     * @param hosts
     */
    void setAgentAlarmStatus(List<String> hosts){
        if (AGENT_ALARM_MAP==null) {
            logger.info("初始化AGENT_ALARM_MAP");
            AGENT_ALARM_MAP = new HashMap<>();
        }
        List<String> list = getHostStatus(hosts, true);
        int count = 0;
        String host;
        for(String alarm : list){
            host = hosts.get(count);
//            info(host + " " + alarm);
            count += 1;
            if (alarm!=null&&alarm.length()>0){
                logger.info(host + "is alarmis alar " + alarm);
                AGENT_ALARM_MAP.put(host, alarm);
            }
        }
    }


    /**
     * @param messages
     */
    void pushMessages(String messages) {

        // 报警开关
        String alarmSwitch = redisUtil.get(MonitorCacheConfig.cacheAlarmSwitch);
        if (alarmSwitch != null && alarmSwitch.length() > 0 && !alarmSwitch.equals("1")) {
            logger.info("报警开关关闭,不能发送报警信息...");
            return;
        }

        logger.info("发送报警消息为: " + messages);
        // 报警发送到redis队列里
        for (int i = 0; i < 2; i++) {
            Long push = redisUtil.lpush(MonitorCacheConfig.cacheAlarmQueueKey, messages);
            if (push > 0) {
                break;
            }
        }
        // 通知服务端做报警处理
        String url = Configure.get("noticeSendUrl");
        info(HttpUtil.sendPost(url, ""));
    }

    /**
     * @param groupsId
     *
     * @return
     */
    List<String> getHosts(String groupsId) {
        HashSet<String> hosts = getGroupsHosts(groupsId);
        List<String> hostList = new ArrayList<String>();
        for (String host : hosts) {
            hostList.add(host);
        }
        return hostList;
    }

    /**
     * 检查缓存文件，并获取内容
     * @param name
     * @param interval
     * @return
     */
    public boolean checkCacheFileTime(String name, long interval){
        String fileName = tempDir + separator + name;

        File file = new File(fileName);
        if (file.exists()){
            if (System.currentTimeMillis()/1000 - file.lastModified()/1000 > interval){
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    /**
     * 获取agent报警的信息
     * @param host
     * @return
     */
    MonitorMessagesEntity getAgentMessages(String host){
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
        return  messagesEntity;
    }


    /**
     * 发送agent报警信息
     * @param host
     */
    void sendAgentAlarm(String  host ){
        MonitorMessagesEntity messagesEntity = getAgentMessages(host);
        messagesEntity.setSevertityId(2);
        messagesEntity.setAlarmCount(1);
        String server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + host);

        // 如果ping失败，属于严重，发送到所有联系方式到管理员组
        String ping = Ping.execPing(server);

        if (!ping.toUpperCase().contains("TTL")) {
            String alarmKey = MonitorCacheConfig.cacheAgentAlarmNumber + host;
            String alarmTimeKey = MonitorCacheConfig.cacheAgentAlarmNumber + host+"_time";
            String alarmNumber = redisUtil.get(alarmKey);
            String alarmTimeData = redisUtil.get(alarmTimeKey);

            if (alarmNumber != null) {
                if (Integer.valueOf(alarmNumber) >= 4) {
                    return;
                } else {
                    if (alarmTimeData != null && DateUtil.getCurrTime() - Integer.valueOf(alarmTimeData)  > 600 ) {
                        messagesEntity.setAlarmCount(Integer.valueOf(alarmNumber)+1);
                        alarmNumber = Integer.valueOf(alarmNumber) + 1 +"";
                        redisUtil.setex(alarmKey, 86400, alarmNumber);
                        redisUtil.setex(alarmTimeKey, 86400, DateUtil.getCurrTime() + "");
                    }else{
                        info("agent check 报警间隔不够");
                        return;
                    }
                }
            } else {
                alarmNumber = "1";
                redisUtil.setex(alarmKey, 86400, 1 + "");
                redisUtil.setex(alarmTimeKey, 86400, DateUtil.getCurrTime()+"");
            }
            messagesEntity.setMessages("agent check 报警 " + alarmNumber + " ping  "
                    + server + " " + ping  +" "
                    + DateUtil.getDate("yyyy-MM-dd HH:mm:ss")
                    +" 检查服务器" + IpUtil.getHostname());
            pushMessages(gson.toJson(messagesEntity));
        }
    }


    /**
     *
     * @param groupsId
     * @param jedis
     * @throws Exception
     */
    void checkHostIsUpdate(String groupsId, Jedis jedis) throws Exception{
        Map<String,String> okMap = new HashMap<>();
        Map<String,String> faildMap = new HashMap<>();
        List<String> hostList = getHosts(groupsId);
        setAgentAlarmStatus(hostList);
        String host;
        int count = 0;
        info(gson.toJson(hostList));
        for (String date : getHostStatus(hostList, false)) {
            host = hostList.get(count);
            count += 1;
            info(host +" " + date);
            if (date == null || date.length() < 2) {
                continue;
            }
            Long currDate = DateUtil.getCurrTime();
            Long lastDate = Long.valueOf(date);
            // 防止本机时间差距太大
            if (currDate - lastDate >= 240  ) {
                // 重试3次，每隔5秒
                String key = RedisUtil.app + "_" + MonitorCacheConfig.cacheHostIsUpdate + host;
                for (int retry =0; retry < 8; retry ++) {
                    String hostDate = jedis.get(key);
                    logger.info("重试检查agent "+retry+" "+host);
                    if (hostDate != null && hostDate.length() > 1) {
                        if (DateUtil.getCurrTime() - Long.valueOf(hostDate) > 300){
                            faildMap.put(host, date);
                        }else{
                            okMap.put(host, date);
                            faildMap.remove(host);
                            continue;
                        }
                    }
                    Thread.sleep(5000);
                }
                info("host date " +  date);
                faildMap.put(host, date);

                logger.info("获取到监控超时服务..." + host);
                // 异常数据放到web页面
                // 报警信息生成
                logger.info("检查到agent失败了，开始检查ping");
                sendAgentAlarm(  host );
            } else {
                info("set is ok " + host);
                okMap.put(host, date);
                logger.info(gson.toJson(AGENT_ALARM_MAP));
                if (AGENT_ALARM_MAP.containsKey(host)){
                    AGENT_ALARM_MAP.remove(host);
                    String server = redisUtil.get(MonitorCacheConfig.cacheHostIdToIp + host);
                    MonitorMessagesEntity messagesEntity = getAgentMessages(host);
                    messagesEntity.setSevertityId(1);
                    messagesEntity.setAlarmCount(0);
                    String key = MonitorCacheConfig.cacheAgentAlarmNumber + host;
                    String result = redisUtil.get(key);
                    if(result!=null&&result.length()>0) {
                        redisUtil.del(key);
                        messagesEntity.setMessages("agent 检查 ping 恢复  " + server + " " + DateUtil.getDate("yyyy-MM-dd HH:mm:ss") + " agent server " + IpUtil.getHostname());
                        pushMessages(gson.toJson(messagesEntity));
                    }
                }
            }
        }
        redisUtil.setex(MonitorCacheConfig.cacheAgentIsOk + "_" + groupsId,300, gson.toJson(okMap));
        redisUtil.setex(MonitorCacheConfig.cacheAgentUnreachable + "_" + groupsId, 300, gson.toJson(faildMap));
    }

    /**
     *  获取组信息
     * @return
     */
    Map<String, String>  getGroups(){
        String groups = "";
        String fileName = tempDir + separator + "cache_groups_name";
        if (checkCacheFileTime("cache_groups_name", 1800)) {
            groups = redisUtil.get(MonitorCacheConfig.cacheGroupName);
            FileIoUtil.writeFile(fileName, groups, false);
        }
        groups = FileIoUtil.readFile(fileName);

        logger.info("获取到grous " + groups);
        logger.info("获取到grous " + groups);
        Map<String, String> groupsMap = new HashMap<>();
        if (groups != null) {
            groupsMap = gson.fromJson(groups, HashMap.class);
        }
        return groupsMap;
    }


    /**
     * 分布式检查监控agent是否存活，如果5分钟内没有
     * 执行数据上报，就判断agent已经死亡，并将报警信息
     * 发送给web页面，恢复后从web页面删除, 报警信息发送
     * 给系统管理员
     */
    @Scheduled(cron = "*/30 * * * * ?")
    void checkMonitorUpdate() throws Exception {
        if (!IS_MONITOR && !IS_DEFAULT) {
            return;
        }
        if (DateUtil.getCurrTime()-AGENT_CHECK_TIME>60) {
            AGENT_CHECK_TIME = DateUtil.getCurrTime();
        }else{
            return;
        }
        String isCheck = Configure.get("agentActiveCheck");
        if (isCheck.equals("false")){
            return;
        }
        Map<String, String> groupsMap = getGroups();
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
            info("开始检查agent是否存活");
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
            if (! CACHE_CHECK_MONITOR_LOCK) {
                continue;
            }
            if (DateUtil.getCurrTime()-start > 60 ){
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
        info("get groups data " + result);
        if (result != null && result.length() > 0) {
            groups = gson.fromJson(result, HashSet.class);
            HOST_GROUPS.put(id, groups);
            try {
                for (Double g : groups) {
                    GROUPS_IDS.add(new Double(g).intValue()+"");
                }
            }catch (Exception e){
                logger.error("groups id error",e);
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
        info("获取配置文件...");
        if (!IS_DEFAULT) {
            info("除默认配置外，没有找到已配置的项目,删除配置..");
            if (!id.equals("0")) {
                redisUtil.del(key + id);
            }
        } else {
            String result = redisUtil.get(key + id);
            info("setHostGroupsConfigs" + result);
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
        info("获取默认配置");
        MonitorConfigureEntity configureEntity = new MonitorConfigureEntity();
        configureEntity.setConfigureId(0);
        configureEntity.setMonitorConfigureTp("template");
        configureEntity.setTemplateId("0");
        configureEntity.setIsValid(1);
        return configureEntity;
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
            String configId = redisUtil.get(key + id + "_" + cid);
            info("get config " + id + " " + cid + " " + configId);
            if (configId != null && configId.length() > 0) {
                if (!IS_DEFAULT) {
                    info("除默认配置外，没有找到已配置的项目,删除配置..");
                    redisUtil.del(MonitorCacheConfig.cacheHostConfigKey + id);
                    continue;
                }
                configId = configId.replace("\"", "");
                String config = redisUtil.get(MonitorCacheConfig.cacheConfigureKey + configId);
                info("get config data configId " + configId + " " + config);
                MonitorConfigureEntity configureEntity = gson.fromJson(config, MonitorConfigureEntity.class);
                if (!hConfigs.containsKey(id)) {
                    monitorConfigureEntities = new HashSet<>();
                } else {
                    monitorConfigureEntities = hConfigs.get(id);
                }
                // 配置文件按id存放
                CONFIGS.put(configId, configureEntity);
                // 主机的所属业务线
                if (configureEntity.getGroupsId().length() > 0) {
                    HOST_GROUP = configureEntity.getGroupsId();
                }

                monitorConfigureEntities.add(configureEntity);
                hConfigs.put(id, monitorConfigureEntities);
            } else {
                logger.error("configs " + id + " " + cid + " not found from redis");
                // 添加默认配置
                if (!monitorConfigureEntities.contains(getMonitorConfig())) {
                    info("添加默认配置...");
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
                info("通过模板配置item...");
                items.add(configId + "_" + item2);
                SCRIPT_ITEM_CONFIG.put(item2, monitorConfigureEntity);
            }
        }
        // 设置默认监控项目
        info("获取默认项目");
        String defaultItems = redisUtil.get(MonitorCacheConfig.cacheIsDefault);
        info("获取到defaultItems " + defaultItems);
        if (defaultItems != null && defaultItems.length() > 0) {
            ArrayList<String> list = gson.fromJson(defaultItems, ArrayList.class);
            MonitorConfigureEntity configureEntity = getMonitorConfig();
            info("添加默认配置...");
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
                        info("获取到监控项目, ID:" + configId + "_" + item);
                        items.add(configId + "_" + item);

                        // 存储每个ITEM的配置文件
                        SCRIPT_ITEM_CONFIG.put(item, monitorConfigureEntity);
                    }

                    // 添加模板里的项目
                    if (configType.equals("template")) {
                        String templates = monitorConfigureEntity.getTemplateId();
                        info("获取到templates " + templates);
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
            info("get item configur " + id);
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
                    info("set SCRIPT_ITEM " + monitorItemEntity.getScriptId() + " " + gson.toJson(monitorItemEntity));
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
            info("set scripts " + item);
            // 获取项目ID和配置ID
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
                        info("SCRIPTS add " + configId + "_" + scriptId);
                    }
                }
            }
        }
    }

    /**
     *  获取脚本内容
     *  2017-02-03
    **/
    MonitorScriptsEntity getScripts(String scriptsId){
        String scriptUrl = Configure.get("sysInfoScriptApi");
        for(int i=0;i<5;i++) {
            String script = HttpUtil.sendPost(scriptUrl, "scriptsId=" + scriptsId);
            if (script != null && script.length() > 1) {
                MonitorScriptsEntity scriptsEntity = gson.fromJson(script, MonitorScriptsEntity.class);
                return scriptsEntity;
            }
            info("获取脚本重试" + i);
        }
        return new MonitorScriptsEntity();
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
            if (monitorScriptsEntity.getScriptsId()!=null){
                SCRIPT_CONFIGS.put(id + "", monitorScriptsEntity);
                info(tempDir + "" + id);
                FileIoUtil.writeFile(tempDir + "" + id, monitorScriptsEntity.getContent(), false);
                logger.info("设置脚本权限 " + id);
                run("chmod a+x " + tempDir + id);
            } else {
                logger.error("script " + id + " not found from api");
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
     * @param entity
     *
     * @return
     */
    String getArg(MonitorConfigureEntity entity) {
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
            args += " " + ip;
        }
        return args;
    }

    /**
     * 获取脚本的参数
     *
     * @param entity
     *
     * @return
     */
    String getArg(MonitorItemEntity entity) {
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
            args += " " + ip;
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
                        info("通过模板获取到item配置" + itemId);
                        SCRIPT_CHECK_INTERVAL.put(entity.getScriptId() + "", entity.getCheckInterval());
                        // 监控的时间,单位为秒
                        SCRIPT_TIME.add(entity.getCheckInterval());
                        if (SCRIPT_TIME_MAP.containsKey(entity.getCheckInterval() + "")) {
                            checkTime = SCRIPT_TIME_MAP.get(entity.getCheckInterval() + "");
                        }
                        info("checkTime add 1 " + configId + "_" + entity.getScriptId());
                        checkTime.add(configId + "_" + entity.getScriptId() + "");
                        // 每个map里存放每个时间点要执行的脚本id
                        SCRIPT_TIME_MAP.put(entity.getCheckInterval() + "", checkTime);
                        SCRIPT_RETRY_MAP.put(entity.getScriptId() + "", entity);
                    }
                    checkTime = new HashSet();
                    if (configureEntity.getMonitorConfigureTp().equals("item")) {
                        info("通过item去配置SCRIPT_TIME_MAP");
                        SCRIPT_TIME.add(configureEntity.getCheckInterval() + "");
                        info("SCTIPT_TIME set " + gson.toJson(SCRIPT_TIME));
                        if (SCRIPT_TIME_MAP.containsKey(configureEntity.getCheckInterval() + "")) {
                            checkTime = SCRIPT_TIME_MAP.get(configureEntity.getCheckInterval() + "");
                            info("SCRIPT_TIME_MAP 包含 " + gson.toJson(checkTime));
                        }
                        SCRIPT_CHECK_INTERVAL.put(entity.getScriptId() + "", configureEntity.getCheckInterval() + "");
                        info("checkTime add 2 " + configId + "_" + entity.getScriptId());
                        checkTime.add(configId + "_" + entity.getScriptId() + "");
                        SCRIPT_TIME_MAP.put(configureEntity.getCheckInterval() + "", checkTime);
                        SCRIPT_RETRY_MAP.put(entity.getScriptId() + "", configureEntity);
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
                        info("获取通过item配置的参数....");
                        args = getArg(configureEntity);
                    } else {
                        info("获取通过template配置的参数....");
                        args = getArg(entity);
                    }
                    SCRIPT_ARGV.put(scriptId, args);
                    info("SCRIPT_ARGV put " + scriptId + " " + args);
                }
            }
        }
    }

    /**
     * 获取主机ID
     *
     * @return
     */
    String getHosts() {
        if (HOST_IDS != null) {
            return HOST_IDS;
        }
        return "";
    }

    /**
     * 运行时间
     *
     * @param time
     * @param success
     * @param faild
     */
    void runScript(String time, ArrayList<PushEntity> success, ArrayList<PushEntity> faild) {
        String command;
        List<PushEntity> entitys;
        HashSet<String> scriptTime;
        Long lastTime;
        // 获取这个时间保护的时间
        info("runScript " + time + " " + gson.toJson(SCRIPT_TIME_MAP));
        scriptTime = SCRIPT_TIME_MAP.get(time);
        for (String id : scriptTime) {
            String[] ids = id.split("_");
            String s = ids[1];

            // 获取脚本的最近运行时间
            lastTime = SCRIPT_RUNTIME.get(id);
            if (lastTime == null) {
                lastTime = 1L;
            }
            long nowTime = DateUtil.getCurrTime();
            info("获取配置文件信息" + ids[0]);
            if ((nowTime - lastTime) >= Integer.valueOf(SCRIPT_CHECK_INTERVAL.get(s + ""))) {
                info("SCRIPT_RUNTIME put " + id);
                SCRIPT_RUNTIME.put(id, DateUtil.getCurrTime());
                command = tempDir + s + SCRIPT_ARGV.get(id);
                info("start exec script " + command);
                entitys = run(command);
                for (PushEntity entity : entitys) {
                    String alarmId = id + "_" + getServerId(entity) + "_" + entity.getGroups() + "_" + entity.getName();
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
                            info(gson.toJson(entity));
                            success.add(entity);
                        }
                        // 判断脚本运行状态成功
                        if (entity.getStatus().equals("1")) {
                            info("status 1 PushEntity " + gson.toJson(entity));
                            success.add(entity);
                            if (ALARM_COUNT.containsKey(alarmId)) {
                                if (getAlarmStatus(alarmId)) {
                                    ALARM_COUNT.remove(alarmId);
                                    info("获取到报警状态为 " + entity.getStatus() + " ALARM_COUNT.remove " + alarmId);
                                    if (!ALARM_LAST_TIME.containsKey(alarmId)) {
                                        info("初始化 ALARM_LAST_TIME ...");
                                        ALARM_LAST_TIME.put(alarmId, DateUtil.getCurrTime());
                                    }
                                    //  发送报警信息, 报警后40秒内恢复,不发送恢复信息, 去掉多余的报警
                                    if (ALARM_LAST_TIME != null && DateUtil.getCurrTime() - ALARM_LAST_TIME.get(alarmId) > 40) {
                                        MonitorItemEntity monitorItemEntity = SCRIPT_TO_ITEM.get(s + "_" + ids[0]);
                                        if (monitorItemEntity != null) {
                                            if (monitorItemEntity.getIsRecover() == 1) {
                                                queue.add(entity);
                                                info("添加恢复报警到队列啦...1");
                                            }
                                        } else {
                                            queue.add(entity);
                                            info("添加恢复报警到队列啦...2");
                                        }
                                    } else {
                                        logger.info("恢复时间太短,跳出恢复报警...");
                                    }
                                    info("remove ALARM_COUNT: " + alarmId);
                                }
                            }
                        } else {
                            // 统计非正常状态的信息
                            if (Integer.valueOf(entity.getStatus()) > 1) {
                                info("获取到失败状态 " + gson.toJson(entity) + " " + alarmId);
                                faild.add(entity);
                                if (!ALARM_COUNT.containsKey(alarmId)) {
                                    info("获取到报警状态为 " + entity.getStatus() + " init ALARM_COUNT 1 " + alarmId);
                                    ALARM_COUNT.put(alarmId, 1);
                                } else if (ALARM_COUNT.get(alarmId) == 0) {
                                    info("获取到报警状态为 " + entity.getStatus() + " init ALARM_COUNT 2 " + alarmId);
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
    void setAlarmMap(String type, String ids) {
        String id = ids + "_" + type;
        int old = 0;
        if (ALARM_MAP.containsKey(id)) {
            old = ALARM_MAP.get(id);
        }
        old += 1;
        ALARM_MAP.put(id, old);
        if (!type.equals("ok")) {
            removeMap(ids + "_ok");
        }
        if (!type.equals("faild")) {
            removeMap(ids + "_faild");
        }
        if (!type.equals("warning")) {
            removeMap(ids + "_warning");
        }
        if (!type.equals("unknown")) {
            removeMap(ids + "_unknown");
        }
    }

    /**
     * @return
     */
    int getAlarmCount(String id) {
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
    boolean getAlarmStatus(String id) {
        if (getAlarmCount(id) > 1) {
            return true;
        }
        return false;
    }

    /**
     * 缓存各种报警检查装
     * 避免对redis造成压力
     * 每次检查和上次结果一样就不进行redis更新
     */

    /**
     * 上报监控结果
     *
     * @param entity
     */
    void pushMonitor(ArrayList<PushEntity> entity, String url) {
        ArrayList ok = new ArrayList();
        ArrayList faild = new ArrayList();
        ArrayList unknown = new ArrayList();
        ArrayList warning = new ArrayList();

        info(entity.size() + "");
        if (entity.size() > 0) {
            String data = Base64Util.encode(gson.toJson(entity));
            if (data.length()<5){
                info("获取到数据为空，退出pushMonitor");
                return;
            }
             // 发送监控数据
            info(sendPost(url, "lentity=" + data));
            info("entity size: " + entity.size());

            for (PushEntity pushEntity : entity) {
                String id = pushEntity.getConfigId() +
                        "_" + pushEntity.getScriptId() +
                        "_" + pushEntity.getServer() +
                        "_" + pushEntity.getGroups() +
                        "_" + pushEntity.getName();
                switch (pushEntity.getStatus()) {
                    case "1":
                        ok.add(id);
                        setAlarmMap("ok", id);
                        if (getAlarmStatus(id)) {
                            //  发送报警信息
                            info("remove ALARM_COUNT: " + id);
                            ALARM_COUNT.remove(id);
                            info("添加恢复报警到队列啦...");
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
            info("set ok data ");
            setGroupsData("ok", ok);
        }
        if (faild.size() > 0) {
            info("set faild data ");
            setGroupsData("faild", faild);
        }
        if (warning.size() > 0) {
            info("set warning data ");
            setGroupsData("warning", warning);
        }
        if (unknown.size() > 0) {
            info("set unknown data ");
            setGroupsData("unknown", unknown);
        }
    }

    /**
     * @param pushEntity
     * @param id
     */
    void setStatus(PushEntity pushEntity, String id) {
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
    HashSet getContact(MonitorContactsEntity contactsEntity, String type, HashSet contactSet) {
        switch (type) {
            case "mobile":
                contactSet.add(contactsEntity.getMobile());
                break;
            case "email":
                contactSet.add(contactsEntity.getMail());
                break;
            case "weixin":
                contactSet.add(contactsEntity.getMobile());
                break;
            case "ding":
                contactSet.add(contactsEntity.getNo());
                break;
        }
        return contactSet;
    }

    /**
     * 获取联系人
     *
     * @return
     */
    String getContact(String groups, String type) {
        if (groups.length() < 1) {
            return "";
        }
        String[] g = groups.split(",");
        String contact = "";
        for (String group : g) {
            if (group.length() < 1) {
                continue;
            }
            if (CONTACT_GROUP.containsKey(group)) {
                contact = CONTACT_GROUP.get(group);
            } else {
                String result = redisUtil.get(cacheContactGroupKey + group);
                if (result != null) {
                    MonitorContactGroupEntity entity = gson.fromJson(result, MonitorContactGroupEntity.class);
                    contact += entity.getMember()+",";
                }
            }
        }

        logger.info("获取到联系组 " + contact);
        HashSet<String> contactSet = new HashSet();
        String[] contacts = contact.split(",");
        info(contacts.toString());
        MonitorContactsEntity entity = new MonitorContactsEntity();
        for (String c : contacts) {
            if (c==null || c.length()<1){continue;}
            if (CONTACT.containsKey(c)) {
                entity = CONTACT.get(c);
            } else {
                String result = redisUtil.get(cacheContactKey + c);
                if (result != null) {
                    entity = gson.fromJson(result, MonitorContactsEntity.class);
                }
            }
            contactSet = getContact(entity, type, contactSet);
        }
        String result = "";
        for (String c : contactSet) {
            result += c + ",";
        }
        logger.info("获取到联系人 " + result);
        if (result.length()>2) {
            return result.substring(0, result.length() - 1);
        }else{
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
        String description = "";
        // 给有配置的添加配置里的描述信息
        try {
            MonitorConfigureEntity configureEntity = CONFIGS.get(pushEntity.getConfigId());
            description = configureEntity.getDescription();
        }catch (Exception e){
            logger.error("没有获取到配置文件信息", e);
        }
        String messages = gson.toJson(entity);
        messages = messages.replace("${message}", entity.getAlarmCount() - 1 + " " + pushEntity.getMessages());
        messages = messages.replace("${groups}", "");
        messages = messages.replace("${command}", pushEntity.getCommand());
        if (pushEntity.getIp() != null) {
            messages = messages.replace("${server}", pushEntity.getIp()+" " + description);
        } else {
            for (String ip : LOCAL_IP) {
                messages = messages.replace("${server}", ip + " " + IpUtil.getHostname() + " " + description);
            }
        }
        messages = messages.replace("${time}", DateUtil.getDay());
        return messages;
    }

    /**
     * @return
     */
    String getAdminGroup() {
        String adminGroup = redisUtil.get(MonitorCacheConfig.cacheIsAdmin);
        if (adminGroup == null) {
            adminGroup = "";
        }
        return adminGroup;
    }

    /**
     * 获取默认的管理员组
     *
     * @param itemEntity
     *
     * @return
     */
    String getAdminGroup(MonitorItemEntity itemEntity) {
        // 在项目中配置发送给管理员的项目全部发报警给管理员
        String adminGroup;
        if (itemEntity.getIsAdmin() != null && itemEntity.getIsAdmin().length() > 0) {
            adminGroup = getAdminGroup();
        } else {
            adminGroup = "";
        }
        return adminGroup;
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
        if (pushEntity.getIp()!=null) {
            entity.setIp(pushEntity.getIp());
        }else{
            for (String ip : LOCAL_IP) {
                entity.setIp(ip);
            }
        }
        entity.setValue(pushEntity.getValue());
        entity.setIndexName(pushEntity.getName());

        // 报警服务器设置
        int serverId = 0;
        serverId = Integer.valueOf(HOST_IDS);
        String alarmId = pushEntity.getConfigId() + "_" + scriptId + "_" + serverId + "_" + pushEntity.getGroups() + "_" + pushEntity.getName();
        info("pushMessages 获取到ALARM_COUNT id: " + alarmId);
        if (ALARM_COUNT.containsKey(alarmId)) {
            entity.setAlarmCount(ALARM_COUNT.get(alarmId));
        } else {
            entity.setAlarmCount(1);
        }
        MonitorItemEntity itemEntity = gson.fromJson(SCRIPT_ITEM.get(scriptId + ""), MonitorItemEntity.class);

        String recover = "";
        String message = "";
        if (itemEntity != null) {
            info("获取到script_item:" + gson.toJson(itemEntity));
            recover = itemEntity.getRecoverMessages();
            message = itemEntity.getAlarmMessages();
        } else {
            recover = "报警恢复了";
            message = "报警啦";
        }

        MonitorConfigureEntity configureEntity = SCRIPT_ITEM_CONFIG.get(itemEntity.getItemId() + "");

        // 在项目中配置发送给管理员的项目全部发报警给管理员
        String adminGroup = getAdminGroup(itemEntity);
        String mobileGroups = configureEntity.getMobileGroups() + "," + adminGroup;
        String emailGroups = configureEntity.getEmailGroups() + "," + adminGroup;
        String dingGroups = configureEntity.getDingGroups() + "," + adminGroup;
        String weixinGroups = configureEntity.getWeixinGroups() + "," + adminGroup;
        String all = configureEntity.getAllGroups() ;
        String mobile = "";
        String email = "";
        String ding = "";
        String weixin = "";
        if (all != null && all.length() > 0) {
            mobile = getContact(all+ "," + adminGroup, "mobile");
            email = getContact(all+ "," + adminGroup, "email");
            ding = getContact(all+ "," + adminGroup, "ding");
            weixin = getContact(all+ "," + adminGroup, "weixin");
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
        if (pushEntity.getIp()!=null){
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
        pushMessages(messages);
    }

    /**
     *
     * @param command
     * @return
     */
    String runScript(String command){
        String result = "";
        String line = "";
        try {
            info("run 获取到脚本 " + command);
            Process process = runtime.exec(command);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                info(line);
                result += line;
            }
            is.close();
            isr.close();
            br.close();
        }catch (Exception e){
            logger.error("脚本执行出错", e);
        }
        return result;
    }

    /**
     * 脚本执行
     *
     * @param command
     *
     * @return
     */
    List<PushEntity> run(String command) {
        String result = "";
        try {
            result = runScript(command);
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(result, type);
            info("脚本执行结果 " + result);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(command + " run faild");
        }
        return null;
    }

    /**
     * 清除过期的报警数据
     * @param dataMap
     * @return
     */
    Map<String, String> clearExpiredMap(Map<String, String> dataMap){
        if (dataMap==null){
            return new HashMap<>();
        }
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> entry: dataMap.entrySet()){
            String id = entry.getKey();
            String time = entry.getValue();
            if (DateUtil.getCurrTime()-Long.valueOf(time) > 3600){
                logger.info("移除过期的报警数据 "+ id);
            }else{
               newMap.put(id, time);
            }
        }
        return newMap;
    }

    /**
     * @param faildData
     * @param okData
     * @param warningData
     * @param unknownData
     * @param type
     * @param ids
     */
    Map getGroupData(Map<String, String> faildData, Map<String, String> okData, Map<String, String> warningData, Map<String, String> unknownData, String type, ArrayList<String> ids) {
        Map map = new HashMap();
        String currTime = DateUtil.getCurrTime() + "";
        for (String id : ids) {
            switch (type) {
                case "ok":
                    okData.put(id, currTime);
                    faildData.remove(id);
                    unknownData.remove(id);
                    warningData.remove(id);
                    break;
                case "faild":
                    faildData.put(id, currTime);
                    unknownData.remove(id);
                    warningData.remove(id);
                    okData.remove(id);
                    break;
                case "warning":
                    warningData.put(id, currTime);
                    faildData.remove(id);
                    unknownData.remove(id);
                    okData.remove(id);
                    break;
                case "unknown":
                    unknownData.put(id, currTime);
                    faildData.remove(id);
                    warningData.remove(id);
                    okData.remove(id);
                    break;
            }
        }
        map.put("ok", okData);
        map.put("faild", faildData);
        map.put("warning", warningData);
        map.put("unknown", unknownData);
        return map;
    }

    /**
     * 业务线告警状态设置
     *
     * @param type
     * @param ids
     */
    void setGroupsData(String type, ArrayList<String> ids) {
        String result = redisUtil.get(MonitorCacheConfig.cacheHostStatusMap+HOST_IDS);
        Map<String, String > redisData  = new HashMap<>();
        Map<String, String> okData = new HashMap<>();
        Map<String, String> faildData = new HashMap<>();
        Map<String, String> warningData = new HashMap<>();
        Map<String, String> unknownData = new HashMap<>();
        if (result!=null&&result.length()>0){
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
        redisUtil.setex(MonitorCacheConfig.cacheHostStatusMap+HOST_IDS, 3600, gson.toJson(redisData));
    }
}
