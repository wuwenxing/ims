//基于layui的模块加载
layui.link("/css/actPrize.min.css");
layui.use(['element', 'table', 'searchPanel', 'form','upload','laydate','layer'],function(element,table,searchPanel,form,upload,laydate,layer){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    upload = layui.upload;
    laydate = layui.laydate;
    layer = layui.layer;
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
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-add").bind("click",evtFuncs.addItem);
        $(".node-del").bind("click",evtFuncs.delItem);
        $(".node-detail").bind("click",evtFuncs.detailItem);
        $(".node-code-manage").bind("click",evtFuncs.codeManage);
        $(".node-refresh").bind("click",evtFuncs.refresh);
        table.on('tool(dataTable)', evtFuncs.opts);
        form.on('submit(search)', evtFuncs.search);
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/prizelist.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {title: '操作', toolbar: '#tableDataActive',align:"center",width:200},
                    {field:"giftNumber",title:"物品编号",align:"center",width:250},
                    {field:"giftName",title:"物品名称",width:250},
                    {field:"giftType",title:"物品类型",width:100},
                    {field:"itemCategory",title:"物品种类",align:"center",width:100},
                    {field:"giftAmount",title:"物品数量",align:"center",width:100},
                    {field:"giftPrice",title:"物品价格",align:"center",width:100},
                    {field:"enableFlag",title:"物品状态",align:"center",width:100},
                    {field:"startDate",title:"物品有效期",align:"center"}
                ]],
                done:function(res, curr, count){
                    //点击单元格查看详情
                    $(".layui-table td[data-field=activityPeriods]").bind("click",evtFuncs.toDetail);
                    $(".layui-table td[data-field=activityName]").bind("click",evtFuncs.toDetail);
                    //接口物品
                    var giftTypeTd = $(".layui-table td[data-field=giftType] .layui-table-cell"); 
                    giftTypeTd.each(function(index, item){
                        var othis = $(item);
                        var giftType = othis.text();
                        if(giftType == "real"){
                            othis.text("真实物品");
                        }else if(giftType == "interface"){
                            othis.text("接口物品");
                        }else if(giftType == "virtual"){
                            othis.text("虚拟物品");
                        }else if(giftType == "tokenCoin"){
                            othis.text("代币");
                        }else if(giftType == "withGold"){
                            othis.text("赠金");
                        }else if(giftType == "analogCoin"){
                            othis.text("模拟币");
                        }
                    });
                    //物品种类
                    var giftCategoryTd = $(".layui-table td[data-field=itemCategory] .layui-table-cell");
                    giftCategoryTd.each(function(index, item){
                        var othis = $(item);
                        var giftCate = othis.text();
                        if(giftCate == "mobile_charge"){
                            othis.text("话费");
                        }else if(giftCate == "mobile_data"){
                            othis.text("流量");
                        }else if(giftCate == "member_vip"){
                            othis.text("会员");
                        }else{
                            othis.text("-");
                        }
                    });
                    //物品状态
                    var giftyStatus = $(".layui-table td[data-field=enableFlag] .layui-table-cell");
                    giftyStatus.each(function(index, item){
                        var othis = $(item);
                        var giftCate = othis.text();
                        if(giftCate == "Y"){
                            othis.text("启用");
                        }else if(giftCate == "N"){
                            othis.text("禁用");
                        }
                    });
                    //物品有效期
                    var giftValidDate = $(".layui-table td[data-field=startDate] .layui-table-cell");
                    var date = $.myTime.CurTime();
                    var curDate = $.myTime.UnixToDate(date, true);
                    giftValidDate.each(function(index, item){
                        var othis = $(item);
                        if(res.data[index].endDate < curDate){
                            othis.html("<span>"+res.data[index].startDate + " 至 </span><span style='color:red;'>" + res.data[index].endDate+"</span>");
                        }else{
                            othis.html("<span>"+res.data[index].startDate + " 至 </span><span>" + res.data[index].endDate+"</span>");
                        }
                    });
                }
            });

        },
        opts:function(type, data){
            if(type == "add"){
                var confirmIndex = layer.confirm(new EJS({url:"./ejs/addSelect.ejs"}).render(data),{
                    area:["350px","300px"],
                    title:"新增物品类型"
                },function(index){
                    $('.node-submit').trigger('click');
                });
                form.render();
                form.on('submit(seeAddRow)', function (obj) {
                    console.log("data=>",data);
                    custFuns.itemOpts(true,obj.field.giftType,data);
                    layer.close(confirmIndex);
                    return false;
                });

            }else if(type == "edit"){
                custFuns.itemOpts(false,data.giftType,data);
            }
        },
        itemOpts:function(isAdd,obj,data){
            var tstr = isAdd ? "新增":"修改";
            var html = new EJS({url:"./ejs/addItem.ejs"}).render({"data":data});
            var formIndex = layer.open({
                title:tstr+custFuns.formatType(obj)+"设置",
                type:1,
                content:html,
                area:["650px","320px"],
                btn:["确认","取消"],
                yes: function(index, layero){
                //按钮【按钮一】的回调
                    form.render();
                    if(isAdd){
                        $('.addPrize').trigger('click');
                        form.on('submit(addPrize)',function(data){
                            layer.msg(JSON.stringify(data));
                            console.log("2222222222")
                            return false;
                        });

                    }else{
                        $('.editPrize').trigger('click');
                        form.on('submit(editPrize)',function(data){
                            layer.msg(JSON.stringify(data));
                            console.log("11111111")
                            return false;
                        });

                    }
                }
                ,btn2: function(index, layero){
                //按钮【按钮二】的回调
                
                //return false 开启该代码可禁止点击该按钮关闭
                }
            },function(){
                
            });
            form.render();
            laydate.render({
                elem:"#date1",
                type:"datetime"
            });
            laydate.render({
                elem:"#date2",
                type:"datetime"
            });
        },
        formatType:function(type){
            var str = "";
            if(type == "real"){
                str = "真实物品";
            }else if(type == "virtual"){
                str = "虚拟物品";
            }else if(type == "interface"){
                str = "接口物品";
            }else if(type == "withGold"){
                str = "赠金";
            }else if(type == "analogCoin"){
                str = "模拟币";
            }else if(type == "tokenCoin"){
                str = "代币";
            }
            return str;            
        }

    };
    //事件处理函数
    var evtFuncs = {
        addItem:function(){
            custFuns.opts("add",{});
        },
        delItem:function(){
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
        detailItem:function(){

        },
        codeManage:function(){
            $.ajax({
                url:"/api/codelist.json",
                success:function(res){
                    var columns = [
                        {type:"checkbox"},
                        {type:"numbers",title:"序号"},
                        {field:"code",title:"串码"},
                        {field:"activityName",title:"活动名称"},
                        {field:"taskName",title:"任务名称"},
                        {field:"accountNo",title:"客户账号"},
                        {field:"status",title:"状态"}
                    ];
                    //过滤
                    table.render({
                        elem:"#codeTab",
                        data:res.data,
                        id: 'codeTab',
                        page: true, //开启分页
                        limit: 50,
                        even: true,
                        cols:[columns]
                    });
                }
            });
            var activeIndex = layer.confirm(new EJS({url:"./ejs/codelist.ejs"}).render(),{
                title:"串码管理",
                area:["800px","600px"]
            },function(){
                
            });
            form.render();

        },
        opts:function(ev){
            if(ev.event == "edit"){
                $.ajax({
                    url:"/api/prizeDetail.json",
                    success:function(res){
                        custFuns.opts("edit",res);  
                    }
                })
            }else if(ev.event == "del"){

            }
        },
        upload:function(evt){
            var othis = $(this);
            if(othis.attr("data-type") == "whitelist"){

            }else{

            }
        },
        search:function(data){
            layer.msg(JSON.stringify(data.field));
            return false;   
        },
        refresh:function(){
            custFuns.loadData();
        }
    };
    //加载入口函数
    init();
});