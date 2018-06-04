//基于layui的模块加载
layui.link("/css/keyValue.min.css");
layui.link("/css/actCreate.min.css");
layui.use(['element', 'table', 'searchPanel', 'form','layer'],function(element,table,searchPanel,form,layer){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    layer = layui.layer;
    //全局变量申明
    var curData = null;
    var queryJson = null;
    var actTypeList = null;
    //文件入口函数
    var init = function(){
        queryJson = parseUrl(location.href).queryJson;
        custFuns.searchEx();
        custFuns.loadData({proposalStatus:"ActWaitForApprove",enableFlag:'Y',isExpiration:'false'});
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
        $(".reset-btn").bind("click",evtFuns.resetSearchForm);
        table.on("tool(dataTable)",evtFuns.tabOpts);
        form.on("select(actStatus)",evtFuns.changeStatus);
    };
    //自定义函数
    var custFuns = {
        loadData:function(params){
            if(params==undefined){
                params={}
            }
            params=$.extend(params,orderBy);
            table.render({
                url: $reqPostUrl.actPage,
                where:params,
                cellMinWidth: 100,
                cols:[[
                    {type:"numbers"},
                    {type:'checkbox'},
                    {field:"pno",title:"提案号",width:200},
                    {field:"activityPeriods",title:"活动编号",width:200},
                    {field:"activityName",title:"活动名称",width:200},
                    {field:"tagName",title:"所属标签"},
                    {field:"activityTypeName",title:"活动类型"},
                    {field:"startTime",title:"活动开始时间",sort:true,width:160},
                    {field:"endTime",title:"活动结束时间",sort:true,width:160},
                    {field:"enableFlagName",title:"活动状态"},//活动状态enableFlag
                    {field:"proposalStatus",title:"提案状态",templet:function(d){
                        if(d.proposalStatus == "ActHasApproved"){
                            return '已审批';
                        }
                        if(d.proposalStatus == "ActHasCanceled"){
                            return '已取消';
                        }
                        if(d.proposalStatus == "ActWaitForApprove"){
                            return '待审批';
                        }
                    }},
                    {field:"approver",title:"审批人",templet:function(d){
                        if(d.proposalStatus == "ActHasApproved"){
                            return d.proposer;
                        }
                        if(d.proposalStatus == "ActHasCanceled"){
                            return d.canceller;
                        }
                        if(d.proposalStatus == "ActWaitForApprove"){
                            return '';
                        }
                    }},
                    {field:"approveDate",title:"审批时间",sort:true,width:200,templet:function(d){
                        if(d.proposalStatus == "ActHasApproved"){
                            return d.approveDate;
                        }
                        if(d.proposalStatus == "ActHasCanceled"){
                            return d.cancelDate;
                        }
                        if(d.proposalStatus == "ActWaitForApprove"){
                            return '';
                        }
                    }},
                    {field:"createUser",title:"创建人"},
                ]],
                done:function(res){
                    if(res.code == '10009'){
                        //清除缓存数据
                        localStorage.removeItem('companyList');
                        localStorage.removeItem('userInfo');
                        layer.msg(res.msg);
                        setTimeout(function(){
                            parent.location.href = "/html/login.html";
                        }, 500);
                        return;
                    };
                    tabbar();
                    curData = res.data;
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });
        },
        searchEx:function(){
            ActCom.loadDataSetLinkage();//
            ActCom.loadDictData("ActivitytProposalStatus","creStatus",function(){
                $("#creStatus").val("ActWaitForApprove");
            });//活动状态
            ActCom.loadDictData("ActivityType","actType",function(data){
                actTypeList = data;
            });//活动类型·
            $('#actStatus').val("Y");
            //活动名称
            form.on("submit(search)",function(data){
                custFuns.loadData(data.field);
                return false;
            });
        },
        opts:function(type ,data){
            if(type == "add"){
                var confirmIndex = layer.confirm(new EJS({url:"./addSelect.ejs"}).render({data:actTypeList}),{
                    area:["auto","auto"],
                    title:"选择活动类型",
                    btn:["确定"]
                },function(index){
                    $('.node-submit').trigger('click');
                });
                form.render();
                form.on('submit(seeAddRow)', function (data) {
                    parent.addTab({
                        text:"新增活动提案",
                        content:"/html/actSetting/actAdd/index.html?model=add&activityType="+data.field.activityType+"&parentId="+queryJson.id,
                        id:"actAdd",
                        query:new Date().getTime()
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
            if(data.length == 1){
                if(data[0].proposalStatus != "ActHasCanceled" && $.myTime.DateToUnix(data[0].endTime) > $.myTime.CurTime() ){
                    //活动修改
                    parent.addTab({
                        text:"修改活动提案",
                        content:"/html/actSetting/actEdit/index.html?model=edit&activityPeriods="+data[0].activityPeriods+"&activityType="+data[0].activityType+"&id="+data[0].id+"&parentId="+queryJson.id,
                        id:"actEdit"
                    });
                }else{
                    layer.msg("已取消或无效的活动不能修改");
                }
            }else if(data.length > 1){
                layer.msg("一次只能修改一条记录");
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        agreeItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                layer.confirm("确认要审批通过此记录吗？",function(){
                    var ids = "";
                    for(var i=0;i<data.length;i++){
                        if(data[i].proposalStatus == "ActHasApproved" || data[i].proposalStatus == "ActHasCanceled"){
                            layer.msg("已审批的活动不能再次审批");
                            break;
                        }
                        ids+=data[i].id+",";
                    }
                    $.ajaxInterceptor.post($reqPostUrl.actApprove + ids.substr(0,ids.length-1),{},function(res){
                        if(res.code != "OK"){
                            layer.msg(res.msg);return;
                        }
                        layer.msg("操作成功");
                        evtFuns.update();
                    });
                });
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        cancelItem:function(){//取消活动
            
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                var ids = "";
                var isCancel = false;
                for(var i=0;i<data.length;i++){
                    if(data[i].proposalStatus != 'ActWaitForApprove'){
                        isCancel = true;
                        layer.msg("请选择待审批的活动");
                        break;
                    }
                    ids+=data[i].id+",";
                }
                ids = ids.replace(/(\,)$/,'');
                if(!isCancel){
                    layer.confirm('确认需要取消的记录吗？', {
                        title: '<div>取消记录</div>',
                        btn: ['确定', '取消']
                    }, function (index) {
                        $.ajaxInterceptor.post($reqPostUrl.actCancelById + ids,{},function(res){
                            // 
                            if(res.code != $response.statusCode){
                                layer.msg(res.msg);return;
                            }
                            layer.msg("取消成功");
                            evtFuns.update();
                        });                       
                        //确定回调
                        layer.close(index);
                    });
                }

            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        copyItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length == 1){
                //复制活动
                parent.addTab({
                    text:"新增活动提案",
                    content:"/html/actSetting/actEdit/index.html?model=copy&activityPeriods="+data[0].activityPeriods+"&activityType="+data[0].activityType+"&id="+data[0].id+"&parentId="+queryJson.id,
                    id:"actEdit"
                });
            }else if(data.length > 1){
                layer.msg("一次只能修改一条记录");
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        update:function(){
           $(".search").trigger("click");
        },
        tabOpts:function(obj){
            if(obj.event == "edit"){
                custFuns.opts("edit",{});
            }else if(obj.event == "del"){

            }
        },
        changeStatus:function(data){//处理活动无效查询
            if(data.value==""){
                $("#isExpiration").val("");
            }else if(data.value == 'invalid'){
                $("#isExpiration").val("true");
                $("#actStatus").val("");
            }else{
                $("#isExpiration").val("false");
            }
        },
        resetSearchForm:function(){//重置
            $("#actNum").val("");
            $("#actName").val("");
            $("#actType").val("");
            $(".layui-searchPanel-content input[name='pno']").val("");
            form.render();
        },
        toDetail:function(){
            var othis = $(this);
            var activityPeriods = othis.closest("tr").find('td[data-field=activityPeriods]').text();
            var index = othis.closest("tr").attr("data-index");
            var activityType = curData[index].activityType;//获取任务类型
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actDetailView/index.html?model=detail&activityPeriods="+activityPeriods+"&activityType="+activityType+"&parentId="+queryJson.id+"&datetime="+new Date().getTime(),
                id:"actDetailView"
            });
        }
    };
    //加载入口函数
    init();
});