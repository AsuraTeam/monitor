package com.asura.monitor.util;

import com.google.gson.Gson;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.HttpUtil;
import com.asura.util.RedisUtil;
import org.apache.commons.collections.map.HashedMap;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;


/**
 * 将监控数据写入到es服务器
 * Created by zhaoyun on 2017/9/17
 */
@ComponentScan
@Controller
public class ToElasticsearchUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ToElasticsearchUtil.class);
    private static String server;
    private static ArrayList<String> esServers;
    private static int port;
    private static int serversSize;
    private static String clusterName;
    private static int pushSize;
    private static LinkedTransferQueue<Map> linkedTransferQueue;
    private static Map<String, Map> hostMap;
    private static boolean esSwitch = true;
    private static TransportClient client;
    private static String lock = null;
    private static int counter = 0;
    private static int faildCounter = 0;
    private static Gson GSON;

    /**
     * @param entity
     * @param hostMap
     */
    static void pushMap(CmdbResourceServerEntity entity, Map hostMap) {
        Map tempMap = new HashedMap();
        tempMap.put("groups", entity.getGroupsName());
        tempMap.put("entname", entity.getEntName());
        hostMap.put(entity.getIpAddress(), tempMap);
    }

    /**
     * @param type
     * @param ip
     * @param name
     * @param value
     */
    public static void pushQueue(String type, String ip, String name, String value) {
        setParam();
        if (!esSwitch) {
            return;
        }
        Map map = new HashedMap();
        map.put("ip", ip);
        map.put("type", type);
        map.put("name", name);
        map.put("value", Double.valueOf(value));
        if (hostMap == null) {
            hostMap = new HashedMap();
        }
        if (!hostMap.containsKey(ip)) {
            // 重新初始化一次
            RedisUtil redisUtil = new RedisUtil();
            String data = redisUtil.get(MonitorCacheConfig.cacheIpHostInfo + ip);
            if (CheckUtil.checkString(data)) {
                Gson gson = new Gson();
                CmdbResourceServerEntity entity = gson.fromJson(data, CmdbResourceServerEntity.class);
                pushMap(entity, hostMap);
            }

            if (!hostMap.containsKey(ip)) {
                CmdbResourceServerEntity entity = new CmdbResourceServerEntity();
                entity.setIpAddress(ip);
                entity.setGroupsName("未知");
                entity.setEntName("未知");
                pushMap(entity, hostMap);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + "+08:00");
        String time = sdf.format(new Date());
        map.put("time", time);
        Map tempMap = hostMap.get(ip);
        map.put("groups", tempMap.get("groups"));
        map.put("entname", tempMap.get("entname"));
        linkedTransferQueue.add(map);
        try {
            pushToEs();
        } catch (Exception e) {
            LOGGER.info("map ", new Gson().toJson(map));
            e.printStackTrace();
        }
    }


    public static void setParam() {
        if (server != null) {
            return;
        }
        if (!esSwitch){
            return;
        }
        linkedTransferQueue = new LinkedTransferQueue();
        Resource resource;
        Properties props;
        resource = new ClassPathResource("/system.properties");
        try {
            esServers = new ArrayList();
            props = PropertiesLoaderUtils.loadProperties(resource);
            String esServer = (String) props.get("es.server");
            if (CheckUtil.checkString(esServer)) {
                esServer = esServer.trim();

                String[] es = esServer.split(",");

                for (int i = 0; i < es.length; i++) {
                    if (CheckUtil.checkString(es[i])) {
                        esServers.add(es[i].trim());
                        LOGGER.info("获取到es服务器"+es[i]);
                    }
                }
                server = esServers.get(0);
                LOGGER.info("当前ES服务器设置为"+ server);
                esSwitch = true;
                GSON = new Gson();
            } else {
                esSwitch = false;
                LOGGER.info("没有配置es服务器");
                return;
            }
            clusterName = (String) props.get("es.cluster.name");
            clusterName = clusterName.trim();
            String cport = (String) props.get("es.port");
            String pushSizeS = (String) props.get("es.push.size");
            if (CheckUtil.checkString(cport)) {
                port = Integer.valueOf(cport.trim());
            } else {
                port = 9200;
            }
            if (CheckUtil.checkString(pushSizeS)) {
                pushSize = Integer.valueOf(pushSizeS);
            } else {
                // 默认打包3000个指标上报到es
                pushSize = 3000;
            }
            client = transportClient();
            try {
                makeEsAlias();
            } catch (Exception e) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    static TransportClient transportClient() {
        if (!esSwitch){
            return null;
        }
        if (null != lock){
            return null;
        }else{
            lock = "1";
        }
        if (counter > 10){
            esSwitch = false;
            return null;
        }
        LOGGER.info("开始连接ES服务 transportClient");
        TransportClient client1 = null;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName).build();
             client1 = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(server), Integer.valueOf(port)));
             lock = null;
            return client1;
        } catch (Exception e) {
            e.printStackTrace();
            if (null != client1){
                client1.close();
            }
            if (serversSize < esServers.size()-1){
                for (int i=serversSize; i<esServers.size();i++){
                    server = esServers.get(i);
                    serversSize = i;
                    LOGGER.info("重试设置ES为"+server);
                    if (serversSize >= esServers.size()){
                        serversSize = 0;
                    }
                    break;
                }
            }
            counter += 1;
            LOGGER.error("ES服务连接失败", e);
            lock = null;
            return null;
        }

    }

    /**
     * 生成别名和索引
     */
    public static void makeEsAlias() throws Exception {

        TransportClient client = transportClient();
        IndicesAliasesRequestBuilder response = client.admin().indices()
                .prepareAliases();
        LOGGER.info("开始生成 别名");
        response.addAlias("monitor-*", "asura");
        response.execute().actionGet();
        client.close();
    }

//
//    public static void pushToEsHttp(){
//        if (linkedTransferQueue.size() >= pushSize) {
//            Map map;
//            Map maps;
//            StringBuilder data = new StringBuilder();
//            String key = "monitor-" + DateUtil.getDate("yyyy-MM-dd");
//            String index = "{ \" index \":{\" _index\":\" %s\",\"_type\":\"monitor\"}}\n".replace("%s", key);
//            for (int i = 0; i < pushSize; i++) {
//                map = linkedTransferQueue.poll();
//                if (map == null) {
//                    continue;
//                }
//                try {
//                    maps = new HashMap();
//                    maps.put("@type", map.get("type"));
//                    maps.put("@name", map.get("name"));
//                    maps.put("@value", map.get("value"));
//                    maps.put("@timestamp", map.get("time"));
//                    maps.put("@ip", map.get("ip"));
//                    data.append(index + GSON.toJson(maps) + "\n");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//            String url = "http://".concat(server).concat(":") + port + "/_bulk";
//            try {
//                HttpUtil.httpPostJson(url, data.toString(), "POST");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * type 监控组对应监控数据的groups
     * name 监控指标名称
     * value 监控值
     * time 数据上报时间
     * ip 服务器IP地址
     *
     * @throws Exception
     */
    public static void pushToEs() throws Exception {

        if (linkedTransferQueue.size() >= pushSize) {
            String key  = "monitor-" + DateUtil.getDate("yyyy-MM-dd");
            if (null == client){
                client = transportClient();
            }
            try {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                for (int i = 0; i < pushSize; i++) {
                    Map map = linkedTransferQueue.poll();
                    if (map == null) {
                        continue;
                    }
                    bulkRequest.add(client.prepareIndex(key, "monitor")
                            .setSource(jsonBuilder()
                                    .startObject()
                                    .field("@type", map.get("type"))
                                    .field("@name", map.get("name"))
                                    .field("@value", map.get("value"))
                                    .field("@timestamp", map.get("time"))
                                    .field("@ip", map.get("ip"))
                                    .field("@groups", map.get("groups"))
                                    .field("@entname", map.get("entname"))
                                    .endObject()
                            )
                    );
                }
                bulkRequest.get();
            } catch (Exception e) {
                faildCounter += 1;
                if (faildCounter > 30 ){
                    client.close();
                    client = transportClient();
                    LOGGER.error("上传ES数据失败", e);
                    faildCounter = 0;
                }
            }
        }
    }

}

