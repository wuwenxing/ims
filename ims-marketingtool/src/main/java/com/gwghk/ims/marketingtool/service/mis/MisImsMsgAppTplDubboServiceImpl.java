package com.gwghk.ims.marketingtool.service.mis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.inf.mis.marketingtool.MisImsMsgAppTplDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.vo.marketingtool.ImsMsgAppTplVO;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.ims.marketingtool.dao.entity.ImsMsgAppTpl;
import com.gwghk.ims.marketingtool.enums.AppMsgTypeEnum;
import com.gwghk.ims.marketingtool.enums.AppPushTypeEnum;
import com.gwghk.ims.marketingtool.manager.ImsMsgAppTplManager;

/**
 * 摘要：App消息模板服务实现类
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年3月31日
 */
@Service
public class MisImsMsgAppTplDubboServiceImpl implements MisImsMsgAppTplDubboService{

	private static final Logger logger = LoggerFactory.getLogger(MisImsMsgAppTplDubboServiceImpl.class);

	@Autowired
	private ImsMsgAppTplManager imsMsgAppTplManager;
	
	@Autowired
	private MisSystemDictDubboService misSystemDictDubboService ;

	/**
	 * 功能: 分页查询
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> findPageList(ImsMsgAppTplVO vo,@Company Long companyId) {
		try {
			Map<String,Object> map = imsMsgAppTplManager.findPageList(vo);
			Object obj = map.get("list");
			if(obj != null){
				List<ImsMsgAppTplVO> list = (List<ImsMsgAppTplVO>)obj;
				for(ImsMsgAppTplVO ims : list){
					MisRespResult<SystemDictVO> result = misSystemDictDubboService.findByDictCode(ims.getLang(), companyId) ;
					if(result.isOk()){
						SystemDictVO key = result.getData() ;
						if(null != key){
							ims.setLang(key.getDictNameCn());
						}
					}
					ims.setPushTypeTxt(AppPushTypeEnum.getLabelKeyByValue(ims.getPushType()));
					ims.setMsgTypeTxt(AppMsgTypeEnum.getValueByCode(ims.getMsgType()));
				}
			}
			return map;
		} catch (Exception e) {
			logger.error("<--系统异常,【findPageList【】", e);
			return null;
		}
	}

	public MisRespResult<List<ImsMsgAppTplVO>> findList(ImsMsgAppTplVO vo,@Company Long companyId) {
		try {
			List<ImsMsgAppTpl> list = imsMsgAppTplManager.findList(vo);
			return MisRespResult.success(ImsBeanUtil.copyList(list, ImsMsgAppTplVO.class));
		} catch (Exception e) {
			logger.error("<--系统异常,【findList【】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	@Override
	public MisRespResult<ImsMsgAppTplVO> findById(Long id,@Company Long companyId) {
		logger.info("findById-->【id:{}】", id);
		try {
			return MisRespResult.success(ImsBeanUtil.copyNotNull(new ImsMsgAppTplVO(),imsMsgAppTplManager.findById(id)));
		} catch (Exception e) {
			logger.error("<--系统异常,【findById】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：保存或修改记录
	 */
	@Override
	public MisRespResult<Void> saveOrUpdate(ImsMsgAppTplVO vo,@Company Long companyId) {
		logger.info("saveOrUpdate-->【code:{},tplName:{},companyId:{}】",vo.getCode(),vo.getTplName(),companyId);
		try {
			ImsMsgAppTpl  imsMsgAppTpl = ImsBeanUtil.copyNotNull(new ImsMsgAppTpl(),vo);
			return imsMsgAppTplManager.saveOrUpdate(imsMsgAppTpl);
		} catch (Exception e) {
			logger.error("<--系统异常，【saveOrUpdate】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * 功能：批量删除记录
	 */
	@Override
	public MisRespResult<Void> deleteByIds(String ids,@Company Long companyId) {
		logger.info("deleteByIds-->【ids:{},companyId:{}】",ids,companyId);
		try {
			int num = imsMsgAppTplManager.deleteByIds(ids);
			logger.info("批量删除app消息模板成功！条数:{}",num);
			return MisRespResult.success();
		} catch (Exception e) {
			logger.error("<--系统异常，【deleteByIds】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
}
