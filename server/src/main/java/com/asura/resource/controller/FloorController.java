package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import com.asura.resource.entity.CmdbResourceFloorEntity;
import com.asura.resource.service.CmdbResourceFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  资源配置楼层机房
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/floor/")
public class FloorController {

    @Autowired
    private CmdbResourceFloorService service ;


    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/floor/list";
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
        PagingResult<CmdbResourceFloorEntity> result = service.findAll(searchMap,pageBounds);
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(){
        return "/resource/configure/floor/add";
    }



    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceFloorEntity entity, HttpSession session){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if(entity.getFloorId()!=null){
            service.update(entity);
        }else {
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model){
        CmdbResourceFloorEntity result = service.findById(id,CmdbResourceFloorEntity.class);
        model.addAttribute("configs",result);
        return "/resource/configure/floor/add";
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping("delete")
    public ResponseVo delete(){
        return ResponseVo.responseOk(null);
    }
}
