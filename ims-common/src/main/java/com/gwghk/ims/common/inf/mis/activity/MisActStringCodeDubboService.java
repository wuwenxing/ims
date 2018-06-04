package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.vo.activity.ActStringCodeVO;

public interface MisActStringCodeDubboService {
	
	/**
	 * 分页查询串码
	 * @param reqVo
	 * @return
	 */
	MisRespResult<PageR<ActStringCodeVO>> findPageList(ActStringCodeVO reqVo,@Company Long companyId);
	
	/**
	 * 获得串码列表集合
	 * @param reqVo
	 * @return
	 */
	MisRespResult<List<ActStringCodeVO>> findList(ActStringCodeVO reqVo,@Company Long companyId);
 
	/**
	 * 根据id获得串码
	 * @param id
	 * @return
	 */
	MisRespResult<ActStringCodeVO> findById(Long id,@Company Long companyId);
	
	/**
	 * 根据串码编号获得串码
	 * @param stringCode
	 * @return
	 */
	MisRespResult<ActStringCodeVO> findByStringCode(String stringCode,@Company Long companyId);
	
	/**
	 * 更新或新增串码 -(更新是根据id更新)
	 * 校验串码唯 一性
	 * @param entityVO
	 * @return
	 */
	MisRespResult<Void> saveOrUpdate(ActStringCodeVO entityVO,@Company Long companyId);
	
	
	/**
	 * 更新串码编号更新串码
	 * 不校验串码，直接覆盖
	 * @param reqVo
	 * @return
	 */
	MisRespResult<Void> updateByStringCode(ActStringCodeVO reqVo,@Company Long companyId);
	
	/**
	 * 获得串码个数
	 * @param itemNumber
	 * @param status
	 * @param companyId
	 * @return
	 */
	MisRespResult<Integer>  getStringCodeCount(ActStringCodeVO reqVo,@Company Long companyId);
	/**
	 * 批量删除串码
	 * @param idArray
	 * @return
	 */
	MisRespResult<Void> deleteByIds(String idArray,@Company Long companyId);
	
	/**
	 * 获得所有串码
	 * @param companyId
	 * @return
	 */
	MisRespResult<List<String>> findAllStringCodes(Long companyId);
	
	/**
	 * 批量添加
	 * 不校验串码唯 一
	 * @param stringCodeList -明码串码集合
	 * @param itemNumber -物品编号
	 * @param companyId
	 * @return
	 */
	MisRespResult<Integer> batchAdd(List<String> stringCodeList,String itemNumber,@Company Long companyId);
	

}
