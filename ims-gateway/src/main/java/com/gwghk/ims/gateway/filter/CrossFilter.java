package com.gwghk.ims.gateway.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 摘要：跨域处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月9日
 */
public class CrossFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse  httpServletResponse = (HttpServletResponse)response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authentication");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");
		chain.doFilter(request, httpServletResponse);
	}

	@Override
	public void destroy() {
	}
}
