package com.gwghk.ims.common.enums;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * 摘要： 活动的任务类型
 * @author eva.liu
 * @version 1.0
 * @Date 2017年6月14日
 */
public enum ActTaskTypeEnum {
    //参数：1代号,2任务名称,3任务参数单位code，4任务参数输入的ValidType[gtZeroDecimalTwo:大于0且最多两位小数,zeroDecimalTwo:大于或等于0且最多两位小数, int大于0的整正数],
    //5奖励物品参数单位code，6不能与之同时存在的任务编号(如果与当前任务编号一致，表示最多只能有一个) ,7是否模拟账号的,8是否可以设置最大领取次数,9是否可以设置最大领取金额
    ADD_DEMO_ACCOUNT("add_demo_account","注册模拟账号","","","MONEY","add_demo_account",true,false,false),
    //  同一类型中， 平仓次数与累计平仓次数、平仓手数与累计平仓手数不能同时使用
    DEMO_CLOSE_TIMES("demo_close_times","模拟账号平仓次数","TIMES","int","MONEY"," ",true,true,false),
    DEMO_CLOSE_LOT("demo_close_lot","模拟账号平仓手数","LOT","gtZeroDecimalTwo","MONEY"," ",true,true,false),
    DEMO_CLOSE_PROFIT("demo_close_profit","模拟账号单笔平仓盈利","MONEY","zeroDecimalTwo","MONEY","",true,true,false),
    DEMO_CLOSE_LOSS("demo_close_loss","模拟账号单笔平仓亏损","MONEY","zeroDecimalTwo","MONEY","",true,true,false),
    DEMO_CLOSE_PROFIT_PERCENT("demo_close_profit_percent","模拟账号单笔平仓盈利%","MONEY","zeroDecimalTwo","%","",true,false,true),
    DEMO_CLOSE_LOSS_PERCENT("demo_close_loss_percent","模拟账号单笔平仓亏损%","MONEY","zeroDecimalTwo","%","",true,false,true),
    DEMO_TOTAL_CLOSE_TIMES("demo_total_close_times","模拟账号累计平仓次数","TIMES","int","MONEY"," ",true,false,false),
    DEMO_TOTAL_CLOSE_LOT("demo_total_close_lot","模拟账号累计平仓手数","LOT","gtZeroDecimalTwo","MONEY"," ",true,false,false),
    DEMO_TOTAL_CLOSE_PROFIT("demo_total_close_profit","模拟账号平仓盈利总额","MONEY","gtZeroDecimalTwo","MONEY","",true,false,false),
    DEMO_TOTAL_CLOSE_LOSS("demo_total_close_loss","模拟账号平仓亏损总额","MONEY","gtZeroDecimalTwo","MONEY","",true,false,false),

    DEMO_FIRST_OPEN("demo_first_open","模拟账号首次开仓","","","MONEY","demo_first_trade_in",true,false,false),
    /** 模拟账号首次平仓 */
    DEMO_FIRST_CLOSE("demo_first_close","模拟账号首次平仓","","","MONEY","demo_first_trade_out",true,false,false),
    /** 模拟账号开仓次数 */
    DEMO_OPEN_TIMES("demo_open_times","模拟账号开仓次数","TIMES","int","MONEY","",true,true,false),
    /** 模拟账号开仓手数*/
    DEMO_OPEN_LOT("demo_open_lot","模拟账号开仓手数","LOT","gtZeroDecimalTwo","MONEY","",true,true,false),
    /** 模拟账号累计开仓次数 */
    DEMO_TOTAL_OPEN_TIMES("demo_total_open_times","模拟账号累计开仓次数","TIMES","int","MONEY"," ",true,false,false),
    /** 模拟账号累计开仓手数*/
    DEMO_TOTAL_OPEN_LOT("demo_total_open_lot","模拟账号累计开仓手数","LOT","gtZeroDecimalTwo","MONEY"," ",true,false,false),
    /** 模拟账号首次平仓盈利*/
    DEMO_FIRST_CLOSE_PROFIT("demo_first_close_profit","模拟账号首次平仓盈利","MONEY","zeroDecimalTwo","MONEY","",true,true,false),
    /** 模拟账号首次平仓亏损 */
    DEMO_FIRST_CLOSE_LOSS("demo_first_close_loss","模拟账号首次平仓亏损","MONEY","zeroDecimalTwo","MONEY","",true,true,false),
    /** 模拟账号首次平仓盈利% */
    DEMO_FIRST_CLOSE_PROFIT_PERCENT("demo_first_close_profit_percent","模拟账号首次平仓盈利%","MONEY","zeroDecimalTwo","%","",true,false,true),
    /** 模拟账号首次平仓亏损 */
    DEMO_FIRST_CLOSE_LOSS_PERCENT("demo_first_close_loss_percent","模拟账号首次平仓亏损%","MONEY","zeroDecimalTwo","%","",true,false,true),
    //--end
 
    ADD_REAL_ACCOUNT("add_real_account","注册真实账号","","","MONEY","add_real_account",false,false,false),
    REAL_ACTIVATION("real_activation","真实账号激活","MONEY","zeroDecimalTwo","MONEY","real_activation",false,false,false),
    REAL_DEPOSIT("real_deposit","真实账号入金","MONEY","zeroDecimalTwo","MONEY","real_deposit",false,false,false),
    REAL_DEPOSIT_TOTAL("real_deposit_total","真实账号累计入金","MONEY","gtZeroDecimalTwo","MONEY","real_deposit_total",false,false,false),
    REAL_NET_DEPOSIT_TOTAL("real_net_deposit_total","真实账号累计净入金","MONEY","gtZeroDecimalTwo","MONEY","real_net_deposit_total",false,false,false),
    REAL_NET_DEPOSIT_TOTAL_NEWCUSTOMER("real_net_deposit_total_newCustomer","真实账号新客累计净入金","MONEY","gtZeroDecimalTwo","MONEY","real_net_deposit_total_newCustomer",false,false,false),
    REAL_CLOSE_TIMES("real_close_times","真实账号平仓次数","TIMES","int","MONEY","",false,true,false),
    REAL_CLOSE_LOT("real_close_lot","真实账号平仓手数","LOT","gtZeroDecimalTwo","MONEY","",false,true,false),
    REAL_CLOSE_PROFIT("real_close_profit","真实账号单笔平仓盈利","MONEY","zeroDecimalTwo","MONEY","",false,true,false),
    REAL_CLOSE_LOSS("real_close_loss","真实账号单笔平仓亏损","MONEY","zeroDecimalTwo","MONEY","",false,true,false),
    REAL_CLOSE_PROFIT_PERCENT("real_close_profit_percent","真实账号单笔平仓盈利%","MONEY","zeroDecimalTwo","%","",false,false,true),
    REAL_CLOSE_LOSS_PERCENT("real_close_loss_percent","真实账号单笔平仓亏损%","MONEY","zeroDecimalTwo","%","",false,false,true),
    REAL_TOTAL_CLOSE_TIMES("real_total_close_times","真实账号累计平仓次数","TIMES","int","MONEY"," ",false,false,false),
    REAL_TOTAL_CLOSE_LOT("real_total_close_lot","真实账号累计平仓手数","LOT","gtZeroDecimalTwo","MONEY"," ",false,false,false),
    REAL_TOTAL_CLOSE_PROFIT("real_total_close_profit","真实账号平仓盈利总额","MONEY","gtZeroDecimalTwo","MONEY","",false,false,false),
    REAL_TOTAL_CLOSE_LOSS("real_total_close_loss","真实账号平仓亏损总额","MONEY","gtZeroDecimalTwo","MONEY","",false,false,false),

    /** 真实账号首次开仓 */
    REAL_FIRST_OPEN("real_first_open","真实账号首次开仓","","","MONEY","real_first_trade_in",false,false,false),
    /** 真实账号首次平仓 */
    REAL_FIRST_CLOSE("real_first_close","真实账号首次平仓","","","MONEY","real_first_trade_out",false,false,false),
    /** 真实账号开仓次数 */
    REAL_OPEN_TIMES("real_open_times","真实账号开仓次数","TIMES","int","MONEY","",false,true,false),
    /** 真实账号开仓手数*/
    REAL_OPEN_LOT("real_open_lot","真实账号开仓手数","LOT","gtZeroDecimalTwo","MONEY","",false,true,false),
    /** 真实账号累计开仓次数 */
    REAL_TOTAL_OPEN_TIMES("real_total_open_times","真实账号累计开仓次数","TIMES","int","MONEY"," ",false,false,false),
    /** 真实账号累计开仓手数*/
    REAL_TOTAL_OPEN_LOT("real_total_open_lot","真实账号累计开仓手数","LOT","gtZeroDecimalTwo","MONEY"," ",false,false,false),
  
    /** 真实账号首次平仓盈利*/
    REAL_FIRST_CLOSE_PROFIT("real_first_close_profit","真实账号首次平仓盈利","MONEY","zeroDecimalTwo","MONEY","",false,false,false),
    /** 真实账号首次平仓亏损 */
    REAL_FIRST_CLOSE_LOSS("real_first_close_loss","真实账号首次平仓亏损","MONEY","zeroDecimalTwo","MONEY","",false,false,false),
    /** 真实账号首次平仓盈利%*/
    REAL_FIRST_CLOSE_PROFIT_PERCENT("real_first_close_profit_percent","真实账号首次平仓盈利%","MONEY","zeroDecimalTwo","%","",false,false,true),
    /** 真实账号首次平仓亏损 */
    REAL_FIRST_CLOSE_LOSS_PERCENT("real_first_close_loss_percent","真实账号首次平仓亏损%","MONEY","zeroDecimalTwo","%","",false,false,true),
 
    FREE_EXCHANGE("free_exchange","自由兑换","","","MONEY","free_exchange",null,true,false),
    //--end
    ZDY_TASK("zdy_task","自定义任务","","","MONEY","",null,true,false),
   
    ;
	private   String code;//代号
	private   String name;//任务名称
	private   String unitCode;//任务单位code
	private   String unitValidTypeStr;//任务参数校验validType
	private   String itemUnitCode;//奖励物品参数单位code
	private   String outCode;//不能与之同时存在的任务编号
	private   Boolean isReceiveMaxMoney;//是否可以设置最大领取金额
	private   Boolean isReceiveMaxNum;//是否可以设置最大领取次数
	private   Boolean demo;//是否模拟账号的 ,null表示，即可demo又可real
	
	ActTaskTypeEnum(String code, String name,String unitCode,String unitValidTypeStr,String itemUnitCode,String outCode,Boolean demo,boolean isReceiveMaxNum,boolean isReceiveMaxMoney) {
		this.code = code;
		this.name = name;
		this.unitCode = unitCode;
		this.unitValidTypeStr = unitValidTypeStr;
		this.itemUnitCode = itemUnitCode;
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
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    /**
     * 获得默认显示的任务单位 名称
     * @return
     */
    public String getUnitStr() {
        return getFormatCode(this.unitCode,null);
    }
 
    
    public String getItemUnitStr() {
        return getFormatCode(this.itemUnitCode,null);
    }
    
    /**
     * 根据任务code获得任务的参数单位
     * @return
     */
    public String getUnitCodeByTaskCode(String taskCode) {
    	ActTaskTypeEnum a = getActTaskTypeEnumByKey(taskCode);
    	if(a!=null){
    		return a.getUnitCode();
    	}
        return  null;
    }
    
    /**
     * 根据任务code获得任务的参数单位的中文
     * @return
     */
    public String getUnitCodeStr(String ccyUnit) {
    	ActTaskTypeEnum a = getActTaskTypeEnumByKey(this.code);
    	if(a!=null){
    		return getFormatCode(a.getUnitCode(),ccyUnit);
    	}
        return  null;
    }
    
    /**
     * 根据任务code获得任务的物品参数单位
     * @return
     */
    public String getItemUnitCodeByTaskCode(String taskCode) {
    	ActTaskTypeEnum a = getActTaskTypeEnumByKey(taskCode);
    	if(a!=null){
    		return a.getItemUnitCode();
    	}
        return  null;
    }
    
    /**
     * 根据任务code获得任务的物品参数单位中文
     * @return
     */
    public String getItemUnitCodeStrByTaskCode(String taskCode,String ccyUnit) {
    	ActTaskTypeEnum a = getActTaskTypeEnumByKey(taskCode);
    	if(a!=null){
    		return getFormatCode(a.getItemUnitCode(),ccyUnit);
    	}
        return  null;
    }
    
    /**
     * 获得指定币中的显示的任务单位 名称
     * @return
     */
    public String getItemUnitStrByCcyUnit(String ccyUnit) {
        return getFormatCode(this.itemUnitCode,ccyUnit);
    }
     
    public String getFormatCode(String code,String ccyUnit) {
        if(StringUtils.isNotBlank(code)){
            if("MONEY".equals(code)){//当前平台是USD币种，而任务默认单位MONEY，按
                return "USD".equals(ccyUnit)?ActUnitEnum.getFormatCode(ccyUnit):ActUnitEnum.getFormatCode("CNY");
            }
            return ActUnitEnum.getFormatCode(code);
        }
        return "";
    }
    
    /**
     * 获得指定平台币中的显示的任务单位 名称
     * @return
     */
    public String getUnitStrByCcy(String ccyUnit) {
        return getFormatCode(this.unitCode,ccyUnit);
    }
    
  
    public String getUnitCode() {
        return unitCode;
    }


    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }


    public String getItemUnitCode() {
        return itemUnitCode;
    }


    public void setItemUnitCode(String itemUnitCode) {
        this.itemUnitCode = itemUnitCode;
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

    public Boolean getDemo() {
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

   
	public static String getNameByKey(String code){
        if(code!=null){
            for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
                if(code.equals(ae.getCode())){
                    return ae.getName();
                }
            }
        }
        return null;
    }
	
	public static ActTaskTypeEnum getActTaskTypeEnumByKey(String code){
        if(code!=null){
            for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
                if(code.equals(ae.getCode()) ||
                    code.startsWith(ActTaskTypeEnum.ZDY_TASK.getCode()) && ae.getCode().equals(ActTaskTypeEnum.ZDY_TASK.getCode())){
                   return ae;
                }
            }
        }
        return null;
    }
	
    /**
     * 不包含自定义任务
     * @return
     */
    public static List<ActTaskTypeEnum> getList(){
        List<ActTaskTypeEnum> result = new ArrayList<ActTaskTypeEnum>();
        for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
            if(ae.getCode().equals(ActTaskTypeEnum.ZDY_TASK.getCode())){
                continue;
            }
            result.add(ae);
        }
        return result;
    }
    
    
    /**
     * 任务型新增改复制可以加载的任务类型
     *  不包含入金任务及部分开仓相关任务及自由兑换任务
     * @return
     */
    public static List<ActTaskTypeEnum> getRwList(){
        List<ActTaskTypeEnum> result = new ArrayList<ActTaskTypeEnum>();
        for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
            if(ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT.getCode()) 
                || ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT_TOTAL.getCode())
                || isComExceptCodes(ae.getCode())){
                continue;
            }
            
            result.add(ae);
        }
        return result;
    }
    
    /**
     * 层级型活动新增改复制可以加载的任务类型
     * 不包含入金任务及部分开仓相关任务及自由兑换任务
     * @return
     */
    public static List<ActTaskTypeEnum> getCjList(){
        List<ActTaskTypeEnum> result = new ArrayList<ActTaskTypeEnum>();
        for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
            if(ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT.getCode()) 
                || ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT_TOTAL.getCode())
                || isComExceptCodes(ae.getCode())){
                continue;
            }
            
            result.add(ae);
        }
        return result;
    }
    
    /**
     * 物品兑换demo型活动新增改复制可以加载的任务类型
     * 只加载demo任务及排除及开仓相关任务
     * @return
     */
    public static List<ActTaskTypeEnum> getWpdhDemoList(){
        List<ActTaskTypeEnum> result = new ArrayList<ActTaskTypeEnum>();
        for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
            if(ae.getDemo()!=null && !ae.getDemo() ||isComExceptCodes(ae.getCode())
                ||ae.getCode().equals(ActTaskTypeEnum.DEMO_OPEN_TIMES.getCode())
                 ||ae.getCode().equals(ActTaskTypeEnum.DEMO_OPEN_LOT.getCode())
                  ||ae.getCode().equals(ActTaskTypeEnum.DEMO_TOTAL_OPEN_TIMES.getCode())
                   ||ae.getCode().equals(ActTaskTypeEnum.DEMO_TOTAL_OPEN_LOT.getCode())
              ){
                continue;
            }
            result.add(ae);
        }
        return result;
    }
 
    /**
     * 物品兑换型活动新增改复制可以加载的任务类型
     *  不包含入金任务及部分开仓相关任务
     */
    public static List<ActTaskTypeEnum> getWpdhList(){
        List<ActTaskTypeEnum> result = new ArrayList<ActTaskTypeEnum>();
        for(ActTaskTypeEnum ae : ActTaskTypeEnum.values()){
            if(ae.getCode().equals(ActTaskTypeEnum.ZDY_TASK.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_DEPOSIT_TOTAL.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_CLOSE_PROFIT_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_CLOSE_LOSS_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_CLOSE_PROFIT_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_CLOSE_LOSS_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_PROFIT_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_LOSS_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_PROFIT_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_LOSS_PERCENT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_OPEN.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_PROFIT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_LOSS.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_OPEN.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_CLOSE.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_PROFIT.getCode())
                || ae.getCode().equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_LOSS.getCode())
               
                ){
                continue;
            }
            result.add(ae);
        }
        return result;
    }
   
    /**
     * 通用需要排除的任务(目前任务型、层级型、入金赠金型、物品兑换型还不需要加载这些（开仓及首次平仓相关的任务），目前只有物品兑换型需要加载，后续会慢慢加上)
     * @return
     */
    public static boolean isComExceptCodes(String rwCode){
            if(rwCode.equals(ActTaskTypeEnum.ZDY_TASK.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_OPEN.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_PROFIT.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_LOSS.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_PROFIT_PERCENT.getCode())
                || rwCode.equals(ActTaskTypeEnum.DEMO_FIRST_CLOSE_LOSS_PERCENT.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_OPEN.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_CLOSE.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_PROFIT.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_LOSS.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_PROFIT_PERCENT.getCode())
                || rwCode.equals(ActTaskTypeEnum.REAL_FIRST_CLOSE_LOSS_PERCENT.getCode())
                 ){
               return true;
            }
         
        return false;
    }
 
}
