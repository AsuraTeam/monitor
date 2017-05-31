package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.controller.CacheController;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.CheckUtil;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.resource.entity.CmdbResourceCabinetEntity;
import com.asura.resource.entity.CmdbResourceFloorEntity;
import com.asura.resource.service.CmdbResourceCabinetService;
import com.asura.resource.service.CmdbResourceFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  资源配置机柜配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/cabinet/")
public class CabinetController {

    @Autowired
    private CmdbResourceCabinetService service ;

    @Autowired
    private CmdbResourceFloorService floorService ;

    @Autowired
    private PermissionsCheck permissionsCheck;
    @Autowired
    private IndexController logSave;
    @Autowired
    private CmdbResourceServerService serverService;
    @Autowired
    private CacheController cacheController;


   /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/cabinet/list";
    }


    /**
     * 获取机柜数据
     * @param foolderId
     * @param length
     * @param start
     */
    PagingResult<CmdbResourceCabinetEntity> getCabinetData(int foolderId, int length, int start){
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (foolderId > 0 ){
            searchMap.put("floorId", foolderId);
        }
        PagingResult<CmdbResourceCabinetEntity> result = service.findAll(searchMap,pageBounds);
        return result;
    }

    /**
     * 列表数据
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PagingResult<CmdbResourceCabinetEntity> result = getCabinetData(0, length, start);
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(Model model){
        SearchMap map = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(100000,1);
        PagingResult<CmdbResourceFloorEntity> result = floorService.findAll(map,pageBounds);
        model.addAttribute("groups",result.getRows());
        return "/resource/configure/cabinet/add";
    }



    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceCabinetEntity entity, HttpSession session){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if(entity.getCabinetId()!=null){
            service.update(entity);
        }else {
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        cacheController.cacheCabinet();
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model){
        CmdbResourceCabinetEntity result = service.findById(id,CmdbResourceCabinetEntity.class);
        model.addAttribute("configs",result);
        return "/resource/configure/cabinet/add";
    }

    /**
     * 删除机柜信息
     * @return
     */
    @RequestMapping("deleteSave")
    @ResponseBody
    public ResponseVo delete(int id, HttpServletRequest request){
        SearchMap searchMap = new SearchMap();
        searchMap.put("cabinetId", id);
        List<CmdbResourceServerEntity> re = serverService.getDataList(searchMap,"selectByAll");
        if (re.size() > 0){
            return ResponseVo.responseError("请删除依赖的设备后删除机柜"+ re.get(0).getIpAddress());
        }
        CmdbResourceCabinetEntity result = service.findById(id, CmdbResourceCabinetEntity.class);
        service.delete(result);
        logSave.logSave(request, "删除机柜"+ new Gson().toJson(result));
        return ResponseVo.responseOk(null);
    }
}
