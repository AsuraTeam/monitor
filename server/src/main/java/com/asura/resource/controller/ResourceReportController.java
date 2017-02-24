package com.asura.resource.controller;

import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.util.DateUtil;
import com.asura.resource.entity.CmdbResourceNetworkEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceNetworkService;
import com.asura.resource.service.CmdbResourceServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
 *  资源配置报表
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/report/")
public class ResourceReportController {


    @Autowired
    private CmdbResourceServerService serverService;

    private Gson gson = new Gson();

    private SearchMap searchMapNull = new SearchMap();
    private PageBounds pageBoundsNull = PageResponse.getPageBounds(100000,1);

    @Autowired
    private NetworkController controller = new NetworkController();

    @Autowired
    private CmdbResourceNetworkService networkService ;

    /**
     * 报表首页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){

        // 总数
        CmdbResourceServerEntity totle = serverService.countResourceTotle();
        model.addAttribute("totle",totle.getCnt());

        // 计算关机的数量
        CmdbResourceServerEntity isOff = serverService.countResourceIsOff();
        model.addAttribute("isOff",isOff.getCnt());

        // 计算没有在列表的数量
        List<CmdbResourceServerEntity> r = serverService.getDataList(null,"selectNorecordCount");
        if(r.size()>0) {
            model.addAttribute("noRecord", r.get(0).getCnt());
        }else {
            model.addAttribute("noRecord", 0);
        }
        return "/resource/report/index";
    }


    /**
     * 网络地址使用报表
     * @return
     */
    @RequestMapping("network")
    public String network(Model model){
        ArrayList arr = controller.getNetwork();
        List<CmdbResourceNetworkEntity> list = networkService.getDataList(searchMapNull,"selectAllUseFree");
        int used = 0;
        int free = 0;
        for (CmdbResourceNetworkEntity e:list){
            if(e.getStatus()==0){
                used = e.getCnt();

            }
            if(e.getStatus()==1){
                free = e.getCnt();

            }
        }

        List<CmdbResourceNetworkEntity> result = networkService.getDataList(searchMapNull,"selectCountNetwork");
        model.addAttribute("used",used);
        if(result.size()>0) {
            model.addAttribute("networkNumber", result.get(0).getCnt());
        }else {
            model.addAttribute("networkNumber", 0);
        }
        model.addAttribute("used",used);
        model.addAttribute("free",free);
        model.addAttribute("totle",used+free);
        model.addAttribute("addList",arr);
        return "/resource/report/network";
    }


    /**
     * 公用，list数据
     * @param result
     * @param draw
     * @return
     */
    public String  getCountGroupMap(List<CmdbResourceServerEntity> result, int draw){
        Map map = new HashMap();
        map.put("data", result);
        map.put("recordsFiltered", 0);
        map.put("recordsTotal", 0);
        map.put("draw", draw);
        return gson.toJson(map);
    }

    /**
     * 获取业务分组的数据
     * @return
     */
    @RequestMapping(value="groupsData",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String groupData(int draw, String entName){
        SearchMap searchMap = new SearchMap();
        if(entName!=null&&entName.length()>1){
            searchMap.put("entName",entName);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(searchMap,"reportGroups");
        return getCountGroupMap(result,1);
    }

    /**
     * 按环境分组
     * @param draw
     * @return
     */
    @RequestMapping(value="entnameData",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String entnameData(int draw,String groupsName){
        SearchMap searchMap = new SearchMap();
        if(groupsName!=null&&groupsName.length()>1) {
            searchMap.put("groupsName", groupsName);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(searchMap,"reportEntName");
        return getCountGroupMap(result,1);
    }

    /**
     * 按服务类型分组
     * @param draw
     * @return
     */
    @RequestMapping(value="reportServiceName",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String reportServiceName(int draw, String groupsName){
        SearchMap searchMap = new SearchMap();
        if(groupsName!=null&&groupsName.length()>1) {
            searchMap.put("groupsName", groupsName);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(searchMap,"reportServiceName");
        return getCountGroupMap(result,1);
    }

    /**
     * 获取不同类型的数据
     * @return
     */
    @RequestMapping(value="typeData",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String typeData(int draw, String groups){
        SearchMap map = new SearchMap();
        if(groups!=null&&groups.length()>1) {
            map.put("groupsName", groups);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(map,"reportTypeName");
        return getCountGroupMap(result,1);
    }

    /**
     * 获取每个月的创建数量
     * @return
     * @throws Exception
     */
    public  ArrayList[] getReportData(SearchMap map) throws  Exception{

        List<CmdbResourceServerEntity> result = serverService.getDataList(map,"reportMonth");
        ArrayList[] set = new ArrayList[12];
        String[] tList;
        int count = 0;
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] t = new String[2];
        String[] day = DateUtil.getDay().split("-");

        long hours ;
        int cnt;

        for(int i=1;i<13;i++){
            String mT = i+"";
            if(i<10){
                mT  = "0"+mT;
            }

            t[1] = day[0]+"-"+mT;
            cnt = 0;

            ArrayList temp = new ArrayList();
            for(CmdbResourceServerEntity c:result) {
                String month = c.getDates();
                if(month.equals(t[1])) {
                    cnt = c.getCnt();
                    System.out.println(month + "-01 00:00:00");
                    hours = dateFormat.parse(month + "-01 00:00:00").getTime();
                    temp.add(hours);
                    System.out.println("hours");
                    System.out.println(hours);
                    temp.add(cnt);
                }
            }
            System.out.println(t[1]);
            hours = dateFormat.parse(t[1] + "-01 00:00:00").getTime();
            if(temp.size()<1){
                temp.add(hours);
                System.out.println(hours);
                temp.add(cnt);
            }
            set[count] = temp;
            count += 1;
        }
        return set;
    }



    /**
     * 获取创建数量，按月，天，年
     * @return
     */
    @RequestMapping("createData")
    @ResponseBody
    public String createData() throws  Exception{
        SearchMap searchMap = new SearchMap();
        searchMap.put("format","year");
        searchMap.put("dates","2016");
        ArrayList[] result = getReportData(searchMap);
        return gson.toJson(result);
    }

    /**
     * 获取每个机柜的数据
     * @return
     */
    @RequestMapping(value="cabinetData",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String cabinetData(String cabinetName){
        SearchMap searchMap = new SearchMap();
        if(cabinetName != null) {
            searchMap.put("cabinetName", cabinetName);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(searchMapNull,"selectByAll");
        return gson.toJson(result);
    }

    /**
     * 获取机房的数据
     * @return
     */
    @RequestMapping(value="floorData",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String floorData(){
        return "";
    }

    /**
     * 获取用户的数据
     * @return
     */
    @RequestMapping(value="selectUserCount",produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String selectUserCount(String groupsName, String  entName){
        SearchMap searchMap = new SearchMap();
        if(groupsName!=null&&groupsName.length()>1) {
            searchMap.put("groupsName", groupsName);
        }
        if(entName!=null&&entName.length()>1){
            searchMap.put("entName",entName);
        }
        List<CmdbResourceServerEntity> result = serverService.getDataList(searchMap,"reportUserName");
        return getCountGroupMap(result,1);
    }

    /**
     * ip地址使用top图
     * @return
     */
    @RequestMapping("networkUse")
    public String networkUse(){
        return "resource/report/network";
    }
}
