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
        $(".node-export").bind("click",evtFuns.export);
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-search").bind("click",evtFuns.search);
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
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true,width:250},
                    {field:"activityName",title:"活动名称",align:"center",sort:true,width:250},
                    {field:"accountNo",title:"客户账号",align:"center",sort:true,width:250},
                    {field:"chineseName",title:"客户姓名",align:"center",sort:true,width:250},
                    {field:"email",title:"客户邮箱",align:"center",sort:true,width:250},
                    {field:"mobile",title:"客户电话",align:"center",sort:true,width:250},
                    {field:"env",title:"账号类型",align:"center",sort:true,width:250},
                    {field:"platform",title:"平台",sort:true,width:250},
                    {field:"joinTime",title:"参与时间",align:"center",sort:true,width:250},
                    {field:"taskName",title:"任务名称",sort:true,width:120},
                    {field:"reqNum",title:"要求完成",align:"center",sort:true,width:250},
                    {field:"finishedNum",title:"已完成",align:"center",sort:true,width:250},
                    {field:"unFinishedNum",title:"待完成",align:"center",sort:true,width:250},
                    {field:"updateDate",title:"最后更新时间",align:"center",sort:true,width:250}
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        exportExcel:function(){

            return false;
        },
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