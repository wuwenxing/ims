package com.gwghk.sys.api.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.system.SystemDictVO;
import com.gwghk.sys.api.dao.entity.SystemDict;
import com.gwghk.sys.api.dao.inf.SystemDictDao;

/**
 * 摘要：系统-数据字典业务逻辑处理
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class SystemDictManager {

	@Autowired
	private SystemDictDao systemDictDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<SystemDictVO> findPageList(SystemDictVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(SystemDict.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<SystemDict>(this.findList(vo)), new PageR<SystemDictVO>(),SystemDictVO.class);
	}

	/**
	 * 功能：根据查询条件->查询数据字典列表
	 */
	public List<SystemDict> findList(SystemDictVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("dictType", vo.getDictType());
		map.put("dictCode", vo.getDictCode());
		map.put("parentDictCode", vo.getParentDictCode());
		map.put("dictNameCn", vo.getDictNameCn());
		map.put("dictNameEn", vo.getDictNameEn());
		map.put("dictNameTw", vo.getDictNameTw());
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return systemDictDao.findListByMap(map);
	}

	/**
	 * 功能：根据查询条件->查询数据字典列表
	 */
	@Cacheable(value="dictCache", key="'dictCodeAndCompanyId:' + #dictCode + '_' + #companyId")
	public SystemDict findByDictCode(String dictCode, Long companyId) {
		return systemDictDao.findByDictCode(dictCode, companyId);
	}
	
	/**
	 * 功能：根据查询条件->查询数据字典列表
	 */
	@Cacheable(value="dictCache", key="'parentDictCodeAndCompanyId:' + #parentDictCode + '_' + #companyId")
	public List<SystemDict> findListByParentDictCode(String parentDictCode, Long companyId) {
		return systemDictDao.findListByParentDictCode(parentDictCode, companyId);
	}
	
	/**
	 * 功能：根据数据字典id->数据字典信息
	 */
	public SystemDict findById(Long dictId) {
		return systemDictDao.findObject(dictId);
	}

	/**
	 * 功能：新增或修改时保存数据字典信息
	 */
	public void saveOrUpdate(SystemDictVO vo) throws Exception {
		if (null == vo.getDictId()) {
			systemDictDao.save(ImsBeanUtil.copyNotNull(new SystemDict(), vo));
		} else {
			SystemDict oldSystemDict = findById(vo.getDictId());
			// 如果有子节点,则更新子节点的父code及companyId
			if(null == oldSystemDict.getCompanyId()){
				
			}
			if(!oldSystemDict.getDictCode().equals(vo.getDictCode())
					|| (null == oldSystemDict.getCompanyId() && null != vo.getCompanyId())
					|| (null != oldSystemDict.getCompanyId() && !oldSystemDict.getCompanyId().equals(vo.getCompanyId()))){
				List<SystemDict> list = systemDictDao.findListByParentDictCode(oldSystemDict.getDictCode(), oldSystemDict.getCompanyId());
				if(null != list && list.size() > 0){
					list.stream().forEach(r -> {
						r.setParentDictCode(vo.getDictCode());
						r.setUpdateDate(vo.getUpdateDate());
						r.setUpdateIp(vo.getUpdateIp());
						r.setUpdateUser(vo.getUpdateUser());
						r.setCompanyId(vo.getCompanyId());
						systemDictDao.update(r);
					});
				}
			}
			ImsBeanUtil.copyNotNull(oldSystemDict,vo);
			// 注意companyId为null时，代表共用
			oldSystemDict.setCompanyId(vo.getCompanyId());
			systemDictDao.update(oldSystemDict);
		}
	}

	/**
	 * 功能：批量删除数据字典信息
	 */
	public void deleteByIdArray(String idArray) {
		systemDictDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
}