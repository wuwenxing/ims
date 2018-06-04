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
        $(".node-reout").bind("click",evtFuns.reout);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //活动名称
            ActCom.initAllActivity();
            ActCom.loadDictData('ActPlatforms','platform');
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
        loadData:function(params){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
             console.log("outquery params:"+JSON.stringify(params));
            table.render({
                url: baseUrl+'mis/act/thirdcallrecord/page',
                where:params,
                method:"GET",
                cellMinWidth:120,
                cols:[[
                    {type:"numbers"},
                    {type:'checkbox'},
                    {field:"activityPeriods",title:"活动编号",width:200},
                    {field:"accountNo",title:"账号"},
                    {field:"platform",title:"平台"},
                    //类型(bonus:奖励金额)
                    {field:"type",title:"类别",templet:function(d){
                        return d.type=="bonus"?"奖励金额":"其它";
                    }},
                    //操作(增加贈金:addBonus,releaseBonus:释放贈金未释放金额)
                    {field:"code",title:"操作类型",templet: function(d){
                        if(d.code=="addBonus"){
                            return "新增奖励金额";
                        }else if(d.code=="releaseBonus"){
                            return "释放奖励金额";
                        }else if(d.code=='cancelBonus'){
                            return '扣回奖励金额';
                        }else{
                            return '';
                        }
                    }},
                    {field:"thirdDealResult",title:"处理结果",templet: function(d){
                        return d.thirdDealResult == 'success' ? '成功':'失败'; }},
                    {field:"recordNo",title:"操作流水号",width:200},
                    {field:"parentRecordNo",title:"关联发放记录流水号",width:200},
                    {field:"thirdRecordNo",title:"关联外部系统订单号",width:200},
                    {field:"tryCount",title:"重发次数"},
                    {field:"remark",title:"备注"},
                    {field:"createDate",title:"创建时间",sort:true,width:160},
                    {field:"updateDate",title:"更新时间",sort:true,width:160}
                ]],
                done:function(res){
                    //点击单元格查看详情
                     tabbar();
                    if(res.code == '10009'){
                        layer.msg(res.msg);
                        setTimeout(function(){
                            parent.location.href = "/html/login.html";
                        }, 500);
                        return;
                    };
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        reout:function(){
            var checkStatus = table.checkStatus("dataTable"),
            data = checkStatus.data;
            console.log("out query checkData:"+JSON.stringify(data));
            if(data.length){
                layer.confirm('确定要重发选中的记录吗!',function(){
                    for(var i=0;i<data.length;i++){
                    if(data[i].thirdDealResult=="success"){
                        layer.msg("请选择处理结果为失败的记录进行重发!");
                        return false;
                    }
                    }
                    var ids = "";
                    for(var i=0;i<data.length;i++){
                        ids+=data[i].id+",";
                    }
                    $.ajaxInterceptor.ajax({
                        url:baseUrl+"mis/act/thirdcallrecord/recall/"+ids.substr(0,ids.length-1),
                        data:{
                            ids:ids.substr(0,ids.length-1)
                        },
                        success:function(res){
                            if(res.code==$response.statusCode){
                                layer.msg("重发成功");
                                $(".node-search").trigger("click");
                            }else{
                                layer.msg("重发失败");
                            }
                        }
                    });
                })

            }else{
                layer.msg("请先选择要一条记录");
            }
            return false;
        },
        refresh:function(){
            $(".node-search").trigger("click");
            return false;
        },
        search:function(){
            form.on("submit(search)",function(data){
                custFuns.loadData(data.field);
                return false;
            });
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