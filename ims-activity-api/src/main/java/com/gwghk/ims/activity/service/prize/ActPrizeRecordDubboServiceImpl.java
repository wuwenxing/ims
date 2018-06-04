package com.gwghk.ims.activity.service.prize;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 摘要：奖品记录服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月23日
 */
@Service
public class ActPrizeRecordDubboServiceImpl /*implements ActPrizeRecordDubboService*/ {
	
	/*@Autowired
    private ActPrizeRecordManager  actPrizeRecordManager;
	
	@Autowired
	private RedisHelper<String,String> redisHelper;
	*/
	/**
     * 功能：根据查询条件->查询对应的所有奖品记录
     */
   /* public List<ActPrizeRecordRespDTO> getAllPrizeRecordList(ActPrizeRecordReqDTO actPrizeRecordReqDto) {
    	return actPrizeRecordManager.getAllPrizeRecordList(actPrizeRecordReqDto);
    }

	@Override
	public String getRedisPrizeRecord(String recordNumber) {
		String value = redisHelper.get(recordNumber);
		return value;
	}*/
	
	
	/**
	 * 
	 * @MethodName: batchUpdateAbnormalPrizeRecord
	 * @Description: 更新异常发放记录订单
	 * @param companyCode
	 * @return void
	 */
	/*public void batchUpdateAbnormalPrizeRecord(String companyCode){
		actPrizeRecordManager.batchUpdateAbnormalPrizeRecord(companyCode);
	}*/
	
	
}
