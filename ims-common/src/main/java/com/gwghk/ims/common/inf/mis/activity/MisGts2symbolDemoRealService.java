package com.gwghk.ims.common.inf.mis.activity;

import java.util.List;
import java.util.Map;

import com.gwghk.ims.common.annotation.Company;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.vo.activity.Gts2symbolDemoRealVO;

/**  
* 摘要:   
* @author George.li  
* @date 2018年5月28日  
* @version 1.0  
*/
public interface MisGts2symbolDemoRealService {
	 
    
    /**
     * 功能：列表查询
     * 
     * @param reqDto 请求对象
     * @return List
     */
    public MisRespResult<List<Gts2symbolDemoRealVO>> findAll(Gts2symbolDemoRealVO reqDto,@Company Long companyId);
   
    
    /**
     * 获取所有交易产品
     * @param companyId
     * @return map key:产品代号 value:产品名称
     */
    public Map<String,String> findAllMap(Long companyId);
      
    

}
