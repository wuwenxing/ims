package com.gwghk.ims.marketingtool.manager.market.flow;

/**
 * 流量服务监听器
 * @author wayne
 */
public abstract interface FlowListener<T> {
	/**
	 * 流量充值以前做的操作
	 * @param emailInfo
	 */
	public abstract void updateBefore(FlowContext<T> flowContext);
	/**
	 * 流量充值成功的操作
	 * @param emailContext
	 */
	public abstract void updateAfter(FlowContext<T> flowContext);
	/**
	 * 流量充值异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(FlowContext<T> flowContext);
}
