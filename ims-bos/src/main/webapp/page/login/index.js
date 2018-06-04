$(function() {

	// li 点击动态样式
	$(".nav-container li").click(function() {
		 $(this).addClass("active").siblings().removeClass("active");
	});
	$(".left-side .nav li").click(function() {
		 $(this).addClass("active").siblings().removeClass("active");
	});
	
	// 初始化
	index.init();
	
	// 窗口大小监听事件
	$(window).resize(function(){
		$('#menu_tabs').tabs('resize');
	});
});

var index = {
	menuTabsIndex : 0,
	/**
	 * 初始化
	 */
	init:function(){
		
	},
	/**
	 * 自适应iframe的高度
	 */
	iFrameHeight : function() {
		var tab = $('#menu_tabs').tabs('getSelected');
		var ifm = tab.find('iframe')[0];
		var oldHeight = ifm.height;
		if (ifm.contentDocument && ifm.contentDocument.body.offsetHeight){
			var height = ifm.contentDocument.body.offsetHeight;
			if(height < 768){
				height = 768;
			}else if(oldHeight == height){
				// 不处理
			}else{
				ifm.height = height + 20;
			}
		}else if(ifm.Document && ifm.Document.body.scrollHeight){
			var height = ifm.Document.body.scrollHeight
			if(height < 768){
				height = 768;
			}else if(oldHeight == height){
				// 不处理
			}else{
				ifm.height = height + 20;
			}
		}
	},
	/**
	 * 添加tab选项卡或更新选项卡
	 */
	addOrUpdateTabs : function(title, url) {
		$("#welcome").hide();
		var flag = $('#menu_tabs').tabs('exists', title);
		if (flag) {
			$('#menu_tabs').tabs('select', title);
			if('' != url && null != url && 'undefined' != url){
				url = BASE_PATH + url;
			}
			index.refreshTabs(url);
		} else {
			index.menuTabsIndex++;
			var tabsDivId = "tabsContent" + index.menuTabsIndex;
			$('#menu_tabs').tabs('add', {
				tools:[{
			        iconCls:'icon-mini-refresh',
			        handler:function(){
			        	index.refreshTabs();
			        }
			    }],
				title : title,
				content : "<div id=\""+tabsDivId+"\"></div>",
				border : false,
				closable : true
			});
			$('#menu_tabs').tabs('select', title);
			$("#menu_tabs div .panel:last .panel-body").css({'overflow':'hidden','position':'relative'});
			$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px; \"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\" />正在处理，请稍待。。。</div>");
			var iframe = document.createElement("iframe");
			iframe.src = BASE_PATH + url;
			iframe.name = tabsDivId;
			iframe.frameBorder = 0;
			iframe.scrolling = "no";
			iframe.height = '768';
			iframe.width = '100%';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				});
			} else {
				iframe.onload = function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				};
			}
			$("#" + tabsDivId).append(iframe);
		}
		// 返回页面顶部
		scroll(0,0);
	},
	/**
	 * 添加tab选项卡
	 */
	addTabs : function(title, url) {
		$("#welcome").hide();
		var flag = $('#menu_tabs').tabs('exists', title);
		if (flag) {
			$('#menu_tabs').tabs('select', title);
		} else {
			index.menuTabsIndex++;
			var tabsDivId = "tabsContent" + index.menuTabsIndex;
			$('#menu_tabs').tabs('add', {
				tools:[{
			        iconCls:'icon-mini-refresh',
			        handler:function(){
			        	index.refreshTabs();
			        }
			    }],
				title : title,
				content : "<div id=\""+tabsDivId+"\"></div>",
				border : false,
				closable : true
			});
			$('#menu_tabs').tabs('select', title);
			$("#menu_tabs div .panel:last .panel-body").css({'overflow':'hidden','position':'relative'});
			$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px; \"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\" />正在处理，请稍待。。。</div>");
			var iframe = document.createElement("iframe");
			iframe.src = BASE_PATH + url;
			iframe.name = tabsDivId;
			iframe.frameBorder = 0;
			iframe.scrolling = "no";
			iframe.height = '768';
			iframe.width = '100%';
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				});
			} else {
				iframe.onload = function() {
					$("#" + tabsDivId + " .loadingDiv").remove();
					index.iFrameHeight();
				};
			}
			$("#" + tabsDivId).append(iframe);
		}
		// 返回页面顶部
		scroll(0,0);
	},
	/**
	 * 刷新Tabs
	 */
	refreshTabs : function(url){
		$("#welcome").hide();
		var tab = $('#menu_tabs').tabs('getSelected');
		var refreshIfram = tab.find('iframe')[0];
		var src = refreshIfram.src;
		if('' != url && null != url && 'undefined' != url){
			src = url;
		}
		var tabsDivId = refreshIfram.name;
		// ie6下会报错
		try{
			refreshIfram.remove();
		}catch(err){
			refreshIfram.removeNode(true);
		}

		$("#" + tabsDivId).append("<div class=\"loadingDiv\" style=\"padding:15px;\"><img src = '"+ BASE_PATH +"img/icons/loading.gif' style=\"vertical-align: middle; padding-right: 5px;\"/>正在处理，请稍待。。。</div>");
		var iframe = document.createElement("iframe");
		iframe.src = src;
		iframe.name = tabsDivId;
		iframe.frameBorder = 0;
		iframe.scrolling = "no";
		iframe.height = '768';
		iframe.width = '100%';
		if (iframe.attachEvent) {
			iframe.attachEvent("onload", function() {
				$("#" + tabsDivId + " .loadingDiv").remove();
				index.iFrameHeight();
			});
		} else {
			iframe.onload = function() {
				$("#" + tabsDivId + " .loadingDiv").remove();
				index.iFrameHeight();
			};
		}
		$("#" + tabsDivId).append(iframe);
	},
	/**
	 * 关闭当前新增框，并刷新指定Tabs
	 */
	closeOrUpdateTabs : function(title){
		index.closeTabs();
		index.addOrUpdateTabs(title);
	},
	/**
	 * 关闭指定Tabs
	 */
	closeTabs : function(title){
		$('#menu_tabs').tabs('close', title);
		if(title === undefined || title == '' || title == null){
			var tab = $('#menu_tabs').tabs('getSelected');
			$('#menu_tabs').tabs("close", $('#menu_tabs').tabs('getTabIndex',tab));
		}else{
			$('#menu_tabs').tabs('close', title);
		}
	},
	/**
	 * tabs关闭事件
	 */
	closeTabsEvent: function(){
		if($('#menu_tabs').tabs('tabs').length == 0){
			$("#welcome").show();
		}
	},
	/**
	 * 左侧li折叠以及展开
	 */
	operLi: function(){
		$("li.leftMenu ul").removeClass("in").attr("aria-expanded", "false");
	}
	,
	/**
	 * 
	 */
	showProcess: function(){
		$.messager.progress();
	},
	/**
	 * 
	 */
	closeProcess: function(){
		$.messager.progress("close");
	}
	
}
