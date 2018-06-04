package com.gwghk.ims.common.dto.job;

import java.io.Serializable;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：客户参与活动请求对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月23日
 */
public class ActAccountActivityReqDTO extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 2984705759787931770L;

    /**
     * 平台账号
     */
    private String accountNo;
    
    /**
     * 平台
     */
    private String platform;
    
    /**
     * 显示的总数
     */
    private Long total;

    public Long getTotal() {
    	if (null == total) {
    		total = 10L;
    	}
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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
}