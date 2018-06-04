package com.gwghk.ims.common.common;

import java.io.Serializable;

/**
 * 摘要：最基层对象
 * @author eva.guo
 * @version 1.0
 * @Date 2018年4月12日
 */
public class Kuak  implements  Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2221339232210317368L;
	
	private String key;
	
	private String title;
	
	public Kuak() {
    }

    public Kuak(String key, String title) {
        setKey(key);
        setTitle(title);
    }
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
}
