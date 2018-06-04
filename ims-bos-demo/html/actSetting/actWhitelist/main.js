//基于layui的模块加载
layui.use(['element', 'table', 'searchPanel', 'form'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
        custFuns.searchEx();
        var type = null;
        var query = parseUrl(window.location.href);
        if(query.path.indexOf("actWhitelist") != -1){
            type = "whitelist";
        }else if(query.path.indexOf("actBlacklist") != -1){
            type = "blacklist";
        }
        custFuns.loadData(type);
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-refresh").bind("click",evtFuns.update);
    };
    //自定义函数
    var custFuns = {
        loadData:function(type){
            var url = "";
            if(type == "whitelist"){
                url = "/api/actCreate.json";
            }else{
                url = "/api/actCreate.json";
            }
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: url,
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true},
                    {field:"activityName",title:"活动名称",sort:true},
                    {field:"accountNo",title:"客户账号",sort:true},
                    {field:"env",title:"账号类型"},
                    {field:"platform",title:"平台",align:"center",sort:true,width:80},
                    {field:"updateDate",title:"最后更新时间",align:"center",sort:true}
                ]],
                done:function(){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });
        },
        searchEx:function(){
            //活动编号
            $.ajax({
                url:"/api/getPriodList.json",
                success:function(res){
                    console.log(res);
                    if(res.status){
                        var data =  res.data;
                        var html = "<option value=''>--请选择--</option>";
                        for(var i=0;i<data.length;i++){
                            html+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                        }
                        $("#actNum").html(html);
                        form.render("select");
                    }
                }
            });
            //活动名称
            $.ajax({
                url:"/api/getActivityName.json",
                success:function(res){
                    if(res.status){
                        var data =  res.data;
                        var html = "<option value=''>--请选择--</option>";
                        for(var i=0;i<data.length;i++){
                            html+="<option value='"+data[i]+"'>"+data[i]+"</option>";
                        }
                        $("#actName").html(html);
                        form.render("select");
                    }
                }
            });
            form.on("submit(search)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
        }
    };
    //事件处理函数
    var evtFuns = {
        update:function(){
            custFuns.loadData();
        }
    };
    //加载入口函数
    init();
});