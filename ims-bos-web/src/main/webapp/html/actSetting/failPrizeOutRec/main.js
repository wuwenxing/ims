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
        ActCom.loadDictData('ActPlatforms','platform');
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-reout").bind("click",evtFuns.reoutItem);//重发
        $(".node-del").bind("click",evtFuns.delItem);//删除
        $(".node-refresh").bind("click",evtFuns.refresh);//刷新
        $(".node-search").bind("click",evtFuns.search);//搜索
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //活动名称
           ActCom.loadDataSetLinkage();
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
            if(params==undefined){
                params={};
            }
            params=$.extend(params,orderBy);
            console.log("params:"+JSON.stringify(params));
            var columns=[
                    {type:"numbers"},
                    {type:'checkbox'},
                    {field:"activityPeriods",title:"活动编号",width:200},
                    {field:"activityName",title:"活动名称",width:200},
                    {field:"accountNo",title:"客户账号"},
                    {field:"platform",title:"平台"},
                    {field:"updateDate",title:"最后更新时间",sort:true,width:160},
                    {field:"redoStatus",title:"发放状态"},
                    {field:"errorNo",title:"重试次数"},
                    {field:"errorInfo",title:"错误信息"}
            ];
            table.render({
                url:reqPostUrl.failPrizeOutRecPage,
                cols: [columns],
                method:"GET",
                where:params,
                cellMinWidth:100,
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
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {

        showData:function(){
            alert("查看数据");
        },
        reoutItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                layer.confirm("确定要重发选中的记录吗?",function(){
                    var ids = "";
                    for(var i=0;i<data.length;i++){
                        ids+=data[i].id+",";
                    }
                    ids=ids.substr(0,ids.length-1);
                    $.ajaxInterceptor.ajax({
                        url:baseUrl+"/mis/act/prizerecordredo/batch/resend/"+ids,
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
        delItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                var ids = "";
                for(var i=0;i<data.length;i++){
                    ids+=data[i].id+",";
                }
                layer.confirm("确定要删除选中的记录吗?",function(){
                    $.ajaxInterceptor.ajax({
                        url:baseUrl+"/mis/act/prizerecordredo/batch/delete/"+ids,
                        /*data:{
                            ids:ids.substr(0,ids.length-1)
                        },*/
                        success:function(res){
                            if(res.code==$response.statusCode){
                                layer.msg("删除成功!");
                                $(".node-search").trigger("click");
                            }else{
                                layer.msg("删除失败!");
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