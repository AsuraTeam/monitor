package com.asura.resource.controller;


import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.resource.entity.CmdbResourceCabinetEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceCabinetService;
import com.asura.resource.service.CmdbResourceServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 资源配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/27 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/data/")
public class ResourceIndexController {

    @Autowired
    private CmdbResourceCabinetService cabinetService;

    @Autowired
    private CmdbResourceServerService serverService;


    private SearchMap searchMapNull = new SearchMap();
    private PageBounds pageBoundsNull = PageResponse.getPageBounds(10000, 1);


    private Gson gson = new Gson();

    /**
     * 机柜数据展示
     *
     * @return
     */
    @RequestMapping("index")
    public String index(Model model) {
        ArrayList<String> scopeList  = getCabinetScopeModel(searchMapNull);
        model.addAttribute("scope",scopeList);
        // 按机柜来存储数据
        Map cabinetNameMap = getCabinetNameModel(scopeList,searchMapNull);
        model.addAttribute("name",cabinetNameMap);
        return "/resource/data/index";
    }

    /**
     * 获取搜索的数据
     * @param cabinetScope
     * @param cabinetName
     * @return
     */
    public SearchMap getSearchMap(String cabinetScope,String cabinetName,String search,String nullCabinet, String isOff){
        SearchMap map = new SearchMap();
        if(cabinetScope!=null&&cabinetScope.length()>1) {
            map.put("cabinetScope", cabinetScope);
        }
        if(cabinetName!=null&&cabinetName.length()>1) {
            map.put("cabinetName", cabinetName);
        }
        if(search!=null&&search.length()>1){
            map.put("search",search);
        }
        if(nullCabinet!=null){
            map.put("nullCabinet",1);
        }
        if(isOff!=null&&isOff.contains("1")){
            map.put("isOff",1);
        }
        return map;
    }

    /**
     * 获取机柜名称
     * @param scopeList
     * @return
     */
    public  Map getCabinetNameModel(ArrayList<String> scopeList,SearchMap map){
        // 获取机柜名称


        // 按机柜来存储数据
        Map cabinetNameMap = new HashMap<>();
        PagingResult<CmdbResourceCabinetEntity> cabinetNames = cabinetService.findAll(map,pageBoundsNull);
        for(String scopeName:scopeList) {
            System.out.println(scopeName);
            // 存储机柜名称
            ArrayList nameList = new ArrayList();
            for (CmdbResourceCabinetEntity c : cabinetNames.getRows()) {
                if (!nameList.contains(c.getCabinetName())&& c.getCabinetScope().equals(scopeName) ) {
                    nameList.add(c.getCabinetName());
                }
            }
            cabinetNameMap.put(scopeName,nameList);
        }
        return cabinetNameMap;
    }
    /**
     * 获取机柜区域的数据
     * @return
     */
    public  ArrayList<String>  getCabinetScopeModel(SearchMap map){

        List<CmdbResourceCabinetEntity> result = cabinetService.selectCabinetName(map,"selectCabinetName");
        ArrayList<String> scopeList  = new ArrayList();
        for(CmdbResourceCabinetEntity c:result){
            if(!scopeList.contains(c.getCabinetScope())) {
                scopeList.add(c.getCabinetScope());
            }
        }
        return scopeList;
    }
        /**
         * 机柜数据展示
         *
         * @return
         */
    @RequestMapping("indexSub")
    public String indexSub(Model model,String cabinetScope,String cabinetNameGet,String search,String nullCabinet,String isOff) {

        SearchMap map = getSearchMap(cabinetScope,cabinetNameGet,search,nullCabinet,isOff);
        ArrayList<String> scopeList  = getCabinetScopeModel(map);
        model.addAttribute("scope",scopeList);

        //先获取机柜名字
        ArrayList<String> cname = new ArrayList();
        PagingResult<CmdbResourceCabinetEntity> cabinetName = cabinetService.findAll(map,pageBoundsNull);
        for(CmdbResourceCabinetEntity c:cabinetName.getRows()){
            if(!cname.contains(c.getCabinetName())){
                 cname.add(c.getCabinetName());
            }
        }

        // 按机柜来存储数据
        List<CmdbResourceServerEntity> cabinetData = serverService.getDataList(map,"selectByAll");
        Map cabinetMap = new HashMap<>();
        int length = 12;
        for(String name:cname){
            ArrayList<CmdbResourceServerEntity> rdata = new ArrayList<>();
            for(CmdbResourceServerEntity data:cabinetData){
                length = data.getNumber();
                if(name.equals(data.getCabinetName())){
                    rdata.add(data);
                }
            }

            // 将机柜空的补全数据
            ArrayList<CmdbResourceServerEntity> tempList = new ArrayList<>();
            for(int i=length;i>0;i--){
                boolean is = false;
                for(CmdbResourceServerEntity c:rdata){
                    if(c.getCabinetLevel()==i){
                        if(!tempList.contains(c)) {
                            tempList.add(c);
                            is = true;
                        }
                    }


                }
                if(!is) {
                    CmdbResourceServerEntity temp = new CmdbResourceServerEntity();
                    temp.setCabinetLevel(i);
                    if (!tempList.contains(temp)) {
                        tempList.add(temp);
                    }
                }
            }
            cabinetMap.put(name,tempList);
        }



        // 获取机柜名称
        // 按机柜来存储数据
        Map cabinetNameMap = getCabinetNameModel(scopeList,map);

        model.addAttribute("name",cabinetNameMap);
        model.addAttribute("cabinetData",cabinetMap);
        return "/resource/data/indexSub";
    }

    /**
     * 获取机柜名称
     * @param cabinetScope
    * @return
            */
    @RequestMapping(value="getCabinetName",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getScopeData(String cabinetScope){
        SearchMap map = new SearchMap();
        map.put("cabinetScope",cabinetScope);
        PagingResult<CmdbResourceCabinetEntity> result = cabinetService.findAll(searchMapNull,pageBoundsNull);
        return  gson.toJson(result.getRows());
    }


}
