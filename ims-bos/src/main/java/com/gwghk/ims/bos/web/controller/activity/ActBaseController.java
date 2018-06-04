package com.gwghk.ims.bos.web.controller.activity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import com.gwghk.freeib.framework.common.util.DateUtil;
import com.gwghk.freeib.framework.common.util.StringUtil;
import com.gwghk.ims.bos.web.common.cache.DictCache;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.enums.MenuUrlEnum;
import com.gwghk.ims.bos.web.controller.system.BaseController;
import com.gwghk.ims.common.enums.CompanyEnum;

/**
 * controller层公共方法
 * 
 * @author gavin.guo
 */
public class ActBaseController extends BaseController {

	/**
	 * 功能：新增时，需添加的默认请求 数据
	 *
	 * @return
	 */
	protected void commonAddInitData(ModelMap map, String activityType) {
		commonMenuData(map);
		String companyCode = CompanyEnum.getCodeByKey(UserContext.get().getCompanyId());
		map.put("defaultActCode", companyCode + "_" + activityType + "_" + DateUtil.format2YyyymmddHhmmss());
		map.put("activityType", activityType);
	}

	/**
	 * 功能：添加默认的返回菜单
	 *
	 * @return
	 */
	protected void commonMenuData(ModelMap map) {
		map.put("mainMenuName", MenuUrlEnum.ACTSETTINGPROPOSAL.getValue());
		map.put("mainMenuCode", MenuUrlEnum.ACTSETTINGPROPOSAL.getValue());
	}

	protected String getStrValue(String[] arr, int index) {
		if (arr != null && arr.length > index) {
			return arr[index];
		}
		return null;
	}

	protected Long getLongValue(String[] arr, int index) {
		if (arr != null && arr.length > index && StringUtils.isNotBlank(arr[index])) {
			return Long.parseLong(arr[index]);
		}
		return null;
	}

	protected Integer getIntegerValue(String[] arr, int index) {
		if (arr != null && arr.length > index && StringUtils.isNotBlank(arr[index])) {
			return Integer.parseInt(arr[index]);
		}
		return null;
	}

	protected Double getDoubleValue(String[] arr, int index) {
		if (arr != null && arr.length > index && StringUtils.isNotBlank(arr[index])) {
			return Double.valueOf(arr[index]);
		}
		return null;
	}

	protected String[] getReqStrArr(String arrStr, String splitStr) {
		if (StringUtils.isNotBlank(arrStr)) {
			return arrStr.split(splitStr);
		}
		return null;
	}

	protected String getDicNameByArrStr(String arrStr) throws Exception{
		if (StringUtils.isNotBlank(arrStr)) {
			StringBuffer aStr = new StringBuffer();
			String[] arr = arrStr.split(",");
			int length = arr.length;
			for (int i = 0; i < length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					aStr.append(DictCache.getDictNameByDictCode(arr[i]));
					if (i < length - 1) {
						aStr.append(",");
					}
				}
			}
			return aStr.toString();
		}
		return "";
	}
}
