package com.gwghk.ims.message.dao.entity;

public class Gts2Cashout {
	private Gts2AccountInfo accountInfo;
	private Gts2CashoutProposal proposal;

	public Gts2AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(Gts2AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public Gts2CashoutProposal getProposal() {
		return proposal;
	}

	public void setProposal(Gts2CashoutProposal proposal) {
		this.proposal = proposal;
	}

}
