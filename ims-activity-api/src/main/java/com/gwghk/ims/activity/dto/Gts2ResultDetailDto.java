package com.gwghk.ims.activity.dto;

import java.util.Map;

/**
 * 
 * @ClassName: Gts2ResultDetailDto
 * @Description: 调用GTS2的API后返回的resul结果详细信息
 * @author lawrence
 * @date 2017年10月23日
 *
 */
public class Gts2ResultDetailDto {
	private String code;
	private Gts2ResultErrorDto error;
	// 成功返回的信息
	private String result;
	// 成功会返回信息结果集
	private Map<String, String> context;
	//错误结果集
	private Map<String, Gts2ResultErrorDto> fieldErrors;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Gts2ResultErrorDto getError() {
		return error;
	}
	public void setError(Gts2ResultErrorDto error) {
		this.error = error;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, String> getContext() {
		return context;
	}
	public void setContext(Map<String, String> context) {
		this.context = context;
	}
	public Map<String, Gts2ResultErrorDto> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(Map<String, Gts2ResultErrorDto> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
