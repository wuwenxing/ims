//基于layui的模块加载
layui.use(['element','form','layer','table','laydate','searchPanel'],function(element,form,layer,table,laydate){
    element = layui.element;
    form = layui.form;
    layer = layui.layer;
    table = layui.table;
    laydate = layui.laydate;
    searchPanel = layui.searchPanel;
    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
        custFuns.searchEx();
        var params={};
        var isFisrt=true;
        ActCom.loadDictData('ActPlatforms','platform');
        custFuns.loadData(params,isFisrt);
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-search").bind("click",evtFuns.search);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //
            laydate.render({
                elem:"#registerStartTime",
                type:"datetime"
            });
            laydate.render({
                elem:"#registerEndTime",
                type:"datetime",
                done: function(value, date, endDate){
                    $("#registerEndTime").val(addTime(value));
                }
            });
            laydate.render({
                elem:"#activatedStartTime",
                type:"datetime"
            });
            laydate.render({
                elem:"#activatedEndTime",
                type:"datetime",
                done: function(value, date, endDate){
                    $("#activatedEndTime").val(addTime(value));
                }
            });
        },
        loadData:function(params,isFirst){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
            console.log("outquery params:"+JSON.stringify(params));
            if(isFirst){
                    table.render({
                    data: {},
                    cols:[[
                        {type:"numbers"},
                        {type:'checkbox'},
                        {field:"accountNo",title:"账号",align:"center",width:120},
                        {field:"platform",title:"平台",align:"center",width:100},
                        {field:"chineseName",title:"姓名",align:"center",width:120},
                        {field:"accountCurrency",title:"账户币种",align:"center",width:100},
                        {field:"accountLevel",title:"账户级别",align:"center",width:100},
                        {field:"mobile",title:"手机号码",align:"center",width:150},
                        {field:"email",title:"邮箱",align:"center",width:200},
                        {field:"accountStatus",title:"账号状态",align:"center",width:100},
                        {field:"activatedStatus",title:"激活状态",align:"center",width:100},
                        {field:"accountBlack",title:"是否黑名单用户",align:"center",width:100},
                        {field:"cancelBefore",title:"是否注销过",align:"center",width:100},
                        {field:"registerTime",title:"注册时间",align:"center",width:200},
                        {field:"activatedTime",title:"激活时间",align:"center",width:200},
                        {field:"createDate",title:"创建时间",align:"center",sort:true,width:200}
                    ]]
                })
            }else{
                if(params.accountType!="real"&&params.accountType!="demo"){
                    layer.msg("请选择账户类型!");
                    return false;
                }
                if(params.accountNo==""){
	                if(params.registerStartTime==""&&params.registerEndTime==""&&params.activatedStartTime==""&&params.activatedEndTime==""){
	                    layer.msg("请选择注册时间或者激活时间");
	                    return false;
	                }
                }
                table.render({
                url: baseUrl+'/mis/act/customerinfo/page',
                method:"GET",
                where:params,
                cols:[[
                    {type:"numbers"},
                    {type:'checkbox'},
                    {field:"accountNo",title:"账号",align:"center",width:120},
                    {field:"platform",title:"平台",align:"center",width:100},
                    {field:"chineseName",title:"姓名",align:"center",width:120},
                    {field:"accountCurrency",title:"账户币种",align:"center",width:100,templet:function(d){
                        if(d.accountCurrency=='USD'){
                            return '美元';
                        }else if(d.accountCurrency=='CNY'){
                            return '人民币';
                        }else{
                            return d.accountCurrency;
                        }
                    }},
                    {field:"accountLevel",title:"账户级别",align:"center",width:100},
                    {field:"mobile",title:"手机号码",align:"center",width:150},
                    {field:"email",title:"邮箱",align:"center",width:200},
                    {field:"accountStatus",title:"账号状态",align:"center",width:100,templet:function(d){
                        if(d.accountStatus=='A'){
                            return "启用";
                        }else if(d.accountStatus=='S'){
                            return '禁用';
                        }else if(d.accountStatus=='D'){
                            return '注销';
                        }
                    }},
                    {field:"activatedStatus",title:"激活状态",align:"center",width:100,templet:function(d){
                        return d.activatedStatus=='Y'?"激活":"未激活";
                    }},
                    {field:"accountBlack",title:"是否黑名单用户",align:"center",width:100,templet:function(d){
                        if(d.accountBlack=='Y'){
                            return "是";
                        }else if(d.accountBlack=='N'){
                            return '否';
                        }else{
                            return '';
                        }
                    }},
                    {field:"cancelBefore",title:"是否注销过",align:"center",width:100,templet:function(d){
                        return d.cancelBefore=='Y'?'是':'否';
                    }},
                    {field:"registerTime",title:"注册时间",align:"center",width:200,templet:function(d){
                        if(d.registerTime!=undefined&&d.registerTime!=null){
                            return formatDateTime(d.registerTime);
                        }
                        return '';
                    }},
                    {field:"activatedTime",title:"激活时间",align:"center",width:200,templet:function(d){
                        if(d.activatedTime!=undefined&&d.activatedTime!=null){
                            return formatDateTime(d.activatedTime);
                        }
                        return '';
                    }},
                    {field:"createDate",title:"创建时间",align:"center",sort:true,width:200}
                ]]
            })

            }
            
        }
    };
    //事件处理函数
    var evtFuns = {
        refresh:function(){
            $(".node-search").trigger("click");
            return false;
        },
        search:function(){
            form.on("submit(search)",function(data){
                //layer.msg(JSON.stringify(data.field));
                custFuns.loadData(data.field);
                return false;
            });
        }
    };
    //加载入口函数
    init();
});