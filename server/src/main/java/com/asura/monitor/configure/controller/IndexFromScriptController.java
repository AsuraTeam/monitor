package com.asura.monitor.configure.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.configure.entity.MonitorIndexFromScriptsEntity;
import com.asura.monitor.configure.service.MonitorIndexFromScriptsService;
import com.asura.monitor.configure.service.MonitorScriptsService;
import com.asura.monitor.graph.util.FileRender;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * 指标名称配置
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/configure/")
public class IndexFromScriptController {

    @Autowired
    private PermissionsCheck permissionsCheck;
    private Gson gson = new Gson();

     @Autowired
     private MonitorIndexFromScriptsService indexService;

    @Autowired
    private MonitorScriptsService scriptsService;

    /**
     * 指标配置
     * @return
     */
    @RequestMapping("index/add")
    public String indexAdd(int id,Model model){
        if(id>0){
            MonitorIndexFromScriptsEntity result = indexService.findById(id,MonitorIndexFromScriptsEntity.class);
            model.addAttribute("configs",result);
        }
        model.addAttribute("scripts", scriptsService.findAll(new SearchMap(),new PageBounds(),"selectByAll").getRows());
        return "monitor/configure/index/add";
    }

    /**
     * 获取最新指标的数据到库里面
     * @return
     */
    @RequestMapping("index/getIndex")
    @ResponseBody
    public String getIndex(){
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000,1);
        List indexList = new ArrayList<>();
        PagingResult<MonitorIndexFromScriptsEntity> result = indexService.findAll(searchMap, pageBounds, "selectByAll");
        for (MonitorIndexFromScriptsEntity entity: result.getRows()){
            indexList.add(entity.getIndexName());
        }
        String dir = dataDir + separator + "graph" + separator +"index" +separator;
        File file = new File(dir);
        File[] list = file.listFiles();
        for (File files:list){
           String index =  files.getName();
            if (!indexList.contains(index)){
                try {
                    MonitorIndexFromScriptsEntity scriptsEntity = new MonitorIndexFromScriptsEntity();
                    scriptsEntity.setIndexName(index);
                    scriptsEntity.setLastModifyTime(DateUtil.getTimeStamp());
                    String scriptId = FileRender.readLastLine(dir + index+ separator + "id");
                    scriptsEntity.setScriptsId(Integer.valueOf(scriptId.replace("\n","")));
                    indexService.save(scriptsEntity);
                }catch (Exception e){
                      e.printStackTrace();
                }
            }
        }
        return "ok";
    }

    /**
     * 指标配置
     * @return
     */
    @RequestMapping("index/list")
    public String indexList(){
        return "monitor/configure/index/list";
    }

    /**
     * 脚本配置
     *
     * @return
     */
    @RequestMapping(value = "index/listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String scriptList(int draw, int start, int length, String indexId, HttpServletRequest request) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (indexId != null) {
            searchMap.put("scriptsId", Integer.valueOf(indexId));
        }
        String key = request.getParameter("search[value]");
        if (key!=null&&key.length()>0){
            searchMap.put("key", key);
        }
        PagingResult<MonitorIndexFromScriptsEntity> result = indexService.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }


    /**
     * 指标配置
     *
     * @return
     */
    @RequestMapping("index/save")
    @ResponseBody
    public ResponseVo scriptSave(MonitorIndexFromScriptsEntity entity, HttpSession session) {
        String user = permissionsCheck.getLoginUser(session);
        RedisUtil redisUtil = new RedisUtil();
        entity.setLastModifyUser(user);
        String name = entity.getIndexName();
        name = name.replace("/", "SLASH");
        entity.setIndexName(name);
        entity.setLastModifyTime(DateUtil.getTimeStamp());
        if (entity.getIndexId() != null) {
            indexService.update(entity);
        } else {
            indexService.save(entity);
        }
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(1000000,1);
        PagingResult<MonitorIndexFromScriptsEntity> result = indexService.findAll(searchMap, pageBounds, "selectByAll");
        Jedis jedis = redisUtil.getJedis();
        for (MonitorIndexFromScriptsEntity scriptsEntity: result.getRows()) {
            jedis.set(RedisUtil.app+"_"+ MonitorCacheConfig.cacheIndexScript + scriptsEntity.getIndexName(), entity.getScriptsId() + "");
        }
        return ResponseVo.responseOk(null);
    }

}
