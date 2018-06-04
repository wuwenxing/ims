package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActProposalModifyDetail;
import com.gwghk.ims.activity.dao.inf.ActProposalModifyDetailDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActProposalModifyDetailVO;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.common.util.bean.FieldChange;

/**
 * 摘要：活动修改提案-业务逻辑处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月13日
 */
@Component
@Transactional
public class ActProposalModifyDetailManager {

	@Autowired
	private ActProposalModifyDetailDao actProposalModifyDetailDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActProposalModifyDetailVO> findPageList(ActProposalModifyDetailVO reqVO) {
		PageHelper.startPage(reqVO.getPage(), reqVO.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActProposalModifyDetail.class, reqVO.getSort(), reqVO.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActProposalModifyDetail>(this.findList(reqVO)),
				new PageR<ActProposalModifyDetailVO>(), ActProposalModifyDetailVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActProposalModifyDetail> findList(ActProposalModifyDetailVO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("pno", dto.getPno());
		map.put("modifyType", dto.getModifyType());
		map.put("fieldName", dto.getFieldName());
		map.put("fieldType", dto.getFieldType());
		map.put("fromValue", dto.getFromValue());
		map.put("toValue", dto.getToValue());
		map.put("companyId", dto.getCompanyId());
		return actProposalModifyDetailDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActProposalModifyDetail findById(Long id) {
		return actProposalModifyDetailDao.findObject(id);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActProposalModifyDetailVO reqVO) throws Exception {
		if (null == reqVO.getId()) {
			reqVO.setCreateDate(new Date());
			reqVO.setUpdateDate(new Date());
			reqVO.setEnableFlag("Y");
			reqVO.setDeleteFlag("N");
			actProposalModifyDetailDao.save(ImsBeanUtil.copyNotNull(new ActProposalModifyDetail(), reqVO));
		} else {
			ActProposalModifyDetail old = findById(reqVO.getId());
			ImsBeanUtil.copyNotNull(old, reqVO);
			old.setUpdateDate(new Date());
			actProposalModifyDetailDao.update(old);
		}
	}

	
	 /**
	 * 保存修改详情
	 */
    public void initModifyDetailsFromChange(String pno, Map<String, Map<String, FieldChange>> changes,String modifyType, Integer fromVerionNo,Long companyId) {
        if (changes != null && !changes.isEmpty()) {
            for (String refId : changes.keySet()) {
                String[] refs = refId.split("/");
                //recordId :修改活动基本信息，则recordId为活动的id;
                //修改活动参与信息，则recordId为活动的参与条件id,recordParentId为活动id;
                //修改活动任务，recordId为活动任务id(负数为新增),recordParentId为活动id;
                //修改活动奖励物品，recordId为活动奖励id(负数为新增),recordParentId=活动任务id/活动id;
                Long recordId = refs.length > 0 && StringUtil.isNotEmpty(refs[0])&&!refs[0].equals("null") ? Long.parseLong(refs[0]) : null;
                String recordParentId = null;
                if(refs.length > 1){
                	recordParentId=refId.substring(refId.indexOf("/")+1);
                }
                Map<String, FieldChange> modifyChanges = changes.get(refId);
                for (FieldChange change : modifyChanges.values()) {
                    ActProposalModifyDetail detail = new ActProposalModifyDetail();
                    detail.setPno(pno);
                    detail.setRecordId(recordId);
                    detail.setRecordParentId(recordParentId);
                    detail.setFromVerionNo(fromVerionNo != null ? fromVerionNo : 0);
                    detail.setToVerionNo(0);
                    detail.setVersionNo(0);
                    detail.setFieldName(change.getName());
                    detail.setModifyType(modifyType);
                    if (StringUtils.isNotBlank(change.getFrom().getTypeName())
                        && !"NULL".equals(change.getFrom().getTypeName())) {
                        detail.setFieldType(change.getFrom().getTypeName());
                    } else {
                        detail.setFieldType(change.getTo().getTypeName());
                    }
                    detail.setFromValue(change.getFrom().stringValue());
                    detail.setToValue(change.getTo().stringValue());
                    detail.setCompanyId(companyId);
                    detail.beforeSave();
                    actProposalModifyDetailDao.save(detail);
                }
            }
        }

    }
    
	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actProposalModifyDetailDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}