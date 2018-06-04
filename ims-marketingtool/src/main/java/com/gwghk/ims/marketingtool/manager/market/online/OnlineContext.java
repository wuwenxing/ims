package com.gwghk.ims.marketingtool.manager.market.online;

/**
 * 话费充值执行的上下文信息-即存储开始到结束的数据
 * 
 * @author wayne
 */
public class OnlineContext<T> {

	private OnlineInfo onlineInfo;// 待话费充值实体
	private T obj; // 保存的上下文对象
	private Object response;// 发送后返回的实体对象(容联)
	private Throwable throwable;// 异常情况对象


	public OnlineInfo getOnlineInfo() {
		return onlineInfo;
	}

	public void setOnlineInfo(OnlineInfo onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
