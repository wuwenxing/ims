package com.gwghk.ims.activity.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActProduct;
import com.gwghk.ims.activity.dao.inf.ActProductDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActProductVO;

/**
 * 摘要：产品维护
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
@Component
@Transactional
public class ActProductManager {

	@Autowired
	private ActProductDao actProductDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActProductVO> findPageList(ActProductVO vo) {
		PageHelper.startPage(vo.getPage(), vo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ActProduct.class, vo.getSort(), vo.getOrder()));
		return PageCustomizedUtil.copyPageList(new PageR<ActProduct>(this.findList(vo)), new PageR<ActProductVO>(),ActProductVO.class);
	}

	/**
	 * 功能：根据查询条件->查询列表
	 * @param vo
	 * @return
	 */
	public List<ActProduct> findList(ActProductVO vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("productCode", vo.getProductCode());
		map.put("productName", vo.getProductName());
		map.put("enableFlag", vo.getEnableFlag());
		map.put("companyId", vo.getCompanyId());
		map.put("sort", vo.getSort());
		map.put("order", vo.getOrder());
		return actProductDao.findListByMap(map);
	}

	/**
	 * 功能：根据id->信息
	 */
	public ActProduct findById(Long id) {
		return actProductDao.findObject(id);
	}

	/**
	 * 功能：根据ProductCode->获取信息
	 */
	public ActProduct findByProductCode(String productCode, Long companyId) {
		return actProductDao.findByProductCode(productCode, companyId);
	}

	/**
	 * 功能：新增或修改时保存信息
	 */
	public void saveOrUpdate(ActProductVO vo) throws Exception {
		if (null == vo.getId()) {
			actProductDao.save(ImsBeanUtil.copyNotNull(new ActProduct(), vo));
		} else {
			ActProduct oldActProduct = findById(vo.getId());
			ImsBeanUtil.copyNotNull(oldActProduct,vo);
			actProductDao.update(oldActProduct);
		}
	}

	/**
	 * 功能：批量删除信息
	 */
	public void deleteByIdArray(String idArray) {
		actProductDao.deleteBatch(Arrays.asList(idArray.split(",")));
	}
	
}