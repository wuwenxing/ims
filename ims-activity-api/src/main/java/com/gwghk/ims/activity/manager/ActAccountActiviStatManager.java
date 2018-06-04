package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActAccountActiviStat;
import com.gwghk.ims.activity.dao.inf.ActAccountActiviStatDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatVO;
import com.gwghk.ims.common.vo.activity.ActAccountActiviStatViewVO;

/**
 * 活动参与用户-关联状态
 * @author wayne
 * 
 */
@Component
@Transactional
public class ActAccountActiviStatManager {

	@Autowired
	private ActAccountActiviStatDao actAccountActiviStatDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActAccountActiviStatVO> findPageList(ActAccountActiviStatVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActAccountActiviStat.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActAccountActiviStat>(this.findList(vo)),new PageR<ActAccountActiviStatVO>(), ActAccountActiviStatVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */	
	@SuppressWarnings("unchecked")
	public List<ActAccountActiviStat> findList(ActAccountActiviStatVO vo) {
		Map<String, Object> map = ImsBeanUtil.toMap(vo);
		return actAccountActiviStatDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->获取信息
	 */
	public ActAccountActiviStat findById(Long id) {
		return actAccountActiviStatDao.findObject(id);
	}

	/**
	 * 功能：根据accountNo->获取信息
	 */

	public ActAccountActiviStat findByAccountNo(String accountNo,String actNo,String platform) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountNo",accountNo);
		map.put("actNo",actNo);
		map.put("platform",platform);
		return actAccountActiviStatDao.findObjectByMap(map) ;
	}
	
	public List<ActAccountActiviStat> findByAccountNo(String accountNo, Long companyId) {
		return actAccountActiviStatDao.findByAccountNo(accountNo, companyId);
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActAccountActiviStat vo) {
		//判断是否存在
		ActAccountActiviStat obj=actAccountActiviStatDao.findByBean(vo);
		
		if (null == obj) {
			actAccountActiviStatDao.save(vo);
		} else {
			ActAccountActiviStat accActStat = ImsBeanUtil.copyNotNull(new ActAccountActiviStat(), vo);
			accActStat.beforeUpdate();
			actAccountActiviStatDao.update(accActStat);
		}
		return MisRespResult.success() ;
	}
	
	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ActAccountActiviStatViewVO vo) throws Exception {
		if (null == vo.getId()) {
			actAccountActiviStatDao.save(ImsBeanUtil.copyNotNull(new ActAccountActiviStat(), vo));
		} else {
			ActAccountActiviStat old = findById(vo.getId());
			ImsBeanUtil.copyNotNull(old, vo);
			actAccountActiviStatDao.update(old);
		}
		return MisRespResult.success() ;
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actAccountActiviStatDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}

	public ActAccountActiviStat findByBean(ActAccountActiviStat entity) {
		return actAccountActiviStatDao.findByBean(entity);
	}

	public boolean update(ActAccountActiviStat entity) {
		return actAccountActiviStatDao.update(entity)>0;
	}
	
}