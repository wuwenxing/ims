package com.gwghk.ims.activity.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStatView;
import com.gwghk.ims.activity.dao.inf.ActAccountActiviStatViewDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatViewVO;

/**
 * 活动参与用户-多表关联视图
 * @author wayne
 * 
 */
@Component
@Transactional
public class ActAccountActiviStatViewManager {

	@Autowired
	private ActAccountActiviStatViewDao actAccountActivityDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActAccountActiviStatViewVO> findPageList(ActAccountActiviStatViewVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActAccountActiviStat.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActAccountActiviStatView>(this.findList(vo)), new PageR<ActAccountActiviStatViewVO>(), ActAccountActiviStatViewVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActAccountActiviStatView> findList(ActAccountActiviStatViewVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("actNo", vo.getActNo());
		map.put("actName", vo.getActName());
		map.put("tagCodes", vo.getTagCodes());
		if("true".equals(vo.getWhitelistFlag())){
			map.put("conditionVal", "\"allowWhiteUsers\":1,");
		}
		map.put("accountNo", vo.getAccountNo());
		map.put("custMobile", vo.getCustMobile());
		map.put("platform", vo.getPlatform());
		map.put("platforms", vo.getPlatforms());
		map.put("startTimeStr", vo.getStartTimeStr());
		map.put("endTimeStr", vo.getEndTimeStr());
		map.put("realSettlementFlag", vo.getRealSettlementFlag());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return actAccountActivityDao.findListByView(map);
	}

	/**
	 * 功能：根据查询条件
	 */
	public ActAccountActiviStatView findViewById(Long id) {
		return actAccountActivityDao.findViewById(id);
	}
	
}