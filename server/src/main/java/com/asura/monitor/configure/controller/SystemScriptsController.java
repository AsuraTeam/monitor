package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.entity.MonitorScriptsEntity;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.configure.entity.MonitorSystemScriptsEntity;
import com.asura.monitor.configure.service.MonitorSystemScriptsService;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import static com.asura.monitor.graph.util.FileRender.readFile;
import static com.asura.monitor.graph.util.FileWriter.dataDir;
import static com.asura.monitor.graph.util.FileWriter.separator;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/scripts/")
public class SystemScriptsController {

    @Autowired
    private MonitorScriptsService monitorScriptsService;

    @Autowired
    private MonitorSystemScriptsService scriptsService;

    @Autowired
    private PermissionsCheck permissionsCheck;
    /**
     * 脚本配置
     * @return
     */
    @RequestMapping("add")
    public String add(int id,Model model){
        if(id>0){
            MonitorSystemScriptsEntity result = scriptsService.findById(id, MonitorSystemScriptsEntity.class);
            model.addAttribute("configs",result);
        }
        return "monitor/scripts/add";
    }

    /**
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "monitor/scripts/list";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<MonitorSystemScriptsEntity> result = scriptsService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }


    /**
     * 脚本配置
     *
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo scriptSave(MonitorSystemScriptsEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getScriptsId() != null) {
            scriptsService.update(entity);
        } else {
            scriptsService.save(entity);
        }

        return ResponseVo.responseOk(null);
    }


    /**
     *
     * @param os
     * @return
     * @throws Exception
    */
    @RequestMapping(value = "api/scripts", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getScripts(String os, String force, String scriptsId) throws Exception{
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(2, 1);
        if (scriptsId==null) {
            String tempDir = System.getProperty("java.io.tmpdir") + FileRender.separator;
            Gson gson = new Gson();
            searchMap.put("os", os);
            String file = tempDir + "scripts_data" + FileRender.replace(os);
            if (FileRender.checkCacheFileTime(file, 1800)) {
                PagingResult<MonitorSystemScriptsEntity> result = scriptsService.findAll(searchMap, pageBounds, "selectByAll");
                MonitorSystemScriptsEntity entity = result.getRows().get(0);
                FileWriter.writeFile(file, gson.toJson(entity), false);
            }
            String data = readFile(file);
            return data;
        }else{
            int scriptId = Integer.valueOf(scriptsId.trim());
            MonitorScriptsEntity scriptsEntity = monitorScriptsService.findById(scriptId, MonitorScriptsEntity.class);
            return new Gson().toJson(scriptsEntity);
        }
    }

    /**
     * 获取系统信息
     * @return
     */
    @RequestMapping(value = "getSysInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getSysInfo(String ip) {
        String file = dataDir + separator + "graph" + separator
                +"sysinfo"
                +separator
                +DateUtil.getDate("yyyy")
                +separator
                +DateUtil.getDate("MM")
                +separator
                +DateUtil.getDate("dd") + separator;
        file = file +separator+ ip;
        file = FileRender.replace(file);
        String result = FileRender.readFile(file);
        return result;
    }
}
