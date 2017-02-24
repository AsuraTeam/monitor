package com.asura.monitor.configure;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.graph.entity.CmdbQuartzEntity;
import com.asura.monitor.graph.service.CmdbQuartzService;
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
 *  任务计划配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/8/10 11:59
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/configure/quartz/")
public class QuartzController {

    @Autowired
    private CmdbQuartzService service ;

    @Autowired
    private PermissionsCheck permissionsCheck;

    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/monitor/configure/quartz/list";
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
        PagingResult<CmdbQuartzEntity> result = service.findAll(searchMap,pageBounds,"selectByAll");
        return PageResponse.getMap(result, draw);
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(){
        return "/monitor/configure/quartz/add";
    }


    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbQuartzEntity entity, HttpSession session){
        String user = permissionsCheck.getLoginUser(session);
        entity.setLastModifyUser(user);
        if(entity.getQuartzId()!=null){
            service.update(entity);
        }else {
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
        CmdbQuartzEntity result = service.findById(id,CmdbQuartzEntity.class);
        model.addAttribute("configs",result);
        return "/monitor/configure/quartz/add";
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
