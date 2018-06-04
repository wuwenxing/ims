package com.gwghk.ims.monitor.notice;

import java.io.Serializable;

/**
 * 摘要：Gw基础服务接口返回结果对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2016年12月12日
 */
public class GwApiRespResult<T> implements Serializable {

    private static final long serialVersionUID = 8724365909968157425L;

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果信息
     */
    private String msg;

    /**
     * 扩展对象(放置分页信息、其他信息等)
     */
    private Object extendData;

    /**
     * 返回结果的数据对象
     */
    private T data;

    public GwApiRespResult() {
    }

    public GwApiRespResult(String code) {
        this.code = code;
    }
    
    public GwApiRespResult(String code, String message){
    	this.code = code;
    	this.msg = message;
    }
    
    public GwApiRespResult(GwApiResultCode apiResultCode){
    	this.code = apiResultCode.getCode();
    	this.msg = apiResultCode.getMessage();
    }
    
    public boolean isOk() {
        return GwApiResultCode.OK.getCode().equals(code);
    }
    
    public boolean isNotOk(){
    	return !this.isOk();
    }
    
    public GwApiRespResult<T> setRespMsg(GwApiResultCode apiResultCode){
    	this.code = apiResultCode.getCode();
    	this.msg = apiResultCode.getMessage();
    	return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public GwApiRespResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getExtendData() {
        return extendData;
    }

    public GwApiRespResult<T> setExtendData(Object extendData) {
        this.extendData = extendData;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
