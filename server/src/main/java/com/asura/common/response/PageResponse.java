package com.asura.common.response;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/7.
 */
@Component
public class PageResponse {

    public static String getMap(PagingResult result, int draw){
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("data", result.getRows());
        map.put("recordsTotal", result.getTotal());
        map.put("recordsFiltered", result.getTotal());
        map.put("draw", draw);
        return gson.toJson(map);
    }


    /**
     *
     * @param list
     * @param draw
     * @return
     */
    public static String getList(List list, int draw){
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", list.size());
        map.put("recordsFiltered", list.size());
        map.put("draw", draw);
        return gson.toJson(map);
    }


    /**
     *
     * @param length
     * @param start
     * @return
     */
    public static PageBounds getPageBounds(int length, int start) {
        int page = 1;
        if(start==0){
            start = 1;
        }
        try {
            if (length <= 0) length = 10;
             page = start / length + 1;
        }catch (Exception e){
            page = 1;
            length = 10;
        }
        PageBounds pageBounds = new PageBounds(page, length, true);
        return pageBounds;
    }

    /**
     *
     * @param start
     * @param length
     * @param number
     * @return
     */
    public  static  boolean checkPaging(int start, int length, int number){
        int page = 1;
        if(start==0){
            start = 1;
        }
        try {
            if (length <= 0){
                length = 10;
            }
            page = start / length + 1;
        }catch (Exception e){
            page = 1;
            length = 10;
        }

        if (page==1 && number <= length){
            return true;
        }

        if (number  > start && number <= (page) * length ) {
            return true;
        }
        return false;
    }
}
