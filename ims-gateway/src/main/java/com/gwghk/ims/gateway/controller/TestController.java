package com.gwghk.ims.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwghk.ims.common.inf.mis.activity.MisActBlackListDubboService;

@Controller
@RequestMapping("test")
public class TestController extends BaseController {
	//@Autowired
	private MisActBlackListDubboService service;
	
	@RequestMapping("do1")
	public String do1() {
		//ActBlackListVO vo= new ActBlackListVO();
		//vo.setCompanyId(1L);
		//service.findPageList(vo);
		return null;
	}
}
