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
        $(".node-send").bind("click",evtFuns.send);
        $(".node-export").bind("click",evtFuns.export);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {   
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/msgSendList.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {field:"mobile",title:"模板编号",align:"center",width:120},
                    {field:"content",title:"语言",align:"center",width:100},
                    {field:"channel",title:"名称",align:"center",width:120},
                    {field:"source",title:"内容",align:"center",width:100},
                    {field:"businessNo",title:"扩展信息1",align:"center",width:100},
                    {field:"status",title:"备注",align:"center",width:150},
                    {field:"respCode",title:"状态",align:"center",width:200},
                    {field:"failTimes",title:"最后更新时间",align:"center",width:100},
                    {field:"reqUrl",title:"最后更新时间",align:"center",width:100},
                    {field:"sendTime",title:"最后更新时间",align:"center",width:100},
                    {field:"code",title:"最后更新时间",align:"center",width:100}
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
            var confirmIndex = layer.confirm(new EJS({url:"./ejs/send.ejs"}).render(),{
                title:'新增',
                area:["500px","350px"]
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
        export:function(){

        },

    };
    //加载入口函数
    init();
});