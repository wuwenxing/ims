//基于layui的模块加载
layui.use(['element', 'table', 'searchPanel', 'form', 'laydate'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    laydate = layui.laydate;

    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
        laydate.render({
            elem:"#startDate",
            type:"datetime"
        });
        laydate.render({
            elem:"#endDate",
            type:"datetime"
        });
        bindEvents();
        custFuns.loadData();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-search").bind("click",evtFuns.search);
        $(".node-add").bind("click",evtFuns.send);
        $(".node-reSend").bind("click",evtFuns.reSend);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {   
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/appMsgSendList.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"recipents",title:"账号",align:"center",width:120},
                    {field:"title",title:"消息标题",align:"center",width:100},
                    {field:"contentTypeTxt",title:"消息内容类型",align:"center",width:120},
                    {field:"content",title:"消息内容",align:"center",width:100},
                    {field:"summary",title:"消息摘要",align:"center",width:100},
                    {field:"status",title:"发送状态",align:"center",width:150},
                    {field:"sendTime",title:"发送时间",align:"center",width:150},
                    {field:"updateDate",title:"更新时间",align:"center",width:150},
                    {field:"failTimes",title:"失败的次数",align:"center",width:150},
                    {field:"pushApp",title:"推送的APP",align:"center",width:150},
                    {field:"pushType",title:"推送对象",align:"center",width:150},
                    {field:"msgShowPosition",title:"消息展示位置",align:"center",width:150},
                    {field:"otherMsg",title:"其他信息",align:"center",width:150},
                    {field:"msgId",title:"消息Id",align:"center",width:150},
                    {field:"code",title:"对应消息模板编号",align:"center",width:150}
                ]],
                done:function(){
                }
            });
        }
    };
    //事件处理函数
    var evtFuns = {
        search:function(ev){
            form.on("submit(search)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });

        },
        send:function(){
            var confirmIndex = layer.confirm(new EJS({url:"./ejs/item.ejs"}).render(),{
                title:'新增',
                area:["800px","550px"]
            },function(){
                $(".addItem").trigger("click");
            });
            form.render();
            form.on("submit(addItem)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });

        },
        refresh:function(){
            custFuns.loadData();
        },
        reSend:function(){

        },

    };
    //加载入口函数
    init();
});