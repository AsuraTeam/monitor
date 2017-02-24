package com.asura.authority.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.authority.entity.AuthorityPagesEntity;
import com.asura.authority.service.AuthorityPagesService;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

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
@RequestMapping("/authority/pages/")
public class AuthorityPagesController {

    @Autowired
    private AuthorityPagesService service;

    @Autowired
    IndexController indexController;

    @Autowired
    private PermissionsCheck permissionsCheck;


    /**
     * @return
     */
    @RequestMapping("add")
    public String add(int id, Model model) {
        if (id > 0) {
            AuthorityPagesEntity result = service.findById(id, AuthorityPagesEntity.class);
            model.addAttribute("configs", result);
        }
        SearchMap searchMap = new SearchMap();
        PageBounds pageBounds = PageResponse.getPageBounds(10000,1);
        PagingResult<AuthorityPagesEntity> result = service.findAll(searchMap, pageBounds, "findAllModelName");
        model.addAttribute("models", result.getRows());
        return "/authority/pages/add";
    }

    /**
     * @return
     */
    @RequestMapping("list")
    public String list() {
        return "/authority/pages/list";
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
    public String listData(int start, int length, int draw, HttpServletRequest request){
        String search = request.getParameter("search[value]");
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        if (search!=null&&search.length()>1){
            searchMap.put("search", search);
        }
        PagingResult<AuthorityPagesEntity> result = service.findAll(searchMap, pageBounds, "selectByAll");
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
    public String  save(AuthorityPagesEntity entity, HttpSession session, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyTime(BigInteger.valueOf(System.currentTimeMillis()/1000));
        entity.setLastModifyUser(user);
        if (entity.getPagesId()!=null && entity.getPagesId()>0){
            service.update(entity);
        }else{
            service.save(entity);
        }

        indexController.logSave(request,"保存权限页面 " + entity.getPagesUrl());
        return "ok";
    }
}
