package com.gwghk.ims.common.inf;

import com.gwghk.ims.common.annotation.Company;

public interface SeqDubboService {
	
	String getSeq(String seqCode,@Company Long companyId);
}
