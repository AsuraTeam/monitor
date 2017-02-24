package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.resource.entity.CmdbResourceGroupsEntity;
import com.asura.resource.entity.CmdbResourceUserEntity;
import com.asura.resource.service.CmdbResourceGroupsService;
import com.asura.resource.service.CmdbResourceUserService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
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
 *  资源配置用户配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/user/")
public class UserController {

    @Autowired
    private CmdbResourceUserService service ;

    @Autowired
    private CmdbResourceGroupsService groupsService ;

    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/user/list";
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
        PagingResult<CmdbResourceUserEntity> result = service.findAll(searchMap,pageBounds);
        return PageResponse.getMap(result, draw);
    }

    public Model getGroups(Model model){
        PageBounds pageBounds = PageResponse.getPageBounds(100000, 1);
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceGroupsEntity> result = groupsService.findAll(searchMap,pageBounds);
        model.addAttribute("groups",result.getRows());
        return model;
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(Model model){
        model = getGroups(model);
        return "/resource/configure/user/add";
    }




    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceUserEntity entity, HttpSession session){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if(entity.getUserId()!=null){
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
        CmdbResourceUserEntity result = service.findById(id,CmdbResourceUserEntity.class);
        model.addAttribute("configs",result);
        model = getGroups(model);
        return "/resource/configure/user/add";
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResponseVo delete(int id){
        CmdbResourceUserEntity result = service.findById(id,CmdbResourceUserEntity.class);
        service.delete(result);
        return ResponseVo.responseOk(null);
    }
}
