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
        $(".node-add").bind("click",evtFuns.addItem);
        $(".node-approved").bind("click",evtFuns.approved);
        $(".node-refuse").bind("click",evtFuns.refuse);
        $(".node-out").bind("click",evtFuns.out);
        $(".node-outsuccess").bind("click",evtFuns.outsuccess);
        $(".node-reout").bind("click",evtFuns.reout);
        $(".node-export").bind("click",evtFuns.export);
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-list").bind("click",evtFuns.prizelist);
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
                elem:"#startDate",
                type:"datetime"
            });
            laydate.render({
                elem:"#endDate",
                type:"datetime"
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/actCreate.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"recordNumber",title:"流水号",align:"center",sort:true,width:250},
                    {field:"dealNumber",title:"返回流水号",align:"center",sort:true,width:250},
                    {field:"guestPlatForm",title:"活动平台",sort:true,width:120},
                    {field:"activityPeriods",title:"活动编号",sort:true,width:250},
                    {field:"activityName",title:"活动名称",align:"center",sort:true,width:250},
                    {field:"taskTitle",title:"任务名称",align:"center",sort:true,width:250},
                    {field:"giftName",title:"物品名称",align:"center",sort:true,width:250},
                    {field:"giftType",title:"物品类型",align:"center",sort:true,width:250},
                    {field:"giftCategory",title:"物品种类",align:"center",sort:true,width:250},
                    {field:"giftPriceTxt",title:"物品价格",align:"center",sort:true,width:250},
                    {field:"accountNo",title:"客户账号",align:"center",sort:true,width:250},
                    {field:"guestName",title:"客户姓名",align:"center",sort:true,width:250},
                    {field:"guestPhone",title:"客户电话",align:"center",sort:true,width:250},
                    {field:"issuingStatus",title:"发放状态",align:"center",sort:true,width:250},
                    {field:"auditStatus",title:"审核状态",align:"center",sort:true,width:250},
                    {field:"giftAmountTxt",title:"发放额度",align:"center",sort:true,width:250},
                    {field:"needTradeLots",title:"要求平仓手数",align:"center",sort:true,width:250},
                    {field:"finishedTradeLots",title:"完成手数",align:"center",sort:true,width:250},
                    {field:"releasedBonusTxt",title:"外部系统已释放赠金",align:"center",sort:true,width:250},
                    {field:"unReleasedBonusTxt",title:"外部系统未释放赠金",align:"center",sort:true,width:250},
                    {field:"sysReleasedBonusTxt",title:"系统已释放赠金",align:"center",sort:true,width:250},
                    {field:"sysUnReleasedBonusTxt",title:"系统未释放赠金",align:"center",sort:true,width:250},
                    {field:"receiveFinishTime",title:"任务完成时间",align:"center",sort:true,width:250},
                    {field:"lotteryTime",title:"发放时间",align:"center",sort:true,width:250},
                    {field:"createDate",title:"创建时间",align:"center",sort:true,width:250},
                    {field:"guestIp",title:"客户IP",align:"center",sort:true,width:250},
                    {field:"addFromBos",title:"来源",align:"center",sort:true,width:250},
                    {field:"remark",title:"备注",align:"center",sort:true,width:250},
                    {field:"otherMsg",title:"其他信息",align:"center",sort:true,width:250},
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            var activeIndex = layer.confirm(new EJS({url:"./item.ejs"}).render({}) ,{
                title:"新增物品发放记录",
                btn:["确认","取消"],
                area:["750px","350px"],
            },function(){       
                $(".addItem").trigger("click");
            });
            form.render();
            form.on("submit(addItem)",function(data){
                alert(data.field);
                layer.close(activeIndex );
                return false;
            });
        },
        approved:function(){

            return false;
        },
        refuse:function(){

            return false;
        },
        out:function(){

            return false;
        },
        outsuccess:function(){

            return false;
        },
        reout:function(){

            return false;
        },
        exportExcel:function(){

            return false;
        },
        refresh:function(){
            custFuns.loadData();
            return false;
        },
        prizelist:function(){
            parent.addTab({"id":"prizelist","text":"应发列表","content":"/html/actSetting/prizelist/index.html"});
        }   
    };
    //加载入口函数
    init();
});