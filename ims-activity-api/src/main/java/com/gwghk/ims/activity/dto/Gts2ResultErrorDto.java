package com.gwghk.ims.activity.dto;

/**
 * 
 * @ClassName: Gts2ResultErrorDto
 * @Description: 调用GTS2的API后返回的错误信息
 * @author lawrence
 * @date 2017年10月23日
 *
 */
public class Gts2ResultErrorDto {
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
