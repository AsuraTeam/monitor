package com.asura.agent.util;

import com.asura.agent.configure.Configure;
import com.asura.agent.controller.MonitorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @author zy
 * @Date 2016-06-21 redis工具
 */
public class RedisUtil {

    private static  final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static String url = Configure.get("redis.server");
    public static String app = "monitor";
    private static  int port = 6379;

    private static boolean isConfig = false;


    /**
     * 获取单个链接
     * @return
     */
    public Jedis getJedis(){
        LOGGER.info("获取链接...");
        Jedis jedis = new Jedis(url, port);
        return jedis;
    }


    /**
     * 设置redis服务器的地址
     */
    static void init(){
        if(!isConfig) {
            LOGGER.info("set redis server start ....");
            String curl = Configure.get("redis.server");
            if (curl.length() > 1) {
                url = curl;
                isConfig = true;
            }
            String cport = Configure.get("redis.port");
            if(cport.length()>1){
                port = Integer.valueOf(cport);
            }
            LOGGER.info("set redis server end ....");
        }

    }

    /**
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        init();
        Jedis jedis = new Jedis(url, port);
        String r = "";
        try {
            LOGGER.info("set "+app + "_" + key, value);
            r = jedis.set(app + "_" + key, value);
            jedis.close();
        } catch (Exception e) {
            r = "";
        }
        return r;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public String setex(String key,int timeOut, String value) {
        init();
        Jedis jedis = new Jedis(url, port);
        String r = "";
        try {
            LOGGER.info("setex "+app + "_" + key, value);
            r = jedis.setex(app + "_" + key,timeOut, value);
            jedis.close();
        } catch (Exception e) {
            r = "";
        }
        return r;
    }

    /**
     * @param key
     * @return
     */
    public String get(String key) {
        init();
        Jedis jedis = new Jedis(url, port);
        String r = "";
        try {
            LOGGER.info("get "+ app + "_" + key);
            r = jedis.get(app + "_" + key);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
            r = "";
            jedis.close();
        }
        return r;
    }

    /**
     * @param key
     * @return
     */
    public long del(String key) {
        init();
        Jedis jedis = new Jedis(url, port);
        long r = 1l;
        try {
            LOGGER.info("del "+ app + "_" + key);
            r = jedis.del(app + "_" + key);
            jedis.close();
        } catch (Exception e) {
            r = 0l;
            jedis.close();
        }
        return r;
    }

    /**
     * @param key
     * @return
     */
    public long lpush(String key, String value) {
        init();
        Jedis jedis = new Jedis(url, port);
        long r = 1l;
        try {
            LOGGER.info("lpush "+ app + "_" + key);
            r = jedis.lpush(app + "_" + key, value);
            jedis.close();
        } catch (Exception e) {
            r = 0;
            jedis.close();
        }
        return r;
    }

    /**
     * 先进先出
     * @param key
     * @return
     */
    public String  rpop(String key) {
        init();
        Jedis jedis = new Jedis(url, port);
        String r = "";
        try {
            LOGGER.info("rpop "+ app + "_" + key);
            r = jedis.rpop(app + "_" + key);
            jedis.close();
        } catch (Exception e) {
            r = "";
            jedis.close();
        }
        return r;
    }

}
