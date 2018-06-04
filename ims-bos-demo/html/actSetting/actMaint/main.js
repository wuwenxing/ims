//基于layui的模块加载
layui.link("/css/actMaint.min.css");
layui.use(['element', 'table', 'searchPanel', 'form','upload'],function(element,table,searchPanel,form,upload){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    upload = layui.upload;
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
        $(".node-edit").bind("click",evtFuns.editItem);
        $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {
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
            
            //上传白名单 
            upload.render({
                elem:"#upload-whitelist",
                url:"",
                done:function(){

                },
                error:function(){

                }
            });
            //上传黑名单
            upload.render({
                elem:"#upload-blacklist",
                url:"",
                done:function(){

                },
                error:function(){

                }
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/actMaintList.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true},
                    {field:"activityName",title:"活动名称",sort:true},
                    {field:"activityTypeStr",title:"活动类型"},
                    {field:"startTime",title:"活动开始时间",align:"center",sort:true},
                    {field:"endTime",title:"活动结束时间",align:"center",sort:true},
                    {field:"enableFlag",title:"活动状态",align:"center",width:120},
                    {field:"createDate",title:"创建时间",align:"center",sort:true},
                    {field:"updateDate",title:"最后更新时间",align:"center",sort:true}
                ]],
                done:function(){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuns.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuns.toDetail);
                }
            });

        }

    };
    //事件处理函数
    var evtFuns = {
        toDetail:function(){
            var othis = $(this);
            var activityPeriods = othis.text();
            parent.addTab({
                text:"查看活动信息",
                content:"/html/actSetting/actAdd/index.html?model=detail&activityPeriods="+activityPeriods,
                id:"actDeatil"
            });

        },
        upload:function(evt){
            var othis = $(this);
            if(othis.attr("data-type") == "whitelist"){

            }else{

            }
        },
        refresh:function(){
            custFuns.loadData();
        }
    };
    //加载入口函数
    init();
});