package com.gwghk.ims.common.enums;

import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * @ClassName: ActSettingType
 * @Description: 任务类型
 * @author eva.liu
 * @date 2017年6月9日
 *
 */
public enum ActTypeEnum {
	/**物品兑换型*/
	WPDH("wpdh","物品兑换型"),
	/**Demo物品兑换型*/
    WPDH_DEMO("wpdhdemo","Demo物品兑换型"),
	/**任务型*/
    RW("rw","任务型"),
    /**入金赠金型*/
    RJZJ("rjzj","入金赠金型"),
    /**层级型*/
    CJ("cj","层级型"),
    /**其他*/
    QT("qt","其他");
	
	private final String code;
	private final String name;
	
	ActTypeEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
 
	public String getCode() {
		return code;
	}



	public String getName() {
		return name;
	}



	public static ActTypeEnum getType(String code){
        if(StringUtil.isNotEmpty(code)){
            for(ActTypeEnum ae : ActTypeEnum.values()){
                if(code.equals(ae.getCode())){
                    return ae;
                }
            }
        }
        return null;
    }
	
	public static String formatByCode(String code){
		 if(StringUtil.isNotEmpty(code)){
            for(ActTypeEnum ae : ActTypeEnum.values()){
                if(code.equals(ae.getCode())){
                    return ae.getName();
                }
            }
        }
        return null;
    }
 
}
