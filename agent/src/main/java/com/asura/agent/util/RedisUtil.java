package com.asura.agent.util;

import com.asura.agent.configure.Configure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @author zy
 * @Date 2016-06-21 redis工具
 */
public class RedisUtil {

    private static  final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    public static String url = Configure.get("redis.server");
    public static final String app = "monitor";
    private static int port;
    private static String redisPassword;
    private static boolean isConfig = false;


    /**
     * 获取单个链接
     * @return
     */
    public Jedis getJedis(){
        init();
        LOGGER.info("获取链接...");
        Jedis jedis = new Jedis(url, port);
        if (redisPassword != null && redisPassword.length() > 0 ){
            jedis.auth(redisPassword);
        }
        return jedis;
    }


    /**
     * 设置redis服务器的地址
     */
    static void init(){
        if(!isConfig) {
            LOGGER.info("set redis server start ....");
            redisPassword = Configure.get("redis.password");
            String curl = Configure.get("redis.server");
            if (curl.length() > 1) {
                url = curl;
                isConfig = true;
            }
            String cport = Configure.get("redis.port");
            if(cport.length() > 1 ){
                port = Integer.valueOf(cport);
            }else{
                port = 6379;
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
        Jedis jedis = getJedis();
        String r ;
        try {
            LOGGER.info("set "+app + "_" + key, value);
            jedis.set(app + "_" + key, value);
            r = "ok";
        } catch (Exception e) {
            LOGGER.error("Redis set ERROR " + key ,e);
            r = "err";
        }
        jedis.close();
        return r;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public String setex(String key,int timeOut, String value) {
        Jedis jedis = getJedis();
        String r = "";
        try {
            LOGGER.info("setex "+app + "_" + key, value);
            jedis.setex(app + "_" + key,timeOut, value);
            r ="ok";
        } catch (Exception e) {
            LOGGER.error("Redis setex ERROR " + key ,e);
            r = "err";
        }
        jedis.close();
        return r;
    }

    /**
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = getJedis();
        String r = "";
        try {
            LOGGER.info("get "+ app + "_" + key);
            r = jedis.get(app + "_" + key);
        } catch (Exception e) {
            LOGGER.error("Redis get ERROR " + key ,e);
            r = "";
        }
        jedis.close();
        return r;
    }

    /**
     * @param key
     * @return
     */
    public long del(String key) {
        Jedis jedis = getJedis();
        long r = 1l;
        try {
            LOGGER.info("del "+ app + "_" + key);
            r = jedis.del(app + "_" + key);
        } catch (Exception e) {
            r = 0l;
        }
        jedis.close();
        return r;
    }

    /**
     * @param key
     * @return
     */
    public long lpush(String key, String value) {
        Jedis jedis = getJedis();
        long r = 1l;
        try {
            LOGGER.info("lpush "+ app + "_" + key);
            r = jedis.lpush(app + "_" + key, value);
        } catch (Exception e) {
            r = 0;
        }
        jedis.close();
        return r;
    }

    /**
     * 先进先出
     * @param key
     * @return
     */
    public String  rpop(String key) {
        Jedis jedis = getJedis();
        String r = "";
        try {
            LOGGER.info("rpop "+ app + "_" + key);
            r = jedis.rpop(app + "_" + key);
        } catch (Exception e) {
            r = "";
        }
        jedis.close();
        return r;
    }
}
