package com.gwghk.ims.message.dao.inf;

import com.gwghk.ims.message.dao.entity.Gts2BoDict;

/**
 * 
 * @ClassName: Gts2BoDictMapper
 * @Description: GTS2数据字典
 * @author lawrence
 * @date 2017年8月18日
 *
 */
public interface Gts2BoDictDao {
	int insert(Gts2BoDict gts2BoDict);
	
	int update(Gts2BoDict gts2BoDict);
}