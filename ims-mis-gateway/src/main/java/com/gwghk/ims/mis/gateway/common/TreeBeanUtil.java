package com.gwghk.ims.mis.gateway.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形菜单Bean对象
 * 
 * @author gavin.guo
 */
public class TreeBeanUtil {

	/**
	 * 树形菜单格式化
	 * 
	 * @param nodeListTmp
	 * @return
	 */
	public static List<TreeBean> formatTreeBean(List<TreeBean> nodeListTmp) {
		List<TreeBean> nodeList = new ArrayList<TreeBean>();
		for (TreeBean outNode : nodeListTmp) {
			boolean flag = false;
			for (TreeBean inNode : nodeListTmp) {
				if (outNode.getParentId() != null && outNode.getParentId().equals(inNode.getId())) {
					flag = true;
					if (inNode.getChildren() == null) {
						inNode.setChildren(new ArrayList<TreeBean>());
					}
					inNode.getChildren().add(outNode);
					break;
				}
			}
			if (!flag) {
				nodeList.add(outNode);
			}
		}
		return nodeList;
	}

}
