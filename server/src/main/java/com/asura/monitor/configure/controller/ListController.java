package com.asura.monitor.configure.controller;

import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.util.RedisUtil;
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
 * 所有监控list页面
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/20 08:00:00
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/configure/")
public class ListController {


    /**
     * 模板列表
     * @return
     */
    @RequestMapping("template/list")
    public String templateList(){
        return "monitor/configure/template/list";
    }

    /**
     * 监控组配置
     * @return
     */
    @RequestMapping("groups/list")
    public String groupsList(){
        return "monitor/configure/groups/list";
    }


    /**
     * 主机配置
     * @return
     */
    @RequestMapping("hosts/list")
    public String hostsList(){
        return "monitor/configure/hosts/list";
    }



    /**
     * 联系人配置
     * @return
     */
    @RequestMapping("contacts/list")
    public String contactsList(){
        return "monitor/configure/contacts/list";
    }

    /**
     * 联系组配置
     * @return
     */
    @RequestMapping("contactGroup/list")
    public String contactGroupList(){
        return "monitor/configure/contactGroup/list";
    }

    /**
     * 消息通道配置
     * @return
     */
    @RequestMapping("messages/list")
    public String messagesList(){
        return "monitor/configure/messages/list";
    }

    /**
     * 报警记录查看
     * @return
     */
    @RequestMapping("messages/record")
    public String messagesRecord(Model model){
        RedisUtil redisUtil = new RedisUtil();
        String alarmSwitch  = redisUtil.get(MonitorCacheConfig.cacheAlarmSwitch);
        model.addAttribute("alarm", alarmSwitch);
        return "monitor/configure/messages/record";
    }

    /**
     * 脚本配置
     * @return
     */
    @RequestMapping("script/list")
    public String scriptList(){
        return "monitor/configure/script/list";
    }

    /**
     * 项目配置
     * @return
     */
    @RequestMapping("item/list")
    public String itemList(){
        return "monitor/configure/item/list";
    }


    /**
     * 监控信息
     * @return
     */
    @RequestMapping("information/list")
    public String informationList(){
        return "monitor/configure/information/list";
    }

    /**
     * 监控配置
     * @return
     */
    @RequestMapping("configure/list")
    public String configureList(){
        return "monitor/configure/configure/list";
    }

}
