package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.Gts2symbolDemoRealWrapper;
import com.gwghk.ims.common.common.BaseDao;
import com.gwghk.ims.common.vo.activity.Gts2symbolDemoRealVO;

public interface VGts2symbolDemoRealDao extends BaseDao<Gts2symbolDemoRealWrapper> {

	public List<Gts2symbolDemoRealWrapper> getGts2symbolDemoReals(Gts2symbolDemoRealVO vo);

}
