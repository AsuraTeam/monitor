package com.asura.monitor.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  监控数据管理
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/23 18:40
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/data/")
public class MonitorController {


    /**
     * 列表页面
     * @return
     */
    @RequestMapping("list")
    public String list(Model model, String groups,String status, String groupsName){
        model.addAttribute("groups",groups);
        model.addAttribute("status",status);
        model.addAttribute("groupsName",groupsName);
        return "monitor/data/list";
    }
}
