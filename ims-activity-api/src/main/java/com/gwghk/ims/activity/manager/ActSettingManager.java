package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.cache.ActSettingLocalCache;
import com.gwghk.ims.activity.dao.entity.ActConditionSetting;
import com.gwghk.ims.activity.dao.entity.ActItemsSetting;
import com.gwghk.ims.activity.dao.entity.ActSetting;
import com.gwghk.ims.activity.dao.entity.ActSettingParamsValJson;
import com.gwghk.ims.activity.dao.inf.ActSettingDao;
import com.gwghk.ims.activity.dao.inf.CommonTableDao;
import com.gwghk.ims.common.common.BaseEntity;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActProposalStatusEnum;
import com.gwghk.ims.common.enums.SeqEnum;
import com.gwghk.ims.common.inf.SeqDubboService;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.activity.ActTaskItemsVO;
import com.gwghk.ims.common.vo.activity.ActTaskSettingVO;
import com.gwghk.ims.common.vo.activity.ActWhiteListVO;
import com.gwghk.unify.framework.common.util.JsonUtil;
import com.gwghk.unify.framework.common.util.StringUtil;

/**
 * 摘要：活动提案-业务逻辑处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月31日
 */
@Component
@Transactional
public class ActSettingManager {
	private static final Logger logger = LoggerFactory.getLogger(ActSettingManager.class);
	
	@Autowired
	private ActWhiteListManager actWhiteListManager;
	
	@Autowired
	private ActBlackListManager actBlackListManager;
	
	/**
	 * 功能：分页查询
	 */
	public PageR<ActSettingVO> findPageList(ActSettingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		String orderStr = PageCustomizedUtil.sort(ActItemsSetting.class, vo.getSort(), vo.getOrder());
		vo.setOrderStr(orderStr);
		return PageCustomizedUtil.copyPageList(new PageR<ActSetting>(this.findActList(vo)),new PageR<ActSettingVO>(), ActSettingVO.class);
	}
 
	/**
	 * 功能：根据查询条件->查询列表
	 */
	public  List<ActSettingVO> findList(ActSettingVO vo) {
		PageHelper.orderBy(PageCustomizedUtil.sort(ActItemsSetting.class, vo.getSort(), vo.getOrder()));
		List<ActSetting> list =  findActList(vo);
		List<ActSettingVO>  voList =  ImsBeanUtil.cloneList(list, ActSettingVO.class);
		return voList;
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	private List<ActSetting> findActList(ActSettingVO vo) {
		Map<String, Object> map = (Map<String, Object>)ImsBeanUtil.toMap(vo);
		return actSettingDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActSetting findById(Long id) {
		return actSettingDao.findObject(id);
	}

	/**
	 * 功能：根据活动编号->获取信息
	 */
	public ActSetting findByactivityPeriods(String activityPeriods){
		return actSettingLocalCache.getByActivityPeriods(activityPeriods);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActSettingDetailsVO vo)  {
		vo.getActBaseInfo().setCompanyId(vo.getCompanyId());
		if(vo.getActCondSetting()!=null){
			vo.getActCondSetting().setCompanyId(vo.getCompanyId());
		}
		ActSetting actSetting = findByactivityPeriods(vo.getActBaseInfo().getActivityPeriods());
		 // 已审批且未失效的修改
	    if (actSetting!=null && ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(actSetting.getProposalStatus())) {
	    	if(actSetting.getEndTime().before(new Date())){//已失效
	    		return new MisRespResult<Void>(MisResultCode.Act20014);
	    	}else{
	    		//生成活动修改提案
	    		return actProposalModifyManager.saveActProposalModifyWithHasApprovedAct(vo,actSetting,vo.getUpdateUser());
	    	}
	    }else{
	    	//直接新增或修改活动
	    	return saveOrUpdateActAllInfo(vo,actSetting);
	    }
	}
	
	/**
	 * 直接保存或修改活动的所有信息
	 * @return
	 */
	private MisRespResult<Void> saveOrUpdateActAllInfo(ActSettingDetailsVO vo,ActSetting actSetting){
		//step1:保存基本信息
		MisRespResult<Void> result = saveOrUpdateActBaseInfo(vo.getActBaseInfo(),actSetting);
		if(result.isOk()){
			//step2:保存参与条件
			if(vo.getActCondSetting()!=null && StringUtil.isEmpty(vo.getActCondSetting().getActivityPeriods())){
				vo.getActCondSetting().setActivityPeriods(vo.getActBaseInfo().getActivityPeriods());
			}
			actConditionSettingManager.saveOrUpdate(vo.getActCondSetting());
			//step3:新增或保存任务
			//step3.1 修改活动，先获取活动下任务及物品，如任务或物品还存在，则更新并移除此集合中
			Set<Long> removeTasks = null;//待移除的任务
			Set<Long> removeTaskItems = null;//待移除的物品
			if(vo.getActBaseInfo().getId()!=null){
				ActTaskSettingVO taskReqVo = new ActTaskSettingVO();
				taskReqVo.setActivityPeriods(vo.getActBaseInfo().getActivityPeriods());
				List<ActTaskSettingVO> oldActTaskSettings = actTaskSettingManager.findList(taskReqVo);
				if(oldActTaskSettings!=null){
					removeTasks = new HashSet<Long>();
					for(ActTaskSettingVO task:oldActTaskSettings){
						removeTasks.add(task.getId());
					}
					//获取活动下旧的物品
					ActTaskItemsVO actTaskItemsReqVo = new ActTaskItemsVO();
					actTaskItemsReqVo.setActivityPeriods(vo.getActBaseInfo().getActivityPeriods());
					List<ActTaskItemsVO> oldActTaskItems = actTaskItemsManager.findList(actTaskItemsReqVo);
					if(oldActTaskItems!=null){
						removeTaskItems = new HashSet<Long>();
						for(ActTaskItemsVO item :oldActTaskItems){
							removeTaskItems.add(item.getId());
						}
					}
				}
			}
			List<ActTaskSettingVO>  actTaskSettings = vo.getActTaskSettings();
			for(ActTaskSettingVO taskVo :actTaskSettings){
				if(StringUtil.isEmpty(taskVo.getActivityPeriods())){
					taskVo.setActivityPeriods(vo.getActBaseInfo().getActivityPeriods());
				}
				if(taskVo.getCompanyId()==null){
					taskVo.setCompanyId(vo.getCompanyId());
				}
				saveOrUpdateActTaskItems(taskVo,removeTasks,removeTaskItems);
			}
			//清除不存在的物品
			if(removeTaskItems!=null && !removeTaskItems.isEmpty()){
				for(Long id :removeTaskItems){
					actTaskItemsManager.deleteById(id);
				}
			}
			//清除不存在的任务
			if(removeTasks!=null && !removeTasks.isEmpty()){
				for(Long id :removeTasks){
					actTaskSettingManager.deleteById(id);
				}
			}
			//加载活动缓存
			actSettingLocalCache.reloadActCacheByActivityPeriods(vo.getActBaseInfo().getActivityPeriods());
			return MisRespResult.success();
		}
		return result;
	}
 
	
	private void saveOrUpdateActTaskItems(ActTaskSettingVO taskVo,Set<Long> removeTasks,Set<Long> removeTaskItems){
		//保存任务
		 Long taskSettingId = actTaskSettingManager.saveOrUpdate(taskVo);
		 List<ActTaskItemsVO> items = taskVo.getTaskItems();
		 if(items!=null && !items.isEmpty()){
			 //保存物品
			 for(ActTaskItemsVO item :items){
				 item.setTaskSettingId(taskSettingId);
				 if(item.getCompanyId()==null){
					 item.setCompanyId(taskVo.getCompanyId());
				 }
				 actTaskItemsManager.saveOrUpdate(item);
				 if(item.getId()!=null){//此物品还存在，则移除集合
					 removeTaskItems.remove(item.getId());
				 }
			 }
		 }
		 if(removeTasks!=null && taskVo.getId()!=null){
			 removeTasks.remove(taskVo.getId());//此任务还存在，则移除集合
		 }
		 if(taskVo.getSubTaskSettings()!=null && !taskVo.getSubTaskSettings().isEmpty()){
			 for(ActTaskSettingVO subVO : taskVo.getSubTaskSettings()){
				 subVO.setActivityPeriods(taskVo.getActivityPeriods());
				 subVO.setTaskItemCode(taskVo.getTaskItemCode());//与父任务的code一致
				 subVO.setParentId(taskSettingId);
				 saveOrUpdateActTaskItems(subVO,removeTasks,removeTaskItems);
			 }
		 }		 
	}
 
 
	/**
	 * 保存或更新活动基本信息
	 * @param actBaseInfo
	 * @return
	 */
	public MisRespResult<Void>  saveOrUpdateActBaseInfo(ActSettingVO actBaseInfo,ActSetting oldActSetting){
        if(oldActSetting!=null){
        	if(actBaseInfo.getId()==null || !actBaseInfo.getId().equals(oldActSetting.getId())){
        		return new MisRespResult<Void>(MisResultCode.Act20001);
        	}
		}else if(actBaseInfo.getId()!=null && oldActSetting==null){
			return new MisRespResult<Void>(MisResultCode.Act20000);
		}
        // 已经开始的活动暂时不支持延期
        if (oldActSetting!=null && new Date().compareTo(oldActSetting.getStartTime()) >= 0 && oldActSetting.getEndTime().compareTo(actBaseInfo.getEndTime()) < 0
                && ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(oldActSetting.getProposalStatus())) {
        	return new MisRespResult<Void>(MisResultCode.Act20009);
        } 
        if(actBaseInfo.getFinishDays()==null){
        	actBaseInfo.setFinishDays(0);
        }
        // 待审批时直接修改
        if (oldActSetting==null ||ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode().equals(oldActSetting.getProposalStatus())) {
        	//直接按新的覆盖
            ActSetting actSetting = new ActSetting();
    		ImsBeanUtil.copyNotNull(actSetting, actBaseInfo);
    		//把需要保存为json格式的参数，复制到Json中
            ActSettingParamsValJson actSettingJson = new ActSettingParamsValJson();
    		ImsBeanUtil.copyNotNull(actSettingJson, actBaseInfo);
    		if (null == actBaseInfo.getId()) {
    			actSetting.setParamsVal(JsonUtil.obj2Str(actSettingJson));
    			actSetting.beforeSave();
    			String pno  = seqDubboService.getSeq(SeqEnum.ActivityProposal.getSeqCode(),actBaseInfo.getCompanyId());
    	        if (StringUtil.isBlank(pno)) {
    	            logger.error("saveOrUpdate->新增活动提案从系列服务中获取序列号失败！");
    	            return MisRespResult.error(MisResultCode.Error10011);
    	        }
    	        actSetting.setPno(pno);
    			actSetting.setProposer(actSetting.getCreateUser());
    			actSetting.setProposalDate(actSetting.getCreateDate());
    			actSetting.setProposalStatus(ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode());
    			actSettingDao.save(actSetting);
    		} else {
    			//拷贝原始数据中的通用值
    			BaseEntity baseEntity = ImsBeanUtil.copyNotNull(new BaseEntity(), oldActSetting);
    			ImsBeanUtil.copyNotNull(actSetting, baseEntity);
    			actSetting.setEnableFlag(actBaseInfo.getEnableFlag());
    			actSetting.setRemark(actBaseInfo.getRemark());
    			actSetting.setPno(oldActSetting.getPno());
    			actSetting.setProposalDate(oldActSetting.getProposalDate());
    			actSetting.setProposalStatus(oldActSetting.getProposalStatus());
    			actSetting.setProposer(oldActSetting.getProposer());
    			actSetting.setParamsVal(JsonUtil.obj2Str(actSettingJson));
    			actSetting.beforeUpdate();
    			actSettingDao.update(actSetting);
    		}
    		return MisRespResult.success();
        }else {
        	return new MisRespResult<Void>(MisResultCode.Act20012);
        }
	}
 
 
    
    /**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actSettingDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
	/**
     * 根据id审批通过
     */
    public MisRespResult<Void> updateApproveById(Long id,String approver,String updateIp) {
        ActSetting oldEntity = findById(id);
        if (oldEntity != null) {
            if (!ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode().equals(oldEntity.getProposalStatus())) {
                // 已经审批，不能再审批
                if(ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(oldEntity.getProposalStatus())
                		||ActProposalStatusEnum.ACtHASCANCELED.getCode().equals(oldEntity.getProposalStatus())){
                    return new MisRespResult<Void>(MisResultCode.Act20011);
                }
                return new MisRespResult<Void>(MisResultCode.Act20012);
            }
            Date curDate = new Date();
            if (oldEntity.getEndTime().compareTo(curDate)<=0) {//活动结束时间小于当前时间,没有人能参加活动，不能审批通过
                return new MisRespResult<Void>(MisResultCode.Act20013);
            }
            // 创建账户参与活动资格表和发送记录表等相关表
         
            Map<String,Object> paramsMap =  new HashMap<String,Object>();
            paramsMap.put("id", id);
			paramsMap.put("approver", approver);
			paramsMap.put("approveDate", curDate);
			paramsMap.put("updateIp", updateIp);
			paramsMap.put("updateDate", curDate);
			paramsMap.put("UpdateUser", approver);
			paramsMap.put("proposalStatus", ActProposalStatusEnum.ACTHASAPPROVED.getCode());
            actSettingDao.updateByMap(paramsMap);
            if("N".equals(oldEntity.getEnableFlag())){//活动是禁用的，则需要创建活动升级维护对象
            	actMaintenanceSettingManager.createActMaintainByActEnableFlag(oldEntity.getActivityPeriods(),null,oldEntity.getEnableFlag(),oldEntity.getCompanyId());
            }
            //处理黑白名单
       
			ActConditionSettingVO actCond = actConditionSettingManager.findCustCondtionSettingVO(oldEntity.getActivityPeriods());
			if(actCond!=null) {
				String blackListFilePath=actCond.getBlackListFilePath();
				String whiteListFilePath=actCond.getWhiteListFilePath();
				//处理黑名单
				if(StringUtils.isNotBlank(blackListFilePath)) {
					ActBlackListVO actBlackListVO=new ActBlackListVO();
					actBlackListVO.setFilePath(whiteListFilePath);
					actBlackListVO.setApprover(approver);
					actBlackListVO.setProposalStatus(ActProposalStatusEnum.ACTHASAPPROVED.getCode()); 
					actBlackListManager.saveFileData(blackListFilePath, oldEntity);
				}
				 
				//处理白名单
				if(StringUtils.isNotBlank(whiteListFilePath)) {
					ActWhiteListVO actWhiteListVO=new ActWhiteListVO();
					actWhiteListVO.setFilePath(whiteListFilePath);
					actWhiteListVO.setApprover(approver);
					actWhiteListVO.setProposalStatus(ActProposalStatusEnum.ACTHASAPPROVED.getCode());
					actWhiteListManager.saveFileData(actWhiteListVO,oldEntity);
				}
		
				
			}
            
            commonTableDao.dropActImsPrizeRecordTableIfEXISTS(oldEntity.getActivityPeriods());
			commonTableDao.createActImsPrizeRecordTableTable(oldEntity.getActivityPeriods());
			
			commonTableDao.dropActImsPrizeRecordExtTableIfEXISTS(oldEntity.getActivityPeriods());
			commonTableDao.createActImsPrizeRecordExtTable(oldEntity.getActivityPeriods()); 
			    
			commonTableDao.dropActImsTaskRecordTableIfEXISTS(oldEntity.getActivityPeriods());
			commonTableDao.createActImsTaskRecordTable(oldEntity.getActivityPeriods()); 
			
			actSettingLocalCache.reloadActCacheByActivityPeriods(oldEntity.getActivityPeriods());
            return MisRespResult.success();
        }
        return new MisRespResult<Void>(MisResultCode.FAIL);
    }
    
    /**
     * 根据id取消
     */
    public MisRespResult<Void> updateCancelById(Long id,String canceler,String updateIp) {
        ActSetting oldEntity = findById(id);
        if (oldEntity != null) {
            if (!ActProposalStatusEnum.ACTWAITFORAPPROVE.getCode().equals(oldEntity.getProposalStatus())) {
            	// 已经审批，不能再审批
                if(ActProposalStatusEnum.ACTHASAPPROVED.getCode().equals(oldEntity.getProposalStatus())
                		||ActProposalStatusEnum.ACtHASCANCELED.getCode().equals(oldEntity.getProposalStatus())){
                    return new MisRespResult<Void>(MisResultCode.Act20011);
                }
                return new MisRespResult<Void>(MisResultCode.Act20012);
            }
            Date curDate = new Date();
            ActSetting entity =new ActSetting();
            entity.setUpdateUser(canceler);
            entity.setUpdateIp(updateIp);
            entity.setCanceller(canceler);
            entity.setCancelDate(curDate);
            entity.setApproveDate(curDate);
            entity.setUpdateDate(curDate);
            entity.setId(id);
            entity.setProposalStatus(ActProposalStatusEnum.ACtHASCANCELED.getCode());
            @SuppressWarnings("unchecked")
			Map<String,Object> paramsMap = ImsBeanUtil.toMap(entity);
            actSettingDao.updateByMap(paramsMap);
            //加载活动缓存
			actSettingLocalCache.reloadActCacheByActivityPeriods(oldEntity.getActivityPeriods());
			return MisRespResult.success();
        }
        return new MisRespResult<Void>(MisResultCode.FAIL);
    }
	
	@Autowired
	private ActSettingDao actSettingDao;
	@Autowired
	private SeqDubboService seqDubboService;
 
	@Autowired
	private ActTaskSettingManager actTaskSettingManager;
	
	@Autowired
	private ActMaintenanceSettingManager actMaintenanceSettingManager;
	
	@Autowired
	private ActConditionSettingManager actConditionSettingManager;
	
	@Autowired
	private ActTaskItemsManager actTaskItemsManager;
	
	@Autowired
	private ActSettingLocalCache actSettingLocalCache;
	
	@Autowired
	private ActProposalModifyManager actProposalModifyManager;
	@Autowired
	private CommonTableDao commonTableDao;

	public ActConditionSetting getActCondCustomInfoByActivityPeriods(String actNo) {
		return actSettingLocalCache.getActCondCustomInfoByActivityPeriods(actNo);
	}
}