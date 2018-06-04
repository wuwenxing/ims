package com.gwghk.ims.bos.web.common.context;

/**
 * 公共常量类
 */
public class Constants {

	/**
	 * 超级管理员用户-默认用户账户-拥有全部权限
	 */
	public final static String superAdmin = "superAdmin";

	public final static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

	/**
	 * 加密结尾
	 */
	public static final String END_WITH_STR = "_ENCRY";
	
	/**
	 * 用户初始密码-默认111
	 */
	public final static String initPassword = "111";

	/**
	 * 最大上传文件大小
	 */
	public static final int MAX_UPLOAD_FILE_SIZE = 10 * 1024 * 1024;
	
	/**
	 * 一批流量最多充值50个手机号码-亿美
	 */
	public final static int batchSizeFlow = 50;
	
	/**
	 * 一批最多发送100个邮箱或手机号码
	 */
	public final static int batchSize = 100;
	
	/**
	 * 一次上传最多上传50000个邮箱或手机号码
	 */
	public final static int uploadSize = 50000;

}
