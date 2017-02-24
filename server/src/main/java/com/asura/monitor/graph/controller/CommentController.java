package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.util.DateUtil;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.service.CmdbResourceGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.asura.monitor.graph.util.FileRender.readHistory;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  公用的数据
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/05 18:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/graph/")
public class CommentController {


    @Autowired
    private CmdbResourceGroupsService groupsService;

    private Gson gson = new Gson();

    public static Model getGroups(Model model,CmdbResourceGroupsService groupsService){
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000, 1);
        PagingResult<CmdbResourceGroupsEntity> groups = groupsService.findAll(searchMap, pageBounds);
        model.addAttribute("groups", groups.getRows());
        return model;
    }

    public static SearchMap getSearchMap(String groups, String ip, String name, HttpServletRequest request){
        SearchMap searchMap = new SearchMap();
        if(groups!=null&&groups.length()>1){
            searchMap.put("groupsName",groups);
        }

        // 模糊匹配
        String search = request.getParameter("search[value]");
        if(search!=null&&search.length()>1){
            searchMap.put("search",search);
        }
        // 匹配名称
        if(name!=null&&name.length()>0){
            searchMap.put("name",name);
        }

        // 匹配Ip
        if(ip!=null&&ip.length()>10){
            searchMap.put("ipAddress",ip);
        }
        return searchMap;
    }

    /**
     * @return
     */
    @RequestMapping(value="historyData",produces={"application/json;charset=utf8"})
    @ResponseBody
    public String historyData(String ip,String name,String startT,String endT,String totle,String type){
        if(startT==null || startT.length()<6){
            startT = DateUtil.getDay();
            endT = DateUtil.getDay();
        }
        if(ip==null||name==null){
            return "参数不完整";
        }
        ArrayList result =  readHistory(ip,type,name,startT,endT,totle);
        return gson.toJson(result);
    }


    /**
     * 页面
     * @returnh
     */
    @RequestMapping("cpu/list")
    public String list(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/cpu/list";
    }

    /**
     * 页面
     * @return
     */
    @RequestMapping("memory/list")
    public String memlist(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/memory/list";
    }

    /**
     * 页面
     * @return
     */
    @RequestMapping("swap/list")
    public String swaplist(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/swap/list";
    }

    /**
     * 页面
     * @returnh
     */
    @RequestMapping("loadavg/list")
    public String loadavglist(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/loadavg/list";
    }


    /**
     * 页面
     * @return
     */
    @RequestMapping("disk/list")
    public String diskUse(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/disk/list";
    }

    /**
     * 页面
     * @return
     */
    @RequestMapping("traffic/list")
    public String trafficUse(Model model){
        model = CommentController.getGroups(model,groupsService);
        return "monitor/graph/traffic/list";
    }
}
