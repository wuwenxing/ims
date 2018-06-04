package com.gwghk.sys.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.dubbo.config.annotation.Service;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.inf.mis.sys.MisSystemLogDubboService;
import com.gwghk.ims.common.vo.system.SystemLogVO;
import com.gwghk.sys.api.manager.SystemLogManager;

/**
 * 摘要：系统-日志服务实现
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月7日
 */
@Service
public class MisSystemLogDubboServiceImpl implements MisSystemLogDubboService{
	private static final Logger logger = LoggerFactory.getLogger(MisSystemLogDubboServiceImpl.class);
	
	@Autowired
	private SystemLogManager systemLogManager;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public MisRespResult<Void> addLog(SystemLogVO logReqDto) {
		logger.info("addLog-->【{}】",logReqDto);
		try{
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						systemLogManager.save(logReqDto);
					} catch (Exception e) {
						logger.error("save db fail!err:{}", new Object[] { e });
					}
				}
			});
			return MisRespResult.success();
		}catch(Exception e){
			logger.error("<--系统异常,【addLog】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<PageR<SystemLogVO>> findPageList(SystemLogVO vo) {
		logger.info("findPageList-->");
		try{
			PageR<SystemLogVO> pageR = systemLogManager.findPageList(vo);
			return MisRespResult.success(pageR);
		}catch(Exception e){
			logger.error("<--系统异常,【findPageList】",e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
