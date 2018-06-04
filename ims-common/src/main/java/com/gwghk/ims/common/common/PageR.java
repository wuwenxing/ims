package com.gwghk.ims.common.common;

import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 摘要：分页返回对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class PageR<T> extends PageInfo<T> {
	private static final long serialVersionUID = -3453251088517226541L;

	public PageR() {
    }
	
	public PageR(List<T> list) {
        super(list);
    }

    public PageR(List<T> list, int navigatePages) {
        super(list, navigatePages);
    }

    public List<T> getRows() {
        return super.getList();
    }
}