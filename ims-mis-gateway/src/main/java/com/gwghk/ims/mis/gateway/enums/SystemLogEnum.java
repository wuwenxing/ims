package com.gwghk.ims.mis.gateway.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gwghk.ims.common.enums.EnumIntf;

/**
 * 摘要：系统日志类型定义
 */
public enum SystemLogEnum implements EnumIntf {
	system("系统管理", "system"),
	basicSetting("基础设置", "basicSetting"),
	active("活动管理", "active")
	;

	/**
	 * 系统模块对应controller
	 */
	public final static String[] logType_system = {"LoginController", "SystemUserController", 
			"SystemMenuController", "SystemLogController"};
	
	/**
	 * 基础设置对应controller
	 */
	public final static String[] logType_basicSetting = {"KeyValController"};

	/**
	 * 活动管理对应controller
	 */
	public final static String[] logType_active = {"ActivityItemsSettingController", "ActivitySettingController"};
	
	private final String name;
	private final String code;
	SystemLogEnum(String _operator, String labelKey) {
		this.name = _operator;
		this.code = labelKey;
	}
	
	public static List<SystemLogEnum> getList(){
		List<SystemLogEnum> result = new ArrayList<SystemLogEnum>();
		for(SystemLogEnum ae : SystemLogEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(SystemLogEnum ae : SystemLogEnum.values()){
			if(ae.getCode().equals(labelKey)){
				return ae.getName();
			}
		}
		return labelKey;
	}
	
	/**
	 * 根据请求路径，获取对应模块
	 * @param requestPath
	 * @return
	 */
	public static String getLogType(String requestPath){
		if(StringUtils.isNotBlank(requestPath)){
			String[] args = requestPath.split("/");
			String path = args[0];
			for(int i=0; i<logType_system.length; i++){
				String logType = logType_system[i];
				if(logType.equals(path)){
					return SystemLogEnum.system.getCode();
				}
			}
			for(int i=0; i<logType_basicSetting.length; i++){
				String logType = logType_basicSetting[i];
				if(logType.equals(path)){
					return SystemLogEnum.basicSetting.getCode();
				}
			}
			for(int i=0; i<logType_active.length; i++){
				String logType = logType_active[i];
				if(logType.equals(path)){
					return SystemLogEnum.active.getCode();
				}
			}
		}
		return SystemLogEnum.system.getCode();
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
	
}
