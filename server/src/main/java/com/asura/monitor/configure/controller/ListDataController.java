package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MonitorConfigureEntity;
import com.asura.monitor.configure.entity.MonitorContactGroupEntity;
import com.asura.monitor.configure.entity.MonitorContactsEntity;
import com.asura.monitor.configure.entity.MonitorGroupsEntity;
import com.asura.monitor.configure.entity.MonitorInformationEntity;
import com.asura.monitor.configure.entity.MonitorItemEntity;
import com.asura.monitor.configure.entity.MonitorMessageChannelEntity;
import com.asura.monitor.configure.entity.MonitorMessagesEntity;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
import com.asura.monitor.configure.entity.MonitorTemplateEntity;
import com.asura.monitor.configure.service.MonitorConfigureService;
import com.asura.monitor.configure.service.MonitorContactGroupService;
import com.asura.monitor.configure.service.MonitorContactsService;
import com.asura.monitor.configure.service.MonitorGroupsService;
import com.asura.monitor.configure.service.MonitorItemService;
import com.asura.monitor.configure.service.MonitorMessageChannelService;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.configure.service.MonitorTemplateService;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
 * 所有监控listData数据页面
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/20 16:00:00
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/configure/")
public class ListDataController {

    @Autowired
    private com.asura.monitor.configure.service.MonitorInformationService informationService;

    @Autowired
    private MonitorItemService itemService;

    @Autowired
    private MonitorGroupsService groupsService;

    @Autowired
    private MonitorScriptsService scriptsService;

    @Autowired
    private MonitorContactsService contactsService;

    @Autowired
    private MonitorTemplateService templateService;

    @Autowired
    private MonitorConfigureService configureService;

    @Autowired
    private MonitorMessageChannelService channelService;

    @Autowired
    private MonitorContactGroupService contactGroupService;

    @Autowired
    private CmdbResourceServerService serverService;

    @Autowired
    private com.asura.monitor.configure.service.MonitorMessagesService messagesService;

    private Gson gson = new Gson();

    private RedisUtil redisUtil = new RedisUtil();

    /**
     * 模板列表
     *
     * @return
     */
    @RequestMapping(value = "template/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String templateListData(int draw, int start, int length, String key, String id) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (key != null && key.length() > 2) {
            searchMap.put("key", key);
        }
        if (id != null && id.length() > 0) {
            searchMap.put("ids", id.split(","));
        }
        PagingResult<MonitorTemplateEntity> result = templateService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取模板的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "template/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getTemplateCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheTemplateKey + name);
    }


    /**
     * 监控组配置
     *
     * @return
     */
    @RequestMapping(value = "groups/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String groupsListData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorGroupsEntity> result = groupsService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }


    /**
     * 获取监控组的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "groups/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupsCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheGroupsKey + name);
    }

    /**
     * 联系人配置
     *
     * @return
     */
    @RequestMapping(value = "contacts/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String contactsListData(int draw, int start, int length, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 0) {
            searchMap.put("key", search);
        }
        PagingResult<MonitorContactsEntity> result = contactsService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取联系人的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "contacts/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getContactsCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheContactKey + name);
    }


    /**
     * 联系人获取
     *
     * @return
     */
    @RequestMapping(value = "contacts/getContacts", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getContacts(int id, String key) {
        PageBounds pageBounds = PageResponse.getPageBounds(100000, 1);
        SearchMap searchMap = new SearchMap();
        if (key.length() > 0) {
            searchMap.put("key", key);
        }
        if (id > 0) {
            MonitorContactGroupEntity entity = contactGroupService.findById(id, MonitorContactGroupEntity.class);
            String member = entity.getMember();
            if (member != null) {
                searchMap.put("member", member.split(","));
            }
        }
        PagingResult<MonitorContactsEntity> result = contactsService.findAll(searchMap, pageBounds, "selectByAll");
        Map map = new HashMap<>();
        map.put("data", result.getRows());
        return gson.toJson(map);
    }

    /**
     * 联系组配置
     *
     * @return
     */
    @RequestMapping(value = "contactGroup/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String contactGroupListData(int draw, int start, int length, String groupsId, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (groupsId != null) {
            String[] groups = groupsId.split(",");
            searchMap.put("groupsIds", groups);
        }
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 0) {
            searchMap.put("key", search);
        }
        PagingResult<MonitorContactGroupEntity> result = contactGroupService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取联系人的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "contactGroup/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getContactGroupCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheContactGroupKey + name);
    }


    /**
     * 脚本配置
     *
     * @return
     */
    @RequestMapping(value = "script/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String scriptList(int draw, int start, int length, String scriptsId, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (scriptsId != null) {
            searchMap.put("scriptsId", Integer.valueOf(scriptsId));
        }
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 0) {
            searchMap.put("key", search);
        }
        PagingResult<MonitorScriptsEntity> result = scriptsService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取脚本的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "script/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getScriptCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheScriptKey + name);
    }

    /**
     * 项目配置
     *
     * @return
     */
    @RequestMapping(value = "item/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String itemList(int draw, int start, int length, String scriptsId, String key, String itemId, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (scriptsId != null) {
            searchMap.put("scriptsId", Integer.valueOf(scriptsId));
        }
        if (key != null && key.length() > 2) {
            searchMap.put("key", key);
        }
        if (itemId != null && itemId.length() > 0) {
            searchMap.put("itemId", itemId);
        }
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 0) {
            searchMap.put("key", search);
        }
        PagingResult<MonitorItemEntity> result = itemService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 获取模板的缓存
     *
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "item/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getItemCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheItemKey + name);
    }


    /**
     * 获取模板里的项目
     *
     * @param id
     *
     * @return
     */
    @RequestMapping(value = "template/getTemplateData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getTemplateData(int id, String type) {
        MonitorTemplateEntity result = templateService.findById(id, MonitorTemplateEntity.class);
        String[] itemList = result.getItems().split(",");
        SearchMap searchMap = new SearchMap();
        searchMap.put("items", itemList);
        ArrayList list = new ArrayList();
        if (type.equals("item")) {
            List<MonitorItemEntity> items = itemService.findAll(searchMap, PageResponse.getPageBounds(10000, 1), "selectByAll").getRows();
            for (MonitorItemEntity m : items) {
                Map map = new HashMap();
                map.put("id", m.getItemId());
                map.put("name", m.getItemName());
                list.add(map);
            }

        } else {

            List<MonitorTemplateEntity> items = templateService.findAll(searchMap, PageResponse.getPageBounds(10000, 1), "selectByAll").getRows();
            for (MonitorTemplateEntity m : items) {
                Map map = new HashMap();
                map.put("id", m.getTemplateId());
                map.put("name", m.getTemplateName());
                list.add(map);
            }
        }
        return gson.toJson(list);
    }

    /**
     * 消息通道
     *
     * @return
     */
    @RequestMapping(value = "messages/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String messagesList(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorMessageChannelEntity> result = channelService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 消息通道
     *
     * @return
     */
    @RequestMapping(value = "messages/recordData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String recordData(int draw, int start, int length, HttpServletRequest request, String startTime, String endTime) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        String search = request.getParameter("search[value]");
        if (search != null && search.length() > 1) {
            searchMap.put("search", search);
        }
        if (startTime != null && startTime.length() > 1) {
            searchMap.put("startT", startTime);
            searchMap.put("endT", endTime);
        }
        PagingResult<MonitorMessagesEntity> result = messagesService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * @param messagesId
     * @param model
     *
     * @return
     */
    @RequestMapping("messages/detail")
    public String messagesDetail(int messagesId, Model model) {
        MonitorMessagesEntity messagesEntity = messagesService.findById(messagesId, MonitorMessagesEntity.class);
        model.addAttribute("configs", messagesEntity);
        return "monitor/configure/messages/detail";
    }

    /**
     * 监控信息
     *
     * @return
     */
    @RequestMapping(value = "information/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String informationList(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorInformationEntity> result = informationService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 看监控配置是否有搜索IP地址
     *
     * @param key
     *
     * @return
     */
    boolean checkIpSearch(String key) {
        for (int i = 0; i < 9; i++) {
            if (key.contains(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 监控配置
     *
     * @return
     */
    @RequestMapping(value = "configure/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String configureList(int draw, int start, int length, String groupsName, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (groupsName != null && groupsName.length() > 1) {
            searchMap.put("groupsName", groupsName);
        }
        String description = request.getParameter("search[value]");
        List<String> ips = new ArrayList<>();
        if (description != null && description.length() > 1) {
            searchMap.put("description", description);
            if (checkIpSearch(description)) {
                searchMap.put("ipAddress", description);
                List<CmdbResourceServerEntity> serverIdsEntity = serverService.getDataList(searchMap, "selectByIp");
                for (CmdbResourceServerEntity entity : serverIdsEntity) {
                    ips.add(String.valueOf(entity.getServerId()));
                }
            }
        }
        PagingResult<MonitorConfigureEntity> result = configureService.findAll(searchMap, pageBounds, "selectByAll");
        boolean isData;
        List<MonitorConfigureEntity> list = new ArrayList<>();
        if (result.getTotal() < 1) {
            for (String ip : ips) {
                searchMap = new SearchMap();
                searchMap.put("ip", ip);
                result = configureService.findAll(searchMap, pageBounds, "selectByAll");
                if (result != null && result.getTotal() > 0) {
                    for (MonitorConfigureEntity configureEntity:result.getRows()) {
                        list.add(configureEntity);
                    }
                }
            }
            return PageResponse.getList(list, draw);
        }
        return PageResponse.getMap(result, draw);
    }

    /**
     * @param name
     *
     * @return
     */
    @RequestMapping(value = "configure/getCache", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getConfigureCache(String name) {
        return redisUtil.get(MonitorCacheConfig.cacheConfigureKey + name);
    }


}
