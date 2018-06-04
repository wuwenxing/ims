package com.gwghk.ims.activity.service.mis;

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
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisActTradeRecordDubboService;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActTradeRecordVO;

/**
 * 摘要：清洗后的交易数据业务处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月25日
 */
@Service
public class MisActTradeRecordDubboServiceImpl implements MisActTradeRecordDubboService{

    @Autowired
    private ActTradeRecordDao actTradeRecordMapper;

    /**
   * 功能：分页查询
   */
	@Override
	public MisRespResult<PageR<ActTradeRecordVO>> findPageList(ActTradeRecordVO vo,@Company Long companyId) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
      PageHelper.orderBy(PageCustomizedUtil.sort(ActTradeRecord.class, vo.getSort(), vo.getOrder()));
      PageR<ActTradeRecordVO> tradeRecordPage =  PageCustomizedUtil.copyPageList(new PageR<ActTradeRecord>(this.findList(vo)),
      												new PageR<ActTradeRecordVO>(),ActTradeRecordVO.class);
      //开仓平仓一条显示
      if(tradeRecordPage != null){
      	if(CollectionUtils.isEmpty(tradeRecordPage.getList())){
      		tradeRecordPage.setList(new ArrayList<>());
      		return MisRespResult.success(tradeRecordPage);
      	}
      	if("out".equals(vo.getTradeType())){
      		vo.setTradeType("");
      		List<ActTradeRecord> allActTradeRecordDTOList = this.findList(vo);
      		List<ActTradeRecordVO>  actTradeRecordDTOList = tradeRecordPage.getList();
          	for(ActTradeRecordVO  actOutTradeRecordDTO : actTradeRecordDTOList){
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
      		List<ActTradeRecordVO>  actTradeRecordDTOList = tradeRecordPage.getList();
          	for(ActTradeRecordVO  actOutTradeRecordDTO : actTradeRecordDTOList){
          		if("out".equals(actOutTradeRecordDTO.getTradeType())){   //如果是平仓，显示对应的开仓时间，如果是开仓，就正常显示
          			boolean flag = true;
          			for(ActTradeRecordVO  actInTradeRecordDTO : actTradeRecordDTOList){
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
    public List<ActTradeRecord> findList(ActTradeRecordVO vo) {
        Map<String,Object> map = new HashMap<>();
        map.put("env", vo.getEnv());
        map.put("companyId", vo.getCompanyId());
        map.put("accountNo", vo.getAccountNo());
        map.put("platform", vo.getPlatform());
        map.put("positionId", vo.getPositionId());
        map.put("tradeType", vo.getTradeType());
        map.put("product", vo.getProduct());
        map.put("tradeStartTime", vo.getTradeStartTime());
        map.put("tradeEndTime", vo.getTradeEndTime());
        return actTradeRecordMapper.findListByMap(map);
    }
}
