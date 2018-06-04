package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
* @ClassName: Gts2CashAdjustReqDto
* @Description: 调用Gts2额度接口参数
* @author lawrence
* @date 2017年5月23日
*
 */
public class Gts2CashAdjustReqDto implements Serializable{
  private static final long serialVersionUID = -114891715395343714L;
  
  private boolean isAutoApprove;
  private BigDecimal payAmount;
  private String payCurrency;
  private BigDecimal transAmount;
  private String transCurrency;
  private String accountNo;
  private String platform;
  private String adjustType;

  public boolean getIsAutoApprove() {
    return isAutoApprove;
  }

  public void setIsAutoApprove(boolean isAutoApprove) {
    this.isAutoApprove = isAutoApprove;
  }

  public BigDecimal getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
  }

  public String getPayCurrency() {
    return payCurrency;
  }

  public void setPayCurrency(String payCurrency) {
    this.payCurrency = payCurrency;
  }

  public BigDecimal getTransAmount() {
    return transAmount;
  }

  public void setTransAmount(BigDecimal transAmount) {
    this.transAmount = transAmount;
  }

  public String getTransCurrency() {
    return transCurrency;
  }

  public void setTransCurrency(String transCurrency) {
    this.transCurrency = transCurrency;
  }
  
  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getAdjustType() {
    return adjustType;
  }

  public void setAdjustType(String adjustType) {
    this.adjustType = adjustType;
  }


}
