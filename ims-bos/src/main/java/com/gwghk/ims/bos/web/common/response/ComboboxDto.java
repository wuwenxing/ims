package com.gwghk.ims.bos.web.common.response;

import java.io.Serializable;

/**
 * 摘要：combobox
 * @author warren
 * @version 1.0
 * @Date 2017年4月14日
 */
public class ComboboxDto implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 8570518556675921612L;
	/**
	 * Id
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 其它数据
	 */
	private Object data;
	
	 
	public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
     
	
}
