package com.gwghk.ims.marketingtool.manager.market.cami;

/**
 * 
 * @ClassName: CamiListener
 * @Description: 卡密服务监听器
 * @author lawrence
 * @date 2018年5月7日
 *
 */
public abstract interface CamiListener<T> {
	/**
	 * 卡密充值以前做的操作
	 * 
	 * @param emailInfo
	 */
	public abstract void updateBefore(CamiContext<T> onlineContext);

	/**
	 * 卡密充值成功的操作
	 * 
	 * @param emailContext
	 */
	public abstract void updateAfter(CamiContext<T> onlineContext);

	/**
	 * 卡密充值异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(CamiContext<T> onlineContext);
}
