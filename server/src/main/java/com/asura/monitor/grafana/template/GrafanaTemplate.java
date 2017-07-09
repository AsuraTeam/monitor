package com.asura.monitor.grafana.template;

import com.google.gson.Gson;
import com.asura.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyun on 2017/7/8.
 */
public class GrafanaTemplate {

    private static Logger logger = LoggerFactory.getLogger(GrafanaTemplate.class);

    /**
     * timepicker
     * grafana时间数据公共的
     *
     * @return
     */
    public static String getTimer() {
        return "{\n" +
                "    \"refresh_intervals\": [\n" +
                "      \"5s\",\n" +
                "      \"10s\",\n" +
                "      \"30s\",\n" +
                "      \"1m\",\n" +
                "      \"5m\",\n" +
                "      \"15m\",\n" +
                "      \"30m\",\n" +
                "      \"1h\",\n" +
                "      \"2h\",\n" +
                "      \"1d\"\n" +
                "    ],\n" +
                "    \"time_options\": [\n" +
                "      \"5m\",\n" +
                "      \"15m\",\n" +
                "      \"1h\",\n" +
                "      \"6h\",\n" +
                "      \"12h\",\n" +
                "      \"24h\",\n" +
                "      \"2d\",\n" +
                "      \"7d\",\n" +
                "      \"30d\"\n" +
                "    ]\n" +
                "  }";
    }

    /**
     * templating
     *
     * @return
     */
    public static String getTemplate(String ip) {
        String d = "{\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"allValue\": null,\n" +
                "        \"current\": {\n" +
                "          \"text\": \"{0}\",\n" +
                "          \"value\": \"{0}\"\n" +
                "        },\n" +
                "        \"datasource\": \"asura\",\n" +
                "        \"hide\": 0,\n" +
                "        \"includeAll\": false,\n" +
                "        \"label\": null,\n" +
                "        \"multi\": false,\n" +
                "        \"name\": \"host\",\n" +
                "        \"options\": [\n" +
                "          {\n" +
                "            \"selected\": true,\n" +
                "            \"text\": \"{0}\",\n" +
                "            \"value\": \"{0}\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"query\": \"{\\\"ip\\\":\\\"{0}\\\"}\",\n" +
                "        \"refresh\": 0,\n" +
                "        \"regex\": \"\",\n" +
                "        \"sort\": 0,\n" +
                "        \"tagValuesQuery\": \"\",\n" +
                "        \"tags\": [],\n" +
                "        \"tagsQuery\": \"\",\n" +
                "        \"type\": \"query\",\n" +
                "        \"useTags\": false\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        d = d.replace("{0}", ip);
        return d;
    }

    /**
     * 图像数据
     *
     * @return
     */
    public static String getPanelsMap(String id, String group, String ip, String spanSize) {
        return  "        {\n" +
                "          \"aliasColors\": {},\n" +
                "          \"bars\": false,\n" +
                "          \"dashLength\": "+10+",\n" +
                "          \"dashes\": false,\n" +
                "          \"datasource\": \"-- Mixed --\",\n" +
                "          \"fill\": "+1+",\n" +
                "          \"height\": \"300px\",\n" +
                "          \"id\": "+id+",\n" +
                "          \"legend\": {\n" +
                "            \"alignAsTable\": true,\n" +
                "            \"avg\": true,\n" +
                "            \"current\": true,\n" +
                "            \"hideEmpty\": false,\n" +
                "            \"max\": true,\n" +
                "            \"min\": true,\n" +
                "            \"rightSide\": false,\n" +
                "            \"show\": true,\n" +
                "            \"total\": false,\n" +
                "            \"values\": true\n" +
                "          },\n" +
                "          \"lines\": true,\n" +
                "          \"linewidth\": 1,\n" +
                "          \"links\": [],\n" +
                "          \"minSpan\": "+4+",\n" +
                "          \"nullPointMode\": \"null\",\n" +
                "          \"percentage\": false,\n" +
                "          \"pointradius\": "+5+",\n" +
                "          \"points\": false,\n" +
                "          \"renderer\": \"flot\",\n" +
                "          \"repeat\": \"null\",\n" +
                "          \"scopedVars\": {\n" +
                "            \"host\": {\n" +
                "              \"selected\": true,\n" +
                "              \"text\": \""+ip+"\",\n" +
                "              \"value\": \""+ip+"\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"seriesOverrides\": [],\n" +
                "          \"spaceLength\": \"10\",\n" +
                "          \"span\": "+spanSize+",\n" +
                "          \"stack\": false,\n" +
                "          \"steppedLine\": false,\n" +
                "          \"thresholds\": [],\n" +
                "          \"timeFrom\": null,\n" +
                "          \"timeShift\": null,\n" +
                "          \"title\": \"$host "+group+"\",\n" +
                "          \"tooltip\": {\n" +
                "            \"shared\": true,\n" +
                "            \"sort\": "+0+",\n" +
                "            \"value_type\": \"individual\"\n" +
                "          },\n" +
                "          \"transparent\": false,\n" +
                "          \"type\": \"graph\",\n" +
                "          \"xaxis\": {\n" +
                "            \"buckets\": null,\n" +
                "            \"mode\": \"time\",\n" +
                "            \"name\": null,\n" +
                "            \"show\": true,\n" +
                "            \"values\": []\n" +
                "          },\n" +
                "          \"yaxes\": [\n" +
                "            {\n" +
                "              \"format\": \"short\",\n" +
                "              \"label\": null,\n" +
                "              \"logBase\": "+1+",\n" +
                "              \"max\": null,\n" +
                "              \"min\": null,\n" +
                "              \"show\": true\n" +
                "            },\n" +
                "            {\n" +
                "              \"format\": \"short\",\n" +
                "              \"label\": null,\n" +
                "              \"logBase\": "+1+",\n" +
                "              \"max\": null,\n" +
                "              \"min\": null,\n" +
                "              \"show\": true\n" +
                "            }\n" +
                "          ]\n" +
                "        }";
    }

    /**
     * metric数据
     * @return
     */
    public static String getTargets(String groups, String name, String tag){
        logger.info("getatargets "+ groups + " " + name + " "+ tag);
        String d = "            {\n" +
                "              \"datasource\": \"asura\",\n" +
                "              \"expr\": \"{0}|{1},$host\",\n" +
                "              \"format\": \"time_series\",\n" +
                "              \"interval\": \"500s\",\n" +
                "              \"intervalFactor\": 2,\n" +
                "              \"legendFormat\": \"{1}\",\n" +
                "              \"refId\": \"{2}\",\n" +
                "              \"step\": 1000\n" +
                "            }";
        d = d.replace("{0}", groups).replace("{1}", name).replace("{2}", tag);
        return d;
    }


    /**
     *
     * @param server
     * @param graphType
     * @param names
     * @param group
     * @param name
     * @param id
     * @param spanSize
     */
    static Map  getRowsSpans(String server, String  graphType, String[] names, String group, String name, String id, String spanSize){

        Gson gson = new Gson();
        Map panelsMap = new HashMap();
        logger.info("mgroups data getRowsSpans " + gson.toJson(names));
        // 多线图
        ArrayList targetsList = new ArrayList();
        if (graphType.equals("group")) {
            panelsMap = gson.fromJson(getPanelsMap(id, names[0].split("\\|")[0], server, spanSize), HashMap.class);
            String[] tags = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
            String[] datas;
            int counter = 0;
            logger.info("names groups  getRowsSpans " + new Gson().toJson(names));
            for (String sname : names) {
                logger.info("getRowsSpans sname " + sname);
                datas = sname.split("\\|");
                if (datas.length < 2 ){
                    continue;
                }
                logger.info("getRowsSpans datas "+ datas);
                targetsList.add(gson.fromJson(getTargets(datas[0], datas[1], tags[counter]), HashMap.class));
                counter += 1;
            }
            panelsMap.put("targets", targetsList);
        }
        // 单线图
        if (graphType.equals("sign")){
            panelsMap = gson.fromJson(getPanelsMap("1", group, server, spanSize), HashMap.class);
            targetsList.add(gson.fromJson(getTargets(group, name, "A"), HashMap.class));
            panelsMap.put("targets", targetsList);
        }
        return panelsMap;
    }

    /**
     *
     * @param server
     * @param graphType
     * @param names
     * @param group
     * @param name
     * @return
     */
    public static Map getRowsMap(String server, String  graphType, String[] names, String group, String name, String id, String spanSize){
        Map rowPubMap = new HashMap();
        rowPubMap.put("repeat", null);
        rowPubMap.put("repeatIteration", null);
        rowPubMap.put("repeatRowId", null);
        rowPubMap.put("showTitle", false);
        rowPubMap.put("title", "Dashboard Row");
        rowPubMap.put("titleSize", "h6");
        rowPubMap.put("collapse",false);
        rowPubMap.put("height", 5);
        ArrayList rowspanels = new ArrayList();
        rowspanels.add(getRowsSpans(server,graphType, names, group, name, id,  spanSize));
        rowPubMap.put("panels", rowspanels);
        return rowPubMap;
    }

    /**
     * @param size
     * @return
     */
    public static String getSpanSize(int size){
        if (size >= 3){
            return "4";
        }
        if (size == 2){
            return "6";
        }
        if (size == 1){
            return "12";
        }
        return "12";
    }

    /**
     *
     * @param request
     * @param graphType
     * @param group
     * @param name
     * @return
     */
    public static Map getGrafanaTemplate(HttpServletRequest request, String graphType, String group, String name, String namesData) {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("id", 1);
        map.put("editMode", false);
        map.put("editable", true);
        map.put("gnetId", 0);
        // 图像提示共享
        map.put("graphTooltip", 2);
        map.put("hideControls", false);
        map.put("links", new ArrayList<>());
        map.put("refresh", false);
        map.put("schemaVersion", 14);
        map.put("style", "dark");
        map.put("tags", new ArrayList<>());
        map.put("timezone", "browser");
        map.put("version", 24);
        map.put("title", Md5Util.MD5(namesData+graphType));
        map.put("timepicker", gson.fromJson(getTimer(), HashMap.class));
        map.put("templating", gson.fromJson(getTemplate(request.getServerName()), HashMap.class));
        Map annotationMap = new HashMap();
        annotationMap.put("list", new ArrayList<>());
        map.put("annotations", annotationMap);
        Map timeMap = new HashMap();
        timeMap.put("from", "now-2h");
        timeMap.put("to", "now");
        map.put("time", timeMap);
        ArrayList rows = new ArrayList();
        Map rowPubMap;
        // 单线图
        if (graphType.equals("sign")){
            rowPubMap = getRowsMap(request.getServerName(), "sign", null, group, name, "1", getSpanSize(namesData.split(",").length));
            rows.add(rowPubMap);
        }
        // 多线图
        if (graphType.equals("group")){
            rowPubMap = getRowsMap(request.getServerName(), "group", namesData.split(","), null, null,"1", "12");
            rows.add(rowPubMap);
        }
        // 多幅图
        if (graphType.equals("mgroup") || graphType.equals("msign")){
            Map<String, ArrayList<String>> groupsMap = new HashMap();
            String[] datas;
            String key;
            String[] names = namesData.split(",");
            for (String sname : names) {
                datas = sname.split("\\|");
                key = datas[0];
                if (groupsMap.containsKey(key)){
                    ArrayList temp =  groupsMap.get(key);
                    temp.add(datas[1]);
                    groupsMap.put(key, temp);
                }else {
                    ArrayList list = new ArrayList();
                    list.add(datas[1]);
                    groupsMap.put(key, list);
                }
            }
            int idCounter = 1;
            String spanSize = getSpanSize(groupsMap.size());
            rowPubMap = getRowsMap(request.getServerName(), "group", "".split(","), null, null, idCounter+"", spanSize);
            ArrayList rowspanels = new ArrayList();
            for (Map.Entry<String, ArrayList<String>> entry: groupsMap.entrySet()) {
                if (graphType.equals("mgroup")) {
                    String namesDatas = "";
                    for (String nameData : entry.getValue()) {
                        namesDatas += entry.getKey() + "|" + nameData + ",";
                    }
                    if (namesDatas.length() < 2 ){
                        continue;
                    }
                    namesDatas = namesDatas.substring(0, namesDatas.length() - 1);
                    rowspanels.add(getRowsSpans(request.getServerName(), "group", namesDatas.split(","), group, null, idCounter+"",  spanSize));
                }
                if (graphType.equals("msign")){
                    String namesDatas;
                    for (String nameData : entry.getValue()) {
                        namesDatas = entry.getKey() + "|" + nameData;
                        rowspanels.add(getRowsSpans(request.getServerName(), "group", namesDatas.split(","), group, null, idCounter+"",  getSpanSize(entry.getValue().size())));
                        idCounter += 1;
                    }
                }
                idCounter += 1;
            }
            rowPubMap.put("panels", rowspanels);
            rows.add(rowPubMap);
        }
        map.put("rows", rows);
        return map;
    }
}