package com.gwghk.ims.marketingtool.manager.market;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.marketingtool.dao.entity.ImsCamiLogDetail;
import com.gwghk.ims.marketingtool.dao.inf.ImsCamiLogDetailDao;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.support.CrudService;
 
@Component
@Transactional
public class ImsCamiLogDetailMapper extends CrudService<ImsCamiLogDetailDao,ImsCamiLogDetail> {

	/**
	 * 功能：新增或修改时保存信息
	 */
	public MisRespResult<Void> saveOrUpdate(ImsCamiLogDetail vo) throws Exception {
		if (null == vo.getDetailId()) {
			this.getDao().saveIgnoreNull(ImsBeanUtil.copyNotNull(new ImsCamiLogDetail(), vo));
		} else {
			ImsCamiLogDetail old = findById(vo.getDetailId());
			ImsBeanUtil.copyNotNull(old, vo);
			this.getDao().updateIgnoreNull(old);
		}
		return MisRespResult.success() ;
	}
	

	/**
	 * 功能：根据id->获取信息
	 */
	public ImsCamiLogDetail findById(Long id) {
		return this.getDao().get(id);
	}
	 

	/**
	 * 功能：批量删除
	 */
	public int deleteByIds(String ids) {
		return this.getDao().delByExpression(new Query().in("id", Arrays.asList(ids.split(","))));
	}
	 
}