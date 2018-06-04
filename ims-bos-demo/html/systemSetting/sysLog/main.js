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
    };
    var custFuncs = {
        loadData:function(params){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: $reqPostUrl.sysLogList,
                method:'POST',
                id: 'dataTable',
                page: true, //开启分页
                limit: 20,
                where:params,
                request:$request,
                response:$response,
                cols: [[ //表头
                    {field: 'id', title: '账号',  sort: true},
                    {field: 'userNo', title: '用户',sort: true},
                    {field: 'createDate', title: '时间',  sort: true},
                    {field: 'descr', title: '操作',},
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
        }
    };
    init();
})
