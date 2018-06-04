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
        form.on('submit(search)', evtFuns.search);
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //活动编号
            ActCom.initAllActivity();//初始化下拉活动列表 查询界面

            //物品类型
            ActCom.loadDictData('ActItemsType','itemType');//物品类型

            //物品种类
            ActCom.loadDictData('ActItemCategory','itemCategory');//物品种类

            //物品列表
            $.ajaxInterceptor.post(reqPostUrl.actItems,{},function(res){
                if(res.code!=$response.statusCode){
                    return false;
                }
                itemList = res.data;
                var html = "<option value=''>--请选择--</option>";
                for(var i=0;i<itemList.length;i++){
                    html+="<option value='"+itemList[i].itemName+"'>"+itemList[i].itemName+"</option>";
                }
                $("#itemName").html(html);
                layui.form.render("select");
                return itemList;
            });

            laydate.render({
                elem:"#startDate",
                type:"date"
            });
            laydate.render({
                elem:"#endDate",
                type:"date",
                done: function(value, date, endDate){
                    $("#endDate").val(addTime(value));
                }
            });
        },
        loadData:function(params){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
            var columns =[
                    {type:'numbers',width:60},
                    {field:"recordNo",title:"流水号",align:"center",sort:true,width:250},
                    {field:"platform",title:"活动平台",sort:true,width:100},
                    {field:"actName",title:"活动名称",align:"center",sort:true,width:250},
                    {field:"taskName",title:"任务名称",align:"center",sort:true,width:250},
                    {field:"itemName",title:"物品名称",align:"center",sort:true,width:200},
                    {field:"itemTypeTxt",title:"物品类型",align:"center",sort:true,width:150},
                    {field:"itemCategoryTxt",title:"物品种类",align:"center",sort:true,width:100},
                    {field:"itemAmount",title:"发放额度",align:"center",width:100},
                    {field:"itemPrice",title:"物品价格",align:"center",width:100},
                    {field:"accountNo",title:"客户账号",align:"center",sort:true,width:250},
                    {field:"custName",title:"客户姓名",align:"center",sort:true,width:100},
                    {field:"custMobile",title:"客户电话",align:"center",sort:true,width:100},
                    {field:"givedTime",title:"发放时间",align:"center",sort:true,width:200},
                    {field:"guestIp",title:"客户IP",align:"center",sort:true,width:250},
                    {field:"addFromBos",title:"来源",align:"center",sort:true,width:100,templet:function(e){ return e.addFromBos==true?'手动':'自动'}},
                    {field:"otherMsg",title:"其他",width:200}
            ];
            table.render({
                where:params,
                url:$reqPostUrl.actPrizeoutRecOutList,
                cols: [columns]
            });

        }
    };
    //事件处理函数
    var evtFuns = {
        search:function (data) {
            custFuns.loadData(data.field);
            return false;
        }
    };
    //加载入口函数
    init();
});