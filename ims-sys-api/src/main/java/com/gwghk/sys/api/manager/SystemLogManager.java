package com.gwghk.sys.api.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.system.SystemLogVO;
import com.gwghk.sys.api.dao.entity.SystemLog;
import com.gwghk.sys.api.dao.inf.SystemLogDao;

/**
 * 摘要：系统-日志逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemLogManager {

	@Autowired
	private SystemLogDao systemLogDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<SystemLogVO> findPageList(SystemLogVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(SystemLog.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<SystemLog>(this.findList(vo)), new PageR<SystemLogVO>(),SystemLogVO.class);
	}

	/**
	 * 功能：根据查询条件->查询日志列表
	 */
	public List<SystemLog> findList(SystemLogVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("userNo", vo.getUserNo());
		map.put("createIp", vo.getCreateIp());
		map.put("method", vo.getMethod());
		map.put("params", vo.getParams());
		map.put("logType", vo.getLogType());
		map.put("startTime", ImsDateUtil.parseDateSecondFormat(vo.getStartTime()));
		map.put("endTime", ImsDateUtil.parseDateSecondFormat(vo.getEndTime()));
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return systemLogDao.findListByMap(map);
	}

	/**
	 * 功能：新增日志信息
	 */
	public void save(SystemLogVO vo) {
		systemLogDao.save(ImsBeanUtil.copyNotNull(new SystemLog(),vo));
	}

	/**
	 * 功能：根据日志id->日志信息
	 */
	public SystemLog findById(Long id) {
		return systemLogDao.findObject(id);
	}
}