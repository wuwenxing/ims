package com.gwghk.ims.activity.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.activity.dao.entity.Gts2symbolDemoRealWrapper;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.manager.VGts2symbolDemoRealManager;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.activity.MisGts2symbolDemoRealService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.activity.Gts2symbolDemoRealVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月28日  
* @version 1.0  
*/
@Service
public class MisGts2symbolDemoRealServiceImpl implements MisGts2symbolDemoRealService {

	private static final Logger logger = LoggerFactory.getLogger(MisGts2symbolDemoRealServiceImpl.class);
	
	@Autowired
	private VGts2symbolDemoRealManager vGts2symbolDemoRealManager;
 
	 
	public MisRespResult<List<Gts2symbolDemoRealVO>> findAll(Gts2symbolDemoRealVO vo,Long companyId) {
	
		logger.info("findList-->【IsDemo:{}】", vo.getIsDemo());
		try {
			List<Gts2symbolDemoRealWrapper> list=vGts2symbolDemoRealManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, Gts2symbolDemoRealVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 

	public Map<String, String> findAllMap(Long companyId) {
		return null;
	}

}
