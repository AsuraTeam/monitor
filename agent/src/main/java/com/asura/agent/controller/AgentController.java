package com.asura.agent.controller;


import com.google.gson.Gson;
import com.asura.agent.configure.Configure;
import com.asura.agent.entity.PushEntity;
import com.asura.agent.thread.GetDataThread;
import com.asura.agent.util.CommandUtil;
import com.asura.agent.util.FileIoUtil;
import com.asura.agent.util.HttpUtil;
import com.asura.agent.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @Date 2016-08-13 06:31:00
 */
@RestController
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class AgentController {

    private final Logger logger = LoggerFactory.getLogger(AgentController.class);

    // 初始化配置文件
    private Configure configure = new Configure();

    // 脚本名称
    private static ArrayList<String> scripts;

    private static Gson gson = new Gson();

    private static Runtime runtime = Runtime.getRuntime();

    private boolean isR ;


    /**
     * 列出已有的脚本名称
     * @return
     */
    @RequestMapping(value="/api/list")
    @ResponseBody
    public String getList(){
        return gson.toJson(scripts);
    }


    /**
     * @return
     */
    @RequestMapping(value="/api/data",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getData(String name)throws Exception {
        ArrayList<PushEntity> pushEntities = new ArrayList<>();
        if (scripts != null) {
            for (String s : scripts) {
                if(name!=null){
                    File f = new File(s);
                    String sname = f.getName();
                    if(name.equals(sname)) {
                        GetDataThread thread = new GetDataThread(pushEntities, s);
                        thread.exec();
                    }
                }else {
                    GetDataThread thread = new GetDataThread(pushEntities, s);
                    thread.exec();
                }
            }
        }
        Thread.sleep(5);
        return gson.toJson(pushEntities);
    }

    /**
     * 执行任务计划上传数据到监控系统
     * 每40秒执行一次
     */
    @Scheduled(cron = "0/40 * * * * ?")
    public void crontab() {
        logger.info("启动监控上报任务计划");
        String result = execCommand();
        logger.info("完成监控上报任务计划完成"+result);
    }


    /**
     * 每1分钟执行一次
     * 修改脚本更新
     */
    @Scheduled(cron="0 */5 * * * ?")
    public void crontabSetScripts(){
        logger.info("设置脚本信息");
        scripts = null;
    }


    /**
     * 执行脚本，任务计划调用
     * @return
     */
    public String execCommand() {

        String url = Configure.getUrl();
        if(url==null||url.length()<3){
            logger.error("url参数没有配置");
            return "";
        }
        setScripts();
        ArrayList<GetDataThread> threadList = new ArrayList<>();
        ArrayList<PushEntity> pushEntities = new ArrayList<>();
        if (scripts != null) {
            for (String s : scripts) {
                logger.info("运行脚本:" + s);
                GetDataThread thread = new GetDataThread(pushEntities, s);
                threadList.add(thread);

            }
        }
        for(GetDataThread t:threadList){
            t.start();
        }
        while (1==1) {
            boolean isStop = false;
            for (GetDataThread t : threadList) {

                try {
                    if (t.isAlive()) {
                        isStop = true;
                        t.interrupt();
                    }
                } catch (Exception e) {
                }
            }
            try {
                Thread.sleep(15000);
            }catch (Exception e){

            }
            if(!isStop) {
                break;
            }
        }
        if (pushEntities.size()<1){
            return "";
        }
        logger.info("lentity="+gson.toJson(pushEntities));
        String reuslt = HttpUtil.sendPost(url,"lentity="+gson.toJson(pushEntities));
        return reuslt;
    }

    /**
     * 设置获取可以执行的脚本文件
     */
    public void setScripts() {
        if (scripts == null) {
            logger.info("获取脚本文件");
            scripts = FileIoUtil.getDirFiles(Configure.get("script"));
            String script = "";
            for (String s : scripts) {
                script += s + ",";
            }
            logger.info("获取到脚本文件:[" + script + "]");
        }
    }


    /**
     * 更新jar包，通过到指定的服务器下载新jar包更新
     */
    @RequestMapping("update")
    @ResponseBody
    public String update(String username,String password) throws  Exception{

        // 获取用户名,密码
        String configUser = Configure.get("username");
        String configPassword = configure.get("password");

        String noUserMsg = "用户名或密码错误,更新程序退出";
        if(configPassword.length()>1 && configUser.length()>1){
            if(username==null||password==null){
                logger.info(noUserMsg);
                return noUserMsg;
            }
            if(!username.equals(configUser)&&password.equals(configPassword)){
                logger.info(noUserMsg);
                return noUserMsg;
            }
        }

        String url = Configure.get("updateUrl");
        if(url.length()<3){
            logger.error("下载配置没有,updateUrl=http://xx.xx/file/");
            return "";
        }

        String path = Configure.getJarPath();

        // 临时文件定义
        String tempFile = "/tmp/agent.jar";
        String tempMd5 = "/tmp/agent.md5";

        logger.info("下载更新包开始...."+url);
        HttpUtil.downloadNet(url,tempFile);
        logger.info("下载更新包接收....");
        File f = new File(tempFile);


        if(f.exists()){

            // 获取文件的md5
            String md5 = Md5Util.getMd5ByFile(f);
            logger.info("更新包的文件md5值为:"+md5);

            // 获取提供更新包下载的MD5
            HttpUtil.downloadNet(url+".md5",tempMd5);
            ArrayList<String> fmd5 = FileIoUtil.readTxtFile(tempMd5);
            String rmd5 = "";

            String md5Msg = "没有获取到更新包的MD5值,更新程序结束";
            if(fmd5.size()>0){
                String[] rmd5List = fmd5.get(0).split(" ");
                rmd5 = rmd5List[0].trim();
                logger.info("提供的更新包md5值为:"+rmd5);
            }else {
                logger.info(md5Msg);
                return md5Msg;
            }

            // 对比已经下载的更新包的MD5值和远程文件提供的md5
            String exitMsg = "更新包提供的MD5和实际下载的MD5值不一致,更新退出";
            if(rmd5.equals(md5)){
                logger.info("更新包的md5和提供的md5一致");
            }else{
                logger.info(exitMsg);
                return exitMsg;
            }

            FileIoUtil.copyFile(tempFile,path);
            logger.info("更新文件完成");
            // 删除文件
            f.delete();
        }else{
            logger.error("下载文件失败");
        }

        String pid = System.getProperty("PID");
        logger.info("执行杀死进程"+pid);
        runtime.exec("kill -9 "+ pid);
        logger.info("执行杀死进程完成");
        return "ok";

    }
}