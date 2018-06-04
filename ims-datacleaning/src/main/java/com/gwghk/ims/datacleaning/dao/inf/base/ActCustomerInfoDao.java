package com.gwghk.ims.datacleaning.dao.inf.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActCashout;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfo;

/**
 * 
 * @ClassName: ActivityCustomerInfoMapper
 * @Description: 用户资料信息mapper
 * @author lawrence
 * @date 2017年5月22日
 *
 */
public interface ActCustomerInfoDao  extends BaseDao<ActCustomerInfo>{
	/**
	 * 
	 * @MethodName: insertGts2Real
	 * @Description: 写入GTS2真实账号数据
	 * @param data
	 * @return
	 * @return int
	 */
	@SuppressWarnings("rawtypes")
	int insertGts2Real(Map data);
	
	/**
	 * 
	 * @MethodName: selectRealData
	 * @Description: 查询范围内的客户资料信息
	 * @param data
	 * @return
	 * @return List<ActCustomerInfo>
	 */
	@SuppressWarnings("rawtypes")
	List<ActCustomerInfo> selectRealData(Map data);

	/**
	 * 
	 * @MethodName: selectLastActCustomerInfo
	 * @Description: 读取写入真实或者模拟账号中的最后一条记录的更新时间
	 * @param env
	 * @param source
	 * @param companyCode
	 * @return
	 * @return ActCustomerInfo
	 */
	ActCustomerInfo selectLastActCustomerInfo(@Param("env") String env, @Param("source") String source,
			@Param("companyCode") String companyCode);

	/**
	 * 
	 * @MethodName: insertGts2Demo
	 * @Description: 写入GTS2模拟账号数据
	 * @param data
	 * @return
	 * @return int
	 */
	@SuppressWarnings("rawtypes")
	int insertGts2Demo(Map data);
	
	/**
	 * 
	 * @MethodName: selectDemoData
	 * @Description: 查询原始表的模拟账号数据
	 * @param data
	 * @return
	 * @return List<ActCustomerInfo>
	 */
	@SuppressWarnings("rawtypes")
	List<ActCustomerInfo> selectDemoData(Map data);
	

	List<ActCustomerInfo> selectDemoByExample(ActCustomerInfo params);

	/**
	 * 
	 * @MethodName: saveOrUpdateRealManual
	 * @Description: 更新或者写入真实客户资料
	 * @param data
	 * @return void
	 */
	void saveOrUpdateRealManual(ActCustomerInfo actCustomerInfo);

	/**
	 * 
	 * @MethodName: saveOrUpdateDemoManual
	 * @Description: 更新或者写入模拟客户资料
	 * @param actCustomerInfo
	 * @return void
	 */
	void saveOrUpdateDemoManual(ActCustomerInfo actCustomerInfo);

	/**
	 * 
	 * @MethodName: selectByParams
	 * @Description: 根据账号平台查询对应的客户资料数据
	 * @param actCustomerInfo
	 * @return
	 * @return ActCustomerInfo
	 */
	ActCustomerInfo selectByParams(ActCustomerInfo actCustomerInfo);
	
	/**
	 * 
	 * @MethodName: updateGroupBlack
	 * @Description: 全局更新集团黑名单标识
	 * @param actCustomerInfo
	 * @return
	 * @return int
	 */
	int updateGroupBlack(ActCustomerInfo actCustomerInfo);
	
	/**
	 * 查询所有没有推送的数据
	 * @return
	 */
	List<ActCustomerInfo> findUnpushCustomerInfo(@Param("env") String env);
	/**
	 * 批量更新推送状态
	 */
	int batchUpdatePushStatus(@Param("env") String env,@Param("id") Long id);
}