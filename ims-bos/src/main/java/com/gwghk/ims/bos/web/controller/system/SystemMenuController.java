package com.gwghk.ims.bos.web.controller.system;

import java.util.ArrayList;
import java.util.List;

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

import com.gwghk.ims.bos.web.common.cache.MenuCache;
import com.gwghk.ims.bos.web.common.easyui.TreeBean;
import com.gwghk.ims.bos.web.common.enums.ErrorCodeEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.response.ErrorCode;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.common.dto.system.SystemMenuDTO;
import com.gwghk.ims.common.inf.sys.SystemMenuDubboService;

@Controller
@RequestMapping("/SystemMenuController")
public class SystemMenuController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemMenuController.class);

	@Autowired
	private SystemMenuDubboService systemMenuDubboService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page() {
		return "/system/menu/menu";
	}

	/**
	 * tree
	 * @return
	 */
	@RequestMapping(value = "/loadMenuTree", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeBean> loadMenuTree(HttpServletRequest request) {
		try{
			boolean hasTopTag = true;// 是否包含顶部页签
			boolean hasMenu = true;//是否包含菜单
			boolean hasFun = true;// 是否包含功能
			String topTagMenuCode = null;// 查询指定顶部页签节点下的菜单,为空不过滤
			return MenuCache.getMenuTreeJson(hasTopTag, hasMenu, hasFun, topTagMenuCode);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<TreeBean>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemMenuDTO findById(Long menuId) {
		try{
			return systemMenuDubboService.findById(menuId).getData();
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemMenuDTO();
		}
	}
	
	/**
	 * 根据code查询
	 */
	@RequestMapping(value = "/findByCode", method = { RequestMethod.POST })
	@ResponseBody
	public SystemMenuDTO findByCode(HttpServletRequest request, String menuCode) {
		try{
			return systemMenuDubboService.findByMenuCode(menuCode, this.getCompanyId(request)).getData();
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemMenuDTO();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute SystemMenuDTO menuEntity) {
		try{
			// 1、校验编号不能重复
			SystemMenuDTO dto = systemMenuDubboService.findByMenuCode(menuEntity.getMenuCode(), this.getCompanyId(request)).getData();
			if (dto != null && !dto.getMenuId().equals(menuEntity.getMenuId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			// 2、新增操作
			this.setPublicAttr(menuEntity, menuEntity.getMenuId());
			systemMenuDubboService.saveOrUpdate(menuEntity);
			MenuCache.refresh();
			return new ResultCode(ResultCodeEnum.saveSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
	/**
	 * 根据menuCode删除
	 */
	@RequestMapping(value = "/deleteByMenuCode", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode deleteByMenuCode(HttpServletRequest request, @RequestParam String menuCode) {
		try{
			// 删除前校验
			List<SystemMenuDTO> menuList = systemMenuDubboService.findByParentMenuCode(menuCode, this.getCompanyId(request)).getData();
			if(null != menuList && menuList.size() > 0){
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.childNodeExists));
				return resultCode;
			}
			systemMenuDubboService.deleteByMenuCode(menuCode, this.getCompanyId(request));
			MenuCache.refresh();
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		}catch(Exception e){
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}
	
}
