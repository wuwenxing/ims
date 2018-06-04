package com.gwghk.ims.bos.web.common.enums;

/**
 * 摘要：活动优惠触发类型
 * 
 * @author misa.liu
 * @version 1.0
 * @Date 2017年6月26日
 */
public enum ActTriggerType {
    
    /** 重大行情事件 */
    IMPORTANTEVENT("importantEvent", "重大行情事件"),
    
    /** 真实物品 */
    MARKETSCOPE("marketScope", "行情波动达到一定范围");
    
    private final String code;
    private final String name;

    ActTriggerType(String code, String name) {
       this.code = code;
       this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
