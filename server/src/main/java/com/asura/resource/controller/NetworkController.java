package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.thread.CommentThread;
import com.asura.resource.entity.CmdbResourceNetworkAddressEntity;
import com.asura.resource.entity.CmdbResourceNetworkEntity;
import com.asura.resource.entity.report.ServerReportEntity;
import com.asura.resource.service.CmdbResourceNetworkAddressService;
import com.asura.resource.service.CmdbResourceNetworkService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.network.NetworkPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
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
 * 资源网络配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/15 18:11:11
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/network/")
public class NetworkController {

    @Autowired
    private CmdbResourceNetworkService service;

    @Autowired
    private CmdbResourceNetworkAddressService networkAddressService;

    @Autowired
    private PermissionsCheck permissionsCheck;

    private Gson gson = new Gson();


    /**
     * 列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/resource/configure/network/list";
    }

    public PagingResult<CmdbResourceNetworkEntity> getData(int length, int start,String sqlId) {
        PageBounds pageBounds = PageResponse.getPageBounds(length,start);
        SearchMap searchMap = new SearchMap();
        return service.findAll(searchMap, pageBounds, sqlId);
    }

    /**
     * 获取IP地址使用的和未使用的
     * @param type
     * @return
     */
    @RequestMapping("getIpStatus")
    @ResponseBody
    public String getIpStatus(int type,int networkId){
        PageBounds pageBounds = PageResponse.getPageBounds(10000,1);
        SearchMap searchMap = new SearchMap();
        searchMap.put("networkId",networkId);
        searchMap.put("type",type);
        PagingResult<CmdbResourceNetworkAddressEntity> result = networkAddressService.findAll(searchMap,pageBounds,"selectByAll");
        Map map = new HashMap();
        map.put("data", result.getRows());
        return JsonEntityTransform.Object2Json(map);
    }

    /**
     *
     * @return
     */
    public ArrayList getNetwork(){

        PagingResult<CmdbResourceNetworkEntity> network1 = getData(10000, 1,"selectNetwork");
        //  先获取网段
        ArrayList<String> network = new ArrayList<>();
        for(CmdbResourceNetworkEntity c:network1.getRows()) {
            if(!network.contains(c.getNetworkPrefix())) {
                network.add(c.getNetworkPrefix());
            }
        }
        return network;
    }

    /**
     * 列表数据
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PagingResult<CmdbResourceNetworkEntity> result = getData(length*2, start, "selectByAll");

        PagingResult<CmdbResourceNetworkEntity> network1 = getData(10000, 1,"selectNetwork");
        //  先获取网段
        ArrayList<String> network = getNetwork();


        ArrayList<String> netTag = new ArrayList<>();
        ArrayList<CmdbResourceNetworkEntity> list = new ArrayList<>();
        for(String n:network) {
            CmdbResourceNetworkEntity entity = new CmdbResourceNetworkEntity();
            for (CmdbResourceNetworkEntity c : result.getRows()) {

                if(c.getNetworkPrefix().equals(n)) {
                    System.out.println(c.getNetworkPrefix());
                    if(entity.getUsed()==0&&entity.getFree()==0){
                        entity.setNetworkId(c.getNetworkId());
                        entity.setNetworkPrefix(c.getNetworkPrefix());
                        entity.setDescription(c.getDescription());
                        entity.setLastModifyTime(c.getLastModifyTime());
                        entity.setNetworkSuffix(c.getNetworkSuffix());
                    }
                    if(c.getStatus()==1) {
                        netTag.add(c.getNetworkPrefix());
                        entity.setUsed(c.getCnt());
                    }
                    if(c.getStatus()==0) {
                        netTag.add(c.getNetworkPrefix());
                        entity.setFree(c.getCnt());
                    }
                }
            }
            if(entity.getNetworkPrefix()!=null) {
                list.add(entity);
            }
        }

        // 补充没有的数据
            for(CmdbResourceNetworkEntity c:network1.getRows()) {
                if(!netTag.contains(c.getNetworkPrefix())){
                    list.add(c);
                }
            }

        Map map = new HashMap();
        map.put("data", list);
        map.put("recordsTotal", list.size());
        map.put("recordsFiltered", list.size());
        map.put("draw", draw);
        return JsonEntityTransform.Object2Json(map);
    }

    @RequestMapping(value = "ping", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void ping() {
        String prefix;
        String ip;
        PagingResult<CmdbResourceNetworkEntity> result = getData(10000, 1,"selectNetwork");
        NetworkPing ping;

        PagingResult<CmdbResourceNetworkAddressEntity> add = networkAddressService.findAll(null, null, "selectByAll");
        ArrayList<String> addList = new ArrayList<>();
        for (CmdbResourceNetworkAddressEntity c : add.getRows()) {
            addList.add(c.getIpPrefixId() + "" + c.getIpSubffix());
        }

        for (CmdbResourceNetworkEntity c : result.getRows()) {
            prefix = c.getNetworkPrefix();

            ping = new NetworkPing(networkAddressService, c.getNetworkId(), addList,prefix);
            ping.start();
            CommentThread.sleep();
        }
    }

    /**
     * 添加页面
     *
     * @return
     */
    @RequestMapping("add")
    public String add(Model model) {
        return "/resource/configure/network/add";
    }


    /**
     * 保存
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceNetworkEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());

        entity.setCreateUser(user);
        if (entity.getNetworkId() != null) {
            service.update(entity);
        } else {
            entity.setCreateTime(BigInteger.valueOf(DateUtil.getDateStampInteter()));
            service.save(entity);
        }
        return ResponseVo.responseOk(null);
    }

    /**
     * @param id
     *
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model) {
        CmdbResourceNetworkEntity result = service.findById(id, CmdbResourceNetworkEntity.class);
        String[] prefix = result.getNetworkPrefix().split("\\.");
        model.addAttribute("input1", prefix[0]);
        model.addAttribute("input2", prefix[1]);
        model.addAttribute("input3", prefix[2]);
        model.addAttribute("configs", result);
        return "/resource/configure/network/add";
    }


    /**
     * 生产画饼图的数据
     */
    public ArrayList setServerEntity(List<CmdbResourceNetworkEntity> result){
        ArrayList list = new ArrayList();
        ServerReportEntity entity;
        for(CmdbResourceNetworkEntity c:result){
            entity = new ServerReportEntity();
            if(c.getStatus()==0) {
                entity.setName("free");
            }else {
                entity.setName("used");
            }

            entity.setY(c.getCnt());
            list.add(entity);
        }
        return list;
    }

    /**
     *
     * @param prefix
     * @return
     */
    @RequestMapping(value = "countDataReport",produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String countDataReport(String prefix){
        SearchMap searchMap = new SearchMap();
        searchMap.put("networkPrefix",prefix);
        List<CmdbResourceNetworkEntity> result = service.getDataList(searchMap,"selectByAll");
        ArrayList list = setServerEntity(result);
        return gson.toJson(list);
    }
}
