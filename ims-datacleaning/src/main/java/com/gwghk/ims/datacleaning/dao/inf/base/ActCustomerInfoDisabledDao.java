package com.gwghk.ims.datacleaning.dao.inf.base;

import org.apache.ibatis.annotations.Param;

import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.datacleaning.dao.entity.ActCustomerInfoDisabled;

/**
 * 
 * @ClassName: ActCustomerInfoDisabledMapper
 * @Description: 客户资料已经注销的栏位信息(身份证，手机号)mapper
 * @author lawrence
 * @date 2018年2月9日
 *
 */
public interface ActCustomerInfoDisabledDao extends BaseDao<ActCustomerInfoDisabled> {
	void insertDisabledIdNumber(@Param("companyCode") String companyCode);

	void insertDisabledMobile(@Param("companyCode") String companyCode);
}