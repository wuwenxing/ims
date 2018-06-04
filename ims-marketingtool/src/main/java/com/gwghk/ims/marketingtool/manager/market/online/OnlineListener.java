package com.gwghk.ims.marketingtool.manager.market.online;

/**
 * 话费服务监听器
 * @author wayne
 */
public abstract interface OnlineListener<T> {
	/**
	 * 话费充值以前做的操作
	 * @param emailInfo
	 */
	public abstract void updateBefore(OnlineContext<T> onlineContext);
	/**
	 * 话费充值成功的操作
	 * @param emailContext
	 */
	public abstract void updateAfter(OnlineContext<T> onlineContext);
	/**
	 * 话费充值异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(OnlineContext<T> onlineContext);
}
