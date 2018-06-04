//基于layui的模块加载
layui.link("/css/customTaskSetting.min.css");
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
        $(".node-add").bind("click",evtFuns.addTask);
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
                url: '/api/customTask.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {title: '操作', toolbar: '#tableDataActive', width: 200 ,align:"center"},
                    {field:"taskCode",title:"任务编号",align:"center",width:250},
                    {field:"taskName",title:"任务名称",align:"center",width:200},
                    {field:"ruleCode",title:"规则识别码",align:"center",width:200},
                    {field:"taskDesc",title:"描述",align:"center",width:200},
                    {field:"enableFlag",title:"状态",align:"center",width:100},
                    {field:"updateUser",title:"最后修改人",align:"center",width:200},
                    {field:"updateDate",title:"最后修改时间",align:"center",width:200}
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        addTask:function(){
            var activeIndex = layer.confirm(new EJS({url:"./addItem.ejs"}).render(),{
                title:"新增",
                area:["800px","350px"]
            },function(){//确定
                $(".node-add").trigger("click");
            });
            form.render();
            form.on("submit(add)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
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