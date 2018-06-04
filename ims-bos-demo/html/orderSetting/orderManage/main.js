//基于layui的模块加载
layui.use(['element','form','layer','table','laydate','searchPanel'],function(element,form,layer,table,laydate){
    element = layui.element;
    form = layui.form;
    layer = layui.layer;
    table = layui.table;
    laydate = layui.laydate;
    searchPanel = layui.searchPanel;
    //全局变量申明
    var curExpressData = null;

    //文件入口函数
    var init = function(){
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-search").bind("click",evtFuns.search);
        table.on('tool(dataTable)', evtFuns.opts);
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/orderlist.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {toolbar:"#tableDataActive",title:"操作",width:150},
                    {field:"recordNumber",title:"流水号",align:"center",width:250},
                    {field:"accountNo",title:"客户账号",align:"center",width:250},
                    {field:"chineseName",title:"客户姓名",align:"center",width:150},
                    {field:"mobile",title:"手机号",align:"center",width:150},
                    {field:"itemsCode",title:"商品编号",align:"center",width:200},
                    {field:"itemsName",title:"商品名称",align:"center",width:250},
                    {field:"activityPeriods",title:"活动编号",align:"center",width:250},
                    {field:"activityName",title:"活动名称",align:"center",width:250},
                    {field:"taskCode",title:"任务编号",align:"center",width:250},
                    {field:"taskName",title:"任务名称",align:"center",width:250},
                    {field:"region",title:"客户所在地区",align:"center",width:200},
                    {field:"expressCompany",title:"快递公司",align:"center",width:150},
                    {field:"expressNo",title:"快递单号",align:"center",width:250},
                    {field:"detailAddress",title:"收货地址",align:"center",width:250},
                    {field:"deliveryDate",title:"发货时间",align:"center",width:200},
                    {field:"deliveryStatus",title:"发货状态",align:"center",width:150},
                    {field:"remark",title:"备注",align:"center",width:250}
                ]],
                done:function(res, curr, count){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                    //图片
                    var imgUrlTd = $(".layui-table td[data-field=imgUrl] .layui-table-cell");
                    imgUrlTd.each(function(index, item){
                        var othis = $(item);
                        othis.html("<img src='"+res.data[index].imgUrl+"'>");
                    });
                }
            })
        },
        reqExpressInfo:function(){
            $.ajax({
                url:"/api/expressInfo.json",
                success:function(res){
                    var content = new EJS({url:"./expressInfo.ejs"}).render({"data":res});
                    custFuns.editExpressInfo(content);
                }
            });
        },
        editExpressInfo:function(content){
            var activeIndex = layer.confirm(content,{
                title:"录入快递信息",
                area:["800px","500px"]

            },function(){
                $(".edit-btn").trigger("click");
            });

            form.on("submit(editDeliveryInfo)",function(data){
                layer.msg(JSON.stringify(data,field));
                return false;
            });

            laydate.render({
                elem:"#deliveryDate",
                type:"datetime"
            });


        }
    };
    //事件处理函数
    var evtFuns = {
        opts:function(data){
            if(data.event == "edit"){
                custFuns.reqExpressInfo();
            }
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
        toDetail:function(){
            var othis = $(this);
            var activityPeriods = othis.text();
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actAdd.html?model=detail&activityPeriods="+activityPeriods,
                id:"actDeatil"
            });
        }
    };
    //加载入口函数
    init();
});