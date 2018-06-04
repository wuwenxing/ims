layui.link('/css/sysLog.min.css');
layui.use(['element', 'table', 'searchPanel', 'form','laydate'], function () {
    var table = layui.table;
    var form = layui.form;
    var tree = layui.tree;
    var laydate = layui.laydate;
    var init = function () {
        laydate.render({
            elem: '#startTime',
            type: 'datetime',
            lang:'cn',
            calendar: true
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime',
            lang:'cn',
            calendar: true
        });
        custFuncs.loadData();
        bindEvents();
    };
    var bindEvents = function () {
        form.on('submit(search)', evtFuncs.search);
        $(".refresh").bind("click",evtFuncs.refresh);
    };
    var custFuncs = {
        loadData:function(params){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
            table.render({
                url: $reqPostUrl.sysLogList,
                where:params,
                cols: [[ //表头
                    {type:"numbers"},
                    {field: 'userNo', title: '用户'},
                    {field: 'createDate', title: '时间',  sort: true},
                    {field: 'method', title: '方法'},
                    {field: 'params', title: '参数'},
                    {field: 'broswer', title: '浏览器'},
                    {field: 'createIp', title: 'IP'}
                ]]
            });
        }
    };
    var evtFuncs = {
        search:function (data) {
            custFuncs.loadData(data.field);
            return false;
        },
        refresh:function(){
            $(".search").trigger("click");
            return false;
        }
    };
    init();
})
