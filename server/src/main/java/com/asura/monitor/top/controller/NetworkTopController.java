package com.asura.monitor.top.controller;

import com.asura.framework.base.paging.SearchMap;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoyun on 2017/7/18.
 */
@Controller
@RequestMapping("/monitor/network/top/")
public class NetworkTopController {

    @Autowired
    private CmdbResourceServerService resourceServerService;

    /**
     * 网络top图
     * @param model
     * @return
     */
    @RequestMapping("network")
    public String network(Model model){
        SearchMap searchMap = new SearchMap();
        searchMap.put("typeName", "防火墙");
        List<CmdbResourceServerEntity> firewall = resourceServerService.getDataList(searchMap, "selectByAll");
        model.addAttribute("firewall", firewall.get(0).getIpAddress());
        searchMap.put("typeName", "核心交换机");
        List<CmdbResourceServerEntity> core = resourceServerService.getDataList(searchMap, "selectByAll");
        model.addAttribute("core", core.get(0).getIpAddress());
        searchMap.put("typeName", "汇聚交换机");
        List<CmdbResourceServerEntity> convergence = resourceServerService.getDataList(searchMap, "selectByAll");
        ArrayList list = new ArrayList();
        Map accessMap = new HashMap();
        ArrayList accessList ;
        for (CmdbResourceServerEntity entity:convergence) {
            list.add(entity.getIpAddress());
            searchMap.put("ipAddress", entity.getIpAddress());
            List<CmdbResourceServerEntity> selectNetworkIp = resourceServerService.getDataList(searchMap, "selectNetworkIp");
            accessList = new ArrayList();
            for (CmdbResourceServerEntity serverEntity: selectNetworkIp){
                accessList.add(serverEntity.getIpAddress());
            }
            accessMap.put(entity.getIpAddress(), accessList);

        }
        model.addAttribute("convergence", list);
        model.addAttribute("access", accessMap);
        return "/monitor/top/network";
    }
}
