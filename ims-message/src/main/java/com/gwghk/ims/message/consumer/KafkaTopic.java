package com.gwghk.ims.message.consumer;

import com.gwghk.ims.common.enums.ActEnvEnum;

public enum KafkaTopic {
	
	GTS2_REAL_CLONE_CUSTOMER_ACCOUNT_INFO("{0}_external_t_clone_customer_account_info","GTS2-real-平台客户资料数据",ActEnvEnum.REAL.getValue(),"external"),
	GTS2_DEMO_CLONE_CUSTOMER_ACCOUNT_INFO("{0}_external_t_clone_customer_account_info","GTS2-demo-平台客户资料数据",ActEnvEnum.DEMO.getValue(),"external"),
	GTS2_REAL_CLONE_TRADE("{0}_external_t_clone_trade","GTS2-real-平台客户资料数据",ActEnvEnum.REAL.getValue(),"external"),
	GTS2_DEMO_CLONE_TRADE("{0}_external_t_clone_trade","GTS2-demo-平台客户资料数据",ActEnvEnum.DEMO.getValue(),"external"),
	GTS2_REAL_CLONE_CASHIN_PROPOSAL("{0}_external_t_clone_cashin_proposal","GTS2-real-入金数据",ActEnvEnum.REAL.getValue(),"external"),
	GTS2_REAL_CLONE_CASHOUT_PROPOSAL("{0}_external_t_clone_cashout_proposal","GTS2-real-出金数据",ActEnvEnum.REAL.getValue(),"external"),
	GTS2_RELATED_CUSTOMER("{0}_t_related_customer","GTS2-real-demo关系",ActEnvEnum.REAL.getValue(),"office"),

	GTS2_DEMO_DEAL("{0}_trade_gts2_gts2deal", "GTS2模拟交易数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_REAL_DEAL("{0}_trade_gts2_gts2deal", "GTS2真实交易数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_REAL_CASHIN("{0}_office_t_cashin_proposal","GTS2入金数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_REAL_CASHOUT("{0}_office_t_cashout_proposal","GTS2出金数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_REAL_COUNTRY_DICT("{0}_office_t_country_dict", "GTS2国籍数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	//FX-real
	GTS2_FX_REAL_CUSTOMER("{0}_office_t_customer_1","GTS2-FX真实客户账号资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_ACCOUNT("{0}_office_t_account_info_1","GTS2-FX真实客户平台资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_ACCOUNT_GROUP("{0}_office_t_account_group_1","GTS2-FX真实客户平台组数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_SYMBOL("{0}_trade_gts2_gts2symbol_1","GTS2-FX真实交易产品数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_GROUP("{0}_trade_gts2_gts2group_1","GTS2-FX真实交易组别",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_BO_DICT("{0}_office_t_bo_dict_1","GTS2-FX真实数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_FX_REAL_RELATED_CUSTOMER("{0}_office_t_related_customer_1","GTS2-FX真实模拟账号关联数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	//FX-demo
	GTS2_FX_DEMO_CUSTOMER("{0}_office_t_customer_1","GTS2-FX模拟客户账号资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_FX_DEMO_ACCOUNT("{0}_office_t_account_info_1","GTS2-FX模拟客户平台资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_FX_DEMO_ACCOUNT_GROUP("{0}_office_t_account_group_1","GTS2-FX模拟客户平台组数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_FX_DEMO_SYMBOL("{0}_trade_gts2_gts2symbol_1","GTS2-FX模拟交易产品数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_FX_DEMO_GROUP("{0}_trade_gts2_gts2group_1","GTS2-FX模拟交易组别",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_FX_DEMO_BO_DICT("{0}_office_t_bo_dict_1","GTS2-FX模拟数据字典",ActEnvEnum.DEMO.getValue(),"office_ori"),	
	
	//HX-demo
	GTS2_HX_DEMO_CUSTOMER("{0}_office_t_customer_2","GTS2-HX模拟客户账号资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HX_DEMO_ACCOUNT("{0}_office_t_account_info_2","GTS2-HX模拟客户平台资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HX_DEMO_ACCOUNT_GROUP("{0}_office_t_account_group_2","GTS2-HX模拟客户平台组数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HX_DEMO_SYMBOL("{0}_trade_gts2_gts2symbol_2","GTS2-HX模拟交易产品数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HX_DEMO_GROUP("{0}_trade_gts2_gts2group_2","GTS2-HX模拟交易组别",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HX_DEMO_BO_DICT("{0}_office_t_bo_dict_2","GTS2-HX模拟数据字典",ActEnvEnum.DEMO.getValue(),"office_ori"),
	//HX-real
	GTS2_HX_REAL_CUSTOMER("{0}_office_t_customer_2","GTS2-HX真实客户账号资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_ACCOUNT("{0}_office_t_account_info_2","GTS2-HX真实客户平台资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_ACCOUNT_GROUP("{0}_office_t_account_group_2","GTS2-HX真实客户平台组数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_SYMBOL("{0}_trade_gts2_gts2symbol_2","GTS2-HX真实交易产品数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_GROUP("{0}_trade_gts2_gts2group_2","GTS2-HX真实交易组别",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_BO_DICT("{0}_office_t_bo_dict_2","GTS2-HX真实数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HX_REAL_RELATED_CUSTOMER("{0}_office_t_related_customer_2","GTS2-HX真实模拟账号关联数据",ActEnvEnum.REAL.getValue(),"office_ori"),

	
	//HXFX-demo
	GTS2_HXFX_DEMO_CUSTOMER("{0}_office_t_customer_9","GTS2-HXFX模拟客户账号资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HXFX_DEMO_ACCOUNT("{0}_office_t_account_info_9","GTS2-HXFX模拟客户平台资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HXFX_DEMO_ACCOUNT_GROUP("{0}_office_t_account_group_9","GTS2-HXFX模拟客户平台组数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HXFX_DEMO_SYMBOL("{0}_trade_gts2_gts2symbol_9","GTS2-HXFX模拟交易产品数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HXFX_DEMO_GROUP("{0}_trade_gts2_gts2group_9","GTS2-HXFX模拟交易组别",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_HXFX_DEMO_BO_DICT("{0}_office_t_bo_dict_9","GTS2-HXFX模拟数据字典",ActEnvEnum.DEMO.getValue(),"office_ori"),
	//HXFX-real
	GTS2_HXFX_REAL_CUSTOMER("{0}_office_t_customer_9","GTS2-HXFX真实客户账号资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_ACCOUNT("{0}_office_t_account_info_9","GTS2-HXFX真实客户平台资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_ACCOUNT_GROUP("{0}_office_t_account_group_9","GTS2-HXFX真实客户平台组数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_SYMBOL("{0}_trade_gts2_gts2symbol_9","GTS2-HXFX真实交易产品数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_GROUP("{0}_trade_gts2_gts2group_9","GTS2-HXFX真实交易组别",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_BO_DICT("{0}_office_t_bo_dict_9","GTS2-HXFX真实数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_HXFX_REAL_RELATED_CUSTOMER("{0}_office_t_related_customer_9","GTS2-HXFX真实模拟账号关联数据",ActEnvEnum.REAL.getValue(),"office_ori"),

	
	//CF-demo
	GTS2_CF_DEMO_CUSTOMER("{0}_office_t_customer_8","GTS2-CF模拟客户账号资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_CF_DEMO_ACCOUNT("{0}_office_t_account_info_8","GTS2-CF模拟客户平台资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_CF_DEMO_ACCOUNT_GROUP("{0}_office_t_account_group_8","GTS2-CF模拟客户平台组数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_CF_DEMO_SYMBOL("{0}_trade_gts2_gts2symbol_8","GTS2-CF模拟交易产品数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_CF_DEMO_GROUP("{0}_trade_gts2_gts2group_8","GTS2-CF模拟交易组别",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_CF_DEMO_BO_DICT("{0}_office_t_bo_dict_8","GTS2-CF模拟数据字典",ActEnvEnum.DEMO.getValue(),"office_ori"),
	//CF-real
	GTS2_CF_REAL_CUSTOMER("{0}_office_t_customer_8","GTS2-CF真实客户账号资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_ACCOUNT("{0}_office_t_account_info_8","GTS2-CF真实客户平台资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_ACCOUNT_GROUP("{0}_office_t_account_group_8","GTS2-CF真实客户平台组数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_SYMBOL("{0}_trade_gts2_gts2symbol_8","GTS2-CF真实交易产品数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_GROUP("{0}_trade_gts2_gts2group_8","GTS2-CF真实交易组别",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_BO_DICT("{0}_office_t_bo_dict_8","GTS2-CF真实数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_CF_REAL_RELATED_CUSTOMER("{0}_office_t_related_customer_8","GTS2-CF真实模拟账号关联数据",ActEnvEnum.REAL.getValue(),"office_ori"),

	
	//PM-demo
	GTS2_PM_DEMO_CUSTOMER("{0}_office_t_customer_3","GTS2-PM模拟客户账号资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_PM_DEMO_ACCOUNT("{0}_office_t_account_info_3","GTS2-PM模拟客户平台资料数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_PM_DEMO_ACCOUNT_GROUP("{0}_office_t_account_group_3","GTS2-PM模拟客户平台组数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_PM_DEMO_SYMBOL("{0}_trade_gts2_gts2symbol_3","GTS2-PM模拟交易产品数据",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_PM_DEMO_GROUP("{0}_trade_gts2_gts2group_3","GTS2-PM模拟交易组别",ActEnvEnum.DEMO.getValue(),"office_ori"),
	GTS2_PM_DEMO_BO_DICT("{0}_office_t_bo_dict_3","GTS2-PM模拟数据字典",ActEnvEnum.DEMO.getValue(),"office_ori"),
	//PM-real
	GTS2_PM_REAL_CUSTOMER("{0}_office_t_customer_3","GTS2-PM真实客户账号资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_ACCOUNT("{0}_office_t_account_info_3","GTS2-PM真实客户平台资料数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_ACCOUNT_GROUP("{0}_office_t_account_group_3","GTS2-PM真实客户平台组数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_SYMBOL("{0}_trade_gts2_gts2symbol_3","GTS2-PM真实交易产品数据",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_GROUP("{0}_trade_gts2_gts2group_3","GTS2-PM真实交易组别",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_BO_DICT("{0}_office_t_bo_dict_3","GTS2-PM真实数据字典",ActEnvEnum.REAL.getValue(),"office_ori"),
	GTS2_PM_REAL_RELATED_CUSTOMER("{0}_office_t_related_customer_3","GTS2-PM真实模拟账号关联数据",ActEnvEnum.REAL.getValue(),"office_ori");
	
	private String value;
	private String name;
	private String env;
	private String type;

	KafkaTopic(String value, String name,String env,String type) {
		this.value = value;
		this.name = name;
		this.env = env;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEnv(){
		return env;
	}

	public String getType() {
		return type;
	}
}
