package com.gwghk.ims.activity.manager;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActBlackList;
import com.gwghk.ims.activity.dao.entity.ActBlackListWrapper;
import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActWhiteList;
import com.gwghk.ims.activity.dao.inf.ActBlackListDao;
import com.gwghk.ims.activity.manager.settle.ActSettlementManager;
import com.gwghk.ims.common.annotation.Company;
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
 * 摘要：活动黑名单-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActBlackListManager {

	Logger logger = LoggerFactory.getLogger(ActBlackListManager.class);

	@Autowired
	private ActBlackListDao actBlackListDao;
	@Autowired
	private ActWhiteListManager actWhiteListManager;
	@Autowired
	private ActSettingManager actSettingManager;
	@Autowired
	private ActProposalModifyManager actProposalModifyManager;
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;
	@Autowired
	private ActSettlementManager actSettlementManager;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActBlackListVO> findPageList(ActBlackListVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActBlackList.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActBlackListWrapper>(this.findList(vo)),
				new PageR<ActBlackListVO>(), ActBlackListVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActBlackListWrapper> findList(ActBlackListVO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", dto.getActivityPeriods());
		map.put("activityName", dto.getActivityName());
		map.put("accountNo", dto.getAccountNo());
		map.put("mobile", dto.getMobile());
		map.put("env", dto.getEnv());
		map.put("platform", dto.getPlatform());
		map.put("companyId", dto.getCompanyId());
		map.put("proposalStatus", dto.getProposalStatus());
		map.put("createDateStartTime", dto.getCreateDateStartTime());
		map.put("createDateEndTime", dto.getCreateDateEndTime());
		return actBlackListDao.findListBySearch(map);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActBlackList> findListByActivityPeriods(String activityPeriods) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", activityPeriods);
		return actBlackListDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActBlackList findById(Integer id) {
		return actBlackListDao.findObject(id);
	}

	/***
	* 
	* 摘要:批量审批   
	* @author George.li  
	* @date 2018年5月30日  
	* @version 1.0   
	* @param idArray
	* @param actBlackListVO
	* @return
	 */
	public MisRespResult<Void> proposal(String idArray,ActBlackListVO reqVO) {
		try {
			List<String> ids=Arrays.asList(idArray.split(","));
			 
			for(String id:ids) {
				ActBlackList  black=findById(Integer.parseInt(id));
				//待审批的状态
				if(!black.getProposalStatus().equals(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode())) {
					return MisRespResult.error("待审批状态黑名单才可审批");
				}
				ActBlackListVO vo=ImsBeanUtil.copyNotNull(new ActBlackListVO(), black);
				vo.setProposalStatus(ActProposalStatusEnum.ACTHASAPPROVED.getCode());
				vo.setApprover(reqVO.getUpdateUser());
				vo.setApproveDate(reqVO.getUpdateDate());
				vo.setUpdateDate(reqVO.getUpdateDate());
				vo.setUpdateUser(reqVO.getUpdateUser());
				saveOrUpdate(vo);
				actSettlementManager.beginUserSettlement(ActSettleTypeEnum.BLACKLIST, black.getActivityPeriods(), black.getAccountNo(), black.getPlatform(), reqVO);
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
	* @param actBlackListVO
	* @return
	 */
	public MisRespResult<Void> cancel(String idArray,ActBlackListVO reqVO) {
		try {
			List<String> ids=Arrays.asList(idArray.split(","));
			 
			for(String id:ids) {
				ActBlackList  black=findById(Integer.parseInt(id));
				//待审批的状态
				if(!black.getProposalStatus().equals(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode())) {
					return MisRespResult.error("待审批状态黑名单才可取消");
				}
				ActBlackListVO vo=ImsBeanUtil.copyNotNull(new ActBlackListVO(), black);
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
	 * 批量保存黑名单
	 * 
	 * @param actBlackListVO
	 * @return
	 */
	public MisRespResult<Void> batchSave(ActBlackListVO actBlackListVO) {
		String activityPeriods = actBlackListVO.getActivityPeriods();
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
			actCondSetting.setBlackListFileName(actBlackListVO.getFileName());
			actCondSetting.setBlackListFilePath(actBlackListVO.getFilePath());
			actCondSetting.setActivityPeriods(activityPeriods);
			actDetailVo.setActCondSetting(ImsBeanUtil.copyNotNull(new ActConditionSettingVO(), actCondSetting));
			actProposalModifyManager.saveActProposalModifyWithHasApprovedAct(actDetailVo, actSetting,
					actBlackListVO.getCreateUser());*/
			actBlackListVO.getFilePath();
			actBlackListVO.setApprover(null);
			actBlackListVO.setApproveDate(null);
			actBlackListVO.setProposalStatus(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode());//默认批量上传为待审批
			saveFileData(actBlackListVO, actSetting);
		 
		} catch (Exception ex) {
			logger.error("batchSave-->异常", ex);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
		return MisRespResult.success();
	}
	
	/**
	 * 
	 * 功能: 通过文件路径上传白名单
	 * 
	 */
	public void saveFileData(ActBlackListVO reqVO, ActSetting actSetting) {
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
									ActBlackListVO blackVO = new ActBlackListVO();
									int typeIndex = accountTypeStr.indexOf(":");
									String accountType = "";
									if (typeIndex > 0) {
										accountType = accountTypeStr.substring(0, typeIndex).trim();
									}
									blackVO.setEnv(accountType);
									blackVO.setActivityPeriods(actSetting.getActivityPeriods());
									blackVO.setAccountNo(accountNo);
									blackVO.setPlatform(platform);
									blackVO.setCompanyId(actSetting.getCompanyId());
								
									if(reqVO.getCreateUser()!=null) {
										//活动列表批量上传,取当前登录用户，当前时间
										blackVO.setCreateUser(reqVO.getCreateUser());
										blackVO.setCreateDate(new Date());
										blackVO.setUpdateUser(reqVO.getCreateUser());
										blackVO.setApproveDate(null);//审批时间
										blackVO.setApprover(null);//审批人
										blackVO.setProposalStatus(null);//审批状态
									}else {
										//活动提案审批通过生成数据,取活动的创建人和创建时间
										blackVO.setCreateUser(actSetting.getProposer());
										blackVO.setCreateDate(actSetting.getProposalDate());
										blackVO.setUpdateUser(reqVO.getApprover());
										blackVO.setApproveDate(reqVO.getApproveDate());//审批时间
										blackVO.setApprover(reqVO.getApprover());//审批人
										blackVO.setProposalStatus(reqVO.getProposalStatus());//审批状态
									}
									
									blackVO.setUpdateDate(new Date());
								
									
									saveOrUpdate(blackVO);
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
				msg = String.format("上传失败:第%s行错误，白名单中已存在此记录，白名单和黑名单中不能同时存在相同的记录!", i + 2);
				return MisRespResult.error(msg);
			} else {
				fileKeys.add(key);
			}
		}
		return MisRespResult.success();
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActBlackListVO dto) throws Exception {
		if (null == dto.getId()) {
			dto.setCreateDate(new Date());
			dto.setUpdateDate(new Date());
			dto.setEnableFlag("Y");
			dto.setDeleteFlag("N");
			actBlackListDao.save(ImsBeanUtil.copyNotNull(new ActBlackList(), dto));
		} else {
			ActBlackList old = findById(dto.getId().intValue());
			ImsBeanUtil.copyNotNull(old, dto);
			old.setUpdateDate(new Date());
			actBlackListDao.update(old);
		}
	}

	/**
	 * 
	 * 功能: 通过文件路径上传黑名单
	 * 
	 */
	public void saveFileData(String filePath, ActSetting actSetting) {
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
									ActBlackListVO blackVO = new ActBlackListVO();

									int typeIndex = accountTypeStr.indexOf(":");
									String accountType = "";
									if (typeIndex > 0) {
										accountType = accountTypeStr.substring(0, typeIndex).trim();
									}
									blackVO.setEnv(accountType);

									blackVO.setAccountNo(accountNo);
									blackVO.setPlatform(platform);
									blackVO.setActivityPeriods(actSetting.getActivityPeriods());
									blackVO.setCompanyId(actSetting.getCompanyId());
									blackVO.setCreateDate(actSetting.getProposalDate());
									blackVO.setCreateUser(actSetting.getProposer());
									saveOrUpdate(blackVO);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error("<---读取文件异常" + filePath, e);
			}
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actBlackListDao.deleteBatch(Arrays.asList(idArray.split(",")));
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
			List<ActWhiteList> list = actWhiteListManager.findListByActivityPeriods(actNo);
			if (!list.isEmpty()) {
				for (ActWhiteList obj : list) {
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