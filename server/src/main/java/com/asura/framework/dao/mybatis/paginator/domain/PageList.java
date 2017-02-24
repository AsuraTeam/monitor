package com.asura.framework.dao.mybatis.paginator.domain;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 
 * <p>包含“分页”信息的List</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class PageList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1412759446332294208L;
    
    private Paginator paginator;

    public PageList() {}
    
	public PageList(Collection<? extends E> c) {
		super(c);
	}

	
	public PageList(Collection<? extends E> c,Paginator p) {
        super(c);
        this.paginator = p;
    }

    public PageList(Paginator p) {
        this.paginator = p;
    }


	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * @return
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	
}
