package com.gwghk.ims.bos.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.gwghk.ims.bos.web.common.cache.DictCache;
import com.gwghk.ims.bos.web.common.easyui.DictTreeBean;
import com.gwghk.ims.bos.web.common.easyui.DictTreeBeanUtil;
import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.ErrorCodeEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.response.ErrorCode;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.system.SystemDictDTO;
import com.gwghk.ims.common.inf.sys.SystemDictDubboService;

@Controller
@RequestMapping("/SystemDictController")
public class SystemDictController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemDictController.class);

	@Autowired
	private SystemDictDubboService systemDictDubboService;

	/**
	 * 跳转管理页面
	 */
	@RequestMapping(value = "/page", method = { RequestMethod.GET })
	public String page(HttpServletRequest request) {
		try {
			return "/system/dict/dict";
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return "/404";
		}
	}

	/**
	 * 数据字典管理-加载菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loadTreeGrid", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<DictTreeBean> loadTreeGrid(HttpServletRequest request, @ModelAttribute SystemDictDTO dictEntity) {
		try {
			List<DictTreeBean> treeBeanList = new ArrayList<DictTreeBean>();
			PageR<SystemDictDTO> pageR = systemDictDubboService.findPageList(dictEntity, this.getCompanyId(request)).getData();
			List<SystemDictDTO> dictList = pageR.getRows();
			if (dictList != null && dictList.size() > 0) {
				for (SystemDictDTO row : dictList) {
					DictTreeBean treeBean = new DictTreeBean();
					treeBean.setId(row.getDictId().toString());
					treeBean.setDictName(row.getDictName());
					treeBean.setDictCode(row.getDictCode());
					treeBean.setParentDictCode(row.getParentDictCode());
					treeBean.setEnableFlag(row.getEnableFlag());
					treeBean.setState("closed");
					treeBean.setType(row.getDictType());
					treeBean.setSort(Integer.parseInt(row.getOrderCode()+""));
					treeBeanList.add(treeBean);
				}
			}
			List<DictTreeBean> nodeList = DictTreeBeanUtil.formatDictTreeBean(treeBeanList);
			PageGrid<DictTreeBean> pageGrid = new PageGrid<DictTreeBean>();
			pageGrid.setRows(nodeList);
			if(null != pageR && null != pageR.getRows() && pageR.getRows().size() > 0){
				if(StringUtils.isNotBlank(pageR.getTotal()+"")){
					pageGrid.setTotal(Integer.parseInt(pageR.getTotal()+""));
				}
			}
			pageGrid.setPageNumber(dictEntity.getPage() < 0 ? 0:dictEntity.getPage());
			pageGrid.setPageSize(dictEntity.getRows() < 0 ? 0:dictEntity.getRows());
			pageGrid.setSort(StringUtils.isEmpty(dictEntity.getSort()) ? "" : dictEntity.getSort());
			pageGrid.setOrder(StringUtils.isEmpty(dictEntity.getOrder()) ? "" : dictEntity.getOrder());
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<DictTreeBean>();
		}
	}

	/**
	 * 数据字典管理-加载子菜单
	 */
	@RequestMapping(value = "/loadChildTreeGrid/{dictCode}", method = RequestMethod.POST)
	@ResponseBody
	public List<DictTreeBean> loadChildTreeGrid(HttpServletRequest request, @PathVariable String dictCode){
		try {
			List<DictTreeBean> treeBeanList = new ArrayList<DictTreeBean>();
			List<SystemDictDTO> dictList = systemDictDubboService.findListByParentDictCode(dictCode, this.getCompanyId(request)).getData();
			if (dictList != null && dictList.size() > 0) {
				for (SystemDictDTO row : dictList) {
					DictTreeBean treeBean = new DictTreeBean();
					treeBean.setId(row.getDictId().toString());
					treeBean.setDictName(row.getDictName());
					treeBean.setDictCode(row.getDictCode());
					treeBean.setParentDictCode(row.getParentDictCode());
					treeBean.setEnableFlag(row.getEnableFlag());
					treeBean.setState("close");
					treeBean.setType(row.getDictType());
					treeBean.setSort(Integer.parseInt(row.getOrderCode()+""));
					treeBeanList.add(treeBean);
				}
			}
			return DictTreeBeanUtil.formatDictTreeBean(treeBeanList);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<DictTreeBean>();
		}
	}
	
	/**
	 * 根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public SystemDictDTO findById(Long dictId) {
		try {
			return systemDictDubboService.findById(dictId).getData();
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new SystemDictDTO();
		}
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(HttpServletRequest request, @ModelAttribute SystemDictDTO dictEntity) {
		try {
			// 1、校验编号不能重复
			SystemDictDTO dto = systemDictDubboService.findByDictCode(dictEntity.getDictCode(), this.getCompanyId(request)).getData();
			if (null != dto && !dto.getDictId().equals(dictEntity.getDictId())) {
				ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
				resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.codeExists));
				return resultCode;
			}
			// 2、新增操作
			systemDictDubboService.saveOrUpdate(dictEntity);
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
	public ResultCode deleteById(HttpServletRequest request, @RequestParam Long dictId) {
		try {
			// 删除前校验
			SystemDictDTO entity = systemDictDubboService.findById(dictId).getData();
			if(null != entity){
				List<SystemDictDTO> list = systemDictDubboService.findListByParentDictCode(entity.getDictCode(), this.getCompanyId(request)).getData();
				if(null != list && list.size() > 0){
					ResultCode resultCode = new ResultCode(ResultCodeEnum.fail);
					resultCode.addErrorInfo(new ErrorCode(ErrorCodeEnum.childNodeExists));
					return resultCode;
				}
			}
			systemDictDubboService.deleteByIdArray(dictId+"");
			return new ResultCode(ResultCodeEnum.deleteSuccess);
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 根据parentDictCode，
	 * 返回<option value=\"" + dict.getDictCode() + "\" selected=\"selected\"></option>这种html文本
	 */
	@RequestMapping(value = "/getSubDictList/{parentDictCode}/{showPleaseFlag}", method = { RequestMethod.POST })
	@ResponseBody
	public List<SystemDictDTO> getSubDictList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String parentDictCode, @PathVariable String showPleaseFlag) {
		try {
			List<SystemDictDTO> list = DictCache.getSubDictList(parentDictCode);
			if("Y".equals(showPleaseFlag)){
				List<SystemDictDTO> dataList = new ArrayList<SystemDictDTO>();
				SystemDictDTO dictEntity = new SystemDictDTO();
				dictEntity.setDictName("---请选择---");
				dictEntity.setDictCode("");
				dataList.add(dictEntity);
				dataList.addAll(list);
				return dataList;
			}else{
				return list;
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ArrayList<SystemDictDTO>();
		}
	}
	
}
