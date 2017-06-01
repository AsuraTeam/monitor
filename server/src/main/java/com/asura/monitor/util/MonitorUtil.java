package com.asura.monitor.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.asura.monitor.graph.util.FileWriter.dataDir;

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
 * @since 1.0
 */

public class MonitorUtil {

    public static final String separator = System.getProperty("file.separator");
    public static final Logger logger = LoggerFactory.getLogger(MonitorUtil.class);
    /**
     *
     * @param lentity
     * @return
     */
    public static List<PushEntity>  getPushEntity(String lentity){
        if( lentity!=null ) {
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(lentity, type);
            return list;
        }
        return new ArrayList<>();
    }


    /**
     * @param lentity
     */
    public static void writePush(String lentity,  String writeType, String ip, Map<String, Long> map){
        try {
            List<PushEntity> list = getPushEntity(lentity);
            if (map != null) {
                if (!map.containsKey("indexCounter")) {
                    map.put("indexCounter", Long.valueOf(list.size()));
                }else{
                    map.put("indexCounter", map.get("indexCounter")+ list.size());
                }
            }
            for (PushEntity entity1 : list) {
                if (entity1 == null) {
                    continue;
                }
                pushMonitorData(entity1, writeType, ip);
            }
        }catch (Exception e){
            logger.error("解析数据失败" + lentity, e);
        }
    }

    /**
     * 记录每个指标的服务器地址
     * @param entity
     */
    static void writeIndexHosts(PushEntity entity){
        // 拼接文件目录
        String dir = dataDir + separator + "graph" + separator +"index" +separator;
        dir = dir + entity.getGroups()+"."+entity.getName()+separator;
        dir = FileRender.replace(dir);
        FileWriter.makeDirs(dir);
        File file = new File(dir + "id");
        if ( !file.exists() ) {
            FileWriter.writeFile(dir + "id", entity.getScriptId(), false);
        }
    }

    /**
     *
     * @param entity
     * @param writeType
     * @param ipAddr
     */
    public static void pushMonitorData(PushEntity entity,  String writeType, String ipAddr) {
        String historyName = FileRender.replace(entity.getName());
        String name = FileRender.replace(entity.getScriptId() +"_"
                + FileRender.replace(entity.getStatus())) + "_"
                + FileRender.replace(entity.getGroups())+ "_"+ FileRender.replace(entity.getName());

        if (entity.getStatus().equals("2")){
            logger.info(name);
        }
        String groups = FileRender.replace(entity.getGroups());
        String value = entity.getValue();
        // 记录每个指标的服务器地址
        writeIndexHosts(entity);
        // 获取客户端IP
        if (entity.getIp() != null && entity.getIp().length() > 7 ) {
            ipAddr = FileRender.replace(entity.getIp());
        }
        // 只将数据写入到文件
        if (name != null && value != null && value.length() > 0) {
            // 画图数据生成
            FileWriter.writeHistory(groups, ipAddr, historyName, value);
            if(writeType.equals("success")) {
                // 监控历史数据生成
                if (entity.getServer() != null) {
                    FileWriter.writeMonitorHistory(FileRender.replace(entity.getServer()), name, entity);
                }
            }
        }
    }
    
    public static String getStopMonitorTime(long value){
        if (value  < 60 ) {
            return "还有" + value + "秒恢复";
        }
        if (value  > 60 && value < 3600 ) {
            return  "还有" + ( value / 60 )  +"分钟恢复";
        }
        if (value  > 3600 && value < 86400 ) {
            return "还有" + (value / 60 / 60) + "小时恢复";
        }
        if (value  > 86400 && value < 604800 ) {
            return  "还有" +( value / 60 / 60 / 24) +"天恢复";
        }
        if (value  > 604800 && value < 2419200 ){
            return "还有" +( value / 60 / 60 / 24 / 7 ) +"周恢复";
        }
        return "监控正常报警中";
    }
}
