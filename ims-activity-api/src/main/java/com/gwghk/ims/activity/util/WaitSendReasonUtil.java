package com.gwghk.ims.activity.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.gwghk.ims.activity.enums.ActWaitSendReasonEnum;

/**
 * 摘要：应发记录的原因
 * 
 * @author eva
 * @date 2017-08-9
 */
public class WaitSendReasonUtil {

    /***************************************************************************************************************************
     * 所谓BidiMap，直译就是双向Map，可以通过key找到value， 也可以通过value找到key，这在我们日常的代码-名称匹配的时候很方便： 因为我们除了需要通过代码找到名称之外，往往也需要处理用户输入的名称，然后获取其代码。
     * 需要注意的是BidiMap当中不光key不能重复，value也不可以。
     */
    private static BidiMap REASONMAP = new DualHashBidiMap();
    static {
        REASONMAP.put(ActWaitSendReasonEnum.DISABLED.getCode(), ActWaitSendReasonEnum.DISABLED.getCodeNum());// 1 禁用
        REASONMAP.put(ActWaitSendReasonEnum.NOTENOUGH.getCode(),ActWaitSendReasonEnum.NOTENOUGH.getCodeNum());// 2 数量不足
        REASONMAP.put(ActWaitSendReasonEnum.TIMEEXPIRED.getCode(),ActWaitSendReasonEnum.TIMEEXPIRED.getCodeNum());// 4 时间过期
        REASONMAP.put(ActWaitSendReasonEnum.TIMENOTREACHED.getCode(), ActWaitSendReasonEnum.TIMENOTREACHED.getCodeNum());// 8时间未到
    }

    public static int coverReason(Set<String> reasons) {
        if (reasons == null || reasons.isEmpty()) {
            return 0;
        }
        int identy = 0;
        for (String id : reasons) {
            Integer iid = (Integer) REASONMAP.get(id);
            if (iid != null) {
                identy = identy | iid;
            }
        }
        return identy;
    }

    public static Set<String> converReasons(Integer reason) {
        if (reason == null || reason < 1) {
            return null;
        }

        Set<String> reasons = new HashSet<String>();
        for (int i = 0; i < 5; i++) {
            int iid = 1 << i;
            if ((iid & reason) < 1) {
                continue;
            }

            String id = (String) REASONMAP.getKey(iid);
            if (id != null) {
                reasons.add(id);
            }
        }
        return reasons;
    }
}
