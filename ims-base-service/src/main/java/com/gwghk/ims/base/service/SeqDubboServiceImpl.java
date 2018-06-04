package com.gwghk.ims.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.base.manager.SeqManager;
import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.inf.SeqDubboService;

/**
 * 摘要：统一序列号服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月31日
 */
@Service
public class SeqDubboServiceImpl implements SeqDubboService{
	private static final Logger logger = LoggerFactory.getLogger(SeqDubboServiceImpl.class);
	
	@Autowired
	private SeqManager seqManager;
	
	/**
	 * 功能：获取序列号
	 */
	public String getSeq(String seqCode,@Company Long companyId) {
		logger.info("getSeq-->【{},{}】",seqCode,companyId);
		return seqManager.updateSeq(seqCode,companyId);
	}
}