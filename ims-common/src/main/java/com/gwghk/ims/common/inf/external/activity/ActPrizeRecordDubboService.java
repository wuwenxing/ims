package com.gwghk.ims.common.inf.external.activity;

import java.util.List;

import com.gwghk.ims.common.dto.activity.ActPrizeRecordReqDTO;
import com.gwghk.ims.common.dto.activity.ActPrizeRecordRespDTO;

/**
 * 摘要：奖品记录服务
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月23日
 */
public interface ActPrizeRecordDubboService {
	
    List<ActPrizeRecordRespDTO> getAllPrizeRecordList(ActPrizeRecordReqDTO actPrizeRecordReqDto);
    
    String getRedisPrizeRecord(String recordNumber);
    
    public void batchUpdateAbnormalPrizeRecord(String companyCode);
}
