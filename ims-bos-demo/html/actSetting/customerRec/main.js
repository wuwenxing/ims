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
                url: '/api/customerData.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
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