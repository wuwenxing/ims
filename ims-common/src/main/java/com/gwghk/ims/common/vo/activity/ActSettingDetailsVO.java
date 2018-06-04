package com.gwghk.ims.common.vo.activity;

import java.io.Serializable;
import java.util.List;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 活动详情信息 -活动的所有信息
 * @author eva
 *
 */
public class ActSettingDetailsVO extends BaseVO  implements Serializable{
	
	 
	private static final long serialVersionUID = 8032238954457513396L;
	
	/**
	 * 活动基本信息
	 */
	private ActSettingVO actBaseInfo; 
	
	/**
	 * 活动参与条件 
	 */
	private ActConditionSettingVO actCondSetting;
	
	/**
	 * 活动下的任务物品
	 */
	private List<ActTaskSettingVO> actTaskSettings;
	
	  
	public ActSettingVO getActBaseInfo() {
		return actBaseInfo;
	}

	public void setActBaseInfo(ActSettingVO actBaseInfo) {
		this.actBaseInfo = actBaseInfo;
	}

	public ActConditionSettingVO getActCondSetting() {
		return actCondSetting;
	}

	public void setActCondSetting(ActConditionSettingVO actCondSetting) {
		this.actCondSetting = actCondSetting;
	}

	public List<ActTaskSettingVO> getActTaskSettings() {
		return actTaskSettings;
	}

	public void setActTaskSettings(List<ActTaskSettingVO> actTaskSettings) {
		this.actTaskSettings = actTaskSettings;
	}

	 
}