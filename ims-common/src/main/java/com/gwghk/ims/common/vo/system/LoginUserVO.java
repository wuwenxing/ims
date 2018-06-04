package com.gwghk.ims.common.vo.system;

import java.io.Serializable;

import com.gwghk.ims.common.vo.BaseVO;

/**
 * 摘要：登录用户VO
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
public class LoginUserVO extends BaseVO implements Serializable {
	private static final long serialVersionUID = -3608817231441699252L;

	/**
	 * 账号
	 */
	private String userNo;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 验证码
	 */
	private String captcha;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
