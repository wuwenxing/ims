package com.gwghk.ims.activity.context;

import java.util.Map;

 /**
  * 活动模板容器
  * @author eva
  *
  */
public class ActTemplateContextContainer {
    private Map<String, ActivityConfigBean> actTemplateList;

	public Map<String, ActivityConfigBean> getActTemplateList() {
		return actTemplateList;
	}

	public void setActTemplateList(Map<String, ActivityConfigBean> actTemplateList) {
		this.actTemplateList = actTemplateList;
	}
     


}
