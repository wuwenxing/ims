//基于layui的模块加载
layui.link("/css/actMaint.min.css");
layui.use(['element', 'table', 'searchPanel', 'form','upload'],function(element,table,searchPanel,form,upload){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    upload = layui.upload;
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
        $(".node-agree").bind("click",evtFuns.agreeItem);
        $(".node-cancel").bind("click",evtFuns.cancelItem);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {
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
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/actEdit.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"proposalStatus",title:"提案状态",align:"center",width:120},
                    {field:"pno",title:"提案号",align:"center"},
                    {field:"activityPeriods",title:"活动编号",align:"center"},
                    {field:"activityName",title:"活动名称"},
                    {field:"activityType",title:"活动类型"},
                    {field:"activityStatus",title:"活动状态",align:"center",width:120},
                    {field:"approver",title:"提案人",align:"center",width:120},
                    {field:"approveDate",title:"提案时间",align:"center"},
                    {field:"proposer",title:"审批人",align:"center"},
                    {field:"proposalDate",title:"审批时间",align:"center",sort:true},
                    {field:"",title:"备注",align:"center"}
                ]],
                done:function(){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });

        }

    };
    //事件处理函数
    var evtFuns = {
        toDetail:function(){
            var othis = $(this);
            var activityPeriods = othis.text();
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actAdd/index.html?model=detail&activityPeriods="+activityPeriods,
                id:"actDeatil"
            });

        },
        refresh:function(){
            custFuns.loadData();
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
        }
    };
    //加载入口函数
    init();
});