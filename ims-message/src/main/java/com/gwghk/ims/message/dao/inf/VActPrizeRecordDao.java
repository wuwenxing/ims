package com.gwghk.ims.message.dao.inf;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.message.dao.entity.VActPrizeRecord;

public interface VActPrizeRecordDao {
	public VActPrizeRecord getBonusDemoPrizeIssuing(@Param("accountNo") String accountNo,@Param("actNo") String actNo,
			@Param("platform") String platform, @Param("companyCode") String companyCode);
}