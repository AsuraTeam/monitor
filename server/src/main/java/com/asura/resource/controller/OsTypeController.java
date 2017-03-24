package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.resource.entity.CmdbResourceOsTypeEntity;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.service.CmdbResourceOsTypeService;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  资源配置操作系统类型配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/osType/")
public class OsTypeController {
    @Autowired
    private CmdbResourceOsTypeService service ;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    private IndexController indexController;

    @Autowired
    private CmdbResourceServerService serverService;

    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/osType/list";
    }

    /**
     * 列表数据
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceOsTypeEntity> result = service.findAll(searchMap,pageBounds);
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(){
        return "/resource/configure/osType/add";
    }




    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceOsTypeEntity entity, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if(entity.getOsId()!=null){
            service.update(entity);
        }else {
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        Gson gson = new Gson();
        indexController.logSave(request, "添加操作系统类型" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model){
        CmdbResourceOsTypeEntity result = service.findById(id,CmdbResourceOsTypeEntity.class);
        model.addAttribute("configs",result);
        return "/resource/configure/osType/add";
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping("deleteSave")
    public ResponseVo delete(int id, HttpServletRequest request){
        Gson gson = new Gson();
        CmdbResourceOsTypeEntity result = service.findById(id, CmdbResourceOsTypeEntity.class);
        SearchMap searchMap = new SearchMap();
        searchMap.put("osName", result.getOsName());
        List<CmdbResourceServerEntity> data = serverService.getDataList(searchMap, "selectByAll");
        if (data.size() < 1) {
            service.delete(result);
            indexController.logSave(request, "删除操作系统类型" + gson.toJson(result));
        }else{
            return ResponseVo.responseError("请先删除引用的设备记录");
        }
        return ResponseVo.responseOk(null);
    }
}
