package com.gwghk.ims.bos.web.controller.basedata;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.EnableFlagEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.bos.web.controller.system.BaseController;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.system.KeyValDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.sys.KeyValDubboService;

@Controller
@RequestMapping("/KeyValController")
public class KeyValController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(KeyValController.class);

	@Autowired
	private KeyValDubboService keyValDubboService;

	/**
	 * 键值维护-跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			request.setAttribute("companyEnum", CompanyEnum.getList());
			request.setAttribute("enableFlagEnum", EnableFlagEnum.getList());
			return "/basedata/keyval/keyVal";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 键值维护-分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<KeyValDTO> pageList(HttpServletRequest request, @ModelAttribute KeyValDTO entity) {
		try {
			entity.setCompanyId(this.getCompanyId(request));
			PageR<KeyValDTO> pageR = keyValDubboService.findPageList(entity).getData();
			PageGrid<KeyValDTO> pageGrid = this.transPage(request, pageR, entity);
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<KeyValDTO>();
		}
	}

	/**
	 * 功能：根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public KeyValDTO findById(Long id) {
		try {
			return keyValDubboService.findById(id).getData();
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new KeyValDTO();
		}
	}

	/**
	 * 功能：新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute KeyValDTO dto) {
		try {
			// 2、新增操作
			this.setPublicAttr(dto, dto.getId());
			keyValDubboService.saveOrUpdate(dto);
			return new ResultCode(ResultCodeEnum.saveSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 根据id删除
	 */
	@RequestMapping(value = "/deleteById", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteById(HttpServletRequest request, @RequestParam String idArray) {
		try {
			keyValDubboService.deleteByIds(idArray);
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
