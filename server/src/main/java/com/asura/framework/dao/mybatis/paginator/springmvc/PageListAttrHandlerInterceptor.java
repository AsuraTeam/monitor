package com.asura.framework.dao.mybatis.paginator.springmvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.asura.framework.dao.mybatis.paginator.domain.PageList;

/**
 * 将ServletRequest和ModelAndView里包含PageList的Paginator取出，并建立名为原属性名+Paginator后缀的属性
 */
public class PageListAttrHandlerInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        Enumeration enumeration = request.getAttributeNames();
        while (enumeration.hasMoreElements()){
            Object element = enumeration.nextElement();
            if(element instanceof String){
                String name = (String)element;
                Object attr = request.getAttribute(name);
                if(attr instanceof PageList){
                    PageList pageList = (PageList)attr;
                    //原属性加后缀
                    request.setAttribute(name+"Paginator", pageList.getPaginator());
                }
            }
        }
        if(modelAndView != null){
            Map<String,Object> model = modelAndView.getModel();
            Map<String,Object> newModel = new HashMap<String, Object>();
            for(Map.Entry<String, Object> item : model.entrySet()){
                Object attr = item.getValue();
                if(attr instanceof PageList){
                    PageList pageList = (PageList)attr;
                    //原属性加后缀
                    newModel.put(item.getKey()+"Paginator", pageList.getPaginator());
                }
            }
            modelAndView.addAllObjects(newModel);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
