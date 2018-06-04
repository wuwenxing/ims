package com.gwghk.ims.activity.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActTradeRecord;
import com.gwghk.ims.activity.dao.inf.ActTradeRecordDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActTradeRecordDTO;
import com.gwghk.ims.common.inf.external.activity.ActTradeRecordDubboService;
import com.gwghk.ims.common.util.PageCustomizedUtil;

/**
 * 摘要：清洗后的交易数据业务处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月25日
 */
@Service
public class ActTradeRecordDubboServiceImpl implements ActTradeRecordDubboService{

    @Autowired
    private ActTradeRecordDao actTradeRecordMapper;

    /**
   * 功能：分页查询
   */
	@Override
	public MisRespResult<PageR<ActTradeRecordDTO>> findPageList(ActTradeRecordDTO dto, Long companyId) {
		PageHelper.startPage(dto.getPage(), dto.getRows(), true);
      PageHelper.orderBy(PageCustomizedUtil.sort(ActTradeRecord.class, dto.getSort(), dto.getOrder()));
      PageR<ActTradeRecordDTO> tradeRecordPage =  PageCustomizedUtil.copyPageList(new PageR<ActTradeRecord>(this.findList(dto)),
      												new PageR<ActTradeRecordDTO>(),ActTradeRecordDTO.class);
      //开仓平仓一条显示
      if(tradeRecordPage != null){
      	if(CollectionUtils.isEmpty(tradeRecordPage.getList())){
      		tradeRecordPage.setList(new ArrayList<>());
      		return MisRespResult.success(tradeRecordPage);
      	}
      	if("out".equals(dto.getTradeType())){
      		dto.setTradeType("");
      		List<ActTradeRecord> allActTradeRecordDTOList = this.findList(dto);
      		List<ActTradeRecordDTO>  actTradeRecordDTOList = tradeRecordPage.getList();
          	for(ActTradeRecordDTO  actOutTradeRecordDTO : actTradeRecordDTOList){
          		boolean flag = true;
          		for(ActTradeRecord  allRecord : allActTradeRecordDTOList){
          			if("in".equals(allRecord.getTradeType()) && allRecord.getPositionNo().equals(actOutTradeRecordDTO.getPositionNo())){
          				flag = false;
          				actOutTradeRecordDTO.setOpenTime(allRecord.getTradeTime());
      					actOutTradeRecordDTO.setCloseTime(actOutTradeRecordDTO.getTradeTime());
      					actOutTradeRecordDTO.setCloseLot(actOutTradeRecordDTO.getTradeLot());
      					actOutTradeRecordDTO.setCloseOrderNo(actOutTradeRecordDTO.getOrderNo());
      					actOutTradeRecordDTO.setPositionId(actOutTradeRecordDTO.getPositionNo());
      					break;
          			}
          		}
          		if(flag){
          			actOutTradeRecordDTO.setCloseTime(actOutTradeRecordDTO.getTradeTime());
          			actOutTradeRecordDTO.setCloseLot(actOutTradeRecordDTO.getTradeLot());
          			actOutTradeRecordDTO.setCloseOrderNo(actOutTradeRecordDTO.getOrderNo());
          			actOutTradeRecordDTO.setPositionId(actOutTradeRecordDTO.getPositionNo());
          		}
          	}
      	}else{
      		List<ActTradeRecordDTO>  actTradeRecordDTOList = tradeRecordPage.getList();
          	for(ActTradeRecordDTO  actOutTradeRecordDTO : actTradeRecordDTOList){
          		if("out".equals(actOutTradeRecordDTO.getTradeType())){   //如果是平仓，显示对应的开仓时间，如果是开仓，就正常显示
          			boolean flag = true;
          			for(ActTradeRecordDTO  actInTradeRecordDTO : actTradeRecordDTOList){
          				if("in".equals(actInTradeRecordDTO.getTradeType()) && 
          						actInTradeRecordDTO.getPositionNo().equals(actOutTradeRecordDTO.getPositionNo())){
          					flag = false;
          					actOutTradeRecordDTO.setOpenTime(actInTradeRecordDTO.getTradeTime());
          					actOutTradeRecordDTO.setCloseTime(actOutTradeRecordDTO.getTradeTime());
          					actOutTradeRecordDTO.setCloseLot(actOutTradeRecordDTO.getTradeLot());
          					actOutTradeRecordDTO.setCloseOrderNo(actOutTradeRecordDTO.getOrderNo());
          					actOutTradeRecordDTO.setPositionId(actOutTradeRecordDTO.getPositionNo());
          					break;
          				}
          			}
          			if(flag){
              			actOutTradeRecordDTO.setCloseTime(actOutTradeRecordDTO.getTradeTime());
              			actOutTradeRecordDTO.setCloseLot(actOutTradeRecordDTO.getTradeLot());
              			actOutTradeRecordDTO.setCloseOrderNo(actOutTradeRecordDTO.getOrderNo());
              			actOutTradeRecordDTO.setPositionId(actOutTradeRecordDTO.getPositionNo());
              		}
          		}else{
          			actOutTradeRecordDTO.setOpenTime(actOutTradeRecordDTO.getTradeTime());
  					actOutTradeRecordDTO.setCloseLot(actOutTradeRecordDTO.getTradeLot());
  					actOutTradeRecordDTO.setCloseOrderNo(actOutTradeRecordDTO.getOrderNo());
  					actOutTradeRecordDTO.setPositionId(actOutTradeRecordDTO.getPositionNo());
          		}
          	}
      	}
      }
      return MisRespResult.success(tradeRecordPage);
	}

    
    /**
     * 功能：列表查询
     */
    public List<ActTradeRecord> findList(ActTradeRecordDTO dto) {
        Map<String,Object> map = new HashMap<>();
        map.put("env", dto.getEnv());
        map.put("companyId", dto.getCompanyId());
        map.put("accountNo", dto.getAccountNo());
        map.put("platform", dto.getPlatform());
        map.put("positionId", dto.getPositionId());
        map.put("tradeType", dto.getTradeType());
        map.put("product", dto.getProduct());
        map.put("tradeStartTime", dto.getTradeStartTime());
        map.put("tradeEndTime", dto.getTradeEndTime());
        return actTradeRecordMapper.findListByMap(map);
    }
}
