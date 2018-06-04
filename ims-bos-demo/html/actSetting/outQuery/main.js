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
                type:"date"
            });
            laydate.render({
                elem:"#endDate",
                type:"date"
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/outQuery.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true,width:250},
                    {field:"accountNo",title:"账号",align:"center",sort:true,width:250},
                    {field:"platform",title:"平台",align:"center",sort:true,width:250},
                    {field:"type",title:"类别",align:"center",sort:true,width:250},
                    {field:"code",title:"操作类型",align:"center",sort:true,width:250},
                    {field:"thirdDealResult",title:"处理结果",align:"center",sort:true,width:250},
                    {field:"recordNo",title:"操作流水号",align:"center",sort:true,width:250},
                    {field:"parentRecordNo",title:"关联发放记录流水号",align:"center",sort:true,width:250},
                    {field:"thirdRecordNo",title:"关联外部系统订单号",align:"center",sort:true,width:250},
                    {field:"tryCount",title:"重发次数",align:"center",sort:true,width:250},
                    {field:"remark",title:"备注",align:"center",sort:true,width:250},
                    {field:"createDate",title:"创建时间",align:"center",sort:true,width:250},
                    {field:"updateDate",title:"更新时间",align:"center",sort:true,width:250}
                ]],
                done:function(){
                    //点击单元格查看详情
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