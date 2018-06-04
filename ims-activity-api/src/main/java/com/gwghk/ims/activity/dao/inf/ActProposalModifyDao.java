package com.gwghk.ims.activity.dao.inf;

import java.util.List;

import com.gwghk.ims.activity.dao.entity.ActProposalModify;
import com.gwghk.ims.activity.dao.entity.ActProposalModifyWrapper;
import com.gwghk.ims.common.common.BaseDao;

public interface ActProposalModifyDao extends BaseDao<ActProposalModify> {
	
	/**
	 * 获取所有的修改提案信息
	 * @return
	 */
	List<ActProposalModifyWrapper> findActProposalModifyList(ActProposalModifyWrapper vo);
}