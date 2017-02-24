package com.asura.agent.thread;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.agent.entity.PushEntity;
import com.asura.agent.util.CommandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * <p> 执行脚本，上传数据到监控平台 </p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @since 1.0
 */
public class GetDataThread extends Thread{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ArrayList<PushEntity> entities;
    private String script;
    private static Gson gson = new Gson();

    /**
     * 将所有的数据封装到一个集合，统一发送，减少调用接口次数
     * @param entities
     */
    public GetDataThread(ArrayList<PushEntity> entities,String script){
        this.entities = entities;
        this.script = script;
    }

    /**
     * 执行系统脚本
     * @return
     */
    public  ArrayList exec() {
        try {
            String result = CommandUtil.runScript(script);
            // 判断是数组还是单个对象
            if (result.startsWith("[")) {
                Type type = new TypeToken<ArrayList<PushEntity>>() {
                }.getType();
                List<PushEntity> list = gson.fromJson(result, type);
                for (PushEntity entity1 : list) {
                    entities.add(entity1);
                }
            } else {
                // 单个实体
                if (result != "") {
                    entities.add(gson.fromJson(result, PushEntity.class));
                }
            }
        }catch (Exception e){
            logger.error("脚本输出出现异常"+script);
            logger.error(e+" ");
        }
        return entities;
    }

    /**
     * 线程调用
     */
    public void run(){
         exec();
    }
}
