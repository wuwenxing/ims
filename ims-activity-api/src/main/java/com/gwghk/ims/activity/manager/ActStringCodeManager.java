package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ActStringCode;
import com.gwghk.ims.activity.dao.entity.ActStringCodeWrapper;
import com.gwghk.ims.activity.dao.inf.ActStringCodeDao;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActStringCodeStatusEnum;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.activity.ActStringCodeVO;
import com.gwghk.unify.framework.common.util.DESEncryptUtil;

/**
 * 摘要：串码管理业务处理
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */ 
@Service
public class ActStringCodeManager {
	@Autowired
	private ActStringCodeDao actStringCodeDao;

	/**
	 * 功能：分页查询
	 */
	public PageR<ActStringCodeVO> findPageList(ActStringCodeVO reqVO) {
		PageHelper.startPage(reqVO.getPage(), reqVO.getRows(), true);
		return PageCustomizedUtil.copyPageList(new PageR<ActStringCodeWrapper>(this.findStringCodeList(reqVO)),new PageR<ActStringCodeVO>(), ActStringCodeVO.class);
	}

	/**
	 * 功能：列表查询
	 */
	public List<ActStringCodeVO> findList(ActStringCodeVO reqVO) {
		List<ActStringCodeWrapper> list = findStringCodeList(reqVO);
		return ImsBeanUtil.cloneList(list, ActStringCodeVO.class);

	}
	
	/**
	 * 功能：根据查询条件->查询列表
	 */
	public List<ActStringCodeWrapper> findStringCodeList(ActStringCodeVO reqVO) {
		String orderBy = PageCustomizedUtil.sort(ActStringCode.class, reqVO.getSort(), reqVO.getOrder());
		if (StringUtils.isNotBlank(orderBy)) {
			if ("itemName".equals(reqVO.getSort())) {
				orderBy = "b." + orderBy.trim();
			} else if ("activityName".equals(reqVO.getSort())) {
				orderBy = "c." + orderBy.trim();
			} else {
				orderBy = "a." + orderBy.trim();
			}
			reqVO.setOrder(orderBy);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemNumber", reqVO.getItemNumber());
		map.put("itemName", reqVO.getItemName());
		map.put("status", reqVO.getStatus());
		map.put("companyId", reqVO.getCompanyId());
		map.put("order", reqVO.getOrder());
		return actStringCodeDao.findListBySearch(map);
	}
 
	/**
	 * 功能：根据主键id查询串码信息
	 * 
	 * @param id
	 *            主键
	 * @return ActStringCode
	 */
	public ActStringCode findById(Long id) {
		return actStringCodeDao.findObject(id);
	}

	/**
	 * 功能：根据串码查询串码信息
	 * 
	 * @param stringCode
	 *            此串码为明码(加密前)
	 * @return ActStringCode
	 * @throws Exception
	 */
	public ActStringCode findByStringCode(String stringCode) {
		if (StringUtils.isNotBlank(stringCode)) {
			try {
				stringCode = new DESEncryptUtil().encryptString(stringCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} // 数据库中为加密字符串
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("stringCode", stringCode);
			return actStringCodeDao.findObjectByMap(map);
		}
		return null;
	}

	/**
	 * 检查当前系统是否存在指定的code,系统唯一
	 * 
	 * @param stringCode
	 *            此串码为明码(加密前)
	 * @return true已存在，false:不存在
	 * @throws Exception
	 */
	public boolean checkStringCode(String stringCode, Long id) {
		if (StringUtils.isNotBlank(stringCode)) {
			try {
				stringCode = new DESEncryptUtil().encryptString(stringCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			} // 数据库中为加密字符串
			int count = actStringCodeDao.checkStringCode(stringCode, id);
			return count > 0 ? true : false;
		}
		return false;

	}

	/**
	 * actStringCodeList 中的stringCode此串码为明码(加密前)
	 * 
	 * @param actStringCodeList
	 * @return
	 */
	public void saveActStringCodeList(List<ActStringCode> actStringCodeList) throws Exception  {
		if (actStringCodeList != null && !actStringCodeList.isEmpty()) {
			for (ActStringCode entity : actStringCodeList) {
				entity.beforeSave();
				entity.setStatus(ActStringCodeStatusEnum.NOTUSED.getValue());// 未使用
				entity.setStringCode(new DESEncryptUtil().encryptString(entity.getStringCode()));
				  // 加密保存
				actStringCodeDao.save(entity);
			}
		}
	}

	/**
	 * entity 中的stringCode串码为明码(加密前)
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public MisRespResult<Void> saveOrUpdate(ActStringCodeVO entityVO) throws Exception {
		MisRespResult<Void> result = new MisRespResult<Void>(MisResultCode.FAIL);
		// 全公司内唯一
		boolean exsit = checkStringCode(entityVO.getStringCode(), entityVO.getId());
		if (exsit) {
			result.setMsg(MisResultCode.Item31001.getMessage());
			return result;
		}
		ActStringCode entity = ImsBeanUtil.clone(entityVO, ActStringCode.class);
		saveOrUpdate(entity);
		return new MisRespResult<Void>(MisResultCode.OK);
	}
	
	/**
	 * 批量添加
	 * @param stringCodeList
	 * @param itemNumber
	 * @param companyId
	 * @throws Exception
	 */
	public Integer batchAdd(List<String> stringCodeList,String itemNumber,Long companyId) throws Exception {
		int i=0;
		if(stringCodeList!=null && !stringCodeList.isEmpty()){
			for(String stringCode : stringCodeList){
				ActStringCode entity = new ActStringCode();
				entity.setItemNumber(itemNumber);
				entity.setCompanyId(companyId);
				entity.setStringCode(stringCode);
				saveOrUpdate(entity);
				i++;
			}
		}
		return i;
	}
	
	/**
	 * 保存或更新实体
	 * @param entity
	 * @throws Exception
	 */
	private void  saveOrUpdate(ActStringCode entity) throws Exception {
		if (null == entity.getId()) {
			entity.beforeSave();
			entity.setStatus(ActStringCodeStatusEnum.NOTUSED.getValue());// 未使用
			entity.setStringCode(new DESEncryptUtil().encryptString(entity.getStringCode()));// 加密保存
			actStringCodeDao.save(entity);
		} else {
			ActStringCode oldEntity = findById(entity.getId());
			ImsBeanUtil.copyNotNull(oldEntity, entity);
			oldEntity.beforeUpdate();
			if (StringUtils.isNotBlank(entity.getStringCode())) {
				oldEntity.setStringCode(new DESEncryptUtil().encryptString(entity.getStringCode()));// 加密保存
			}
			actStringCodeDao.update(oldEntity);
		}
	}

	/**
	 * 
	 * 根据id批量删除
	 */
	public int deleteByIds(String idArray) {
		List<String> ids = new ArrayList<String>();
		ids = Arrays.asList(idArray.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
		int result = actStringCodeDao.deleteBatch(ids);
		return result;
	}

	/**
	 * 
	 * 根据id更新
	 */
	public int updateByPrimaryKey(ActStringCode actStringCode) {
		return actStringCodeDao.update(actStringCode);
	}

	/**
	 * 根据stringCode更新
	 * 
	 * @param entity
	 *            中的stringCode串码为明码(加密前)
	 * @return
	 * @throws Exception
	 */
	public int updateByStringCode(ActStringCode entity) {
		try {
			entity.setStringCode(new DESEncryptUtil().encryptString(entity.getStringCode()));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} // 数据库中为加密字符串
		return actStringCodeDao.updateByStringCode(entity);
	}

	/**
	 * 功能：查指串码个数
	 */
	public int getStringCodeCount(ActStringCode actStringCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemNumber", actStringCode.getItemNumber());
		map.put("status", actStringCode.getStatus());
		map.put("companyId", actStringCode.getCompanyId());
		return actStringCodeDao.findTotal(map);
	}

	/**
	 * 返回所有的串码
	 */
	public List<String> findAllStringCodes() {
		return actStringCodeDao.findAllStringCodes();
	}
}
