package com.gwghk.ims.mis.gateway.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisGts2symbolDemoRealService;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ActSettingVO;
import com.gwghk.ims.common.vo.activity.Gts2symbolDemoRealVO;

/**
 * 
 * 摘要：活动提案controller
 *
 * @author eva.liu
 * @version 1.0
 * @Date 2018年4月3日
 */
@Controller
@RequestMapping("/mis/act")
public class ActSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActSettingController.class);
 
	@Autowired
	private MisActSettingDubboService misActSettingDubboService;
	
	@Autowired
	private MisGts2symbolDemoRealService misGts2symbolDemoRealService;
	
	
	
	/**
	 * @api {post} /mis/act/page 分页查询
	 * @apiSampleRequest /mis/act/page 
	 * @apiGroup ActSettingApi
	 * 
	 * @apiParam {String} activityPeriods 活动编号（模糊查询）
	 * @apiParam {String} activityName 活动名称（模糊查询）
	 * @apiParam {String} activityType 活动类型
	 * @apiParam {String} filterActType 需要过滤的活动类型
	 * @apiParam {Boolean} isExpiration  是否加载无效活动 true:加载，false不加载
	 * @apiParam {List} activityTypeList 活动类型集合
	 * @apiParam {Date} startTime 活动-开始时间
	 * @apiParam {Date} endTime 活动-结束时间
	 * @apiParam {String} proposalStatus 提案状态
	 * @apiParam {List} tagCodes 活动标签
	 * @apiParam {Boolean} isExpiration 是否查询无效活动 true:加载，false不加载
	 * @apiParam {String} orderStr 排序字段
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * { activityPeriods:"xxxx", activityType:"xxxx"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActSettingVO>> pageList(@ModelAttribute ActSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<PageR<ActSettingVO>> result = misActSettingDubboService.findPageList(vo,vo.getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/list 加载活动列表
	 * @apiSampleRequest /mis/act/list
	 * @apiGroup ActSettingApi

	
	* @apiParam {String} activityPeriods 活动编号（模糊查询）
	 * @apiParam {String} activityName 活动名称（模糊查询）
	 * @apiParam {String} activityType 活动类型
	 * @apiParam {String} filterActType 需要过滤的活动类型
	 * @apiParam {Boolean} isExpiration  是否加载无效活动 true:加载，false不加载
	 * @apiParam {List} activityTypeList 活动类型集合
	 * @apiParam {Date} startTime 活动-开始时间
	 * @apiParam {Date} endTime 活动-结束时间
	 * @apiParam {String} proposalStatus 提案状态
	 * @apiParam {List} tagCodes 活动标签
	 * @apiParam {Boolean} isExpiration 是否查询无效活动 true:加载，false不加载
	 * @apiParam {String} orderStr 排序字段
	 * @apiParam {String} enableFlag Y启用N禁用
     * 
	 * 
     * @apiParamExample {json} 请求样例 
     * 	{activityPeriods:"xxx",activityName:"xxx",activityType:"xxx",enableFlag:"xxx",...,page:1,rows:20}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public MisRespResult<List<ActSettingVO>> getActList(@ModelAttribute ActSettingVO vo) {
		try {
			vo.setCompanyId(getCompanyId());
			MisRespResult<List<ActSettingVO>> result = misActSettingDubboService.findList(vo,false,vo.getCompanyId());
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
 

	/**
	 * @api {post} /mis/act/save 新增或修改活动
	 * @apiSampleRequest /mis/act/save 
	 * @apiGroup ActSettingApi
	 * 
	 * @apiParam {long} actBaseInfo.id  活动基本设置.id
	 * @apiParam {String} actBaseInfo.activityPeriods 活动基本设置.活动编号
	 * @apiParam {String} actBaseInfo.activityName 活动基本设置.活动名称
	 * @apiParam {String} actBaseInfo.activityType 活动基本设置.活动类型(来源于数据字典)
	 * @apiParam {Integer} actBaseInfo.turnGroup 活动基本设置.是否允许转组 (0:不允许,1：允许)
	 * @apiParam {Integer} actBaseInfo.transfer 活动基本设置.是否允许转账 (0:不允许,1：允许)
	 * @apiParam {Date} actBaseInfo.startTime 活动基本设置.活动-开始时间
	 * @apiParam {Date} actBaseInfo.endTime 活动基本设置.活动-结束时间
	 * @apiParam {String} actBaseInfo.tagCode 活动基本设置.所属标签
	 * @apiParam {Integer} actBaseInfo.finishDays 活动基本设置.活动完成时间
	 * @apiParam {Integer} actBaseInfo.autoHandOut 活动基本设置.是否自动分发物品(0:不自动，人工审核,1:自动发放)
	 * @apiParam {String} actBaseInfo.priorityPlatform 活动基本设置.发放奖励优先平台
	 * @apiParam {Integer} actBaseInfo.withGoldDays 活动基本设置.赠金有效期
	 * @apiParam {Integer} actBaseInfo.coinDays 活动基本设置.代币有效期
	 * @apiParam {BigDecimal} actBaseInfo.demoKeepAmount 活动基本设置.demo账号保留余额
	 * @apiParam {Integer} actBaseInfo.maxExchangeCount 活动基本设置.最高兑换次数
	 * @apiParam {String} actBaseInfo.activityUrl 活动基本设置.活动url
	 * @apiParam {String} actBaseInfo.otherMsg 活动基本设置.其他信息或备注
	 * 
	 * @apiParam {long} actCondSetting.id 活动参与条件.id 
	 * @apiParam {String} actCondSetting.activityPeriods 活动参与条件.活动编号
	 * @apiParam {String} actCondSetting.conditionType 活动参与条件.条件类型
	 * @apiParam {String} actCondSetting.accountType 活动参与条件.账户类型
	 * @apiParam {Boolean} actCondSetting.accountOnly 活动参与条件.是否账号唯一 true:唯一，false:可允许不唯 一
	 * @apiParam {Boolean} actCondSetting.allowCancelAccount 活动参与条件.是否允许注销过账号参加活动 true:支持 ,false:
	 * @apiParam {String} actCondSetting.accountLevel 活动参与条件.账号级别
	 * @apiParam {String} actCondSetting.platformsCcy 活动参与条件.平台+币种(GTS2#USD,MT4#CNH)
	 * @apiParam {String} actCondSetting.items 活动参与条件.交易产品
	 * @apiParam {Date} actCondSetting.registerStartTime 活动参与条件.账号注册开始日期
	 * @apiParam {Date} actCondSetting.registerEndTime 活动参与条件.账号注册结束日期
	 * @apiParam {Boolean} actCondSetting.allowWhiteUsers 活动参与条件.只允许白名单用户参加活动 true:是 ,false:否
	 * @apiParam {String} actCondSetting.whiteListFileName 活动参与条件.白名单文件地址
	 * @apiParam {String} actCondSetting.blackListFileName 活动参与条件.黑名单文件地址
	 * @apiParam {Boolean} actCondSetting.allowWithdrawals 活动参与条件.是否允许取款
	 * @apiParam {Date} actCondSetting.activateStartTime 活动参与条件.账号激活日期:开始时间
	 * @apiParam {Date} actCondSetting.activateEndTime 活动参与条件.账号激活日期:结束时间
	 * @apiParam {String} actCondSetting.accountStatus 活动参与条件.账户状态
     *
	 * 
	 * @apiParam {long} actTaskSettings.id 任务.id 
	 * @apiParam {String} actTaskSettings.activityPeriods 任务.活动编号
	 * @apiParam {String} taskItemCode 任务.任务编号
	 * @apiParam {String} taskItemVal 任务.单个任务项值
	 * @apiParam {String} taskParamValUnit 任务.单个任务项值单位
	 * @apiParam {String} taskItemVal2 任务.单个任务项值2
	 * @apiParam {String} taskParamVal2Unit 任务.单位任务项值2单位
	 * @apiParam {BigDecimal} taskItemTime 任务.任务时间
	 * @apiParam {String} taskItemTimeUnit 任务.时间单位
	 * @apiParam {Integer} taskOrder 任务.任务序号
	 * @apiParam {Integer} taskGroup 任务.所属组/层级
	 * @apiParam {Long} parentId 任务.父任务id
	 * @apiParam {String} taskDesc 任务.任务描述
	 * @apiParam {String} taskFullName 任务.任务全名（eg:真实账号激活100元1小时)
	 * @apiParam {List} subTaskSettings 任务.子集任务
	 * @apiParam {List} taskItems 任务.活动下的物品集合
	 * 
	 * @apiParam {Long} taskSettingId 奖励物品.任务设置ID
	 * @apiParam {String} itemNumber 奖励物品.物品编号
	 * @apiParam {String} itemName 奖励物品.物品名称
	 * @apiParam {String} itemType 物品类型(来源于数据字典)
	 * @apiParam {String} itemCategory 奖励物品.物品种类
	 * @apiParam {BigDecimal} itemPrice 奖励物品.物品价格或金额
	 * @apiParam {String} itemParamVal 奖励物品.物品参数值，可能是对应的物品金额或百分比或接口公式
	 * @apiParam {String} itemParamValUnit 奖励物品.物品参数单位USD/RMB
	 * @apiParam {Integer} receiveMaxNum 奖励物品.最多领取次数
	 * @apiParam {BigDecimal} receiveMaxMoney 奖励物品.最高领取金额
	 * @apiParam {String} receiveMaxMoneyUnit 奖励物品.最高领取金额单位
	 * @apiParam {BigDecimal} tradeNum 奖励物品.交易手数
	 * @apiParam {String} tradeNumUnit 奖励物品.交易手数单位
	 * @apiParam {BigDecimal} infParamY 奖励物品.接口物品y参数值
	 * @apiParam {BigDecimal} infParamZ 奖励物品.接口物品z参数值
	 * @apiParam {BigDecimal} infParamMax 奖励物品.接品物品最高额度
	 * @apiParam {String} infParamMaxUnit 奖励物品.接口物品最高额度单位 M/G/Month/RMB/USD
	 * @apiParam {BigDecimal} equalValue 奖励物品.等额价值（目前只有：模拟币）
	 * @apiParam {BigDecimal} frequencyTime 奖励物品.频率时间  1天1次
	 * @apiParam {String} frequencyTimeUnit 奖励物品.频率时间要求 单位
	 * @apiParam {Integer} frequencyNum 奖励物品.频率时间内的个数
	 * @apiParam {String} frequencyNumUnit 奖励物品.频率时间内的个数单位
	 * @apiParam {BigDecimal} itemProbability 奖励物品.物品抽中的概率
     * 
	 * @apiParam {List} actTaskSettings 活动下的任务物品
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {serialVersionUID:"xxxx",actBaseInfo:"xxxx",actCondSetting:"xxxx",actTaskSettings:"xxxx",enableFlag:"Y"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request,@RequestBody ActSettingDetailsVO vo) {
		try {
			this.setPublicAttr(vo, vo.getActBaseInfo().getId());
			
			if(vo.getActBaseInfo()!=null) {
				if(vo.getActBaseInfo().getId()==null) {
					vo.getActBaseInfo().setCreateDate(vo.getCreateDate());
					vo.getActBaseInfo().setCreateUser(vo.getCreateUser());
				}else {
					vo.getActBaseInfo().setUpdateDate(vo.getUpdateDate());
					vo.getActBaseInfo().setUpdateUser(vo.getUpdateUser());
				}
				
			}
			
			MisRespResult<Void> result = misActSettingDubboService.saveOrUpdate(vo,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

 
	 
	/**
	 * @api {post} /mis/act/{activityPeriods} 查看活动
	 * @apiSampleRequest /mis/act/fx_001 
	 * @apiGroup ActSettingApi
	 * 
	 * @apiParam {long} actBaseInfo.id  活动基本设置.id
	 * @apiParam {String} actBaseInfo.activityPeriods 活动基本设置.活动编号
	 * @apiParam {String} actBaseInfo.activityName 活动基本设置.活动名称
	 * @apiParam {String} actBaseInfo.activityType 活动基本设置.活动类型(来源于数据字典)
	 * @apiParam {String} actBaseInfo.activityTypeTxt 活动基本设置.活动类型格式化
	 * @apiParam {Integer} actBaseInfo.turnGroup 活动基本设置.是否允许转组 (0:不允许,1：允许)
	 * @apiParam {String} actBaseInfo.turnGroupTxt 活动基本设置.格式化-是否允许转组 (0:不允许,1：允许)
	 * @apiParam {Integer} actBaseInfo.transfer 活动基本设置.是否允许转账 (0:不允许,1：允许)
	 * @apiParam {String} actBaseInfo.transferTxt 活动基本设置.格式化-是否允许转账 (0:不允许,1：允许)
	 * @apiParam {Date} actBaseInfo.startTime 活动基本设置.活动-开始时间
	 * @apiParam {Date} actBaseInfo.endTime 活动基本设置.活动-结束时间
	 * @apiParam {String} actBaseInfo.tagCode 活动基本设置.所属标签
	 * @apiParam {Integer} actBaseInfo.finishDays 活动基本设置.活动完成时间
	 * @apiParam {Integer} actBaseInfo.withGoldDays 活动基本设置.赠金有效期
	 * @apiParam {Integer} actBaseInfo.coinDays 活动基本设置.代币有效期
	 * @apiParam {BigDecimal} actBaseInfo.demoKeepAmount 活动基本设置.demo账号保留余额
	 * @apiParam {Integer} actBaseInfo.maxExchangeCount 活动基本设置.最高兑换次数
	 * @apiParam {String} actBaseInfo.activityUrl 活动基本设置.活动url
	 * @apiParam {String} actBaseInfo.otherMsg 活动基本设置.其他信息或备注
	 * 
	 * @apiParam {long} actCondSetting.id 活动参与条件.id 
	 * @apiParam {String} actCondSetting.activityPeriods 活动参与条件.活动编号
	 * @apiParam {String} actCondSetting.conditionType 活动参与条件.条件类型
	 * @apiParam {String} actCondSetting.accountType 活动参与条件.账户类型
	 * @apiParam {Boolean} actCondSetting.accountOnly 活动参与条件.是否账号唯一 true:唯一，false:可允许不唯 一
	 * @apiParam {Boolean} actCondSetting.allowCancelAccount 活动参与条件.是否允许注销过账号参加活动 true:支持 ,false:
	 * @apiParam {String} actCondSetting.accountLevel 活动参与条件.账号级别
	 * @apiParam {String} actCondSetting.platformsCcy 活动参与条件.平台+币种(GTS2#USD,MT4#CNH)
	 * @apiParam {String} actCondSetting.items 活动参与条件.交易产品
	 * @apiParam {Date} actCondSetting.registerStartTime 活动参与条件.账号注册开始日期
	 * @apiParam {Date} actCondSetting.registerEndTime 活动参与条件.账号注册结束日期
	 * @apiParam {Boolean} actCondSetting.allowWhiteUsers 活动参与条件.只允许白名单用户参加活动 true:是 ,false:否
	 * @apiParam {String} actCondSetting.whiteListFileName 活动参与条件.白名单文件地址
	 * @apiParam {String} actCondSetting.blackListFileName 活动参与条件.黑名单文件地址
	 * @apiParam {Boolean} actCondSetting.allowWithdrawals 活动参与条件.是否允许取款
	 * @apiParam {Date} actCondSetting.activateStartTime 活动参与条件.账号激活日期:开始时间
	 * @apiParam {Date} actCondSetting.activateEndTime 活动参与条件.账号激活日期:结束时间
	 * @apiParam {String} actCondSetting.accountStatus 活动参与条件.账户状态
	 * 
	 * @apiParam {long} actTaskSettings.id 任务.id 
	 * @apiParam {String} actTaskSettings.activityPeriods 任务.活动编号
	 * @apiParam {String} taskItemCode 任务.任务编号
	 * @apiParam {String} taskItemVal 任务.单个任务项值
	 * @apiParam {String} taskParamValUnit 任务.单个任务项值单位
	 * @apiParam {String} taskItemVal2 任务.单个任务项值2
	 * @apiParam {String} taskParamVal2Unit 任务.单位任务项值2单位
	 * @apiParam {BigDecimal} taskItemTime 任务.任务时间
	 * @apiParam {String} taskItemTimeUnit 任务.时间单位
	 * @apiParam {Integer} taskOrder 任务.任务序号
	 * @apiParam {Integer} taskGroup 任务.所属组/层级
	 * @apiParam {Long} parentId 任务.父任务id
	 * @apiParam {String} taskDesc 任务.任务描述
	 * @apiParam {String} taskFullName 任务.任务全名（eg:真实账号激活100元1小时)
	 * @apiParam {List} subTaskSettings 任务.子集任务
	 * @apiParam {List} taskItems 任务.活动下的物品集合
     *    
	 * @apiParam {Long} taskSettingId 奖励物品.任务设置ID
	 * @apiParam {String} itemNumber 奖励物品.物品编号
	 * @apiParam {String} itemName 奖励物品.物品名称
	 * @apiParam {String} itemType 物品类型(来源于数据字典)
	 * @apiParam {String} itemCategory 奖励物品.物品种类
	 * @apiParam {BigDecimal} itemPrice 奖励物品.物品价格或金额
	 * @apiParam {String} itemParamVal 奖励物品.物品参数值，可能是对应的物品金额或百分比或接口公式
	 * @apiParam {String} itemParamValUnit 奖励物品.物品参数单位USD/RMB
	 * @apiParam {Integer} receiveMaxNum 奖励物品.最多领取次数
	 * @apiParam {BigDecimal} receiveMaxMoney 奖励物品.最高领取金额
	 * @apiParam {String} receiveMaxMoneyUnit 奖励物品.最高领取金额单位
	 * @apiParam {BigDecimal} tradeNum 奖励物品.交易手数
	 * @apiParam {String} tradeNumUnit 奖励物品.交易手数单位
	 * @apiParam {BigDecimal} infParamY 奖励物品.接口物品y参数值
	 * @apiParam {BigDecimal} infParamZ 奖励物品.接口物品z参数值
	 * @apiParam {BigDecimal} infParamMax 奖励物品.接品物品最高额度
	 * @apiParam {String} infParamMaxUnit 奖励物品.接口物品最高额度单位 M/G/Month/RMB/USD
	 * @apiParam {BigDecimal} equalValue 奖励物品.等额价值（目前只有：模拟币）
	 * @apiParam {BigDecimal} frequencyTime 奖励物品.频率时间  1天1次
	 * @apiParam {String} frequencyTimeUnit 奖励物品.频率时间要求 单位
	 * @apiParam {Integer} frequencyNum 奖励物品.频率时间内的个数
	 * @apiParam {String} frequencyNumUnit 奖励物品.频率时间内的个数单位
	 * @apiParam {BigDecimal} itemProbability 奖励物品.物品抽中的概率
     * 
	 * @apiParam {String} enableFlag Y启用N禁用
	 * 
	 * @apiParamExample {json} 请求样例 
	 * {actBaseInfo.xxxx:"xxxx",actCondSetting.xxxx:"xxxx",actTaskSettings.xxxx:"xxxx",enableFlag:"Y"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/{activityPeriods}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<ActSettingDetailsVO> findByActivityPeriods(@PathVariable String activityPeriods) {
		try {

			MisRespResult<ActSettingDetailsVO> result = misActSettingDubboService.findByActivityPeriods(activityPeriods,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
	 * @api {post} /mis/act/items/getNewActivityPeriods 系统自动生成的活动编号
	 * @apiSampleRequest /mis/act/items/getNewItemNumber
	 * @apiGroup ActItemsApi
	 * 
     *
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": null
	 *	}
	 */
	@RequestMapping(value = "/getNewActivityPeriods", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<String> getNewActivityPeriods() {
		try {
			MisRespResult<String> result = misActSettingDubboService.getNewActivityPeriods(getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	/**
	 * @api {post} /mis/act/getActConfig/{activityType} 加载活动模板配置信息
	 * @apiSampleRequest /mis/act/getActConfig/{activityType} 
	 * @apiGroup ActSettingApi
	 * 
	 * 
	 * @apiParam {List} actTemplateConfig.baseParams 活动基本信息动态参数
	 * @apiParam {Map} actTemplateConfig.condParams 活动参与条件动态参数
	 * @apiParam {Map} actTemplateConfig.taskConfigs 活动下的任务物品动态配置
	 * @apiParam  Map<String,Object> dynamicList --下拉值及相关单位值
	 * 
	 * @apiParamExample  
	 * {mis/act/getActConfig/rw}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/getActConfig/{activityType}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Map<String,Object>> findActConfigByActivityType(@PathVariable String activityType){
		try {
			MisRespResult<Map<String,Object>> result = misActSettingDubboService.findActConfigByActivityType(activityType,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	 
	/**
	 * @api {post} /mis/act/getActInfoAndConfig/{activityPeriods} 加载活动信息及活动模板信息
	 * @apiSampleRequest /mis/act/getActInfoAndConfig/{activityPeriods} 
	 * @apiGroup ActSettingApi
	 * 
	 * 
	 * @param activityPeriods -活动编号
	 * 
	 * @apiParamExample  
	 * {mis/act/getActConfig/fx001}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/getActInfoAndConfig/{activityPeriods}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public  MisRespResult<Map<String,Object>> findActDetailInfoAndTemplateConfig(@PathVariable String activityPeriods){
		try {
			MisRespResult<Map<String,Object>> result = misActSettingDubboService.findActDetailInfoAndTemplateConfig(null,activityPeriods,getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	 
	/**
	 * @api {post} /mis/act/approve/{ids} 审核通过活动提案
	 * @apiSampleRequest /mis/act/approve/{ids} 
	 * @apiGroup ActSettingApi
	 * 
	 * 
	 * @param ids -活动主健集合
	 * 
	 * @apiParamExample  
	 * {mis/act/approve/10001}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	
	@RequestMapping(value = "/approve/{ids}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> approve(@PathVariable String ids){
		try {
			return misActSettingDubboService.approve(ids,getLoginUser(),getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	 
	/**
	 * @api {post} /mis/act/cancel/{ids} 取消活动提案
	 * @apiSampleRequest /mis/act/cancel/{ids} 
	 * @apiGroup ActSettingApi
	 * 
	 * 
	 * @param ids -活动主健集合
	 * 
	 * @apiParamExample  
	 * {mis/act/cancel/10001}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 * 
	 * @apiSuccessExample {json} 返回样例
	 * {
	 * 	"resultCode": "0",
	 * 	"resultMsg": "请求成功",
	 * 	"data": {...},
	 * 	"extendData": null,
	 * }
	 */
	@RequestMapping(value = "/cancel/{ids}", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public MisRespResult<Void> cancel(@PathVariable String ids){
		try {
			return misActSettingDubboService.cancel(ids,getLoginUser(),getCompanyId());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}
	
	
	/**
     * 功能：查询交易产品列表
     */
    @RequestMapping(value = "/tradeProducts", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public  MisRespResult<List<Gts2symbolDemoRealVO>> tradeProducts(String accountType) {
    	try {
    		Gts2symbolDemoRealVO reqVO =new Gts2symbolDemoRealVO();
    		reqVO.setCompanyId(getCompanyId());
			 if (ActAccountTypeEnum.DEMO.getCode().equals(accountType)) {
                 reqVO.setIsDemo(true);
             } else if (ActAccountTypeEnum.REAL.getCode().equals(accountType)) {
            	 reqVO.setIsDemo(false);
             } else {
            	 reqVO.setIsDemo(null);
             }
			MisRespResult<List<Gts2symbolDemoRealVO>> result = misGts2symbolDemoRealService.findAll(reqVO,reqVO.getCompanyId());
			return MisRespResult.success(result.getData());
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
  	 
       
    }
}