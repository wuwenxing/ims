//基于layui的模块加载
layui.link("/css/actEdit.min.css");
layui.use(['element', 'table', 'searchPanel', 'form','upload'],function(element,table,searchPanel,form,upload){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    upload = layui.upload;
    //全局变量申明
    var curData = null;
    var actType = {
        "rw":"任务型",
        "rjzj":"入金奖励型",
        "cj":"层级型",
        "wpdh":"物品兑换型"
    };
    //文件入口函数
    var init = function(){
        custFuns.searchEx();
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        form.on("submit(search)",evtFuns.search);
        $(".node-agree").bind("click",evtFuns.agreeItem);
        $(".node-cancel").bind("click",evtFuns.cancelItem);
        $(".node-refresh").bind("click",evtFuns.update);
        form.on("select(actStatus)",evtFuns.changeStatus);
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //actApproveList
            //活动编号
            ActCom.loadDataSetLinkage();
            ActCom.loadDictData("ActivityType","actType");//活动类型
            ActCom.loadDictData("ActivitytProposalStatus","creStatus");//提案状态
        },
        loadData:function(params){
             if(params == "undefined"){
                params = {};
            }   
            orderBy.sort = 'updateDate';
            params = $.extend(params,orderBy);
            table.render({
                where:params,
                url: $reqPostUrl.actApproveList,
                cellMinWidth:100,
                cols:[[
                    {type:'checkbox'},
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
                    {field:"pno",title:"提案号",width:200},
                    {field:"activityPeriods",title:"活动编号",width:200},
                    {field:"activityName",title:"活动名称",width:200},
                    {field:"tagName",title:"所属标签"},
                    {field:"activityType",title:"活动类型",templet:function(d){
                        return actType[d.activityType];
                    }},
                    {field:"enableFlagName",title:"活动状态"},
                    {field:"proposer",title:"提案人"},
                    {field:"proposalDate",title:"提案时间",width:160,sort:true},
                    {field:"approver",title:"审批人",templet:function(d){
                        if(d.proposalStatus == "ActHasCanceled"){
                            return d.canceller;
                        }else if(d.proposalStatus == "ActHasApproved"){
                            return d.approver;
                        }else{
                            return '';
                        }
                    }},
                    {field:"approveDate",title:"审批时间",width:160,sort:true,templet:function(d){
                        if(d.proposalStatus == "ActHasCanceled"){
                            return d.cancelDate;
                        }else if(d.proposalStatus == "ActHasApproved"){
                            return d.approveDate;
                        }else{
                            return '';
                        }
                    }},
                    {field:"remark",title:"备注",align:"center"}
                ]],
                done:function(res){
                    tabbar();
                    if(res.code == '10009'){
                        layer.msg(res.msg);
                        setTimeout(function(){
                            parent.location.href = "/html/login.html";
                        }, 500);
                        return;
                    };
                    curData = res.data;
                    //点击单元格查看详情
                    $(".layui-table td[data-field=pno]").bind("click",evtFuns.toEditDetail);
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });

        }

    };
    //事件处理函数
    var evtFuns = {
        toEditDetail:function(){
            var othis = $(this);
            var pno = othis.closest("tr").find('td[data-field=pno]').text();
            var index = othis.closest("tr").attr("data-index");
            var activityType = curData[index].activityType;//获取任务类型
            parent.addTab({
                text:"查看活动修改提案",
                content:"/html/actSetting/actEditProposalView/index.html?model=editDetail&pno="+pno+"&activityType="+activityType,
                id:"actEditProposalView"
            });

        },
        toDetail:function(){
            var othis = $(this);
            var activityPeriods = othis.closest("tr").find('td[data-field=activityPeriods]').text();
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actDetailView/index.html?model=detail&activityPeriods="+activityPeriods+"&parentId="+queryJson.id,
                id:"actDetailView"
            });
        },
        agreeItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                // var pnos = "";
                // for(var i=0;i<data.length;i++){
                //     pnos+=data[i].pno+",";
                // }
                // pnos = pnos.replace(/(\,$)/,'');
                if(data.length == 1){
                    layer.confirm('确认执行选择的记录？', {
                        title: '<div><i class="layui-icon">&#xe640;</i>&nbsp;审批通过</div>',
                        btn: ['确定', '取消']
                    }, function (index) {
                        $.ajaxInterceptor.post($reqPostUrl.actApproveActById+data[0].pno,{},function(res){
                            if(res.code != "OK"){
                                layer.msg(res.msg);return;
                            }
                            layer.msg("操作成功");
                            evtFuns.update();
                        });
                        //确定回调
                        layer.close(index);
                    });
                }else{
                    layer.msg("一次只能操作一条记录");
                }
            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        cancelItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                // var ids = "";
                // for(var i=0;i<data.length;i++){
                //     ids+=data[i].id+",";
                // }
                // ids = ids.replace(/(\,$)/,'');

                if(data.length == 1){
                    if(data[0].proposalStatus != "ActWaitForApprove"){
                        layer.alert("只能取消待审批的活动");return;
                    }
                    layer.prompt({
                        formType: 2,
                        value: '',
                        title: '您确定要取消选中的提案吗？取消原因：',
                        area: ['500px', '100px'] //自定义文本域宽高
                    }, function(value, index, elem){
                        // alert(value); //得到value
                        if(value){
                            $.ajaxInterceptor.post($reqPostUrl.actApproveCancelById+data[0].pno,{cancelReason:value},function(res){
                                if(res.code != "OK"){
                                    layer.msg(res.msg);return;
                                }
                                layer.msg("取消成功");
                                evtFuns.update();
                            });
                            layer.close(index);
                        }else{
                            layer.msg("请填写取消原因");
                        }
                    });
                }else{
                    layer.msg("一次只能操作一条记录");
                }

            }else{
                layer.msg("请先选择要一条记录");
            }
        },
        search:function(data){
            custFuns.loadData(data.field);
            return false;
        },
        update:function(){
            $(".search").trigger("click");
        },
        changeStatus:function(data){
            if(data.value==""){
                $("#isExpiration").val("");
            }else if(data.value == 'invalid'){
                $("#isExpiration").val("true");
                $("#actStatus").val("");
            }else{
                $("#isExpiration").val("false");
            }
        }
    };
    //加载入口函数
    init();
});