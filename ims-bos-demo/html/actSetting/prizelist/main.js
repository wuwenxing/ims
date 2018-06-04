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

    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //活动编号
            $.ajax({
                url:"/api/getPeriodList.json",
                success:function(res){
                    var actNum = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        actNum +="<option value="+res[i]+">"+res[i]+"</option>";
                    }
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
                elem:"#startDate",
                type:"date"
            });
            laydate.render({
                elem:"#endDate",
                type:"date"
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/giftlist.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {field:"recordNumber",title:"流水号",align:"center",sort:true,width:250},
                    {field:"guestPlatForm",title:"活动平台",sort:true,width:100},
                    {field:"activityName",title:"活动名称",align:"center",sort:true,width:250},
                    {field:"taskTitle",title:"任务名称",align:"center",sort:true,width:250},
                    {field:"giftName",title:"物品名称",align:"center",sort:true,width:200},
                    {field:"giftType",title:"物品类型",align:"center",sort:true,width:150},
                    {field:"giftCategory",title:"物品种类",align:"center",sort:true,width:100},
                    {field:"giftAmountTxt",title:"发放额度",align:"center",width:100},
                    {field:"giftPriceTxt",title:"物品价格",align:"center",width:100},
                    {field:"accountNo",title:"客户账号",align:"center",sort:true,width:250},
                    {field:"guestName",title:"客户姓名",align:"center",sort:true,width:100},
                    {field:"guestPhone",title:"客户电话",align:"center",sort:true,width:100},
                    {field:"lotteryTime",title:"发放时间",align:"center",sort:true,width:200},
                    {field:"guestIp",title:"客户IP",align:"center",sort:true,width:250},
                    {field:"addFromBos",title:"来源",align:"center",sort:true,width:100},
                    {field:"otherMsg",title:"其他",width:200},
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {

    };
    //加载入口函数
    init();
});