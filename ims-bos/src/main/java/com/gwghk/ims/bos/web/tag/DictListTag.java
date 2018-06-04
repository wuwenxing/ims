package com.gwghk.ims.bos.web.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gwghk.ims.bos.web.common.cache.DictCache;
import com.gwghk.ims.common.dto.system.SystemDictDTO;

/**
 * 摘要：数据字典select选择标签
 */
public class DictListTag extends TagSupport {

	private static final long serialVersionUID = -4934685405732696502L;

	private String defaultVal; // 默认值
	private String parentDictCode; // 默认值
	private List<SystemDictDTO> dataList; // select数据列表
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(StringUtils.isNotBlank(parentDictCode)){
				dataList = DictCache.getSubDictList(parentDictCode);
			}
			out.print(end().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(defaultVal)) {
			defaultVal = ""; // 默认值为""
		}
		if (dataList != null && dataList.size() > 0) {
			for (SystemDictDTO dict : dataList) {
				if (dict.getDictCode().equals(this.defaultVal)) {
					sb.append(" <option value=\"" + dict.getDictCode() + "\" selected=\"selected\">");
				} else {
					sb.append(" <option value=\"" + dict.getDictCode() + "\">");
				}
				sb.append(dict.getDictName());
				sb.append(" </option>");
			}
		}
		return sb;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public List<SystemDictDTO> getDataList() {
		return dataList;
	}

	public void setDataList(List<SystemDictDTO> dataList) {
		this.dataList = dataList;
	}

	public String getParentDictCode() {
		return parentDictCode;
	}

	public void setParentDictCode(String parentDictCode) {
		this.parentDictCode = parentDictCode;
	}

}
