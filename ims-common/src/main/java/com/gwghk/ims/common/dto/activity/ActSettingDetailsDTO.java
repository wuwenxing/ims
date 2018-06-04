package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 活动详情信息 -活动的所有信息
 * @author eva
 *
 */
public class ActSettingDetailsDTO extends BaseVO  implements Serializable{
	
	 
	private static final long serialVersionUID = 8032238954457513396L;
	
	/**
	 * 活动基本信息
	 */
	private ActSettingDTO actBaseInfo; 
	
	/**
	 * 活动参与条件 
	 */
	private ActConditionSettingDTO actCondSetting;
	
	/**
	 * 活动下的任务物品
	 */
	private List<ActTaskSettingDTO> actTaskSettings;

	public ActSettingDTO getActBaseInfo() {
		return actBaseInfo;
	}

	public void setActBaseInfo(ActSettingDTO actBaseInfo) {
		this.actBaseInfo = actBaseInfo;
	}

	public ActConditionSettingDTO getActCondSetting() {
		return actCondSetting;
	}

	public void setActCondSetting(ActConditionSettingDTO actCondSetting) {
		this.actCondSetting = actCondSetting;
	}

	public List<ActTaskSettingDTO> getActTaskSettings() {
		return actTaskSettings;
	}

	public void setActTaskSettings(List<ActTaskSettingDTO> actTaskSettings) {
		this.actTaskSettings = actTaskSettings;
	}
}