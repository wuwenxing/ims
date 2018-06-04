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
        custFuns.loadData();
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
            //活动名称
            $.ajax({
                url:"/api/listApprovedAct.json",
                success:function(res){
                    var actName = "<option>--请选择--</option>";
                    var actNum = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        actName+="<option value="+res[i].data+">"+res[i].data+"</option>";
                        actNum +="<option value="+res[i].extName+">"+res[i].extName+"</option>";
                    }
                    $("#actName").html(actName);
                    $("#actNum").html(actNum);
                    form.render("select");
                }
            });
            //物品种类
            $.ajax({
                url:"/api/listActItem.json",
                success:function(res){
                    var prizeName = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        prizeName+="<option>"+res[i].name+"</option>";
                    }
                    $("#prizeName").html(prizeName);
                    form.render("select");
                }
            });
            //
            laydate.render({
                elem:"#tradeStartTime",
                type:"date"
            });
            laydate.render({
                elem:"#tradeEndTime",
                type:"date"
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/trade.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {field:"accountNo",title:"账号",align:"center"},
                    {field:"platform",title:"平台",align:"center"},
                    {field:"product",title:"产品",align:"center"},
                    {field:"tradeType",title:"开/平仓",align:"center",sort:true},
                    {field:"closeLot",title:"手数",align:"center"},
                    {field:"profit",title:"盈亏",align:"center"},
                    {field:"closeOrderNo",title:"单号",align:"center"},
                    {field:"positionId",title:"持仓Id",align:"center"},
                    {field:"closeTime",title:"平仓时间",align:"center",sort:true},
                    {field:"openTime",title:"开仓时间",align:"center",sort:true},
                    {field:"createDate",title:"创建时间",align:"center",sort:true}
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        refresh:function(){
            custFuns.loadData();
            return false;
        },
        search:function(){
            form.on("submit(search)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
        }
    };
    //加载入口函数
    init();
});