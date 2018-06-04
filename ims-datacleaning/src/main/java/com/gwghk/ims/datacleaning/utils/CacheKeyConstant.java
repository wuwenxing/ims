package com.gwghk.ims.datacleaning.utils;

import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.CompanyEnum;

public class CacheKeyConstant {
	//FX-demo
	public static String LASTUPDATE_GTS2_FX_DEMO_CUSTOMER_INFO="lastupdate_gts2_fx_demo_customer_info";
	public static String LASTUPDATE_GTS2_FX_DEMO_DEAL="lastupdate_gts2_fx_demo_deal";
	//Fx-real
	public static String LASTUPDATE_GTS2_FX_REAL_CUSTOMER_INFO="lastupdate_gts2_fx_real_customer_info";
	public static String LASTUPDATE_GTS2_FX_REAL_CASHIN="lastupdate_gts2_fx_real_cashin";
	public static String LASTUPDATE_GTS2_FX_REAL_CASHOUT="lastupdate_gts2_fx_real_cashout";
	public static String LASTUPDATE_GTS2_FX_REAL_DEAL="lastupdate_gts2_fx_real_deal";
	
	
	//HX-demo
	public static String LASTUPDATE_GTS2_HX_DEMO_CUSTOMER_INFO="lastupdate_gts2_hx_demo_customer_info";
	public static String LASTUPDATE_GTS2_HX_DEMO_DEAL="lastupdate_gts2_hx_demo_deal";
	//HX-real
	public static String LASTUPDATE_GTS2_HX_REAL_CUSTOMER_INFO="lastupdate_gts2_hx_real_customer_info";
	public static String LASTUPDATE_GTS2_HX_REAL_CASHIN="lastupdate_gts2_hx_real_cashin";
	public static String LASTUPDATE_GTS2_HX_REAL_CASHOUT="lastupdate_gts2_hx_real_cashout";
	public static String LASTUPDATE_GTS2_HX_REAL_DEAL="lastupdate_gts2_hx_real_deal";	
	
	
	//HXFX-demo
	public static String LASTUPDATE_GTS2_HXFX_DEMO_CUSTOMER_INFO="lastupdate_gts2_hxfx_demo_customer_info";
	public static String LASTUPDATE_GTS2_HXFX_DEMO_DEAL="lastupdate_gts2_hxfx_demo_deal";
	//HXFX-real
	public static String LASTUPDATE_GTS2_HXFX_REAL_CUSTOMER_INFO="lastupdate_gts2_hxfx_real_customer_info";
	public static String LASTUPDATE_GTS2_HXFX_REAL_CASHIN="lastupdate_gts2_hxfx_real_cashin";
	public static String LASTUPDATE_GTS2_HXFX_REAL_CASHOUT="lastupdate_gts2_hxfx_real_cashout";
	public static String LASTUPDATE_GTS2_HXFX_REAL_DEAL="lastupdate_gts2_hxfx_real_deal";	

	
	//CF-demo
	public static String LASTUPDATE_GTS2_CF_DEMO_CUSTOMER_INFO="lastupdate_gts2_cf_demo_customer_info";
	public static String LASTUPDATE_GTS2_CF_DEMO_DEAL="lastupdate_gts2_cf_demo_deal";
	//CF-real
	public static String LASTUPDATE_GTS2_CF_REAL_CUSTOMER_INFO="lastupdate_gts2_cf_real_customer_info";
	public static String LASTUPDATE_GTS2_CF_REAL_CASHIN="lastupdate_gts2_cf_real_cashin";
	public static String LASTUPDATE_GTS2_CF_REAL_CASHOUT="lastupdate_gts2_cf_real_cashout";
	public static String LASTUPDATE_GTS2_CF_REAL_DEAL="lastupdate_gts2_cf_real_deal";
	
	//PM-demo
	public static String LASTUPDATE_GTS2_PM_DEMO_CUSTOMER_INFO="lastupdate_gts2_pm_demo_customer_info";
	public static String LASTUPDATE_GTS2_PM_DEMO_DEAL="lastupdate_gts2_pm_demo_deal";
	//PM-real
	public static String LASTUPDATE_GTS2_PM_REAL_CUSTOMER_INFO="lastupdate_gts2_pm_real_customer_info";
	public static String LASTUPDATE_GTS2_PM_REAL_CASHIN="lastupdate_gts2_pm_real_cashin";
	public static String LASTUPDATE_GTS2_PM_REAL_CASHOUT="lastupdate_gts2_pm_real_cashout";
	public static String LASTUPDATE_GTS2_PM_REAL_DEAL="lastupdate_gts2_pm_real_deal";		
		
	
	public static String GTS2_REAL_ACCOUNT_GROUP_ID="gts2_real_account_group_id_";
	public static String GTS2_DEMO_ACCOUNT_GROUP_ID="gts2_demo_account_group_id_";
	public static String GTS2_REAL_DEAL_GROUP_ID="gts2_real_deal_group_id_";
	public static String GTS2_DEMO_DEAL_GROUP_ID="gts2_demo_deal_group_id_";
	
	
//	public static String HX_MT4_DEMO_CUSTOMER_CHANGE="hx_mt4_demo_customer_change";
//	public static String HX_MT4_REAL_CUSTOMER_CHANGE="hx_mt4_real_customer_change";
	
	public static String getCustomerInfoKey(String env,String cmp){
		String key = null;
		if(ActEnvEnum.DEMO.getValue().equals(env)){
			if(CompanyEnum.fx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_FX_DEMO_CUSTOMER_INFO;
			}else if(CompanyEnum.hx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HX_DEMO_CUSTOMER_INFO;
			}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HXFX_DEMO_CUSTOMER_INFO;
			}else if(CompanyEnum.cf.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_CF_DEMO_CUSTOMER_INFO;
			}else if(CompanyEnum.pm.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_PM_DEMO_CUSTOMER_INFO;
			}
		}else if(ActEnvEnum.REAL.getValue().equals(env)){
			if(CompanyEnum.fx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_FX_REAL_CUSTOMER_INFO;
			}else if(CompanyEnum.hx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HX_REAL_CUSTOMER_INFO;
			}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HXFX_REAL_CUSTOMER_INFO;
			}else if(CompanyEnum.cf.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_CF_REAL_CUSTOMER_INFO;
			}else if(CompanyEnum.pm.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_PM_REAL_CUSTOMER_INFO;
			}
		}
		return key;
	}
	
	public static String getCashinKey(String cmp){
		String key = null;
		if(CompanyEnum.fx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_FX_REAL_CASHIN;
		}else if(CompanyEnum.hx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_HX_REAL_CASHIN;
		}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_HXFX_REAL_CASHIN;
		}else if(CompanyEnum.cf.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_CF_REAL_CASHIN;
		}else if(CompanyEnum.pm.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_PM_REAL_CASHIN;
		}
		return key;
	}
	
	public static String getCashoutKey(String cmp){
		String key = null;
		if(CompanyEnum.fx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_FX_REAL_CASHOUT;
		}else if(CompanyEnum.hx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_HX_REAL_CASHOUT;
		}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_HXFX_REAL_CASHOUT;
		}else if(CompanyEnum.cf.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_CF_REAL_CASHOUT;
		}else if(CompanyEnum.pm.getCode().equals(cmp)){
			key = LASTUPDATE_GTS2_PM_REAL_CASHOUT;
		}
		return key;
	}
	
	public static String getDealKey(String env,String cmp){
		String key = null;
		if(ActEnvEnum.DEMO.getValue().equals(env)){
			if(CompanyEnum.fx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_FX_DEMO_DEAL;
			}else if(CompanyEnum.hx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HX_DEMO_DEAL;
			}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HXFX_DEMO_DEAL;
			}else if(CompanyEnum.cf.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_CF_DEMO_DEAL;
			}else if(CompanyEnum.pm.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_PM_DEMO_DEAL;
			}
		}else if(ActEnvEnum.REAL.getValue().equals(env)){
			if(CompanyEnum.fx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_FX_REAL_DEAL;
			}else if(CompanyEnum.hx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HX_REAL_DEAL;
			}else if(CompanyEnum.hxfx.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_HXFX_REAL_DEAL;
			}else if(CompanyEnum.cf.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_CF_REAL_DEAL;
			}else if(CompanyEnum.pm.getCode().equals(cmp)){
				key = LASTUPDATE_GTS2_PM_REAL_DEAL;
			}
		}
		return key;
	}
}
