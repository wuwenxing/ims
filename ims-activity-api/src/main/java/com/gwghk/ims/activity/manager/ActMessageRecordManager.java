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
import com.gwghk.ims.activity.dao.entity.ActMessageRecord;
import com.gwghk.ims.activity.dao.inf.ActMessageRecordDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActMessageRecordDTO;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;

/**
 * 摘要：活动消息记录-业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActMessageRecordManager {

	@Autowired
	private ActMessageRecordDao actMessageRecordDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActMessageRecordDTO> findPageList(ActMessageRecordDTO dto) {
		PageHelper.startPage(dto.getPage(), dto.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActMessageRecord.class, dto.getSort(), dto.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActMessageRecord>(this.findList(dto)),
				new PageR<ActMessageRecordDTO>(), ActMessageRecordDTO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActMessageRecord> findList(ActMessageRecordDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("msgType", dto.getMsgType());
		map.put("msgCode", dto.getMsgCode());
		map.put("msgStatus", dto.getMsgStatus());
		map.put("companyId", dto.getCompanyId());
		return actMessageRecordDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActMessageRecord findById(Long id) {
		return actMessageRecordDao.findObject(id);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActMessageRecordDTO dto) throws Exception {
		if (null == dto.getId()) {
			dto.setCreateDate(new Date());
			dto.setUpdateDate(new Date());
			dto.setEnableFlag("Y");
			dto.setDeleteFlag("N");
			actMessageRecordDao.save(ImsBeanUtil.copyNotNull(new ActMessageRecord(), dto));
		} else {
			ActMessageRecord old = findById(dto.getId());
			ImsBeanUtil.copyNotNull(old, dto);
			old.setUpdateDate(new Date());
			actMessageRecordDao.update(old);
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actMessageRecordDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}