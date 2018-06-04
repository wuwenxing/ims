package com.gwghk.ims.common.enums;

/**
 * 摘要：账号参与活动状态
 * 
 * @author eva.liu
 * @version 1.0
 * @Date 2017年10月25日
 */
public enum ActAccountParticipateStatusEnum {
    /** 无资格参与 */
    NO_QUALIFIED(0, "无资格参与"),
    /** 有参与活动的资格 */
    QUALIFIED(1, "有参与活动的资格"),
    /** 已参加活动:产生了发放记录 */
    HAS_PARTICIPATED(2, "已参加活动"),
    /** 限制不能参加了 */
    NOT_ATTENDED(3, "限制不能参加了");

    private final int code;
    private final String name;

    ActAccountParticipateStatusEnum(int _code, String name) {
        this.code = _code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code) {
        for (ActAccountParticipateStatusEnum ae : ActAccountParticipateStatusEnum.values()) {
            if (ae.getCode() == code) {
                return ae.getName();
            }
        }
        return null;
    }
}