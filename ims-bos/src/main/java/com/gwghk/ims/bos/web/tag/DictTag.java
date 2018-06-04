package com.gwghk.ims.bos.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gwghk.ims.common.dto.system.SystemDictDTO;

/**
 * 摘要：数据字典翻译
 */
public class DictTag extends TagSupport {

	private static final long serialVersionUID = -9185465985775331288L;

	private String value;
	private String defaultVal; // 默认值,如果没有给值，则默认值为""
	private List<SystemDictDTO> dataList; // 翻译对应列表

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(defaultVal)) {
			defaultVal = ""; // 默认值为""
		}

		SystemDictDTO boDict = null;
		if (dataList != null && dataList.size() > 0) {
			for (SystemDictDTO dict : dataList) {
				if (dict.getDictCode().equals(value)) {
					boDict = dict;
					break;
				}
			}
		}

		if (boDict != null) {
			sb.append(boDict.getDictName());
		} else {
			sb.append(defaultVal);
		}
		return sb;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}
