package com.gwghk.ims.bos.web.common.enums;

import java.util.ArrayList;
import java.util.List;


/**
 * 摘要：任务型活动的任务类型
 * @author eva.liu
 * @version 1.0
 * @Date 2017年6月14日
 */
public enum ActRwTaskTypeEnum {
    //参数：1代号,2任务名称,3任务参数单位，4任务参数输入的ValidType[gtZeroDecimalTwo:大于0且最多两位小数,zeroDecimalTwo:大于或等于0且最多两位小数, int大于0的整正数],
    //奖励物品参数单位，6不能与之同时存在的任务编号(如果与当前任务编号一致，表示最多只能有一个) ,7是否模拟账号的,8是否可以设置最大领取次数,9是否可以设置最大领取金额
    ADD_DEMO_ACCOUNT("add_demo_account","注册模拟账号","","","元","add_demo_account",true,false,false),
    //  同一类型中， 平仓次数与累计平仓次数、平仓手数与累计平仓手数不能同时使用
    DEMO_CLOSE_TIMES("demo_close_times","模拟账号平仓次数","次","int","元"," ",true,true,false),
    DEMO_CLOSE_LOT("demo_close_lot","模拟账号平仓手数","手","gtZeroDecimalTwo","元"," ",true,true,false),
    DEMO_CLOSE_PROFIT("demo_close_profit","模拟账号单笔平仓盈利","元","zeroDecimalTwo","元","",true,true,false),
    DEMO_CLOSE_LOSS("demo_close_loss","模拟账号单笔平仓亏损","元","zeroDecimalTwo","元","",true,true,false),
    DEMO_CLOSE_PROFIT_PERCENT("demo_close_profit_percent","模拟账号单笔平仓盈利%","元","zeroDecimalTwo","%","",true,false,true),
    DEMO_CLOSE_LOSS_PERCENT("demo_close_loss_percent","模拟账号单笔平仓亏损%","元","zeroDecimalTwo","%","",true,false,true),
    
  
    DEMO_TOTAL_CLOSE_TIMES("demo_total_close_times","模拟账号累计平仓次数","次","int","元"," ",true,false,false),
    DEMO_TOTAL_CLOSE_LOT("demo_total_close_lot","模拟账号累计平仓手数","手","gtZeroDecimalTwo","元"," ",true,false,false),
    DEMO_TOTAL_CLOSE_PROFIT("demo_total_close_profit","模拟账号平仓盈利总额","元","gtZeroDecimalTwo","元","",true,false,false),
    DEMO_TOTAL_CLOSE_LOSS("demo_total_close_loss","模拟账号平仓亏损总额","元","gtZeroDecimalTwo","元","",true,false,false),
   
    ADD_REAL_ACCOUNT("add_real_account","注册真实账号","","","元","add_real_account",false,false,false),
    REAL_ACTIVATION("real_activation","真实账号激活","","","元","real_activation",false,false,false),
    REAL_DEPOSIT("real_deposit","真实账号首次入金","元","gtZeroDecimalTwo","元","real_deposit",false,false,false),
    REAL_CLOSE_TIMES("real_close_times","真实账号平仓次数","次","int","元"," ",false,true,false),
    REAL_CLOSE_LOT("real_close_lot","真实账号平仓手数","手","gtZeroDecimalTwo","元"," ",false,true,false),
    REAL_CLOSE_PROFIT("real_close_profit","真实账号单笔平仓盈利","元","zeroDecimalTwo","元","",false,true,false),
    REAL_CLOSE_LOSS("real_close_loss","真实账号单笔平仓亏损","元","zeroDecimalTwo","元","",false,true,false),
    REAL_CLOSE_PROFIT_PERCENT("real_close_profit_percent","真实账号单笔平仓盈利%","元","zeroDecimalTwo","%","",false,false,true),
    REAL_CLOSE_LOSS_PERCENT("real_close_loss_percent","真实账号单笔平仓亏损%","元","zeroDecimalTwo","%","",false,false,true),
    
    REAL_TOTAL_CLOSE_TIMES("real_total_close_times","真实账号累计平仓次数","次","int","元"," ",false,false,false),
    REAL_TOTAL_CLOSE_LOT("real_total_close_lot","真实账号累计平仓手数","手","gtZeroDecimalTwo","元"," ",false,false,false),
    REAL_TOTAL_CLOSE_PROFIT("real_total_close_profit","真实账号平仓盈利总额","元","gtZeroDecimalTwo","元","",false,false,false),
    REAL_TOTAL_CLOSE_LOSS("real_total_close_loss","真实账号平仓亏损总额","元","gtZeroDecimalTwo","元","",false,false,false);

	
	private   String code;//代号
	private   String name;//任务名称
	private   String unitStr;//任务单位
	private   String unitValidTypeStr;//任务参数校验validType
	private   String itemUnitStr;//奖励物品参数单位
	private   String outCode;//不能与之同时存在的任务编号
	private   Boolean isReceiveMaxMoney;//是否可以设置最大领取金额
	private   Boolean isReceiveMaxNum;//是否可以设置最大领取次数
	private   Boolean demo;//是否模拟账号的
	
	ActRwTaskTypeEnum(String code, String name,String unitStr,String unitValidTypeStr,String itemUnitStr,String outCode,boolean demo,boolean isReceiveMaxNum,boolean isReceiveMaxMoney) {
		this.code = code;
		this.name = name;
		this.unitStr = unitStr;
		this.unitValidTypeStr = unitValidTypeStr;
		this.itemUnitStr = itemUnitStr;
		this.outCode = outCode;
		this.isReceiveMaxMoney = isReceiveMaxMoney;
		this.isReceiveMaxNum = isReceiveMaxNum;
		this.demo = demo;
	}

	
    public String getOutCode() {
        return outCode;
    }


    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setUnitStr(String unitStr) {
        this.unitStr = unitStr;
    }
 
    public String getUnitStr() {
        return unitStr;
    }
 

    public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
 
 
	public String getItemUnitStr() {
        return itemUnitStr;
    }

    public void setItemUnitStr(String itemUnitStr) {
        this.itemUnitStr = itemUnitStr;
    }
   
    public Boolean getIsReceiveMaxMoney() {
        return isReceiveMaxMoney;
    }

    public void setIsReceiveMaxMoney(Boolean isReceiveMaxMoney) {
        this.isReceiveMaxMoney = isReceiveMaxMoney;
    }

    public Boolean getIsReceiveMaxNum() {
        return isReceiveMaxNum;
    }

    public void setIsReceiveMaxNum(Boolean isReceiveMaxNum) {
        this.isReceiveMaxNum = isReceiveMaxNum;
    }

    public boolean getDemo() {
        return demo;
    }

    public void setDemo(Boolean demo) {
        this.demo = demo;
    }
    
    

    public String getUnitValidTypeStr() {
        return unitValidTypeStr;
    }


    public void setUnitValidTypeStr(String unitValidTypeStr) {
        this.unitValidTypeStr = unitValidTypeStr;
    }


    public static List<ActRwTaskTypeEnum> getList(){
        List<ActRwTaskTypeEnum> result = new ArrayList<ActRwTaskTypeEnum>();
        for(ActRwTaskTypeEnum ae : ActRwTaskTypeEnum.values()){
            result.add(ae);
        }
        return result;
    }
 
	public static String getNameByKey(String code){
        if(code!=null){
            for(ActRwTaskTypeEnum ae : ActRwTaskTypeEnum.values()){
                if(code.equals(ae.getCode())){
                    return ae.getName();
                }
            }
        }
        return null;
    }
	
	public static ActRwTaskTypeEnum getActRwTaskTypeEnumByKey(String code){
        if(code!=null){
            for(ActRwTaskTypeEnum ae : ActRwTaskTypeEnum.values()){
                if(code.equals(ae.getCode())){
                   return ae;
                }
            }
        }
        return null;
    }
}
