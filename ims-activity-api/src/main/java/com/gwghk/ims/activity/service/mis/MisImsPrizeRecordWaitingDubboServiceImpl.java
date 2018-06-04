package com.gwghk.ims.activity.service.mis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.ImsPrizeRecordWaiting;
import com.gwghk.ims.activity.manager.ImsPrizeRecordWaitingManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActUnitEnum;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisImsPrizeRecordWaitingDubboService;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 
 * 摘要：物品发放记录,应发列表 Service
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月10日
 */
@Service
public class MisImsPrizeRecordWaitingDubboServiceImpl implements MisImsPrizeRecordWaitingDubboService {
	private static final Logger logger = LoggerFactory.getLogger(MisImsPrizeRecordWaitingDubboServiceImpl.class);

	@Autowired
	private ImsPrizeRecordWaitingManager imsPrizeRecordWaitingManager;

	public MisRespResult<PageR<ImsPrizeRecordWaitingVO>> findPageList(ImsPrizeRecordWaitingVO vo,@Company Long companyId) {
		logger.info("findPageList-->【ActivityPeriods:{},accountNo:{}】", vo.getActNo(),vo.getAccountNo());
		try {
			PageR<ImsPrizeRecordWaitingVO> pageR = imsPrizeRecordWaitingManager.findPageList(vo);
			List<ImsPrizeRecordWaitingVO> list = pageR.getList() ;
			formateReturnData(list,companyId);
			return MisRespResult.success(pageR);
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	public MisRespResult<List<ImsPrizeRecordWaitingVO>> findList(ImsPrizeRecordWaitingVO vo,@Company Long companyId) {
		logger.info("findList-->【ActivityPeriods:{}】", vo.getActNo());
		try {
			List<ImsPrizeRecordWaiting> list = imsPrizeRecordWaitingManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsPrizeRecordWaitingVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsPrizeRecordWaitingVO> findById(Integer id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(
					ImsBeanUtil.copyNotNull(new ImsPrizeRecordWaitingVO(), imsPrizeRecordWaitingManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	
	/**
	 * 格式化输出的内容
	 * @param list
	 */
	public void formateReturnData(List<ImsPrizeRecordWaitingVO> list,Long companyId){
		for(ImsPrizeRecordWaitingVO r : list){
			r.setGivedStatusTxt(IssuingStatusEnum.format(r.getGivedStatus()));
            if(IssuingStatusEnum.waiting.getValue().equals(r.getGivedStatus())){
                r.setAuditStatusTxt("-");
            }else{
                r.setAuditStatusTxt(AuditStatusEnum.format(r.getAuditStatus()));
            }
            if (ActItemTypeEnum.WITHGOLD.getCode().equals(r.getItemType())
                || ActItemTypeEnum.TOKENCOIN.getCode().equals(r.getItemType())
                || ActItemTypeEnum.ANALOGCOIN.getCode().equals(r.getItemType())) {
                r.setItemCategoryTxt("-");
                r.setItemPriceTxt("-");
            } else {
                r.setItemCategoryTxt(ActItemCategoryEnum.format(r.getItemCategory()));
                if (r.getItemPrice() != null) {
                    r.setItemPriceTxt(BigDecimalUtil.formatComma2BigDecimal(r.getItemPrice()).toString());
                    if (StringUtil.isNotEmpty(r.getItemPriceUnit())) {
                       // r.setItemPriceTxt(r.getItemPriceTxt() + DictCache.getDictNameByDictCode(r.getItemPriceUnit()));
                    	r.setItemPriceTxt(r.getItemPriceTxt() + ActUnitEnum.getFormatCode(r.getItemPriceUnit()));
                    }
                }
            }
            if (r.getItemAmount() != null) {
                //String ItemAmountUnit = DictCache.getDictNameByDictCode(r.getItemAmountUnit());
                String ItemAmountUnit = ActUnitEnum.getFormatCode(r.getItemAmountUnit()) ;
                r.setItemAmountTxt(BigDecimalUtil.toFixedTwoDights(r.getItemAmount()));
                if (StringUtil.isNotEmpty(r.getItemAmountUnit())) {
                    r.setItemAmountTxt(r.getItemAmountTxt() + ItemAmountUnit);
                }
            }
            r.setItemTypeTxt(ActItemTypeEnum.formatValue(r.getItemType()));
            //r.setItemTypeTxt(DictCache.getDictNameByDictCode(r.getItemType()));
		}
		
	}

}
