package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActCouponInfo;
import com.gwghk.ims.activity.dao.inf.ActCouponInfoDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActCouponInfoDTO;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;

/**
 * 摘要：优惠活动设置-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActCouponInfoManager {

	@Autowired
	private ActCouponInfoDao actCouponInfoDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActCouponInfoDTO> findPageList(ActCouponInfoDTO dto) {
		PageHelper.startPage(dto.getPage(), dto.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActCouponInfo.class, dto.getSort(), dto.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActCouponInfo>(this.findList(dto)),
				new PageR<ActCouponInfoDTO>(), ActCouponInfoDTO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActCouponInfo> findList(ActCouponInfoDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityCode", dto.getActivityCode());
		map.put("triggerType", dto.getTriggerType());
		map.put("startTime", dto.getStartTime());
		map.put("endTime", dto.getEndTime());
		map.put("companyId", dto.getCompanyId());
		return actCouponInfoDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActCouponInfo findById(Long id) {
		return actCouponInfoDao.findObject(id);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActCouponInfoDTO dto) throws Exception {
		if (null == dto.getId()) {
			dto.setCreateDate(new Date());
			dto.setUpdateDate(new Date());
			dto.setEnableFlag("Y");
			dto.setDeleteFlag("N");
			actCouponInfoDao.save(ImsBeanUtil.copyNotNull(new ActCouponInfo(), dto));
		} else {
			ActCouponInfo old = findById(dto.getId());
			ImsBeanUtil.copyNotNull(old, dto);
			old.setUpdateDate(new Date());
			actCouponInfoDao.update(old);
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actCouponInfoDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}