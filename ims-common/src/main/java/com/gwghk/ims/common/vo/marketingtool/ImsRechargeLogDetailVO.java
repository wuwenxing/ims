package com.gwghk.ims.common.vo.marketingtool;

import java.io.Serializable;

import com.gwghk.ims.common.annotation.AuthColumn;
import com.gwghk.ims.common.enums.AuthColumnEnum;
import com.gwghk.ims.common.enums.CommitStatusEnum;
import com.gwghk.ims.common.enums.RechargeChannelEnum;
import com.gwghk.ims.common.enums.RechargeStatusEnum;
import com.gwghk.ims.common.enums.RechargeTypeEnum;
import com.gwghk.ims.common.vo.BaseVO;

public class ImsRechargeLogDetailVO extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long detailId;

    /**
     * 充值类型,online/flow
     */
    private String rechargeType;

    /**
     * 充值手机号
     */
    @AuthColumn(columnName = AuthColumnEnum.MOBILEPHONE)
    private String phone;

    /**
     * 手机归属地
     */
    private String phoneArea;

    /**
     * 大小
     */
    private String rechargeSize;

    /**
     * 价格
     */
    private Double price;

    /**
     * 通道类型
     */
    private String interfaceType;

    /**
     * 返回批次号
     */
    private String resBatchNo;

    /**
     * 返回code
     */
    private String resCode;

    /**
     * 提交状态
     */
    private String commitStatus;

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
    
    private String startTimeStr;
    
    private String endTimeStr;
    
    
    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * 充值类型,online/flow
     * @return recharge_type 充值类型,online/flow
     */
    public String getRechargeType() {
        return rechargeType;
    }

    /**
     * 充值类型-格式化
     */
    public String getRechargeTypeName() {
    	if(null != rechargeType){
    		return RechargeTypeEnum.format(rechargeType);
    	}
		return null;
    }

    /**
     * 充值类型,online/flow
     * @param rechargeType 充值类型,online/flow
     */
    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType == null ? null : rechargeType.trim();
    }

    /**
     * 充值手机号
     * @return phone 充值手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 充值手机号
     * @param phone 充值手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 手机归属地
     * @return phone_area 手机归属地
     */
    public String getPhoneArea() {
        return phoneArea;
    }

    /**
     * 手机归属地
     * @param phoneArea 手机归属地
     */
    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea == null ? null : phoneArea.trim();
    }

    /**
     * 大小
     * @return recharge_size 大小
     */
    public String getRechargeSize() {
        return rechargeSize;
    }

    /**
     * 大小
     * @param rechargeSize 大小
     */
    public void setRechargeSize(String rechargeSize) {
        this.rechargeSize = rechargeSize == null ? null : rechargeSize.trim();
    }

    /**
     * 价格
     * @return price 价格
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 价格
     * @param price 价格
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 通道类型
     * @return interface_type 通道类型
     */
    public String getInterfaceType() {
        return interfaceType;
    }

    /**
     * 通道类型-格式化
     */
    public String getInterfaceTypeName() {
    	if(null != interfaceType){
    		return RechargeChannelEnum.format(interfaceType);
    	}
		return null;
    }

    /**
     * 通道类型
     * @param interfaceType 通道类型
     */
    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType == null ? null : interfaceType.trim();
    }

    /**
     * 返回批次号
     * @return res_batch_no 返回批次号
     */
    public String getResBatchNo() {
        return resBatchNo;
    }

    /**
     * 返回批次号
     * @param resBatchNo 返回批次号
     */
    public void setResBatchNo(String resBatchNo) {
        this.resBatchNo = resBatchNo == null ? null : resBatchNo.trim();
    }

    /**
     * 返回code
     * @return res_code 返回code
     */
    public String getResCode() {
        return resCode;
    }

    /**
     * 返回code
     * @param resCode 返回code
     */
    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    /**
     * 提交状态
     * @return commit_status 提交状态
     */
    public String getCommitStatus() {
        return commitStatus;
    }

    /**
     * 提交状态-格式化
     */
    public String getCommitStatusName() {
    	if(null != commitStatus){
    		return CommitStatusEnum.format(commitStatus);
    	}
		return null;
    }

    /**
     * 提交状态
     * @param commitStatus 提交状态
     */
    public void setCommitStatus(String commitStatus) {
        this.commitStatus = commitStatus == null ? null : commitStatus.trim();
    }

    /**
     * 充值状态
     * @return send_status 充值状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 充值状态-格式化
     */
    public String getSendStatusName() {
    	if(null != sendStatus){
    		return RechargeStatusEnum.format(sendStatus);
    	}
		return null;
    }

    /**
     * 充值状态
     * @param sendStatus 充值状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 扩展字段1
     * @return ext1 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展字段1
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    /**
     * 扩展字段2
     * @return ext2 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展字段2
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    /**
     * 扩展字段3
     * @return ext3 扩展字段3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 扩展字段3
     * @param ext3 扩展字段3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

}