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
        params.tradeType="in";
        var isFirst=true;
        ActCom.loadDictData('ActPlatforms','platform');
        custFuns.loadData(params,isFirst);
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
                elem:"#tradeStartTime",
                type:"datetime"
            });
            laydate.render({
                elem:"#tradeEndTime",
                type:"datetime"
            });
            var yesterday=addDate(new Date(),-1);
            var yesterdayStr=formatDateTime(yesterday);
            var todayYear=(new Date()).getFullYear();
            var todayMonth=(new Date()).getMonth();
            var todayDay=(new Date()).getDate();
            var todayTime=(new Date(todayYear,todayMonth,todayDay,'23','59','59')).getTime();//毫秒
            $("#tradeStartTime").val(yesterdayStr)
            $("#tradeEndTime").val(formatDateTime(todayTime));
        },
        loadData:function(params,isFirst){
            if(!isFirst){
                if(params==undefined){
                    params={};
                }
                params=$.extend(params,orderBy);
                 if(params.tradeType!="in"&&params.tradeType!="out"){
                    layer.msg("请选择开/平仓!");
                    return false;
                 }
                 if(params.env!="real"&&params.env!="demo"){
                    layer.msg("请选择账户类型!");
                    return false;
                 }
                 if(params.tradeStartTime==''||params.tradeEndTime==''){
                    layer.msg("创建开始时间和创建结束时间不能为空!");
                    return ;
                 }
                table.render({
                    url: baseUrl+'mis/act/traderecord/page',
                    method:"GET",
                    where:params,
                    cols:[[
                        {type:"numbers"},
                        {field:"accountNo",title:"账号",align:"center"},
                        {field:"platform",title:"平台",align:"center",width:100},
                        {field:"product",title:"产品",align:"center"},
                        {field:"tradeType",title:"开/平仓",align:"center",width:100,templet:function(d){
                            return d.tradeType=="in"?"开仓":"平仓";
                        }},
                        {field:"closeLot",title:"数量",align:"center"},
                        {field:"profit",title:"盈亏",align:"center"},
                        {field:"closeOrderNo",title:"单号",align:"center"},
                        {field:"positionId",title:"持仓Id",align:"center"},
                        {field:"closeTime",title:"平仓时间",align:"center",width:200,templet:function(d){
                            if(d.closeTime!=undefined&&d.closeTime!=null){
                                return formatDateTime(d.closeTime);
                            }
                            return '';
                        }},
                        {field:"openTime",title:"开仓时间",align:"center",width:200,templet:function(d){
                            if(d.openTime!=null&&d.openTime!=undefined){
                               return formatDateTime(d.openTime); 
                            }
                            return "";
                        }},
                        {field:"createDate",title:"创建时间",align:"center",width:200,sort:true}
                    ]]
                })

            }else{
                table.render({
                    data:{},
                    where:params,
                    cols:[[
                        {type:"numbers"},
                        {field:"accountNo",title:"账号",align:"center"},
                        {field:"platform",title:"平台",align:"center"},
                        {field:"product",title:"产品",align:"center"},
                        {field:"tradeType",title:"开/平仓",align:"center",sort:true,templet:function(d){
                            return d.tradeType=="in"?"开仓":"平仓";
                        }},
                        {field:"closeLot",title:"数量",align:"center"},
                        {field:"profit",title:"盈亏",align:"center"},
                        {field:"closeOrderNo",title:"单号",align:"center"},
                        {field:"positionId",title:"持仓Id",align:"center"},
                        {field:"closeTime",title:"平仓时间",align:"center",sort:true},
                        {field:"openTime",title:"开仓时间",align:"center",sort:true},
                        {field:"createDate",title:"创建时间",align:"center",sort:true}
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
                custFuns.loadData(data.field);
                return false;
            });
        }
    };
    //加载入口函数
    init();
});