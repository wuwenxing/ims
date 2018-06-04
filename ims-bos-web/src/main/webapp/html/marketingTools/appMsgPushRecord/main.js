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
            type:"datetime",
            done: function(value, date, endDate){
                $("#endDate").val(addTime(value));
            }
        });
        bindEvents();
        var params={};
        custFuns.loadData(params);
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
        
        loadData:function(params){
         if(params==null||params==undefined){
            params={}
        }
        params = $.extend(params,orderBy);
            console.log("app msg push log:"+JSON.stringify(params));
            table.render({
                url: baseUrl+'mis/ims/msg/app/log/page',
                where:params,
                cellMinWidth:100,
                cols:[[
                    {type:"numbers"},
                    {type:'checkbox'},
                    {field:"recipents",title:"账号"},
                    {field:"title",title:"消息标题"},
                    {field:"contentTypeTxt",title:"消息内容类型",width:150},
                    {field:"content",title:"消息内容"},
                    {field:"summary",title:"消息摘要"},
                    {field:"msgTypeTxt",title:"消息类型"},
                    {field:"pushDevices",title:"推送设备",width:150},
                    {field:"status",title:"发送状态"},
                    {field:"sendTime",title:"发送时间",sort:true,width:160},
                    {field:"updateDate",title:"更新时间",width:160,sort:true},
                    {field:"failTimes",title:"失败的次数"},
                    {field:"pushApp",title:"推送的APP",width:200},
                    {field:"pushType",title:"推送对象",width:150},
                    {field:"msgShowPosition",title:"消息展示位置",width:150},
                    {field:"otherMsg",title:"其他信息"},
                    {field:"msgId",title:"消息Id",width:150},
                    {field:"code",title:"对应消息模板编号",width:150}
                ]]
            });
        },
        save:function(data){
             var pushapp_vals = '';
             var pushapp = $(".pushapp input:checked");
             $.each(pushapp,function(index ,item){
                 pushapp_vals+=item.value+',';
             });
             data.field.pushApp = pushapp_vals.replace(/(\,$)/,'');

            var pushdevices_vals = '';
            var pushdevices = $(".pushdevices input:checked");
            $.each(pushdevices,function(index ,item){
                pushdevices_vals+=item.value+',';
            });
            data.field.pushDevices = pushdevices_vals.replace(/(\,$)/,'');


             var position_vals = '';
             var position = $(".position input:checked");
             $.each(position,function(index ,item){
                 position_vals+=item.value+',';
             });
             data.field.msgShowPosition = position_vals.replace(/(\,$)/,'');
             console.log("app msg data:"+JSON.stringify(data));
            $.ajaxInterceptor.ajax({
                url:baseUrl+"mis/ims/msg/app/log/save",
                type:'POST',
                data:data.field,
                success:function(res){
                    custFuns.loadData();
                    layer.msg("操作成功");
                     $('.node-search').trigger('click');
                    
                },  
                error:function(err){
                    layer.msg("操作失败");
                }
            })
        },
    };
    //事件处理函数
    var evtFuns = {
        search:function(ev){
            form.on("submit(search)",function(data){
                //layer.msg(JSON.stringify(data.field));
                custFuns.loadData(data.field);
                return false;
            });

        },
        send:function(){
            var confirmIndex = layer.confirm(new EJS({url:"./ejs/item.ejs"}).render(),{
                title:'新增',
                area:["auto","528px"],
                // maxHeight:528,
                btn:["新增"]
            },function(){
                $(".addItem").trigger("click");
            });
            laydate.render({
                elem:"#sendDate",
                type:"datetime"
            });
            form.render();
            form.on("submit(addItem)",function(data){
                //layer.msg(JSON.stringify(data.field));
                custFuns.save(data);
                return false;
            });

        },
        refresh:function(){
            $(".node-search").trigger("click");
        },
        reSend:function(){
            var checkStatus = table.checkStatus("dataTable"),
            data = checkStatus.data;
            if(data.length){
                for(var i=0;i<data.length;i++){
                    if(data[i].thirdDealResult=="success"){
                        layer.msg("请选择处理结果为失败的记录进行重发!");
                        return false;
                    }
                }
                layer.confirm("确定要重发选中的记录吗?",function(){
                    var ids = "";
                    for(var i=0;i<data.length;i++){
                        ids+=data[i].id+",";
                    }
                    ids=ids.substr(0,ids.length-1);
                    console.log("resend data ids:"+ids);
                    $.ajaxInterceptor.ajax({
                        url:baseUrl+"mis/ims/msg/app/log/resend",
                        data:{'ids':ids},
                        type:'POST',
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
        }

    };
    //加载入口函数
    init();
});