package com.gwghk.ims.gateway.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.gwghk.ims.common.dto.BaseDTO;

/**
 * 摘要：基础controller
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年12月9日
 */
@Controller
public class BaseController {

    private Map<String,String> accessorInfo=new HashMap<String,String>();
    
    /**
     * ModelAttribute的作用：表示请求该类的每个Controller前都会首先执行它
     */
    @ModelAttribute
    public void initHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//处理ie跨域问题
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
		response.setHeader("X-Powered-By", "3.2.1");
		response.setHeader("P3P", "CP=CAO PSA OUR");
        
		//初始化基本信息
		accessorInfo.put("companyId",request.getParameter("companyId"));
		accessorInfo.put("accessIp",request.getRemoteAddr());
		accessorInfo.put("user",request.getParameter("user"));
        
    }
    
    protected Long getCompanyId() {
    	if(accessorInfo.get("companyId")==null)
    		return null;
    	
    	return Long.parseLong(accessorInfo.get("companyId"));
    }
	
	/**
	 * 新增或更新时，设置公共的属性
	 * @param obj
	 * @param objId 对象为空的标示
	 * @return
	 */
	protected void setPublicAttr(BaseDTO obj, Object objId) {
		if(null == objId){
			// 新增时，设置公共的属性
			Date date = new Date();
			obj.setCreateUser(accessorInfo.get("user"));
			obj.setCreateDate(date);
			obj.setCreateIp(accessorInfo.get("accessIp"));
			obj.setUpdateUser(accessorInfo.get("user"));
			obj.setUpdateDate(date);
			obj.setUpdateIp(accessorInfo.get("accessIp"));
			obj.setCompanyId(Long.parseLong(accessorInfo.get("companyId")));
			obj.setVersionNo(0);
			obj.setEnableFlag("Y");
			obj.setDeleteFlag("N");
		}else{
			// 更新时，设置公共的属性
			Date date = new Date();
			obj.setUpdateUser(accessorInfo.get("user"));
			obj.setUpdateDate(date);
			obj.setUpdateIp(accessorInfo.get("accessIp"));
			obj.setCompanyId(Long.parseLong(accessorInfo.get("companyId")));
		}
	}
	
}
