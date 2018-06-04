package com.gwghk.ims.activity.service.mis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.PrizeRecordRedo;
import com.gwghk.ims.activity.dao.inf.PrizeRecordRedoDao;
import com.gwghk.ims.activity.enums.ActPrizeRecordRedoStatusEnum;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.activity.MisPrizeRecordRedoDubboService;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;

/**
 * 摘要：失败发放记录service
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2017年6月22日
 */
@Service
public class MisPrizeRecordRedoDubboServiceImpl implements MisPrizeRecordRedoDubboService  {

    @Autowired
    private PrizeRecordRedoDao prizeRecordRedoMapper;

    
    /**
   * 功能：分页查询
   */
	@Override
	public MisRespResult<PageR<PrizeRecordRedoVO>> findPageList(PrizeRecordRedoVO reqDto,@Company Long companyId) {
		 PageHelper.startPage(reqDto.getPage(), reqDto.getRows(), true);
       PageHelper.orderBy(PageCustomizedUtil.sort(PrizeRecordRedo.class, reqDto.getSort(), reqDto.getOrder()));
       if (companyId != null) {
           reqDto.setCompanyId(companyId);
       }
       List<PrizeRecordRedo> resultList = prizeRecordRedoMapper.findList(reqDto);
       return MisRespResult.success(PageCustomizedUtil.copyPageList(new PageR<>(resultList), new PageR<PrizeRecordRedoVO>(), PrizeRecordRedoVO.class));
	}


	/**
   * 重发
   */
	@Override
	public MisRespResult<Void> updateResend(List<Long> ids,@Company Long companyId) {
		if (ids != null && !ids.isEmpty()) {
          Map<String,Object> map=new HashMap<String,Object>();
          map.put("ids", ids);
          map.put("redoStatus", ActPrizeRecordRedoStatusEnum.WAITING.getCode());
          prizeRecordRedoMapper.updateByMap(map);
		return MisRespResult.success();
      }
      return MisRespResult.success();
		
	}

	/**
   * 删除
   */
	@Override
	public MisRespResult<Void> deleteByIds(List<Long> ids,@Company Long companyId) {
		 if (ids != null && !ids.isEmpty()) {
			 	Map<String, Object> map=new HashMap<String,Object>();
			 	map.put("ids", ids);
				prizeRecordRedoMapper.deleteByMap(map);
	            return MisRespResult.success();
	        }
	     return MisRespResult.success();
	}

}
