package com.gwghk.ims.common.inf.external.activity;

import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 摘要：发放处理服务接口
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月26日
 */
public interface ActHandleDubboService {
    /**
     * 功能：物品兑换型-兑奖
     */
   /* ApiRespResult<Void> doExchange(ActTaskExchangeReqDto reqDto);*/

    /**
     * 功能：自动生成发放纪录
     */
    ApiRespResult<Void> doAutoGenerateRecord(Long companyId);

    /**
     * 功能：自动处理应发记录
     */
    ApiRespResult<Integer> doWaitSendRecords(Long companyId);
    
    /**
     * 功能：自动处理可发记录中没有串码的记录
     */
    ApiRespResult<Integer> autoDealStringCodeSendRecord(Long companyId);
    
    /**
     * 功能：统计活动进度自动处理
     */
    ApiRespResult<Void> doAutoActProgressStat(Long companyId);
    
    /**
     * 功能：指定活动用户自动生成发放纪录(公司，活动，账号不允许为空)
     */
     ApiRespResult<Void> autoGenerateRecordByAccount(Long companyId,String activityPeriods,String accountNo,String platform,boolean needRequiredTime);
}