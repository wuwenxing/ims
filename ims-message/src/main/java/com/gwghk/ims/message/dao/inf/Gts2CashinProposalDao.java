package com.gwghk.ims.message.dao.inf;

import com.gwghk.ims.message.dao.entity.Gts2CashinProposal;

/**
 * 
 * @ClassName: Gts2CashinProposalMapper
 * @Description: GTS2入金数据
 * @author lawrence
 * @date 2017年8月17日
 *
 */
public interface Gts2CashinProposalDao {
	int save(Gts2CashinProposal gts2CashinProposal);
	
	int update(Gts2CashinProposal gts2CashinProposal);
}