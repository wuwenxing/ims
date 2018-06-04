package com.gwghk.ims.activity.manager;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.entity.ActWhiteListWrapper;
import com.gwghk.ims.activity.dao.inf.ActWhiteListDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActPlatformEnum;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.ActSettleTypeEnum;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.util.FileUtil;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动白名单-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActWhiteListManager {
	Logger logger = LoggerFactory.getLogger(ActWhiteListManager.class);
	@Autowired
	private ActWhiteListDao actWhiteListDao;
	@Autowired
	private ActBlackListManager actBlackListManager;
	@Autowired
	private ActSettingManager actSettingManager;
	@Autowired
	private ActProposalModifyManager actProposalModifyManager;
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActWhiteListVO> findPageList(ActWhiteListVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActWhiteList.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActWhiteListWrapper>(this.findList(vo)),
				new PageR<ActWhiteListVO>(), ActWhiteListVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActWhiteListWrapper> findList(ActWhiteListVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", vo.getActivityPeriods());
		map.put("activityName", vo.getActivityName());
		map.put("accountNo", vo.getAccountNo());
		map.put("mobile", vo.getMobile());
		map.put("env", vo.getEnv());
		map.put("platform", vo.getPlatform());
		map.put("proposalStatus", vo.getProposalStatus());
		map.put("createDateStartTime", vo.getCreateDateStartTime());
		map.put("createDateEndTime", vo.getCreateDateEndTime());
		map.put("companyId", vo.getCompanyId());
		return actWhiteListDao.findListBySearch(map);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActWhiteList> findListByActivityPeriods(String activityPeriods) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", activityPeriods);
		return actWhiteListDao.findListByMap(map);
	}

	/**
	 * 批量保存白名单
	 * 
	 * @param vo
	 * @return
	 */
	/**
	 * @param vo
	 * @return
	 */
	public MisRespResult<Void> batchSave(ActWhiteListVO vo) {
		String activityPeriods = vo.getActivityPeriods();
		if (StringUtils.isEmpty(activityPeriods)) {
			return MisRespResult.error(MisResultCode.Act20000);
		}
		ActSetting actSetting = actSettingManager.findByactivityPeriods(activityPeriods);
		if (null == actSetting) {
			return MisRespResult.error(MisResultCode.Act20000);
		}
		// 已审批且有效的活动才允许修改
		if (!ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(actSetting.getProposalStatus())
				|| actSetting.getEndTime() != null && new Date().after(actSetting.getEndTime())) {
			return MisRespResult.error(MisResultCode.Act20016);
		}
		try {
			/*ActSettingDetailsVO actDetailVo = new ActSettingDetailsVO();
			ActConditionSetting actCondSetting = actConditionSettingManager.findCustCondtionSetting(activityPeriods);
			actCondSetting.setWhiteListFileName(vo.getFileName());
			actCondSetting.setWhiteListFilePath(vo.getFilePath());
			actCondSetting.setActivityPeriods(activityPeriods);
			actDetailVo.setActCondSetting(ImsBeanUtil.copyNotNull(new ActConditionSettingVO(), actCondSetting));
			actProposalModifyManager.saveActProposalModifyWithHasApprovedAct(actDetailVo, actSetting,
					vo.getCreateUser());*/
			 
			vo.getFilePath();
			vo.setApprover(null);
			vo.setApproveDate(null);
			vo.setProposalStatus(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode());//默认批量上传为待审批
			saveFileData(vo, actSetting);
		} catch (Exception ex) {
			logger.error("batchSave-->异常", ex);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
		return MisRespResult.success();
	}

	/**
	 * 检查上传的数据是否正确
	 * 
	 * @param fileItem
	 * @param result
	 * @param compareFileType
	 * @param compareFileKeys
	 * @return
	 * @throws Exception
	 */
	public MisRespResult<Void> checkUploadData(List<List<Object>> result, String compareFilePath, String actNo)
			throws Exception {
		Set<String> compareFileKeys = listCompareSet(compareFilePath, actNo);

		Set<String> fileKeys = new HashSet<String>();// 当前上传文件数据key,
		for (int i = 0; i < result.size(); i++) {
			List<Object> item = result.get(i);
			if (item.size() < 4) {
				String msg = String.format("上传失败:第%s行错误，交易账号、账户类型、平台不能为空!", i + 2);
				return MisRespResult.error(msg);
			}
			Object accountNoObj = item.get(1), accountTypeObj = item.get(2), platformObj = item.get(3);
			if (accountNoObj == null || StringUtil.isNullOrEmpty(accountNoObj.toString()) || accountTypeObj == null
					|| StringUtil.isNullOrEmpty(accountTypeObj.toString()) || platformObj == null
					|| StringUtil.isNullOrEmpty(platformObj.toString())) {
				String msg = String.format("上传失败:第%s行错误，交易账号、账户类型、平台不能为空!", i + 2);
				return MisRespResult.error(msg);
			}
			if (accountNoObj instanceof String == false) {
				String msg = String.format("上传失败:第%s行错误，交易账号格式错误，需为文本格式!", i + 2);
				return MisRespResult.error(msg);
			}
			// 账户类型校验
			String accountTypeStr = accountTypeObj.toString();
			int typeIndex = accountTypeStr.indexOf(":");
			String accountType = "";
			if (typeIndex > 0) {
				accountType = accountTypeStr.substring(0, typeIndex).trim();
			}
			if (StringUtils.isEmpty(accountType) || !(ActAccountTypeEnum.DEMO.getAliasCode().equals(accountType)
					|| ActAccountTypeEnum.REAL.getAliasCode().equals(accountType))) {
				String msg = String.format("上传失败:第%s行错误，账户类型填写有误!", i + 2);
				return MisRespResult.error(msg);
			}
			if (StringUtils.isEmpty(platformObj.toString())
					|| !ActPlatformEnum.isContainsCode(platformObj.toString().trim())) {
				String msg = String.format("上传失败:第%s行错误，平台填写有误!", i + 2);
				return MisRespResult.error(msg);
			}
			String key = accountNoObj.toString() + "_" + accountType + "_" + platformObj.toString();
			if (fileKeys.contains(key)) {
				String msg = String.format("上传失败:第%s行错误，当前上传名单中存在同平台同类型同交易账号的重复记录!", i + 2);
				return MisRespResult.error(msg);
			} else if (compareFileKeys != null && compareFileKeys.contains(key)) {
				String msg = "";
				msg = String.format("上传失败:第%s行错误，黑名单中已存在此记录，白名单和黑名单中不能同时存在相同的记录!", i + 2);
				return MisRespResult.error(msg);
			} else {
				fileKeys.add(key);
			}
		}
		return MisRespResult.success();
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActWhiteList findById(Integer id) {
		return actWhiteListDao.findObject(id);
	}

	/***
	* 
	* 摘要:批量审批   
	* @author George.li  
	* @date 2018年5月30日  
	* @version 1.0   
	* @param idArray
	* @param ActWhiteListVO
	* @return
	 */
	public MisRespResult<Void> proposal(String idArray,ActWhiteListVO reqVO) {
		try {
			List<String> ids=Arrays.asList(idArray.split(","));
			 
			for(String id:ids) {
				ActWhiteList  black=findById(Integer.parseInt(id));
				//待审批的状态
				if(!black.getProposalStatus().equals(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode())) {
					return MisRespResult.error("待审批状态白名单才可审批");
				}
				ActWhiteListVO vo=ImsBeanUtil.copyNotNull(new ActWhiteListVO(), black);
				vo.setProposalStatus(ActProposalStatusEnum.ACTHASAPPROVED.getCode());
				vo.setApprover(reqVO.getUpdateUser());
				vo.setApproveDate(reqVO.getUpdateDate());
				vo.setUpdateDate(reqVO.getUpdateDate());
				vo.setUpdateUser(reqVO.getUpdateUser());
				saveOrUpdate(vo);
			}
			return MisRespResult.success();
		}catch(Exception e) {
			logger.error("<--系统异常，【proposal】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/***
	* 
	* 摘要:批量取消
	* @author George.li  
	* @date 2018年5月30日  
	* @version 1.0   
	* @param idArray
	* @param ActWhiteListVO
	* @return
	 */
	public MisRespResult<Void> cancel(String idArray,ActWhiteListVO reqVO) {
		try {
			List<String> ids=Arrays.asList(idArray.split(","));
			 
			for(String id:ids) {
				ActWhiteList  black=findById(Integer.parseInt(id));
				//待审批的状态
				if(!black.getProposalStatus().equals(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode())) {
					return MisRespResult.error("待审批状态白名单才可取消");
				}
				ActWhiteListVO vo=ImsBeanUtil.copyNotNull(new ActWhiteListVO(), black);
				vo.setProposalStatus(ActProposalStatusEnum.ACtHASCANCELED.getCode());
				vo.setApprover(reqVO.getUpdateUser());
				vo.setApproveDate(reqVO.getUpdateDate());
				vo.setUpdateDate(reqVO.getUpdateDate());
				vo.setUpdateUser(reqVO.getUpdateUser());
				saveOrUpdate(vo);
			}
			return MisRespResult.success();
		}catch(Exception e) {
			logger.error("<--系统异常，【proposal】", e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActWhiteListVO vo) throws Exception {
		if (null == vo.getId()) {
			
			vo.setCreateDate(new Date());
			vo.setUpdateDate(new Date());
			vo.setEnableFlag("Y");
			vo.setDeleteFlag("N");
			actWhiteListDao.save(ImsBeanUtil.copyNotNull(new ActWhiteList(), vo));
		} else {
			ActWhiteList old = findById(vo.getId().intValue());
			ImsBeanUtil.copyNotNull(old, vo);
			old.setUpdateDate(new Date());
			actWhiteListDao.update(old);
		}
	}

	/**
	 * 
	 * 功能: 通过文件路径上传白名单
	 * 
	 */
	public void saveFileData(ActWhiteListVO reqVO, ActSetting actSetting) {
		if (!StringUtils.isEmpty(reqVO.getFilePath())) {
			List<List<Object>> result = null;
			try {
				InputStream in = FileUtil.readInputStream(reqVO.getFilePath());
				if (in != null) {
					result = ExcelUtil.readExcelByInputStream(in);
					if (result != null && !result.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							List<Object> item = result.get(i);
							if (item.size() >= 4) {
								String accountNo = item.get(1) == null ? null : item.get(1).toString();
								String accountTypeStr = item.get(2) == null ? null : item.get(2).toString();
								String platform = item.get(3) == null ? null : item.get(3).toString();
								if (!StringUtils.isEmpty(accountNo) && !StringUtils.isEmpty(accountTypeStr)
										&& !StringUtils.isEmpty(platform)) {
									ActWhiteListVO whiteVO = new ActWhiteListVO();
									int typeIndex = accountTypeStr.indexOf(":");
									String accountType = "";
									if (typeIndex > 0) {
										accountType = accountTypeStr.substring(0, typeIndex).trim();
									}
									whiteVO.setEnv(accountType);
									whiteVO.setActivityPeriods(actSetting.getActivityPeriods());
									whiteVO.setAccountNo(accountNo);
									whiteVO.setPlatform(platform);
									whiteVO.setCompanyId(actSetting.getCompanyId());
								
									if(reqVO.getCreateUser()!=null) {
										//活动列表批量上传,取当前登录用户，当前时间
										whiteVO.setCreateUser(reqVO.getCreateUser());
										whiteVO.setCreateDate(new Date());
										whiteVO.setUpdateUser(reqVO.getCreateUser());
										whiteVO.setApproveDate(null);//审批时间
										whiteVO.setApprover(null);//审批人
										whiteVO.setProposalStatus(null);//审批状态
									}else {
										//活动提案审批通过生成数据,取活动的创建人和创建时间
										whiteVO.setCreateUser(actSetting.getProposer());
										whiteVO.setCreateDate(actSetting.getProposalDate());
										whiteVO.setUpdateUser(reqVO.getApprover());
										whiteVO.setApproveDate(reqVO.getApproveDate());//审批时间
										whiteVO.setApprover(reqVO.getApprover());//审批人
										whiteVO.setProposalStatus(reqVO.getProposalStatus());//审批状态
									}
									
									whiteVO.setUpdateDate(new Date());
								
									
									saveOrUpdate(whiteVO);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("<---读取文件异常" + reqVO.getFilePath(), e);
			}
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actWhiteListDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}

	/**
	 * 获取需要对比的白名单或黑名单列表
	 * 
	 * @param fileName
	 *            --文件名
	 * @return
	 */
	private Set<String> listCompareSet(String filePath, String actNo) {
		Set<String> compareFileKeys = new HashSet<String>();
		if (!StringUtils.isEmpty(actNo)) {
			List<ActBlackList> list = actBlackListManager.findListByActivityPeriods(actNo);
			if (!list.isEmpty()) {
				for (ActBlackList obj : list) {
					String key = obj.getAccountNo() + "_" + obj.getEnv() + "_" + obj.getPlatform();
					compareFileKeys.add(key);
				}
				return compareFileKeys;
			}
		}
		if (!StringUtils.isEmpty(filePath)) {
			List<List<Object>> result = null;
			try {
				InputStream in = FileUtil.readInputStream(filePath);
				if (in != null) {
					result = ExcelUtil.readExcelByInputStream(in);
					if (result != null && !result.isEmpty()) {
						for (int i = 0; i < result.size(); i++) {
							List<Object> item = result.get(i);
							if (item.size() >= 4) {
								String accountNo = item.get(1) == null ? null : item.get(1).toString();
								String accountTypeStr = item.get(2) == null ? null : item.get(2).toString();
								String platform = item.get(3) == null ? null : item.get(3).toString();
								if (!StringUtils.isEmpty(accountNo) && !StringUtils.isEmpty(accountTypeStr)
										&& !StringUtils.isEmpty(platform)) {
									int typeIndex = accountTypeStr.indexOf(":");
									String accountType = "";
									if (typeIndex > 0) {
										accountType = accountTypeStr.substring(0, typeIndex).trim();
									}
									if (!StringUtils.isEmpty(accountType)) {
										String key = accountNo + "_" + accountType + "_" + platform;
										compareFileKeys.add(key);
									}
								}
							}
						}
						return compareFileKeys;
					}
				}
			} catch (Exception e) {
				logger.error("<---读取文件异常" + filePath, e);
				return null;
			}
		}
		return null;
	}
}