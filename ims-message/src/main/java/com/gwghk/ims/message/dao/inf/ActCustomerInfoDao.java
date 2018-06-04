package com.gwghk.ims.message.dao.inf;

import java.util.List;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.message.dao.entity.ActCustomerInfo;

/**
 * 
 * @ClassName: ActivityCustomerInfoMapper
 * @Description: 用户资料信息mapper
 * @author lawrence
 * @date 2017年5月22日
 *
 */
public interface ActCustomerInfoDao extends BaseDao<ActCustomerInfo> {
	/**
	 * 
	 * @MethodName: selectByExample
	 * @Description: 查询参数内的客户资料表
	 * @param params
	 * @return
	 * @return List<ActCustomerInfo>
	 */
	List<ActCustomerInfo> selectByExample(ActCustomerInfo params);

}