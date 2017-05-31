package com.asura.util;

import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.resource.entity.CmdbResourceServerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Properties;

/**
 * @author zy
 * @Date 2016-06-21 redis工具
 */
@ComponentScan
public class RedisUtil  {

    private static  final  Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    public static  String app = "monitor";
    private static String redisPassword;
    private  String url ;
    private static int port;

    public RedisUtil(){
        Resource resource ;
        Properties props ;
        resource = new ClassPathResource("/system.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            url = (String) props.get("redis.server");
            url = url.trim();
            String redisPass = (String) props.get("redis.password");
            if (CheckUtil.checkString(redisPass)) {
                redisPassword = redisPassword.trim();
            }
            String cport = (String) props.get("redis.port");
            if(CheckUtil.checkString(cport)){
                port = Integer.valueOf(cport.trim());
            }else{
                port = 6379;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取单个链接
     * @return
     */
    public Jedis getJedis(){
        LOGGER.info("获取链接...");
        Jedis jedis = new Jedis(url, port);
        if (CheckUtil.checkString(redisPassword)){
            jedis.auth(redisPassword);
        }
        return jedis;
    }


    /**
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = getJedis();
        String r = "";
        try {
            LOGGER.info("set "+app + "_" + key + " "+ value);
            r = jedis.set(app + "_" + key, value);
            jedis.close();
        } catch (Exception e) {
            LOGGER.error("Redis SET 错误", e);
            r = "";
        }
        return r;
    }

    /**
     * 监控使用sethost的id
     * @param entityList
     * @return
     */
    public String setHostId(java.util.List<CmdbResourceServerEntity> entityList) {
        Jedis jedis = getJedis();
        String r = "";
        try {
            for(CmdbResourceServerEntity c:entityList) {
                LOGGER.info(app + "_cache_hosts_id_"+c.getIpAddress());
                // 主机的id
                jedis.set(app + "_cache_hosts_id_"+c.getIpAddress() , c.getServerId()+"");
                // 主机的业务线
                jedis.set(app +"_" +MonitorCacheConfig.getCacheHostGroupsKey+c.getIpAddress() , c.getGroupsId()+"");
                // 获取每个id对应的ip地址
                jedis.set(app+"_"+MonitorCacheConfig.cacheHostIdToIp+ c.getServerId(), c.getIpAddress());
            }
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
        Jedis jedis = getJedis();
        String r = "";
        LOGGER.info(url +" " + port);
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
    public Long del(String key) {
        Jedis jedis = getJedis();
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
            jedis.close();
        } catch (Exception e) {
            r = "";
            jedis.close();
        }
        return r;
    }

    /**
     * 获取队列长度
     * @param key
     * @return
     */
    public Long llen(String key){
        Jedis jedis = getJedis();
        Long r;
        try {
            LOGGER.info("rpop "+ app + "_" + key);
            r = jedis.llen(app + "_" + key);
            jedis.close();
        } catch (Exception e) {
            r = 0L;
            jedis.close();
        }
        return r;
    }

    /**
     * @param key
     * @return
     */
    public long lpush(String key, String value) {
        Jedis jedis = getJedis();
        long r;
        try {
            LOGGER.info("lpush "+ app + "_" + key);
            r = jedis.lpush(app + "_" + key, value);
            jedis.close();
        } catch (Exception e) {
            r = 0l;
            jedis.close();
        }
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
            r = jedis.setex(app + "_" + key,timeOut, value);
            jedis.close();
        } catch (Exception e) {
            r = "";
            jedis.close();
        }
        return r;
    }

  }
