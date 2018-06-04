package com.gwghk.ims.bos.web.controller.activity;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.freeib.framework.common.util.DateUtil;
import com.gwghk.ims.bos.web.common.cache.DictCache;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.ActTypeEnum;
import com.gwghk.ims.bos.web.common.enums.ActivityStatusEnum;
import com.gwghk.ims.bos.web.common.enums.ActivitytProposalStatusEnum;
import com.gwghk.ims.bos.web.common.enums.DictCodeEnum;
import com.gwghk.ims.bos.web.common.enums.EnableFlagEnum;
import com.gwghk.ims.bos.web.common.enums.ErrorCodeEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.response.ErrorCode;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.bos.web.controller.system.BaseController;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActSettingDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.enums.Sequence;
import com.gwghk.ims.common.inf.activity.ActSettingDubboService;
import com.gwghk.ims.common.inf.activity.SequenceDubboService;

/**
 * 摘要：活动提案controller
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月12日
 */
@Controller
@RequestMapping("/ActivitySettingController")
public class ActivitySettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActivitySettingController.class);

	@Autowired
	private ActSettingDubboService actSettingDubboService;
	@Autowired
	private SequenceDubboService sequenceDubboService;
	
	/**
	 * 功能：首页
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(ModelMap map) throws Exception{
		String companyCode = CompanyEnum.getCodeByKey(UserContext.get().getCompanyId());
		map.put("companyCode", companyCode.toUpperCase() + "_");
		map.put("activityStatusEnum", ActivityStatusEnum.getList());
		map.put("activityType", DictCache.getSubDictList(DictCodeEnum.activityType.getLabelKey()));
		return "activity/actSetting";
	}
	
	/**
	 * 功能：分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<ActSettingDTO> pageList(HttpServletRequest request, @ModelAttribute ActSettingDTO dto) {
		try {
			dto.setCompanyId(this.getCompanyId(request));
			PageR<ActSettingDTO> pageR = actSettingDubboService.findPageList(dto).getData();
			PageGrid<ActSettingDTO> pageGrid = this.transPage(request, pageR, dto);
			Date curTime = new Date();
			for(ActSettingDTO record : pageGrid.getRows()){
				record.setActivityTypeText(DictCache.getDictNameByDictCode(record.getActivityType()));
				record.setProposalStatus(DictCache.getDictNameByDictCode(record.getProposalStatus()));
                String activityStatus = record.getEnableFlag();
                if (record.getEndTime() != null && curTime.after(record.getEndTime())) {// 活动结束时间已过期,标记为无效
                    activityStatus = ActivityStatusEnum.INVALID.getLabelKey();
                }
                record.setEnableFlag(ActivityStatusEnum.format(activityStatus));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<ActSettingDTO>();
		}
	}

	/**
	 * 功能：根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public ActSettingDTO findById(Long id) {
		try {
			ActSettingDTO item = actSettingDubboService.findById(id).getData();
			return item;
		} catch (Exception e) {
			logger.error("<----系统出现异常!", e);
			return new ActSettingDTO();
		}
	}

    /**
     * 功能：新增跳转
     */
    @RequestMapping(value = "/add/{actType}", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, ModelMap map, @PathVariable String actType) {
        map.put("actTypeEnum", ActTypeEnum.getList());
        map.put("actType", actType);
        map.put("activityPeriods", CompanyEnum.getCodeByKey(UserContext.get().getCompanyId()).toUpperCase() + "_" + actType + "_" + DateUtil.format2YyyymmddHhmmss());
        return "activity/base/" + actType + "/addActivitySetting";
    }

    /**
     * 功能：更新跳转
     */
    @RequestMapping(value = "/edit/{id}", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, ModelMap map, @PathVariable String id) {
    	try{
            ActSettingDTO dto = actSettingDubboService.findById(Long.parseLong(id)).getData();
            dto.setActivityTypeText(DictCache.getDictNameByDictCode(dto.getActivityType()));
            map.put("actSettingDTO", dto);
            return "activity/base/" + dto.getActivityType() + "/updateActivitySetting";
    	}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return null;
    	}
    }

    /**
     * 功能：查看跳转
     */
    @RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
    public String view(HttpServletRequest request, ModelMap map, @PathVariable String id) {
    	try{
            ActSettingDTO dto = actSettingDubboService.findById(Long.parseLong(id)).getData();
            dto.setActivityTypeText(DictCache.getDictNameByDictCode(dto.getActivityType()));
            map.put("actSettingDTO", dto);
            return "activity/base/" + dto.getActivityType() + "/viewActivitySetting";
    	}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return null;
    	}
    }

    /**
     * 功能：copy跳转
     */
    @RequestMapping(value = "/copy/{id}", method = { RequestMethod.GET })
    public String copy(HttpServletRequest request, ModelMap map, @PathVariable String id) {
    	try{
            ActSettingDTO dto = actSettingDubboService.findById(Long.parseLong(id)).getData();
            dto.setActivityTypeText(DictCache.getDictNameByDictCode(dto.getActivityType()));
            map.put("actSettingDTO", dto);
            map.put("enableFlagEnum", EnableFlagEnum.getList());
            map.put("activityPeriods", CompanyEnum.getCodeByKey(UserContext.get().getCompanyId()).toUpperCase() + "_" + dto.getActivityType() + "_" + DateUtil.format2YyyymmddHhmmss());
            return "activity/base/" + dto.getActivityType() + "/copyActivitySetting";
    	}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return null;
    	}
    }
    
	/**
	 * 功能：新增
	 */
	@RequestMapping(value = "/save/{actType}", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @PathVariable String actType, @ModelAttribute ActSettingDTO entity) {
		try {
			this.setPublicAttr(entity, entity.getId());
			entity.setActivityType(actType);
			entity.setProposalStatus(ActivitytProposalStatusEnum.ActWaitForApprove.getLabelKey());
			entity.setPno(sequenceDubboService.getNextval(Sequence.ActivityProposal.toString()).getData());
			// 验证编号是否重复
			ActSettingDTO dto = actSettingDubboService.findByactivityPeriods(entity.getActivityPeriods(), this.getCompanyId(request)).getData();
			if(null != dto && !dto.getId().equals(entity.getId())){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			actSettingDubboService.saveOrUpdate(entity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
    
	/**
	 * 功能：更新
	 */
	@RequestMapping(value = "/update/{actType}", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode update(HttpServletRequest request, @PathVariable String actType, @ModelAttribute ActSettingDTO entity) {
		try {
			this.setPublicAttr(entity, entity.getId());
			entity.setActivityType(actType);
			entity.setProposalStatus(ActivitytProposalStatusEnum.ActWaitForApprove.getLabelKey());
			entity.setPno(sequenceDubboService.getNextval(Sequence.ActivityProposal.toString()).getData());
			// 验证编号是否重复
			ActSettingDTO dto = actSettingDubboService.findByactivityPeriods(entity.getActivityPeriods(), this.getCompanyId(request)).getData();
			if(null != dto && !dto.getId().equals(entity.getId())){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			actSettingDubboService.saveOrUpdate(entity);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
    
	/**
	 * 功能：取消活动
	 */
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode updateStatus(HttpServletRequest request, Long id) {
		try {
			ActSettingDTO dto = actSettingDubboService.findById(id).getData();
			dto.setProposalStatus(ActivitytProposalStatusEnum.ActHasCanceled.getLabelKey());
			this.setPublicAttr(dto, dto.getId());
			actSettingDubboService.saveOrUpdate(dto);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 功能：根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(String idArray) {
		try {
			actSettingDubboService.deleteByIdArray(idArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

}
