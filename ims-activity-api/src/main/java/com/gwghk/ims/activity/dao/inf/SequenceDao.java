package com.gwghk.ims.activity.dao.inf;

public interface SequenceDao {

    void updateSeq(String seq);
    
    Long getCurSeq(String seq);
}
