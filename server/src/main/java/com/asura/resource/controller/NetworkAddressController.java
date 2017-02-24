package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.util.PermissionsCheck;
import com.asura.resource.entity.CmdbResourceNetworkAddressEntity;
import com.asura.resource.service.CmdbResourceNetworkAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  资源网络ip地址资源信息
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/15 18:11:11
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/networkAddress/")
public class NetworkAddressController {

    @Autowired
    private CmdbResourceNetworkAddressService service ;


    @Autowired
    private PermissionsCheck permissionsCheck;



   /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/networkAddress/list";
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
        PagingResult<CmdbResourceNetworkAddressEntity> result = service.findAll(searchMap,pageBounds,"selectByAll");
        return PageResponse.getMap(result, draw);
    }
}
