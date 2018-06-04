package com.gwghk.ims.mis.gateway.common;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 树形菜单Bean对象
 */
public class TreeBean{
	
	private String id;
	private String parentId;
	private String name;
	private Boolean checked;
	private JSONObject attributes;
	private List<TreeBean> children; // 设置子节点
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public JSONObject getAttributes() {
		return attributes;
	}
	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}
	public List<TreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}
	
}