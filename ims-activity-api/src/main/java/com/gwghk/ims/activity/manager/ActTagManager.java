package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActTag;
import com.gwghk.ims.activity.dao.inf.ActTagDao;
import com.gwghk.ims.common.common.Kuak;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.base.ActTagVO;

/**
 * 摘要：用户标签业务逻辑处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月29日
 */
@Component
@Transactional
public class ActTagManager {

	@Autowired
	private ActTagDao actTagDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActTagVO> findPageList(ActTagVO userTagVO,Long companyId) {
		PageHelper.startPage(userTagVO.getPage(), userTagVO.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActTag.class, userTagVO.getSort(), userTagVO.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActTag>(this.findList(userTagVO,companyId)), new PageR<ActTagVO>(),ActTagVO.class);
	}

	 
	/**
	 * 功能：根据查询条件->查询标签列表
	 */
	public List<ActTag> findList(ActTagVO userTagVO,Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("tagCode", userTagVO.getTagCode());
		map.put("tagName", userTagVO.getTagName());
		map.put("enableFlag", userTagVO.getEnableFlag());
		map.put("companyId", companyId);
		return actTagDao.findListByMap(map);
	}
	
	/**
	 * 功能：获得所有可以下拉的标签
	 */
	public List<Kuak> findTagOptions(ActTagVO userTagVO,Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("enableFlag", userTagVO.getEnableFlag());
		map.put("companyId", companyId);
		List<ActTag> tagList = actTagDao.findListByMap(map);
		List<Kuak> resultList = null;
		if(CollectionUtils.isNotEmpty(tagList)){
			resultList = new ArrayList<Kuak>();
			for(ActTag userTag :tagList){
				resultList.add(new Kuak(userTag.getTagCode(),userTag.getTagName()));
			}
		}
		return resultList;
	}

	 
	/**
	 * 功能：根据标签id->查询标签信息
	 */
	public ActTag findById(Long id,Long companyId) {
		return actTagDao.findObject(id);
	}

	/**
	 * 功能：根据code->查询标签信息
	 */
	public ActTag findByCode(String code,Long companyId) {
		return actTagDao.findByCode(code,companyId);
	}
	
	/**
	 * 功能：新增或修改时保存标签信息
	 */
	public void save(ActTagVO userTagVO,Long companyId) {
		if (null == userTagVO.getId()) {
			ActTag  userTag = new ActTag();
			ImsBeanUtil.copyNotNull(userTag,userTagVO);
			userTag.beforeSave();
			actTagDao.save(userTag);
		} else {
			ActTag  useTag = findById(userTagVO.getId(),companyId);
			ImsBeanUtil.copyNotNull(useTag,userTagVO);
			useTag.setUpdateDate(new Date());
			actTagDao.update(useTag);
		}
	}

	/**
	 * 功能：批量删除标签信息
	 */
	public void deleteByIds(String ids,Long companyId) {
		actTagDao.deleteBatch(Arrays.asList(ids.split(",")));
	}

	/**
	 * 功能：检查code是否存在
	 */
	public boolean checkCode(ActTagVO userTagVO,Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("tagCode", userTagVO.getTagCode());
		map.put("id", userTagVO.getId());
		if (!userTagVO.isSuperAdmin()) {
			map.put("companyId",companyId);
		}
		return actTagDao.checkCode(map) > 0;
	}
}