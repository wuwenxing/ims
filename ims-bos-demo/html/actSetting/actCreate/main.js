//基于layui的模块加载
layui.link("/css/keyValue.min.css");
layui.link("/css/actCreate.min.css");
layui.use(['element', 'table', 'searchPanel', 'form'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
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
        $(".node-edit").bind("click",evtFuns.editItem);
        $(".node-agree").bind("click",evtFuns.agreeItem);
        $(".node-cancel").bind("click",evtFuns.cancelItem);
        $(".node-copy").bind("click",evtFuns.copyItem);
        $(".node-refresh").bind("click",evtFuns.update);
        table.on("tool(dataTable)",evtFuns.tabOpts);
    };
    //自定义函数
    var custFuns = {
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
                    {field:"pno",title:"提案号",align:"center"},
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true},
                    {field:"activityName",title:"活动名称",sort:true},
                    {field:"activityTypeStr",title:"活动类型"},
                    {field:"startTime",title:"活动开始时间",align:"center",sort:true,width:80},
                    {field:"endTime",title:"活动结束时间",align:"center",sort:true},
                    {field:"enableFlag",title:"活动状态",align:"center",sort:true},
                    {field:"proposalStatus",title:"提案状态",align:"center",sort:true},
                    {field:"approver",title:"审批人",align:"center"},
                    {field:"approveDate",title:"审批时间",align:"center",sort:true},
                    {field:"createUser",title:"创建人",align:"center"},
                ]],
                done:function(){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });
        },
        searchEx:function(){
            //活动编号
            $.ajax({
                url:"/api/getPriodList.json",
                success:function(res){
                    console.log(res);
                    if(res.status){
                        var data =  res.data;
                        var html = "<option value=''>--请选择--</option>";
                        for(var i=0;i<data.length;i++){
                            html+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                        }
                        $("#actNum").html(html);
                        form.render("select");
                    }
                }
            });
            //活动名称
            $.ajax({
                url:"/api/getActivityName.json",
                success:function(res){
                    if(res.status){
                        var data =  res.data;
                        var html = "<option value=''>--请选择--</option>";
                        for(var i=0;i<data.length;i++){
                            html+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                        }
                        $("#actName").html(html);
                        form.render("select");
                    }
                }
            });
            form.on("submit(search)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
        },
        opts:function(type ,data){
            
            if(type == "add"){
                var confirmIndex = layer.confirm(new EJS({url:"./addSelect.ejs"}).render(data),{
                    area:["350px","300px"],
                    title:"新增活动"
                },function(index){
                    $('.node-submit').trigger('click');
                });
                form.render();
                form.on('submit(seeAddRow)', function (data) {
                    parent.addTab({
                        text:"新增活动提案",
                        content:"/html/actSetting/actAdd/index.html?model=add&activityType="+data.field.activityType,
                        id:"actAdd"
                    });
                    layer.close(confirmIndex);
                    return false;
                });
            }
        }
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            custFuns.opts("add",{});
        },
        editItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                //活动修改
                parent.addTab({
                    text:"修改活动提案",
                    content:"/html/actSetting/actAdd/index.html?model=edit&activityType=",
                    id:"actEdit"
                });
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        agreeItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                var ids = "";
                for(var i=0;i<data.length;i++){
                    ids+=data[i].id+",";
                }
                $.ajax({
                    url:"/api/approved.json",
                    data:{
                        ids:ids.substr(0,ids.length-1)
                    },
                    success:function(res){
                        if(res.isSuccess){
                            layer.msg(res.resultMsg);
                        }else{

                        }
                    }
                });
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        cancelItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                var ids = "";
                for(var i=0;i<data.length;i++){
                    ids+=data[i].id+",";
                }
                $.ajax({
                    url:"/api/approved.json",
                    data:{
                        ids:ids.substr(0,ids.length-1)
                    },
                    success:function(res){
                        if(res.isSuccess){
                            layer.msg(res.resultMsg);
                        }else{

                        }
                    }
                });

            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        copyItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){

            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        update:function(){
            custFuns.loadData();
        },
        tabOpts:function(obj){
            if(obj.event == "edit"){
                custFuns.opts("edit",{});
            }else if(obj.event == "del"){

            }
        },
        toDetail:function(evt){
            var othis = $(this);
            var activityPeriods = othis.text();
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actAdd/index.html?model=detail&activityPeriods="+activityPeriods,
                id:"actDeatil"
            });
        }
    };
    //加载入口函数
    init();
});