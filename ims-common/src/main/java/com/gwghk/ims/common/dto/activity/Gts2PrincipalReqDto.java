package com.gwghk.ims.common.dto.activity;

import java.io.Serializable;

/**
 * 
* @ClassName: Gts2PrincipalReqDto
* @Description: 调用Gts2的Api必传的Principal信息
* @author lawrence
* @date 2017年5月23日
*
 */
public class Gts2PrincipalReqDto implements Serializable{
  private static final long serialVersionUID = 7166743204958696780L;
  
  private String loginName;
  private String remoteIpAddress;
  private String invoker;
  private String companyId;

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getRemoteIpAddress() {
    return remoteIpAddress;
  }

  public void setRemoteIpAddress(String remoteIpAddress) {
    this.remoteIpAddress = remoteIpAddress;
  }

  public String getInvoker() {
    return invoker;
  }

  public void setInvoker(String invoker) {
    this.invoker = invoker;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }


}
