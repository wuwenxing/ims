package com.gwghk.ims.common.enums;

import java.util.Locale;

/**
 * 摘要：公共常量类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public class Constants {
	/**
	 * 超级管理员用户-默认用户账户-拥有全部权限
	 */
	public final static String superAdmin = "superAdmin";

	/**
	 * 用户初始密码-默认123456
	 */
	public final static String initPassword = "123456";

	/**
	 * 最大上传文件大小
	 */
	public static final int MAX_UPLOAD_FILE_SIZE = 10 * 1024 * 1024;

	public final static Locale LOCALE_ZH_CN = new Locale("zh", "CN", "");

	public final static Locale LOCALE_ZH_TW = new Locale("zh", "TW", "");

	public final static Locale LOCALE_EN_US = new Locale("en", "US", "");
	public final static String LOCALE_ZH_TW_STR = "zh_TW";
	public final static String LOCALE_ZH_CN_STR = "zh_CN";
	public final static String LOCALE_EN_US_STR = "en_US";

	public final static String ACCOUNT_SEQ_LS = "one_sequence";
	public final static String ACCOUNT_SEQ_LJ = "two_sequence";

	
	/**
	 *日志级别定义
	 */
	public final static Integer Log_Leavel_INFO = 1;
	public final static Integer Log_Leavel_WARRING = 2;
	public final static Integer Log_Leavel_ERROR = 3;
	
	/**
	 * 日志类型
	 */
	public final static String Log_Type_LOGIN = "1"; //登陆
	public final static String Log_Type_EXIT = "2";  //退出
	public final static String Log_Type_INSERT = "3"; //新增
	public final static String Log_Type_DEL = "4"; //删除
	public final static String Log_Type_UPDATE = "5"; //更新
	public final static String Log_Type_OTHER = "6"; //其他
	public final static String LOG_TYPE_APPROVE = "7"; //审批
	public final static String LOG_TYPE_CANCEL_APPROVE = "8"; //取消审批
	 
   /**
    * 标记名分隔符
    */
	public final static String TAG_NAME_SEPERATOR = "#_#";
	public final static String COMMA = ",";
	 
	public final static String SYSTEM = "system";
	 
	public final static String ENABLE_Y = "启用";
	public final static String ENABLE_N = "关闭";
	 
	public final static String LOGIN = "登录";

	/**
     * 地区表中所有国家的parentCode
     */
	public final static String COUNTRY_PARENT_CODE = "-1";
	 
	public final static String SALT = "MIICeAIBAD#@K*%NBgkqhki$*G9w0BAQEFAASCAmIwggJ"; // 签名加盐
	public final static String AUTH_HEADE_RNAME = "Authorization";
	public final static String CLIENT_TYPE_HEADE_RNAME = "Client-Type";
	public final static String CLIENT_TYPE = "back"; 	// 客户端类型
	 
		//YESORNO FLAG
		
    public final static Integer YESFLAG = 0;
		
	public final static Integer NOFLAG = 1;
	
	//计算百分比时除数常量
	public final static String DIVISORBASEPERCENT = "100";
	
	//计算精度
	public final static int DIVIDE_DECIMAL = 2;
	
	/**国际化语言key*/
	public final static String WW_TRANS_I18N_LOCALE = "WW_TRANS_I18N_LOCALE";
	public final static String LOCALE_FOR_COOKIE="LOCALE_FOR_COOKIE";
}
