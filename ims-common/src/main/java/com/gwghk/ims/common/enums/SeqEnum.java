package com.gwghk.ims.common.enums;

/**
 * 摘要：统一序列号枚举
 * @author Gavin.guo
 * @version 1.0
 * @Date 2018年3月30日
 */
public enum SeqEnum {
	/**活动提案序列*/
	ActivityProposal("A","activity_no_seq", 10000),
	/**活动修改提案序列*/
	ActivityModify("M","proposal_m_seq", 10000),
	/**活动发送记录编号*/
	ActivityPrizeRecordNumber("R","prize_record_n_seq", 100000000),
	/**消息发送记录编号*/
	MsgNo("Mg","msg_no_seq", 1),
	/**贈金编号  **/
	ActivityBonusRecordNumber("B","bonus_record_n_seq", 100000000),
    /**任务编号序列*/
    ActivityTaskNumber("T", "task_n_seq", 10000)
    
    ;
	
    private final String prefix;
    private final String seqCode;
    private final int mod;

    SeqEnum(String prefix,String seqCode, int mod) {
        this.prefix = prefix;
        this.seqCode = seqCode;
        this.mod = mod;
    }

	public String getPrefix() {
		return prefix;
	}

	public String getSeqCode() {
		return seqCode;
	}

	public int getMod() {
		return mod;
	}
	
	public static SeqEnum getSequence(String seqCode){
		for(SeqEnum seq : SeqEnum.values()){
			if(seq.getSeqCode().equalsIgnoreCase(seqCode)){
				return seq;
			}
		}
		return null;
	}
}
