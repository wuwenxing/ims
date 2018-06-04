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
            elem:"#startTimeStr",
            type:"datetime"
        });
        laydate.render({
            elem:"#endTimeStr",
            type:"datetime",
            done: function(value, date, endDate){
                $("#endTimeStr").val(addTime(value));
            }
        });
        bindEvents();
        var params={};
        var isFirst=true;
        custFuns.loadData(params,isFirst);
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
        loadData:function(params,isFirst){
            if(params==null||params==undefined){
                params={}
            }
            if(isFirst){
                orderBy.sort="sendTime";
            }
            params=$.extend(params,orderBy);

            table.render({
                url: baseUrl+'mis/ims/msg/sms/log/page',
                where:params,
                cellMinWidth:100,
                cols:[[
                    {type:"numbers"},
                    {type:"checkbox"},
                    {field:"mobile",title:"手机号",width:120},
                    {field:"content",title:"发送内容"},
                    {field:"channel",title:"发送渠道"},
                    {field:"source",title:"来源"},
                    {field:"businessNo",title:"业务编号"},
                    {field:"status",title:"发送状态"},
                    {field:"respCode",title:"结果码"},
                    {field:"failTimes",title:"失败的次数"},
                    {field:"reqUrl",title:"发送地址",width:200},
                    {field:"sendTime",title:"发送时间",width:160,sort:true},
                    {field:"code",title:"短信模板编号",width:200}
                ]]
            });
        },
        save:function(data){
            console.log("save msg data:"+JSON.stringify(data.field));
            $.ajaxInterceptor.ajax({
                url:baseUrl+'mis/ims/msg/sms/log/save',
                type:'POST',
                data:data.field,
                success:function(res){
                    if(res.code!="OK"){
                        layer.msg(res.msg);
                        return false;
                    }
                    layer.msg(res.msg);
                    layer.close();
                    $(".node-search").trigger("click");
                    
                },  
                error:function(err){
                    layer.msg(err);
                }
            })

        }
    };
    //事件处理函数
    var evtFuns = {
        search:function(ev){
            form.on("submit(search)",function(data){
                custFuns.loadData(data.field);
                return false;
            });

        },
        send:function(){
            var confirmIndex = layer.confirm(new EJS({url:"./ejs/send.ejs"}).render(),{
                title:'新增',
                area:["auto","auto"],
                btn:["新增"]
            },function(){
                $(".addItem").trigger("click");
            });
            form.render();
            form.on("submit(addItem)",function(data){
                custFuns.save(data);
                return false;
            });

        },
        refresh:function(){
            $('.node-search').trigger('click');
        },
        export:function(){
          var mobile=$("#mobile").val();
          var content=$("#content").val();
          var status=$("#status").val();
          var channel=$("#channel").val();
          var source=$("#source").val();
          var businessNo=$("#businessNo").val();
          var startTimeStr=$("#startTimeStr").val();
          var endTimeStr=$("#endTimeStr").val();
          var url=baseUrl+"mis/ims/msg/sms/log/export"+"?mobile="+mobile+"&content="+content+"&status="+status
          +"&source="+source+"&businessNo="+businessNo+"&startTimeStr="+startTimeStr+"&endTimeStr="+endTimeStr+
          "&channel="+channel+"&sort="+orderBy.sort+"&order="+orderBy.order;

          window.location.href = url;
          return false;

        },

    };
    //加载入口函数
    init();
});