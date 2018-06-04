package com.gwghk.ims.common.vo;

/**
 * 摘要：分页参数对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class PageVO {
	// 每页多少条数据
    private int rows = 20;
    
    // 第几页
    private int page = 1;
        
    // 排序字段名
    private String sort;
    
    // 排序字段顺序
    private String order;

    /**
     * 每页多少条数据
     * @return rows 每页多少条数据
     */
    public int getRows() {
        return rows;
    }

    /**
     * 每页多少条数据
     * @param rows 每页多少条数据
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * 
     * @return page 第几页
     */
    public int getPage() {
        return page;
    }

    /**
     * 第几页
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 排序字段名
     * @return sort 排序字段名
     */
    public String getSort() {
        return sort;
    }

    /**
     * 排序字段名
     * @param sort 排序字段名
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 排序字段顺序
     * @return order 排序字段顺序
     */
    public String getOrder() {
        return order;
    }

    /**
     * 排序字段顺序
     * @param order 排序字段顺序
     */
    public void setOrder(String order) {
        this.order = order;
    }
}
