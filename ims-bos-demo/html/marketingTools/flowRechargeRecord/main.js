//基于layui的模块加载
layui.link("/css/keyValue.min.css");
layui.link("/css/actCreate.min.css");
layui.use(['element', 'table', 'searchPanel', 'form'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;

    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
      form.render();
      custFuns.loadData();
      bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-search").bind("click", evtFuns.searchData);
        $(".node-export").bind("click",evtFuns.exportData);
        $(".node-refresh").bind("click",evtFuns.refresh);
        table.on('tool(dataTable)', evtFuns.opts);//表格操作
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/msgTempList.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {field:"code",title:"通道类型",align:"center",width:120},
                    {field:"lang",title:"物品",align:"center",width:100},
                    {field:"tplName",title:"金额",align:"center",width:120},
                    {field:"tplContent",title:"手机号",align:"center",width:100},
                    {field:"ext1",title:"手机归属地",align:"center",width:100},
                    {field:"remark",title:"返回批次号",align:"center",width:150},
                    {field:"enableFlag",title:"返回代号",align:"center",width:200},
                    {field:"comStatus",title:"提交状态",align:"center",width:100},
                    {field:"updateStatus",title:"充值状态",align:"center",width:100},
                    {field:"crateDate",title:"创建时间",align:"center",width:100},
                    {field:"updateDate",title:"最后更新时间",align:"center",width:100},
                    {field:"remark",title:"备注",align:"center",width:100}
                ]],
                done:function(){
                }
            })
        },
        opts:function(type, data){
        
        }

    };
    //事件处理函数
    var evtFuns = {
		exportData:function(){
	         alert("数据导出");
        },
        refresh:function(){
        	alert("刷新数据");
          custFuns.loadData();
        }
    };
    //加载入口函数
    init();
});