package com.gwghk.ims.common.enums;

/**
 * @ClassName: SyncDataUpdateTypeEnumEnum
 * @Description: 同步数据类型
 * @author lawrence
 * @date 2017年5月24日
 */
public enum SyncDataUpdateTypeEnum {
//    GTS2_RREAL_CUSTOMER("gts2RealCustomer","Gts2真实账号数据同步"),
//    GTS2_DEMO_CUSTOMER("gts2DemoCustomer","Gts2模拟账号数据同步"),
//    GTS2_REAL_TRADE("gts2RealTrade","Gts2真实交易数据同步"),
//    GTS2_DEMO_TRADE("gts2DemoTrade","Gts2模拟交易数据同步"),
//    GTS2_REAL_CASHIN("gts2RealCashin","Gts2真实用户入金同步"),
//    GTS2_REAL_CASHOUT("gts2RealCashout","Gts2真实用户出金同步"),
    
//    HX_RREAL_CUSTOMER("hxRealCustomer","恒信真实账号数据同步"),
//    HX_DEMO_CUSTOMER("hxDemoCustomer","恒信模拟账号数据同步"),
//    HX_RREAL_TRADE("hxRealTrade","恒信真实交易数据同步"),
//    HX_DEMO_TRADE("hxDemoTrade","恒信模拟交易数据同步"),
//    HX_RREAL_CASHIN("hxRealCashin","恒信真实用户入金数据同步"),
    
	GROUP_BLACK_ACCOUNT("groupBlackAccount","集团黑名单同步",null),
    //FX
    GTS2_FX_RREAL_CUSTOMER("gts2FxRealCustomer","清洗客户资料数据【FX(Gts2)-真实->活动中心】",1l),
    GTS2_FX_DEMO_CUSTOMER("gts2FxDemoCustomer","清洗客户资料数据【FX(Gts2)-模拟->活动中心】",1l),
    GTS2_FX_RREAL_CASHIN("gts2FxRealCashin","清洗客户入金数据【FX(Gts2)->活动中心】",1l),
    GTS2_FX_RREAL_CASHOUT("gts2FxRealCashout","清洗客户出金数据【FX(Gts2)->活动中心】",1l),
    GTS2_FX_RREAL_TRADE("gts2FxRealTrade","清洗客户交易数据【FX(Gts2)-真实->活动中心】",1l),
    GTS2_FX_DEMO_TRADE("gts2FxDemoTrade","清洗客户交易数据【FX(Gts2)-模拟->活动中心】",1l),
    
    //HX
    GTS2_HX_REAL_CUSTOMER("gts2HxRealCustomer","清洗客户资料数据【HX(Gts2)-真实->活动中心】",2l),
    GTS2_HX_DEMO_CUSTOMER("gts2HxDemoCustomer","清洗客户资料数据【HX(Gts2)-模拟->活动中心】",2l),
    GTS2_HX_RREAL_CASHIN("gts2HxRealCashin","清洗入金数据【HX(Gts2)->活动中心】",2l),
    GTS2_HX_RREAL_CASHOUT("gts2HxRealCashout","清洗出金数据【HX(Gts2)->活动中心】",2l),
    GTS2_HX_RREAL_TRADE("gts2HxRealTrade","清洗客户交易数据【HX(Gts2)-真实->活动中心】",2l),
    GTS2_HX_DEMO_TRADE("gts2HxDemoTrade","清洗客户交易数据【HX(Gts2)-模拟->活动中心】",2l),
    
    MT4_HX_REAL_CUSTOMER("mt4HxRealCustomer","清洗客户资料数据【HX(MT4)-真实->活动中心】",2l),
    MT4_HX_DEMO_CUSTOMER("mt4HxDemoCustomer","清洗客户资料数据【HX(MT4)-模拟->活动中心】",2l),
    MT4_HX_REAL_CASHIN("mt4HxRealCashin","清洗入金数据【HX(MT4)-真实->活动中心】",2l),
    MT4_HX_REAL_CASHOUT("mt4HxRealCashout","清洗出金数据【HX(MT4)-模拟->活动中心】",2l),
    MT4_HX_REAL_TRADE("mt4HxRealTrade","清洗客户交易数据【HX(MT4)-真实->活动中心】",2l),
    MT4_HX_DEMO_TRADE("mt4HxDemoTrade","清洗客户交易数据【HX(MT4)-模拟->活动中心】",2l),
    
    //HXFX
    GTS2_HXFX_RREAL_CUSTOMER("gts2HxfxRealCustomer","清洗客户资料数据【HXFX(Gts2)-真实->活动中心】",9l),
    GTS2_HXFX_DEMO_CUSTOMER("gts2HxfxDemoCustomer","清洗客户资料数据【HXFX(Gts2)-模拟->活动中心】",9l),
    GTS2_HXFX_RREAL_CASHIN("gts2HxfxRealCashin","清洗客户入金数据【HXFX(Gts2)->活动中心】",9l),
    GTS2_HXFX_RREAL_CASHOUT("gts2HxfxRealCashout","清洗客户出金数据【HXFX(Gts2)->活动中心】",9l),
    GTS2_HXFX_RREAL_TRADE("gts2HxfxRealTrade","清洗客户交易数据【HXFX(Gts2)-真实->活动中心】",9l),
    GTS2_HXFX_DEMO_TRADE("gts2HxfxDemoTrade","清洗客户交易数据【HXFX(Gts2)-模拟->活动中心】",9l),
    
    //PM
    GTS2_PM_RREAL_CUSTOMER("gts2PmRealCustomer","清洗客户资料数据【PM(Gts2)-真实->活动中心】",3l),
    GTS2_PM_DEMO_CUSTOMER("gts2PmDemoCustomer","清洗客户资料数据【PM(Gts2)-模拟->活动中心】",3l),
    GTS2_PM_RREAL_CASHIN("gts2PmRealCashin","清洗客户入金数据【PM(Gts2)->活动中心】",3l),
    GTS2_PM_RREAL_CASHOUT("gts2PmRealCashout","清洗客户出金数据【PM(Gts2)->活动中心】",3l),
    GTS2_PM_RREAL_TRADE("gts2PmRealTrade","清洗客户交易数据【PM(Gts2)-真实->活动中心】",3l),
    GTS2_PM_DEMO_TRADE("gts2PmDemoTrade","清洗客户交易数据【PM(Gts2)-模拟->活动中心】",3l),

    
    //CF
    GTS2_CF_RREAL_CUSTOMER("gts2CfRealCustomer","清洗客户资料数据【CF(Gts2)-真实->活动中心】",8l),
    GTS2_CF_DEMO_CUSTOMER("gts2CfDemoCustomer","清洗客户资料数据【CF(Gts2)-模拟->活动中心】",8l),
    GTS2_CF_RREAL_CASHIN("gts2CfRealCashin","清洗客户入金数据【CF(Gts2)->活动中心】",8l),
    GTS2_CF_RREAL_CASHOUT("gts2CfRealCashout","清洗客户出金数据【CF(Gts2)->活动中心】",8l),
    GTS2_CF_RREAL_TRADE("gts2CfRealTrade","清洗客户交易数据【CF(Gts2)-真实->活动中心】",8l),
    GTS2_CF_DEMO_TRADE("gts2CfDemoTrade","清洗客户交易数据【CF(Gts2)-模拟->活动中心】",8l),

    FX_BONUS("fxBonus","fx贈金",1l),
    HX_BONUS("hxBonus","hx贈金",2l),
    CF_BONUS("cfBonus","cf贈金",8l),
    HXFX_BONUS("hxfxBonus","hxfx贈金",9l),
    PM_BONUS("pmBonus","pm贈金",3l),

    FX_DEMO_AMOUNT("fxDemoAmount","fx模拟金额",1l),
    HX_DEMO_AMOUNT("hxDemoAmount","hx模拟金额",2l),
    CF_DEMO_AMOUNT("cfDemoAmount","cf模拟金额",8l),
    HXFX_DEMO_AMOUNT("hxfxDemoAmount","hxfx模拟金额",9l),
    PM_DEMO_AMOUNT("hxfxDemoAmount","pm模拟金额",3l);
	
	private final String value;
	private final String labelKey;
	private final Long companyId;
	
	SyncDataUpdateTypeEnum(String value, String labelKey,Long companyId){
		this.value = value;
		this.labelKey = labelKey;
		this.companyId = companyId;
	}

	public String getValue() {
		return this.value;
	}
	 
	public String getLabelKey() {
		return this.labelKey;
	}

	public Long getCompanyId() {
		return companyId;
	}
}