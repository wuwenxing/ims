package com.gwghk.ims.activity.dto;

/**
 * 
 * @ClassName: Gts2ResultDto
 * @Description: 调用GTS2的API后返回的结果对象
 * @author lawrence
 * @date 2017年10月23日
 *
 */
public class Gts2ResultDto {

	private String code;
	private Gts2ResultDetailDto result;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Gts2ResultDetailDto getResult() {
		return result;
	}
	public void setResult(Gts2ResultDetailDto result) {
		this.result = result;
	}

}
