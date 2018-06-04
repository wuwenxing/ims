package com.gwghk.ims.common.common;

import java.io.Serializable;

/**
 * 摘要：Mis后台-接口返回结果对象
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年11月9日
 */
public class MisRespResult<T> implements Serializable {

    private static final long serialVersionUID = 8724365909968157425L;

    /**
     * 结果码
     */
    private String code = MisResultCode.OK.getCode();

    /**
     * 结果信息
     */
    private String msg = MisResultCode.OK.getMessage();

    /**
     * 扩展对象(放置分页信息、其他信息等)
     */
    private Object extendData;

    /**
     * 返回结果的数据对象
     */
    private T data;

    public MisRespResult() {
    }

    public MisRespResult(String code) {
        this.code = code;
    }
    
    public MisRespResult(String code, String message){
    	this.code = code;
    	this.msg = message;
    }
    
    public MisRespResult(MisResultCode misApiResultCode){
    	this.code = misApiResultCode.getCode();
    	this.msg = misApiResultCode.getMessage();
    }
    
    public boolean isOk() {
        return MisResultCode.OK.getCode().equals(code);
    }
    
    public boolean isNotOk(){
    	return !this.isOk();
    }
    
    public static <T> MisRespResult<T> error(MisResultCode misApiResultCode){
    	return new MisRespResult<T>(misApiResultCode);
    }
    
    public static <T> MisRespResult<T> error(MisResultCode misApiResultCode,Object...params){
    	return new MisRespResult<T>(misApiResultCode.getCode(),String.format(misApiResultCode.getMessage(),params));
    }
    
	public static <T> MisRespResult<T> error(String msg){
    	return new MisRespResult<T>(MisResultCode.FAIL.getCode(),msg);
    }
    
	public static <T> MisRespResult<T> error(String code, String msg){
    	return new MisRespResult<T>(code,msg);
    }
    
   	public static  MisRespResult<Void> success(){
       	return new MisRespResult<Void>(MisResultCode.OK);
    }
    
   	public static <T> MisRespResult<T> success(T data){
    	MisRespResult<T> ar = new MisRespResult<T>(MisResultCode.OK);
    	ar.setData(data);
    	return ar;
    }
   	
	public static <T> MisRespResult<T> success(T data,Object extendData){
    	MisRespResult<T> ar = new MisRespResult<T>(MisResultCode.OK);
    	return ar.setData(data).setExtendData(extendData);
    }
	
	public MisRespResult<T> setRespMsg(MisResultCode misApiResultCode){
    	this.code = misApiResultCode.getCode();
    	this.msg = misApiResultCode.getMessage();
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

    public MisRespResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    
    public MisRespResult<T> setMsg(String msg,Object...params) {
        this.msg = String.format(msg,params);
        return this;
    }

    public Object getExtendData() {
        return extendData;
    }

    public MisRespResult<T> setExtendData(Object extendData) {
        this.extendData = extendData;
        return this;
    }

    public T getData() {
        return data;
    }

    public MisRespResult<T> setData(T data) {
        this.data = data;
        return this;
    }

	@Override
	public String toString() {
		return "[code=" + code + ", msg=" + msg + ", extendData=" + extendData + ", data=" + data + "]";
	}
}
