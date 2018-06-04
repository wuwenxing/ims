package com.gwghk.ims.mis.gateway.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActSettingDetailsDTO;
import com.gwghk.ims.common.enums.ActAccountTypeEnum;
import com.gwghk.ims.common.enums.ActEnvEnum;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.AuditStatusEnum;
import com.gwghk.ims.common.enums.IssuingStatusEnum;
import com.gwghk.ims.common.excel.POIFormatConfig;
import com.gwghk.ims.common.excel.POIXSSFExcelBuilder;
import com.gwghk.ims.common.inf.external.activity.ActCustomerInfoDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActBlackListDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActCustomerInfoDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisImsPrizeRecordDubboService;
import com.gwghk.ims.common.inf.mis.base.MisImsBlackListDubboService;
import com.gwghk.ims.common.util.BigDecimalUtil;
import com.gwghk.ims.common.util.ExcelUtil;
import com.gwghk.ims.common.util.FileUtil;
import com.gwghk.ims.common.vo.BaseVO;
import com.gwghk.ims.common.vo.activity.ActBlackListVO;
import com.gwghk.ims.common.vo.activity.ActConditionSettingVO;
import com.gwghk.ims.common.vo.activity.ActCustomerInfoVO;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.ims.common.vo.activity.ActSettingDetailsVO;
import com.gwghk.ims.common.vo.activity.ImsPrizeRecordVO;
import com.gwghk.ims.common.vo.activity.PrizeRecordRedoVO;
import com.gwghk.ims.common.vo.base.ImsBlackListVO;
import com.gwghk.ims.mis.gateway.common.DictCache;
import com.gwghk.ims.mis.gateway.enums.DownTemplateEnum;
import com.gwghk.ims.mis.gateway.util.AuthColumnUtil;
import com.gwghk.unify.framework.common.util.StringUtil;
import com.gwghk.unify.framework.file.UploadFileInfo;
import com.gwghk.unify.framework.file.fastdfs.FastDFSManager;
/**
 * 
 * 摘要：物品发放记录
 *
 * @author gordon.xiang
 * @version 1.0
 * @Date 2018年4月10日
 */
@Controller
@RequestMapping("/mis/act/prizerecord")
public class ImsPrizeRecordController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ImsPrizeRecordController.class);

	@Autowired
	private MisImsPrizeRecordDubboService misImsPrizeRecordDubboService;
	
	@Autowired
	private MisActSettingDubboService misActSettingDubboService;
	
	@Autowired
	private MisActBlackListDubboService misActBlackListDubboService;
	
	@Autowired
	private MisActItemsSettingDubboService misActItemsSettingDubboService;
	
	@Autowired
	private MisImsBlackListDubboService misImsBlackListDubboService;
	
	@Autowired
	private MisActCustomerInfoDubboService misActCustomerInfoDubboService;

	/**
	 * @api {post} /mis/act/prizerecord/page 物品发放记录-分页查询
	 * @apiSampleRequest /mis/act/prizerecord/page
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} recordNo 流水号
	 * @apiParam {String} thirdRecordNo 第三方流水号
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} itemType 物品-类型
	 * @apiParam {String} itemCategory 物品-种类
	 * @apiParam {String} itemName 物品-名称
	 * @apiParam {String} givedStatus 发放状态
	 * @apiParam {String} auditStatus 审核状态
	 * @apiParam {String} taskName 任务-名称
	 * @apiParam {String} startCreateDate 开始创建时间
	 * @apiParam {String} endCreateDate 结束创建时间
	 * @apiParam {Boolean} addFromBos 是否后台手动添加(0: 否 1:是)
	 * 
	 * @apiParamExample {json} 请求样例 {actNo:xxxx}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public MisRespResult<List<ImsPrizeRecordVO>> pageList(@ModelAttribute ImsPrizeRecordVO vo,
			HttpServletRequest request) {
		try {
			MisRespResult<PageR<ImsPrizeRecordVO>> result = misImsPrizeRecordDubboService.findPageList(vo,
					getCompanyId());
			return MisRespResult.success(result.getData().getList(), result.getData().getTotal());
		} catch (Exception e) {
			logger.error("查询物品发放记录失败!", e);
			return null;
		}
	}

	/**
	 * @api {get} /mis/act/prizerecord/{actNo}/{id} 物品发放记录-根据ID查询
	 * @apiSampleRequest /mis/act/prizerecord/FX_000011/1
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {Integer} id ID
	 * 
	 * @apiParamExample {json} 请求样例 {id:1}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	@RequestMapping(value = "/{actNo}/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public MisRespResult<ImsPrizeRecordVO> findById(@PathVariable("id") Integer id,
			@PathVariable("actNo") String actNo) {
		return misImsPrizeRecordDubboService.findById(id, actNo, getCompanyId());
	}

	/**
	 * @api {post} /mis/act/prizerecord/save 物品发放记录-保存
	 * @apiSampleRequest /mis/act/prizerecord/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 活动平台
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} taskCode 任务编号
	 * @apiParam {String} itemName 物品名称
	 * @apiParam {String} itemNo 物品编号
	 * @apiParam {String} itemAmount 发放额度
	 * @apiParam {Long} taskItemId 任务物品ID
	 * 
	 * @apiParamExample {json} 请求样例
	 *                  {platform:"GTS",accountNo:"1000021",actName:"测试活动",actNo:"A000001",taskItemId:10}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> save(HttpServletRequest request, @ModelAttribute ImsPrizeRecordVO vo) {
		try {
			if (StringUtil.isNotEmpty(vo.getBatchAddFilePath())) {// 批量新增
				return batchSave(vo);
			} else {
				setPublicAttr(vo, vo.getId());
				MisRespResult<Void> result = misImsPrizeRecordDubboService.saveOrUpdate(vo, getCompanyId());
				return result;
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/prizerecord/batch/save 物品发放记录-批量保存
	 * @apiSampleRequest /mis/act/prizerecord/batch/save
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 活动平台
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} taskCode 任务编号
	 * @apiParam {String} itemName 物品名称
	 * @apiParam {String} itemNo 物品编号
	 * @apiParam {String} itemAmount 发放额度
	 * @apiParam {Long} taskItemId 任务物品ID
	 * @apiParam {File} file 批量添加的账号Excel
	 * 
	 * @apiParamExample {json} 请求样例
	 *                  {platform:"GTS",accountNo:"1000021",actName:"测试活动",actNo:"A000001",taskItemId:10}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	//@RequestMapping(value = "/batch/save", method = { RequestMethod.POST })
	//@ResponseBody
	public MisRespResult<Void> batchSave(ImsPrizeRecordVO vo) {
	
		try {
			setPublicAttr(vo, vo.getId());
            InputStream in = FileUtil.readInputStream(vo.getBatchAddFilePath());
            if (in == null) {
                String msg = String.format("读取不到批量上传的文件：%s", vo.getBatchAddFilePath());
                return MisRespResult.error(msg);
            } else {
                List<List<Object>> accounts = ExcelUtil.readExcelByInputStream(in, vo.getBatchAddFileName());
                if (accounts == null || accounts.isEmpty()) {
                	return MisRespResult.error(MisResultCode.Prize23004);
                } else {
                    Map<String,Object> resultMap = getImportAccount(vo,accounts);
                    @SuppressWarnings("unchecked")
                    List<ImsPrizeRecordVO> accountVOs =(List<ImsPrizeRecordVO>)resultMap.get("accountVOs");
                    Integer successCount = (Integer)resultMap.get("successCount");
                    if(successCount!=null && successCount>0){
                    	//misImsPrizeRecordDubboService.batchSave(vo, accountList, companyId)
                        return misImsPrizeRecordDubboService.saveToBatch(vo, accountVOs,vo.getCompanyId());
                    }else{
                        return  MisRespResult.error(MisResultCode.Error40008);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("<---batchSave->保存失败!", e);
            return MisRespResult.error(MisResultCode.EXCEPTION);
        }

	}
	
	
	/**
     * 校验导入数据
     * @param reqDto
     * @param accounts
     * @return
     */
    private Map<String,Object> getImportAccount(ImsPrizeRecordVO reqVO,List<List<Object>> accounts){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<ImsPrizeRecordVO> accountVOs = new ArrayList<ImsPrizeRecordVO>();
        int successCount = 0;//成功条数
        int failCount = 0;//失败条数
        StringBuffer errorMsg = null;
        
        String actCcy = null;
        String  platforms=null;
        String actAccountType = null;//活动参与账号类型
        ActSettingDetailsVO actSettingDetails=misActSettingDubboService.findByActivityPeriods(reqVO.getActNo(), reqVO.getCompanyId()).getData();
       
        if(actSettingDetails!=null && actSettingDetails.getActCondSetting()!=null){
        	ActConditionSettingVO actConditionSettingVO = (ActConditionSettingVO)actSettingDetails.getActCondSetting();
            //获得活动平台币种CNY,USD
            actCcy = actConditionSettingVO.getCcy();
            //控制平台
            platforms=actConditionSettingVO.getPlatforms();
            actAccountType = actConditionSettingVO.getAccountType();
        }
        List<ActBlackListVO> actBlackList=misActBlackListDubboService.findListByActivityPeriods(reqVO.getActNo(),reqVO.getCompanyId()).getData();//活动黑名单
        
        ActItemsSettingVO actItemsSetting =misActItemsSettingDubboService.findByItemNumber(reqVO.getItemNo(), null, reqVO.getCompanyId()).getData();
        
        for (int i = 0; i < accounts.size(); i++) {
            errorMsg = new StringBuffer();
            List<Object> item = accounts.get(i);
             
            ActCustomerInfoVO customerInfo = null;
            ImsPrizeRecordVO vo = new ImsPrizeRecordVO();
              
            if (item.size() < 1) {
                errorMsg.append("数据为空或都为空字符串!");
            }else{
                String accountNo =  item.get(0).toString().trim();
                String accountTypeStr = item.size()>=2 && item.get(1)!=null?item.get(1).toString().trim():null;
                String platform = item.size()>=3 && item.get(2)!=null ? item.get(2).toString().trim():null;
                String rewardAmountStr = item.size()>=4 && item.get(3)!=null?item.get(3).toString().trim():null;//奖励金额
                String tradeLotsStr = item.size()>=5 &&  item.get(4)!=null? item.get(4).toString().trim():null;//要求手数
                BigDecimal rewardAmount = null;
                BigDecimal tradeLots = null;
                String env = null;
                if (StringUtils.isBlank(accountNo)) {
                    errorMsg.append("账号为空;");
                }
                if (StringUtils.isBlank(platform)) {
                    errorMsg.append("平台为空;");
                }else if(platforms==null || !platforms.contains(platform)){
                    errorMsg.append("活动不支持此账号平台参与;");
                }
                if (StringUtils.isBlank(accountTypeStr)) {
                    errorMsg.append("账号类型为空;");
                }else{
                    // 账户环境
                    if (accountTypeStr != null) {
                        int typeIndex = accountTypeStr.indexOf(":");
                        if (typeIndex > 0) {
                            env = accountTypeStr.substring(0, typeIndex).trim();
                        }else{
                            env = accountTypeStr;
                        }
                    }
                }
                if (StringUtils.isBlank(env) || !(env.equals(ActEnvEnum.REAL.getValue())||env.equals(ActEnvEnum.DEMO.getValue()))) {
                    errorMsg.append("账号类型有误;");
                }else if(env.equals(ActEnvEnum.REAL.getValue()) && (StringUtils.isBlank(actAccountType) 
                    || ActAccountTypeEnum.DEMO.getCode().equals(actAccountType) )){//只允许模拟账号参加
                    errorMsg.append("活动不支持真实账号参与;");
                }else if(env.equals(ActEnvEnum.DEMO.getValue()) && (StringUtils.isBlank(actAccountType) 
                    || ActAccountTypeEnum.REAL.getCode().equals(actAccountType) )){//只允许真实账号参加
                    errorMsg.append("活动不支持模拟账号参与;");
                }
                if(actItemsSetting==null){
                    errorMsg.append("物品不存在");
                }else  if(ActItemTypeEnum.ANALOGCOIN.getCode().equals(actItemsSetting.getItemType()) && i>=50){//模拟币涉及扣除账号模拟币接口，一次性批量导入最多支持50条
                    errorMsg.append("批量导入条数限制,每次最多50条;");
                } else{
                    //代币、模拟币、赠金才要求奖励金额
                 
                    if(ActItemTypeEnum.WITHGOLD.getCode().equals(actItemsSetting.getItemType())
                        ||ActItemTypeEnum.TOKENCOIN.getCode().equals(actItemsSetting.getItemType())
                        ||ActItemTypeEnum.ANALOGCOIN.getCode().equals(actItemsSetting.getItemType())){
                        if (StringUtils.isBlank(rewardAmountStr)) {
                            errorMsg.append("奖励金额为空;");
                        }else{
                            try{ 
                                rewardAmount = BigDecimalUtil.convertDownFromDouble2(rewardAmountStr);
                             }catch(Exception e){
                                 errorMsg.append("奖励金额格式有误;");
                             }
                        }
                    }    
                    if(ActItemTypeEnum.WITHGOLD.getCode().equals(actItemsSetting.getItemType())){
                        if (StringUtils.isBlank(tradeLotsStr)) {
                            errorMsg.append("要求手数为空;");
                        }else{
                            try{
                                tradeLots = BigDecimalUtil.convertDownFromDouble2(tradeLotsStr);
                             }catch(Exception e){
                                 errorMsg.append("要求手数格式有误;");
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(accountNo) && StringUtils.isNotBlank(platform)) {
                        String key = accountNo+"_"+platform;
                        ImsBlackListVO imsBlackListVO=new ImsBlackListVO();
                        imsBlackListVO.setAccountNo(accountNo);
                        ImsBlackListVO groupBlackAccount=misImsBlackListDubboService.findByAccount(imsBlackListVO, reqVO.getCompanyId()).getData();
                        
                        if(groupBlackAccount!=null){
                            errorMsg.append("全局黑名单账号;");
                        }else if(CollectionUtils.isNotEmpty(actBlackList) && actBlackList.contains(key)){
                            errorMsg.append("活动黑名单账号;");
                        }else{
                            if(StringUtils.isNotBlank(env) && env.equals(ActEnvEnum.DEMO.getValue())){
                            	ActCustomerInfoVO  actCustomerInfoVO = misActCustomerInfoDubboService.findActCustomerInfo(accountNo, platform, reqVO.getCompanyId()).getData();
                                if(actCustomerInfoVO==null){
                                    errorMsg.append("此模拟账号系统不存在");
                                }else{
                                    customerInfo = actCustomerInfoVO;
                                }
                            }else if(StringUtils.isNotBlank(env) && env.equals(ActEnvEnum.REAL.getValue())){
                            	ActCustomerInfoVO  actCustomerInfoVO = misActCustomerInfoDubboService.findActCustomerInfo(accountNo, platform, reqVO.getCompanyId()).getData();
                                if(actCustomerInfoVO==null){
                                    errorMsg.append("此真实账号系统不存在");
                                }else{
                                    customerInfo = actCustomerInfoVO;
                                }
                            }
                        }
                    }
                }
                vo.setAccountNo(accountNo);
                vo.setPlatform(platform);
                vo.setEnv(env);
                vo.setItemAmount(rewardAmount);
                vo.setTradeLots(tradeLots);//交易手数
                vo.setRemark(errorMsg.toString());
                if(customerInfo!=null){
                    String curAccountCcy = customerInfo.getAccountCurrency();
                    if("CNH".equals(curAccountCcy)){
                        curAccountCcy="CNY";
                    }
                    if(!actCcy.equals(curAccountCcy)){
                        errorMsg.append("活动不支持此账号币种参与;");
                    }
                     
                    vo.setCustEmail(customerInfo.getEmail());
                    vo.setCustMobile(customerInfo.getMobile());
                    vo.setCustName(customerInfo.getChineseName());
                    vo.setAccountCurrency(customerInfo.getAccountCurrency());
                 
                    
                }               
            }
            if (StringUtils.isBlank(errorMsg.toString())) {
                // 获得当前账号平台的参与信息
            	//TODO 调用参与资格信息接口  该数据来源占未提供  George.li 2018-05.08
                /*ActJoinQualify actJoinQualify = actJoinQualifyService.getActJoinQualifyByAccount(reqVO.getActNo(), reqVO.getAccountNo(), reqVO.getPlatform(), reqVO.getCompanyId());
                if(actJoinQualify!=null && actJoinQualify.getIsSettlement()!=null && actJoinQualify.getIsSettlement()){//已清算
                    errorMsg.append("当前账号在此活动内已清算，不能新增;");
                }*/
            }
            if (StringUtils.isNotBlank(errorMsg.toString())) {
                vo.setRemark(errorMsg.toString());
                //vo.setSuccess(false);
                failCount++;
            }else{
                //vo.setSuccess(true);
                successCount++;
            }
            accountVOs.add(vo);
        }
           
        resultMap.put("accountVOs", accountVOs);
        resultMap.put("successCount", successCount);
        resultMap.put("failCount", failCount);
        return resultMap;
    }
	
	
	/***
	* 
	* 摘要:文件上传   
	* @author George.li  
	* @date 2018年5月7日  
	* @version 1.0   
	* @param file
	* @return
	 */
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<UploadFileInfo> upload(@RequestParam("file") MultipartFile file) {
		try {
			MisRespResult<UploadFileInfo> res = new MisRespResult<>();
			res.setData(FastDFSManager.upload(file.getInputStream(), file.getName()));
			return res;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/prizerecord/batch/{actNo}/{action} 物品发放记录-批量操作
	 * @apiSampleRequest /mis/act/prizerecord/batch/FX_00011/pass
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} action 批量操作的动作 pass审批通过 refuse审批拒绝 outlib出库 issue发放成功
	 *           redo重新发放
	 * @apiParam {String} ids 操作的ID多个用,隔开
	 * 
	 * @apiParamExample {json} 请求样例 {action:"pass",ids:"1,2"}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	@RequestMapping(value = "/batch/{actNo}/{action}", method = { RequestMethod.POST })
	@ResponseBody
	public MisRespResult<Void> batchOperate(HttpServletRequest request, @PathVariable("actNo") String actNo,
			@PathVariable("action") String action, @RequestParam("ids") String ids, String remark) {
		try {
			BaseVO base = new BaseVO();
			setPublicAttr(base, 0);
			MisRespResult<Void> result = misImsPrizeRecordDubboService.batchOperate(ids, action, remark, actNo, base,
					getCompanyId());
			return result;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return MisRespResult.error(MisResultCode.EXCEPTION);
		}
	}

	/**
	 * @api {post} /mis/act/prizerecord/export 物品发放记录-导出Excel
	 * @apiSampleRequest /mis/act/prizerecord/export
	 * @apiGroup ActivityApi
	 * 
	 * @apiParam {String} recordNo 流水号
	 * @apiParam {String} thirdRecordNo 第三方流水号
	 * @apiParam {String} actNo 活动编号
	 * @apiParam {String} actName 活动名称
	 * @apiParam {String} accountNo 客户账号
	 * @apiParam {String} platform 平台
	 * @apiParam {String} itemType 物品-类型
	 * @apiParam {String} itemCategory 物品-种类
	 * @apiParam {String} itemName 物品-名称
	 * @apiParam {String} givedStatus 发放状态
	 * @apiParam {String} auditStatus 审核状态
	 * @apiParam {String} taskName 任务-名称
	 * @apiParam {Boolean} addFromBos 是否后台手动添加(0: 否 1:是)
	 * 
	 * @apiParamExample {json} 请求样例 {actNo:xxxx}
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据
	 *
	 * @apiSuccessExample {json} 返回样例 { "resultCode": "0", "resultMsg": "请求成功",
	 *                    "data": {...}, "extendData": null }
	 */
	@RequestMapping("/export")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ImsPrizeRecordVO vo) {
		try {
			vo.setAlreadySend(true);
			// 1、导出工具类
			POIXSSFExcelBuilder builder = new POIXSSFExcelBuilder(
					this.getClass().getResourceAsStream(DownTemplateEnum.ImsPrizeRecordList.getPath()));
			// 2、需要导出的数据
			MisRespResult<List<ImsPrizeRecordVO>> res = misImsPrizeRecordDubboService.findList(vo, getCompanyId());
			List<ImsPrizeRecordVO> recordList = res.getData();
			warpDataList(request, recordList);
			// 3、格式化与导出
			builder.setList(recordList, new POIFormatConfig<ImsPrizeRecordVO>() {
				@Override
				public Object fromatValue(String fieldName, Object fieldValue, ImsPrizeRecordVO param) {
					if (fieldName != null && "addFromBos".equals(fieldName + "")) {
						if (null != fieldValue) {
							if ("true".equals(fieldValue + "")) {
								return "手动";
							} else {
								return "自动";
							}
						}
					}
					return fieldValue;
				}
			});
			builder.put("totalRecordSize", recordList.size());
			builder.createSXSSFWb();
			// 对response处理使其支持Excel
			ExcelUtil.wrapExcelExportResponse(DownTemplateEnum.ActPrizeRecordList.getValue(), request, response);
			builder.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("exportExcel->导出发放记录失败!", e);
		}
	}

	/**
	 * 功能：发放记录列表字段特殊处理
	 */
	private void warpDataList(HttpServletRequest request, List<ImsPrizeRecordVO> recordList) throws Exception {
		if (recordList != null && !recordList.isEmpty()) {
			Long roleId = getLoginUser().getRoleId();
			for (ImsPrizeRecordVO r : recordList) {
				// 权限的字段过滤
				AuthColumnUtil.encryptColumn(r, roleId);
				r.setGivedStatus(IssuingStatusEnum.format(r.getGivedStatus()));
				if (IssuingStatusEnum.waiting.getValue().equals(r.getGivedStatus())) {
					r.setAuditStatus("-");
				} else {
					r.setAuditStatus(AuditStatusEnum.format(r.getAuditStatus()));
				}
				if (ActItemTypeEnum.WITHGOLD.getCode().equals(r.getItemType())
						|| ActItemTypeEnum.TOKENCOIN.getCode().equals(r.getItemType())
						|| ActItemTypeEnum.ANALOGCOIN.getCode().equals(r.getItemType())) {
					r.setItemCategory("-");
					r.setItemPriceTxt("-");
				} else {
					r.setItemCategory(ActItemCategoryEnum.format(r.getItemCategory()));
					if (r.getItemPrice() != null) {
						r.setItemPriceTxt(BigDecimalUtil.formatComma2BigDecimal(r.getItemPrice()).toString());
						if (StringUtil.isNotEmpty(r.getItemPriceUnit())) {
							r.setItemPriceTxt(
									r.getItemPriceTxt() + DictCache.getDictNameByDictCode(r.getItemPriceUnit()));
						}
					}
				}
				if (r.getItemAmount() != null) {

					String ItemAmountUnit = DictCache.getDictNameByDictCode(r.getItemAmountUnit());
					r.setItemAmountTxt(BigDecimalUtil.toFixedTwoDights(r.getItemAmount()));
					if (StringUtil.isNotEmpty(r.getItemAmountUnit())) {
						r.setItemAmountTxt(r.getItemAmountTxt() + ItemAmountUnit);
					}
				}
				r.setItemType(DictCache.getDictNameByDictCode(r.getItemType()));
			}
		}
	}

}
