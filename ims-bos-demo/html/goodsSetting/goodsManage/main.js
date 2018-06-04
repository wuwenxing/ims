//基于layui的模块加载
layui.link("/css/goodsManage.min.css");
layui.use(['element','form','layer','table','laydate','searchPanel','upload'],function(element,form,layer,table,laydate){
    element = layui.element;
    form = layui.form;
    layer = layui.layer;
    table = layui.table;
    laydate = layui.laydate;
    searchPanel = layui.searchPanel;
    upload = layui.upload;
    //全局变量申明
    var actNameObj = null;
    var prizeNameObj = null;

    //文件入口函数
    var init = function(){
        custFuns.searchEx();
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-add").bind("click",evtFuns.addItem);
        $(".node-del").bind("click",evtFuns.delItem);
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-search").bind("click",evtFuns.search);
    };
    //自定义函数
    var custFuns = {
        searchEx:function(){
            //活动名称
            $.ajax({
                url:"/api/listApprovedAct.json",
                success:function(res){
                    actNameObj = res;
                    var actName = "<option>--请选择--</option>";
                    var actNum = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        actName+="<option value="+res[i].data+">"+res[i].data+"</option>";
                        actNum +="<option value="+res[i].extName+">"+res[i].extName+"</option>";
                    }
                    $("#actName").html(actName);
                    $("#actNum").html(actNum);
                    form.render("select");
                }
            });
            //物品种类
            $.ajax({
                url:"/api/listActItem.json",
                success:function(res){
                    prizeNameObj = res;
                    var prizeName = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        prizeName+="<option>"+res[i].name+"</option>";
                    }
                    $("#prizeName").html(prizeName);
                    form.render("select");
                }
            });
            //
            laydate.render({
                elem:"#startDate",
                type:"date"
            });
            laydate.render({
                elem:"#endDate",
                type:"date"
            });
        },
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/goodsManage.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {title:"操作", width:250, align:'center', toolbar: '#tableDataActive'},
                    {field:"activityName",title:"活动名称",align:"center",sort:true,width:250}, 
                    {field:"mallItemsName",title:"物品名称",align:"center",sort:true,width:250},
                    {field:"refActItemsCode",title:"物品编号",align:"center",sort:true,width:250},
                    {field:"mallItemsTypeName",title:"物品类型",align:"center",sort:true,width:250},
                    {field:"imgUrl",title:"物品图片",align:"center",sort:true,width:250},
                    {field:"enableFlag",title:"物品状态",align:"center",sort:true,width:250},
                    {field:"mallItemsNum",title:"物品数量",align:"center",sort:true,width:250},
                    {field:"startDate",title:"物品有效期",align:"center",sort:true,width:250},
                    {field:"updateDate",title:"修改时间",align:"center",sort:true,width:250}
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
        }
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            var activeIndex = layer.confirm(new EJS({url:"./goodsItem.ejs"}).render({
                "actData":actNameObj,
                "prizeCategory":prizeNameObj
            }),{
                title:"新增",
                area:["800px","500px"]

            },function(){
                $(".add").trigger("click");              
            });
            form.render();
            form.on("submit(add)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
            upload.render({
                elem:"#upload",
                accept: 'images',
                auto: false,
                url: '/upload/',
                multiple: false,
                choose:function(obj){
                    var files = obj.pushFile(); //将每次选择的文件追加到文件队列
                    //图像预览，如果是多文件，会逐个添加。(不支持ie8/9)
                    obj.preview(function(index, file, result){
                        var imgobj = new Image(); //创建新img对象
                        imgobj.src = result; //指定数据源
                        imgobj.className = 'thumb';
                        imgobj.style.width = "150px";
                        imgobj.onclick = function(result) {
                            //单击预览
                            $("#upload").trigger("click");
                        };
                        $("#div_prev").html(imgobj); //添加到预览区域
                        $(".up-btn").hide();
                    });
                },
                done:function(){

                },
                error:function(){

                }
            });
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