package com.asura.authority.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.authority.entity.AuthorityPagesEntity;
import com.asura.authority.entity.AuthorityPermissionEntity;
import com.asura.authority.service.AuthorityPagesService;
import com.asura.authority.service.AuthorityPermissionService;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
@RequestMapping("/authority/permission/")
public class AuthorityPermissionController {

    @Autowired
    private AuthorityPermissionService service;

    @Autowired
    private PermissionsCheck permissionsCheck;

    @Autowired
    IndexController indexController;

    @Autowired
    private AuthorityPagesService pagesService;



    /**
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model) {
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000, 1);
        if (id > 0) {
            AuthorityPermissionEntity result = service.findById(id, AuthorityPermissionEntity.class);
            model.addAttribute("configs", result);
            searchMap.put("user", result.getUser());
            PagingResult<AuthorityPermissionEntity> pageIds = service.findAll(searchMap, pageBounds, "findUserPages");
            ArrayList list = new ArrayList();
            for (AuthorityPermissionEntity entity:pageIds.getRows()){
                list.add(entity.getPagesId());
            }
            model.addAttribute("pagesId", list);
        }
        PagingResult<AuthorityPermissionEntity> result = service.findAll(searchMap, pageBounds, "findAllUser");
        model.addAttribute("users", result.getRows());
        PagingResult<AuthorityPagesEntity> pages = pagesService.findAll(searchMap, pageBounds, "findAllPage");
        model.addAttribute("pages", pages.getRows());
        PagingResult<AuthorityPagesEntity> models = pagesService.findAll(searchMap, pageBounds, "findAllModelName");
        model.addAttribute("models", models.getRows());
        return "/authority/permission/add";
    }

    /**
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/authority/permission/list";
    }


    /**
     *
     * @param start
     * @param length
     * @param draw
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String listData(int start, int length, int draw){
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<AuthorityPermissionEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     *
     * @param entity
     * @param session
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public String  save(AuthorityPermissionEntity entity, HttpSession session, String pages, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyTime(DateUtil.getDate(DateUtil.TIME_FORMAT));
        String[] pagesList = pages.split(",");
        SearchMap searchMap = new SearchMap();
        searchMap.put("user", entity.getUser());
        PageBounds pageBounds = PageResponse.getPageBounds(10000,1);
        ArrayList<Integer> list = new ArrayList();
        PagingResult<AuthorityPermissionEntity> pageData = service.findAll(searchMap, pageBounds, "findUserPermission");
        for (AuthorityPermissionEntity pagesEntity:pageData.getRows()){
            list.add(pagesEntity.getPagesId());
        }
        service.delete(entity);
        for (String addId: pagesList) {
            AuthorityPermissionEntity addEntity = new AuthorityPermissionEntity();
            addEntity.setUser(entity.getUser());
            addEntity.setPagesId(Integer.valueOf(addId));
            addEntity.setLastModifyUser(user);
            addEntity.setLastModifyTime(DateUtil.getDate(DateUtil.TIME_FORMAT));
            service.save(addEntity);
        }
        indexController.logSave(request,"保存用户权限 " + entity.getUser());
        return "ok";
    }
}
