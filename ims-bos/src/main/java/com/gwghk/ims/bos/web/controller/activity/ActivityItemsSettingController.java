package com.gwghk.ims.bos.web.controller.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.freeib.framework.common.util.DateUtil;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.easyui.PageGrid;
import com.gwghk.ims.bos.web.common.enums.ActGiftTypeEnum;
import com.gwghk.ims.bos.web.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.bos.web.common.enums.EnableFlagEnum;
import com.gwghk.ims.bos.web.common.enums.ItemsUnitEnum;
import com.gwghk.ims.bos.web.common.enums.ResultCodeEnum;
import com.gwghk.ims.bos.web.common.response.ComboboxDto;
import com.gwghk.ims.bos.web.common.response.ResultCode;
import com.gwghk.ims.bos.web.controller.system.BaseController;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.dto.activity.ActItemsSettingDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.activity.ActItemsSettingDubboService;

/**
 * 摘要：活动物品controller
 * 
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年5月12日
 */
@Controller
@RequestMapping("/ActivityItemsSettingController")
public class ActivityItemsSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ActivityItemsSettingController.class);

	@Autowired
	private ActItemsSettingDubboService actItemsSettingDubboService;

	/**
	 * 功能：首页
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(ModelMap map) {
		String companyCode = CompanyEnum.getCodeByKey(UserContext.get().getCompanyId());
		map.put("companyCode", companyCode.toUpperCase() + "_");
		map.put("actGitNumberPrefix", companyCode.toUpperCase() + "_" + DateUtil.format2YyyymmddHhmmss());
		map.put("actGiftTypeEnum", ActGiftTypeEnum.getList());
		map.put("actItemCategoryEnum", ActItemCategoryEnum.getList());
		map.put("enableFlagEnum", EnableFlagEnum.getList());
		map.put("itemsUnitEnum_1", ItemsUnitEnum.getList_1());
		map.put("itemsUnitEnum_2", ItemsUnitEnum.RMB);
		map.put("itemsUnitEnum_3", ItemsUnitEnum.Month);
		return "activity/actItemsSetting";
	}

	/**
	 * 功能：分页查询
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.POST })
	@ResponseBody
	public PageGrid<ActItemsSettingDTO> pageList(HttpServletRequest request, @ModelAttribute ActItemsSettingDTO dto) {
		try {
			dto.setCompanyId(this.getCompanyId(request));
			PageR<ActItemsSettingDTO> pageR = actItemsSettingDubboService.findPageList(dto).getData();
			PageGrid<ActItemsSettingDTO> pageGrid = this.transPage(request, pageR, dto);
			for(ActItemsSettingDTO record : pageGrid.getRows()){
				record.setGiftType(ActGiftTypeEnum.format(record.getGiftType()));
				record.setItemCategory(ActItemCategoryEnum.format(record.getItemCategory()));
				record.setEnableFlag(EnableFlagEnum.format(record.getEnableFlag()));
			}
			return pageGrid;
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new PageGrid<ActItemsSettingDTO>();
		}
	}

	/**
	 * 功能：查询活动下物品combobox列表
	 */
	@RequestMapping(value = "/combobox", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<ComboboxDto> combobox(@ModelAttribute ActItemsSettingDTO dto) {
		List<ComboboxDto> comboboxList = new ArrayList<ComboboxDto>();
		try {
			ComboboxDto comboboxDto = null;
			comboboxDto = new ComboboxDto();
			comboboxDto.setId("-1");
			comboboxDto.setName("请选择");
			comboboxList.add(comboboxDto);
			dto.setCompanyId(UserContext.get().getCompanyId());
			List<ActItemsSettingDTO> actItemsSettingList = actItemsSettingDubboService.findList(dto).getData();
			if (CollectionUtils.isNotEmpty(actItemsSettingList)) {
				for (ActItemsSettingDTO entity : actItemsSettingList) {
					comboboxDto = new ComboboxDto();
					comboboxDto.setId(entity.getGiftNumber());
					comboboxDto.setName(entity.getGiftName());
					comboboxList.add(comboboxDto);
				}
			}
		} catch (Exception e) {
			logger.error("<----deleteById->删除失败!", e);
			return new ArrayList<ComboboxDto>();
		}
		return comboboxList;
	}

	/**
	 * 功能：根据id查询
	 */
	@RequestMapping(value = "/findById", method = { RequestMethod.POST })
	@ResponseBody
	public ActItemsSettingDTO findById(Long id) {
		try {
			ActItemsSettingDTO item = actItemsSettingDubboService.findById(id).getData();
			// 虚拟物品，检验是否有发放记录
			// if (item.getGiftType().equals(ActItemsType.VIRTUAL.getCode())
			// || item.getGiftType().equals(ActItemsType.INTERFACE.getCode())) {
			// int prizeNum =
			// prizeRecordDubboService.hasPrizeRecord(item.getGiftNumber());
			// if (prizeNum > 0) {
			// item.setHasPrizeRecord(true);
			// }
			// }
			return item;
		} catch (Exception e) {
			logger.error("<----系统出现异常!", e);
			return new ActItemsSettingDTO();
		}
	}

	/**
	 * 功能：根据物品编号查询
	 */
	@RequestMapping(value = "/findByGiftNumber", method = { RequestMethod.POST })
	@ResponseBody
	public ActItemsSettingDTO findByGiftNumber(String giftNumber) {
		try {
			return actItemsSettingDubboService.findByGiftNumber(giftNumber).getData();
		} catch (Exception e) {
			logger.error("<----系统出现异常!", e);
			return new ActItemsSettingDTO();
		}
	}

	/**
	 * 功能：根据物品名称查询
	 */
	@RequestMapping(value = "/findByGiftName", method = { RequestMethod.POST })
	@ResponseBody
	public List<ActItemsSettingDTO> findByGiftName(String giftName) {
		List<ActItemsSettingDTO> list = new ArrayList<ActItemsSettingDTO>();
		try {
			list = actItemsSettingDubboService.findByGiftName(giftName).getData();
		} catch (Exception e) {
			logger.error("<----系统出现异常!", e);
		}
		return list;
	}

	/**
	 * 功能：新增
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultCode save(@ModelAttribute ActItemsSettingDTO entity) {
		try {
			if (ActGiftTypeEnum.VIRTUAL.getLabelKey().equals(entity.getGiftType())) {
				if ("mobile_charge".equals(entity.getItemCategory())) {
					entity.setItemUnit("RMB");
				} else if ("member_vip".equals(entity.getItemCategory())) {
					entity.setItemUnit("Month");
				}
			} else if (ActGiftTypeEnum.ANALOGCOIN.getLabelKey().equals(entity.getGiftType())
					|| ActGiftTypeEnum.TOKENCOIN.getLabelKey().equals(entity.getGiftType())
					|| ActGiftTypeEnum.WITHGOLD.getLabelKey().equals(entity.getGiftType())) {
				entity.setItemUnit("USD");
			}
			this.setPublicAttr(entity, entity.getId());
			actItemsSettingDubboService.saveOrUpdate(entity);
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
			// 删除前检验该物品是否正在使用
			if (actItemsSettingDubboService.checkItemInUse(idArray).getData()) {
				actItemsSettingDubboService.deleteByIdArray(idArray);
				return new ResultCode(ResultCodeEnum.deleteSuccess);
			} else {
				logger.error("<----deleteById->删除失败!活动物品正在使用！");
				return new ResultCode(ResultCodeEnum.fail.getLabelKey(), "该活动物品正在使用！");
			}
		} catch (Exception e) {
			logger.error("系统出现异常:" + e.getMessage(), e);
			return new ResultCode(ResultCodeEnum.exception.getLabelKey(), "系统出现异常:" + e.getMessage());
		}
	}

	/**
	 * 功能：获得所有已启用的物品
	 */
	@RequestMapping(value = "/listActItems/enable", method = { RequestMethod.POST, RequestMethod.POST })
	@ResponseBody
	public List<ComboboxDto> listEnableActTasks() {
		List<ComboboxDto> comboboxList = new ArrayList<ComboboxDto>();
		try {
			return listActTasks(true);
		} catch (Exception e) {
			logger.error("<----listEnableActTasks->查询失败!", e);
		}
		return comboboxList;
	}

	/**
	 * 功能：获得所有物品(已启用和未启用的)
	 */
	@RequestMapping(value = "/listActItems", method = { RequestMethod.POST })
	@ResponseBody
	public List<ComboboxDto> listActTasks() {
		List<ComboboxDto> comboboxList = new ArrayList<ComboboxDto>();
		try {
			return listActTasks(null);
		} catch (Exception e) {
			logger.error("<----listActTasks->查询失败!", e);
		}
		return comboboxList;
	}

	/**
	 * 获得物品
	 * 
	 * @param enable
	 *            --是否启用的
	 * @return
	 */
	private List<ComboboxDto> listActTasks(Boolean enable) {
		List<ComboboxDto> comboboxList = new ArrayList<ComboboxDto>();
		ComboboxDto comboboxDto = null;
		ActItemsSettingDTO reqDto = new ActItemsSettingDTO();
		if (enable != null) {
			reqDto.setEnableFlag(enable ? "Y" : "N");
		} else {
			reqDto.setEnableFlag(null);
		}
		reqDto.setDeleteFlag("N");
		reqDto.setCompanyId(UserContext.get().getCompanyId());
		reqDto.setOrder("create_date");// 默认按创建时间排序
		List<ActItemsSettingDTO> actItemsList = actItemsSettingDubboService.findList(reqDto).getData();
		if (CollectionUtils.isNotEmpty(actItemsList)) {
			for (ActItemsSettingDTO entity : actItemsList) {
				comboboxDto = new ComboboxDto();
				comboboxDto.setId(entity.getId().toString());
				comboboxDto.setName(entity.getGiftName());
				comboboxList.add(comboboxDto);

			}
		}
		return comboboxList;
	}

}
