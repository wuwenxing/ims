package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

/**
 * 下拉选项值
 * @author eva
 *
 */
public class OptionVO   implements Serializable{
	
	private static final long serialVersionUID = 5052080060339116041L;

	 
	/**
	 * 参数属性名
	 */
     private String key;
    
   
     @Override
	public String toString() {
		return "OptionVO [key=" + key + ", title=" + title + "]";
	}


	/**
      * 值
      */
     private String title;


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
