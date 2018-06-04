package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：系统-菜单请求VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月24日
 */
public class SystemMenuVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = 7542738036359904378L;

	/**
	 * 菜单ID
	 */
	private Long menuId;

	/**
	 * 父菜单编码
	 */
	private String parentMenuCode;
	
	/**
	 * 菜单编码
	 */
	private String menuCode;

	/**
	 * 菜单类型(1菜单2功能)
	 */
	private String menuType;
	
	/**
	 * 菜单Item
	 */
	private String menuItem;

	/**
	 * 菜单名称-英文
	 */
	private String menuNameEn;
	
	/**
	 * 菜单名称-简体
	 */
	private String menuNameCn;
	
	/**
	 * 菜单名称-繁体
	 */
	private String menuNameTw;

	/**
	 * 链接URL
	 */
	private String menuUrl;

	/**
	 * 排序号
	 */
	private Long orderCode;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}

	public String getMenuNameEn() {
		return menuNameEn;
	}

	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}

	public String getMenuNameCn() {
		return menuNameCn;
	}

	public void setMenuNameCn(String menuNameCn) {
		this.menuNameCn = menuNameCn;
	}

	public String getMenuNameTw() {
		return menuNameTw;
	}

	public void setMenuNameTw(String menuNameTw) {
		this.menuNameTw = menuNameTw;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Long orderCode) {
		this.orderCode = orderCode;
	}
}
