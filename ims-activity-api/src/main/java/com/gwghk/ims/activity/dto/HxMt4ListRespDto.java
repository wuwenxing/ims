package com.gwghk.ims.activity.dto;

import java.util.List;

/**
 * 
 * @ClassName: HxMt4LoginDto
 * @Description: HX-MT4登录返回的sid数据
 * @author lawrence
 * @date 2017年12月14日
 *
 */
public class HxMt4ListRespDto {

	private List<HxMt4RespMapDto> data;

	public List<HxMt4RespMapDto> getData() {
		return data;
	}

	public void setData(List<HxMt4RespMapDto> data) {
		this.data = data;
	}

}
