package com.asura.monitor.util;

import com.google.gson.Gson;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
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
    private static int port;
    private static String clusterName;
    private static int pushSize;
    private static LinkedTransferQueue<Map> linkedTransferQueue;
    private static Map<String, Map> hostMap;
    private static boolean esSwitch = false;
    private static TransportClient client;

    /**
     * @param entity
     * @param hostMap
     */
    static void pushMap(CmdbResourceServerEntity entity, Map hostMap) {
        Map tempMap = new HashedMap();
        tempMap.put("groups", entity.getGroupsName());
        tempMap.put("cabinet", entity.getCabinetName());
        tempMap.put("entname", entity.getEntName());
        tempMap.put("username", entity.getUserName());
        tempMap.put("domain", entity.getDomainName());
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
            client = transportClient();
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
                entity.setDomainName("未知");
                entity.setIpAddress(ip);
                entity.setGroupsName("未知");
                entity.setCabinetName("未知");
                entity.setUserName("未知");
                entity.setEntName("未知");
                pushMap(entity, hostMap);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + "+08:00");
        String time = sdf.format(new Date());
        Map tempMap = hostMap.get(ip);
        map.put("time", time);
        map.put("groups", tempMap.get("groups"));
        map.put("cabinet", tempMap.get("cabinet"));
        map.put("entname", tempMap.get("entname"));
        map.put("username", tempMap.get("username"));
        map.put("domain", tempMap.get("domain"));
        linkedTransferQueue.add(map);
        try {
            pushToEs();
        } catch (Exception e) {
            LOGGER.info("map ", new Gson().toJson(map));
            LOGGER.error("pushToEs错误", e);
        }
    }

    public static void setParam() {
        if (server != null) {
            return;
        }
        linkedTransferQueue = new LinkedTransferQueue();
        Resource resource;
        Properties props;
        resource = new ClassPathResource("/system.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            server = (String) props.get("es.server");
            server = server.trim();
            if (CheckUtil.checkString(server)) {
                esSwitch = true;
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
        LOGGER.info("开始连接ES服务 transportClient");
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(server), Integer.valueOf(port)));
            return client;
        } catch (Exception e) {
            LOGGER.error("ES服务连接失败", e);
            return null;
        }
    }

    /**
     * 生成别名和索引
     */
    public static void makeEsAlias() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + "+08:00");
        String time = sdf.format(new Date());
        TransportClient client = transportClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        IndicesAliasesRequestBuilder response = client.admin().indices()
                .prepareAliases();
        LOGGER.info("开始生成 别名");
        Long now = System.currentTimeMillis();
        for (int i = 1; i < 2; i++) {
            try {
                String day = DateUtil.timeStamp2Date(now + (3600 * 24 * i * 1000) + "", "yyyy-MM-dd");
                bulkRequest.add(client.prepareIndex("monitor-" + day, "monitor")
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("@type", "test")
                                .field("@name", "test")
                                .field("@value", 0.01)
                                .field("@timestamp", time)
                                .field("@groups", "test")
                                .field("@cabinet", "test")
                                .field("@entname", "test")
                                .field("@username", "test")
                                .field("@domain", "test")
                                .field("@ip", "test")
                                .endObject()
                        )
                );
                bulkRequest.execute().get();
                XContentBuilder mapping = jsonBuilder()
                        .startObject()
                        .startObject("properties")
                        .startObject("@name").field("type", "text").field("fielddata", true).endObject()
                        .startObject("@groups").field("type", "text").field("fielddata", true).endObject()
                        .startObject("@entname").field("type", "text").field("fielddata", true).endObject()
                        .startObject("@usernam").field("type", "text").field("fielddata", true).endObject()
                        .startObject("@domain").field("type", "text").field("fielddata", true).endObject()
                        .startObject("@ip").field("type", "text").field("fielddata", true).endObject()
                        .endObject().endObject();
                PutMappingRequest mappingRequest = Requests.putMappingRequest("monitor-" + day).type("monitor").source(mapping);
                client.admin().indices().putMapping(mappingRequest).actionGet();
                LOGGER.info("生成索引别名 " + day);
                response.addAlias("monitor-" + day, "asura");
            } catch (Exception e) {
                LOGGER.error("生成别名失败", e);
            }
        }
        response.execute().actionGet();
        client.close();
    }

    /**
     * type 监控组对应监控数据的groups
     * name 监控指标名称
     * value 监控值
     * time 数据上报时间
     * groups 业务线
     * cabinet 机柜
     * entname 环境名称
     * username 负责名称
     * domain 域名
     * ip 服务器IP地址
     *
     * @throws Exception
     */
    public static void pushToEs() throws Exception {

        if (linkedTransferQueue.size() >= pushSize) {
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            try {
                for (int i = 0; i < pushSize; i++) {
                    Map map = linkedTransferQueue.poll();
                    if (map == null) {
                        continue;
                    }
                    bulkRequest.add(client.prepareIndex("monitor-" + DateUtil.getDate("yyyy-MM-dd"), "monitor")
                            .setSource(jsonBuilder()
                                    .startObject()
                                    .field("@type", map.get("type"))
                                    .field("@name", map.get("name"))
                                    .field("@value", map.get("value"))
                                    .field("@timestamp", map.get("time"))
                                    .field("@groups", map.get("groups"))
                                    .field("@cabinet", map.get("cabinet"))
                                    .field("@entname", map.get("entname"))
                                    .field("@username", map.get("username"))
                                    .field("@domain", map.get("domain"))
                                    .field("@ip", map.get("ip"))
                                    .endObject()
                            )
                    );
                }

                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    client.close();
                    client = transportClient();
                    LOGGER.error("上传ES数据失败", bulkResponse.hasFailures());
                    // process failures by iterating through each bulk response item
                }
            } catch (Exception e) {
                client.close();
                client = transportClient();
            }
        }
    }


}

