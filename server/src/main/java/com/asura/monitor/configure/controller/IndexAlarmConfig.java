package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.monitor.configure.entity.MonitorIndexAlarmEntity;
import com.asura.monitor.configure.service.MonitorIndexAlarmService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 监控指标报警
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/monitor/index/alarm/")
public class IndexAlarmConfig {

    @Autowired
    private   MonitorIndexAlarmService alarmService;

    @Autowired
    private PermissionsCheck permissionsCheck;

    // 报错报警的参数信息，启动或更改时改数据
    public  static Map<String, MonitorIndexAlarmEntity> alarmEntities;
    public  static  long alarmEntitiesTime;


    /**
     * 监控组配置
     *
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model) {
        if (id > 0) {
            MonitorIndexAlarmEntity result = alarmService.findById(id, MonitorIndexAlarmEntity.class);
            model.addAttribute("configs", result);
        }
        return "monitor/index/alarm/add";
    }


    /**
     * 指标配置列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "monitor/index/alarm/list";
    }



    /**
     * 查看配置
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorIndexAlarmEntity> result = alarmService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }


    /**
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String save(MonitorIndexAlarmEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getAlarmId() != null) {
            alarmService.update(entity);
        } else {
            alarmService.save(entity);
        }
        return "ok";
    }
}
