//基于layui的模块加载
layui.link("/css/prizeOutRec.min.css");
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
        params.cashType="cashIn";
        ActCom.loadDictData('ActPlatforms','platform');
        var isFirst=true;
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
                elem:"#startApproveDate",
                type:"datetime"
            });
            laydate.render({
                elem:"#endApproveDate",
                type:"datetime",
                done: function(value, date, endDate){
                    $("#endApproveDate").val(addTime(value));
                }
            });
        },
        loadData:function(params,isFirst){
            if(params==undefined){
                params={};
            }
            params=$.extend(params,orderBy);
             var realUrl='';
             if(params.cashType=="cashIn"){
                realUrl=baseUrl+'mis/act/cashinreal/page';
                params.firstDeposit=params.first;
             }else{
                realUrl=baseUrl+'mis/act/cashoutreal/page';
                params.firstWithdraw=params.first;
             }
             console.log("url:"+realUrl+",outquery params:"+JSON.stringify(params));
            table.render({
                url: realUrl,
                where:params,
                method:"GET",
                cellMinWidth:120,
                cols:[
                    [
                    {type:"numbers"},
                    {field:"pno",title:"订单号",width:200},
                    {field:"platform",title:"平台"},
                    {field:"accountNo",title:"账号"},
                    {field:"transAmount",title:"入金/出金金额",width:200},
                    {field:"approveDate",title:"时间",width:160,sort:true,templet:function(d){
                        return formatDateTime(d.approveDate);
                    }},
                    {field:"firstDeposit",title:"是否首次入金",templet:function(d){
                        if(d.firstDeposit=="Y"){
                            return "是"
                        }else if(d.firstDeposit=='N'){
                            return "否";
                        }else{
                            return "";
                        }
                    }},
                    {field:"firstWithdraw",title:"是否首次出金",templet:function(d){
                        if(d.firstWithdraw=="Y"){
                            return "是"
                        }else if(d.firstWithdraw=='N'){
                            return "否";
                        }else{
                            return "";
                        }
                    }},
                    {field:"source",title:"来源",templet:function(d){
                        return d.source=="0"?"GTS2库":"旧库";
                    }}
                ]]
            })
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