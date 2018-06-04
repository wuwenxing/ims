package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecord;
import com.gwghk.ims.activity.dao.entity.ImsPrizeRecordWaiting;
import com.gwghk.ims.activity.dao.inf.ImsPrizeRecordWaitingDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordWaitingVO;

/**
 * 
 * 摘要：物品发放记录，应发记录逻辑
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月19日
 */
@Component
@Transactional
public class ImsPrizeRecordWaitingManager {
	Logger logger = LoggerFactory.getLogger(ImsPrizeRecordWaitingManager.class) ;
	@Autowired
	private ImsPrizeRecordWaitingDao imsPrizeRecordWaitingDao;
	@Autowired
	private ImsPrizeRecordManager imsPrizeRecordManager ;

	/**
	 * 功能：分页查询
	 */
	public PageR<ImsPrizeRecordWaitingVO> findPageList(ImsPrizeRecordWaitingVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ImsPrizeRecordWaiting.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ImsPrizeRecordWaiting>(this.findList(vo)),
				new PageR<ImsPrizeRecordWaitingVO>(), ImsPrizeRecordWaitingVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 */
	@SuppressWarnings("unchecked")
	public List<ImsPrizeRecordWaiting> findList(ImsPrizeRecordWaitingVO vo) {
		Map<String, Object> map = ImsBeanUtil.toMap(vo);
		return imsPrizeRecordWaitingDao.findListByMap(map) ;
	}
	
	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ImsPrizeRecordWaiting> findListByActivityPeriods(String activityPeriods) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityPeriods", activityPeriods);
		return imsPrizeRecordWaitingDao.findListByMap(map) ;
	}
	

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsPrizeRecordWaiting findById(Integer id) {
		return imsPrizeRecordWaitingDao.findObject(id);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ImsPrizeRecordWaitingVO vo) {
		if (null == vo.getId()) {
			imsPrizeRecordWaitingDao.save(ImsBeanUtil.copyNotNull(new ImsPrizeRecordWaiting(), vo));
		} else {
			//如果发放记录应该，改为可发，则新增一条发放记录，删除本记录
			ImsPrizeRecordWaiting old = findById(vo.getId());
			ImsBeanUtil.copyNotNull(old, vo);
			if(vo.getWaitingStatus() != null && vo.getWaitingStatus() == 0){
				ImsPrizeRecord ims = ImsBeanUtil.copyNotNull(new ImsPrizeRecord(), old) ;
				ims.setId(null);
				imsPrizeRecordManager.saveOrUpdate(ims);
				imsPrizeRecordWaitingDao.delete(vo.getId()) ;
			}else{
				imsPrizeRecordWaitingDao.update(old);
			}
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		imsPrizeRecordWaitingDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
}