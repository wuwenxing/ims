package com.gwghk.ims.marketingtool.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwghk.ims.common.common.BaseEntity;

/**
 * 
 * @ClassName: TCamiLogDetail
 * @Description: 卡密处理log
 * @author lawrence
 * @date 2018年5月7日
 *
 */
@Table(name="ims_cami_log_detail")
public class ImsCamiLogDetail extends BaseEntity {

	@Id
    @Column(name="detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long detailId;

	/**
	 * 提交状态
	 */
	private String commitStatus;

	/**
	 * 通道类型
	 */
	private String interfaceType;

	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 卡密名称
	 */
	private String camiName;

	/**
	 * 返回code
	 */
	private String resCode;

	/**
	 * 充值状态
	 */
	private String sendStatus;

	/**
	 * 扩展字段1
	 */
	private String ext1;

	/**
	 * 扩展字段2
	 */
	private String ext2;

	/**
	 * 扩展字段3
	 */
	private String ext3;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 返回批次号
	 */
	private String resBatchNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getCommitStatus() {
		return commitStatus;
	}

	public void setCommitStatus(String commitStatus) {
		this.commitStatus = commitStatus;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getResBatchNo() {
		return resBatchNo;
	}

	public void setResBatchNo(String resBatchNo) {
		this.resBatchNo = resBatchNo;
	}

	public String getCamiName() {
		return camiName;
	}

	public void setCamiName(String camiName) {
		this.camiName = camiName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}