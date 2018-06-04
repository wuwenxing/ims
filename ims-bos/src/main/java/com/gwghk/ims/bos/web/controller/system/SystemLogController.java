package com.gwghk.ims.bos.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.SystemLogEnum;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.system.SystemLogDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.sys.SystemLogDubboService;

@Controller
@RequestMapping("/SystemLogController")
public class SystemLogController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogController.class);
	
	@Autowired
	private SystemLogDubboService systemLogDubboService;
	
	/**
	 * 日志-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("companyEnum", CompanyEnum.getList());
			request.setAttribute("systemLogEnum", SystemLogEnum.getList());
			return "/system/systemLog/systemLog";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 日志-分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<SystemLogDTO> pageList(HttpServletRequest request,
			@ModelAttribute SystemLogDTO systemLogEntity) {
		try {
			PageR<SystemLogDTO> pageR = systemLogDubboService.findPageList(systemLogEntity).getData();
			PageGrid<SystemLogDTO> pageGrid = this.transPage(request, pageR, systemLogEntity);
			for(SystemLogDTO dto:pageGrid.getRows()){
				dto.setLogType(SystemLogEnum.format(dto.getLogType()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<SystemLogDTO>();
		}
	}
	
}
