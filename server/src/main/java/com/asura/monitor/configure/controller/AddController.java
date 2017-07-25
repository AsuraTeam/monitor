package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.entity.*;
import com.asura.monitor.configure.service.*;
import com.asura.resource.controller.ServerController;
import com.asura.resource.service.CmdbResourceEntnameService;
import com.asura.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 所有监控添加配置页面
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/20 18:20:11
 * @since 1.0:
 */
@Controller
@RequestMapping("/monitor/configure/")
public class AddController {

    @Autowired
    private com.asura.monitor.configure.service.MonitorInformationService informationService;

    @Autowired
    private MonitorItemService itemService;

    @Autowired
    private MonitorConfigureService configureService;

    @Autowired
    private MonitorGroupsService groupsService;

    @Autowired
    private MonitorScriptsService scriptsService;

    @Autowired
    private MonitorContactGroupService contactGroupService;

    @Autowired
    private MonitorContactsService contactsService;

    @Autowired
    private MonitorMessageChannelService channelService;

    @Autowired
    private MonitorTemplateService templateService;

    @Autowired
    private ServerController serverController;

    @Autowired
    private MonitorAlarmConfigureService alarmConfigureService;

    private SearchMap searchMapNull = new SearchMap();
    private PageBounds pageBoundsNull = PageResponse.getPageBounds(10000, 1);

    /**
     * 模板列表
     *
     * @return
     */
    @RequestMapping("template/add")
    public String templateAdd(int id, Model model) {
        if (id > 0) {
            MonitorTemplateEntity result = templateService.findById(id, MonitorTemplateEntity.class);
            model.addAttribute("configs", result);
        }
        model.addAttribute("items", itemService.findAll(searchMapNull, pageBoundsNull, "selectByAll").getRows());
        return "monitor/configure/template/add";
    }

    /**
     *
     */
    @RequestMapping("template")
    public String add() {
        return "monitor/configure/messages/template";
    }

    /**
     * 监控组配置
     *
     * @return
     */
    @RequestMapping("groups/add")
    public String groupsAdd(int id, Model model) {
        if (id > 0) {
            MonitorGroupsEntity result = groupsService.findById(id, MonitorGroupsEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/configure/groups/add";
    }


    /**
     * 消息通道配置
     *
     * @return
     */
    @RequestMapping("messages/add")
    public String messagesAdd(int id, Model model) {
        if (id > 0) {
            MonitorMessageChannelEntity result = channelService.findById(id, MonitorMessageChannelEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/configure/messages/add";
    }


    /**
     * 联系人配置
     *
     * @return
     */
    @RequestMapping("contacts/add")
    public String contactsAdd(int id, Model model) {
        if (id > 0) {
            MonitorContactsEntity result = contactsService.findById(id, MonitorContactsEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/configure/contacts/add";
    }

    /**
     * 联系组配置
     *
     * @return
     */
    @RequestMapping("contactGroup/add")
    public String contactGroupAdd(int id, Model model) {
        if (id > 0) {
            MonitorContactGroupEntity result = contactGroupService.findById(id, MonitorContactGroupEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/configure/contactGroup/add";
    }


    /**
     * 脚本配置
     *
     * @return
     */
    @RequestMapping("script/add")
    public String scriptAdd(int id, Model model) {
        if (id > 0) {
            MonitorScriptsEntity result = scriptsService.findById(id, MonitorScriptsEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/configure/script/add";
    }

    /**
     * 项目配置
     *
     * @return
     */
    @RequestMapping("item/add")
    public String itemAdd(int id, Model model) {
        if (id > 0) {
            MonitorItemEntity result = itemService.findById(id, MonitorItemEntity.class);
            model.addAttribute("configs", result);
        }
        model.addAttribute("scripts", scriptsService.findAll(searchMapNull, pageBoundsNull, "selectByAll").getRows());
        return "monitor/configure/item/add";
    }

    /**
     *
     * 对不同类型的报警
     * @return
     */
    @RequestMapping("alarm/add")
    public String alarmAdd(int id, Model model) {
        if (id > 0) {
            MonitorAlarmConfigureEntity result = alarmConfigureService.findById(id, MonitorAlarmConfigureEntity.class);
            model.addAttribute("configs", result);
        }
        serverController.getData(model);
        return "monitor/configure/alarm/add";
    }



    /**
     * 监控配置
     *
     * @return
     */
    @RequestMapping("configure/add")
    public String configureAdd(int id, Model model, String copy) {
        if (id > 0) {
            MonitorConfigureEntity result = configureService.findById(id, MonitorConfigureEntity.class);
            if (result.getScriptId() != null) {
                MonitorScriptsEntity scriptsEntity = scriptsService.findById(result.getScriptId(), MonitorScriptsEntity.class);
                model.addAttribute("fileName", scriptsEntity.getFileName());
            }
            if (CheckUtil.checkString(copy)){
                result.setConfigureId(0);
            }
            model.addAttribute("configs", result);
        }
        model.addAttribute("scripts", scriptsService.findAll(searchMapNull, pageBoundsNull, "selectByAll").getRows());
        return "monitor/configure/configure/add";
    }
}
