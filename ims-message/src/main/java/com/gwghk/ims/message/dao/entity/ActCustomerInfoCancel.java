package com.gwghk.ims.message.dao.entity;

/**
 * 
* @ClassName: ActCustomerInfoCancel
* @Description: 客户资料已经注销的栏位信息(身份证，手机号)
* @author lawrence
* @date 2018年2月9日
*
 */
public class ActCustomerInfoCancel extends BaseEntity {

	private Long id;
	private String type;
	private String dataValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}