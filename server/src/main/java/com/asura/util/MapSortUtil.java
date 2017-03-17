package com.asura.util;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class MapSortUtil {

    public static Map sortMap(Map oldMap) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg0.getValue() - arg1.getValue();
            }
        });
        Map newMap = new LinkedHashMap();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }

    /**
     * @param map
     * @return
     */
    public static String doubleSort(Map<String, Double> map) {
        List<Map.Entry<String, Double>> wordMap = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        Collections.sort(wordMap, new Comparator<Map.Entry<String, Double>>() {//根据value排序
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                double result = o2.getValue() - o1.getValue();
                if (result > 0) return 1;
                else if (result == 0) return 0;
                else return -1;
            }
        });
        return new Gson().toJson(wordMap);
    }

}