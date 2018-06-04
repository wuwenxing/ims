package com.gwghk.ims.common.inf.external.prize;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.ApiRespResult;

/**
 * 摘要：发放处理服务接口
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月26日
 */
public interface ActProgressDubboService {
    /**
     * 功能：统计活动进度自动处理
     */
    ApiRespResult<Void> doAutoActProgressStat(@Company Long companyId);
}