layui.use(['tree','form'],function(tree,form){
    tree = layui.tree;
    form = layui.form;

    //全局变量申明
    var curData = null;
    var addData = null;
    var g_data = null;

    //文件入口函数
    var init = function(){
        bindEvents();
        custFuns.loadData();
    };
    //事件绑定
    var bindEvents = function(){
        $(".refreshBtn").bind("click",custFuns.loadData);
        $(".addBtn").bind("click",evtFuns.addItem);
        $(".delBtn").bind("click",evtFuns.delItem);
        $(".unfoldBtn").bind("click",evtFuns.unfoldItem);
        $(".foldBtn").bind("click",evtFuns.foldItem);
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            $.ajax({
                url:$reqPostUrl.sysMenuManList,
                type:'POST',
                success:function(res){
                    if(res.status){
                        g_data = res.data;//文件夹数据
                        console.log("res",res);
                        // custFuns.loadTree(res);
                    }
                },
                error:function(error){

                }
            });
        },
        loadTree:function(res){
            $(".tree").empty();//清空上次加载的dom
            layui.tree({
                elem:".tree",
                skin: 'shihuang',
                nodes:res.data, 
                click:function(data){
                    curData = data;
                    $.extend(data,{"pattern":"normal"});
                    //填充数据
                    var rightHtml = new EJS({url:"./menuSetting-table.ejs"}).render(data);
                    $(".right").html(rightHtml);
                    //排序增加
                    $(".right .add").bind("click",function(){
                        var sort = $(".right .sort").val();
                        sort++;
                        $(".right .sort").val(sort);    
                    });
                    $(".right .decrease").bind("click",function(){
                        var sort = $(".right .sort").val();
                        sort--;
                        if(sort == 0){
                            $(".right .sort").val(0);      
                            return;
                        }
                        $(".right .sort").val(sort);
                    });
                    form.render();
                    $(".right").addClass("layui-show");
                    form.on("submit(menu-form)",function(){
                        alert("提交");
                        return false;//阻止页面刷新
                    });
                }
            });
        },
        handleData:function(id,originData,callback,opts){//查找id
            for(var i=0;i<originData.length;i++){
                var sub = originData[i];
                if(sub.id == id){
                    switch(opts){
                        case("spread"):
                            sub = curData;//重新赋值
                            break;
                        case("add"):
                            //sub = menuSet.pramas.curData;//重新赋值
                            var re = /(\d_*\d)_(\d)$/;
                            var front = sub.id.replace(re,"$1");
                            addData["id"] = front+"_"+(originData.length-0+1);
                            var type = addData["type"];
                            var curType = curData["type"]; 
                            if(type == "1"){//如果当前添加的数据是菜单类型
                                addData["children"] = [];
                            }
                            if(curType == "1"){
                                curData["children"].push(addData);
                            }else{
                                originData.push(addData);
                            }
                            break;
                        case("del"):
                            originData.splice(i,1);//删除
                            break;
                        default:
                            sub = curData;//寻找父级文件夹名称
                            break;
                    }
                    break;
                }
                if(sub.children){
                    callback(id,sub.children,custFuns.handleData,opts);
                }
            }
        },
        treeSpread:function(data,callback,status){
            data["spread"] = status;
            if(data.children){
                for(var i=0;i<data.children.length;i++){
                    var sub = data.children[i];
                    if(!sub.children){
                        continue;
                    }
                    //sub["spread"] ? (sub["spread"]=!sub["spread"]) : (sub["spread"] = true); 
                    sub["spread"] = status;
                    callback(sub,custFuns.treeSpread,status);
                }
            }
        },
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            if(curData){
                var addTab = new EJS({url:"/js/systemSetting/menuSetting-table.ejs"}).render({"pattern":"add"});
                var index = layer.open({
                    type:1,
                    area:["650px"],
                    title:"新增菜单",
                    content:addTab,
                    success:function(){
                        //绑定提交表单的元素--确定 attributes
                        $(".layui-layer-btn0")[0].setAttribute("lay-submit","");
                        $(".layui-layer-btn0")[0].setAttribute("layui-filter","add-form");

                        $(".add-form .add").bind("click",function(){

                        });

                        $(".add-form .decrease").bind("click",function(){

                        });
                        form.render();
                        form.on("submit(add-form)",function(data){
                            addData = data.field;
                            custFuns.handleData(curData.id,g_data,custFuns.handleData,"add");
                            custFuns.loadTree({"data":g_data});
                            layer.close(index);
                            return false;
                        });
                    },
                    btn:["确定","取消"],
                    yes:function(){//确定
                        //代理提交按钮
                        $(".realBtns .layui-btn").unbind("click").bind("click",function(){
                            console.log("delegate");
                        });
                        $(".realBtns .layui-btn").click();
                    },
                    btn2:function(){//取消

                    }

                });
            }
            
        },
        delItem:function(){
            if(curData){
                custFuns.handleData(curData.id,g_data,custFuns.handleData,"del");            
                custFuns.loadTree({"data":g_data});
            }
        },
        unfoldItem:function(){
            if(curData){
                custFuns.treeSpread(curData,custFuns.treeSpread,true);
                custFuns.handleData(curData.id,g_data,custFuns.handleData,"spread");            
                custFuns.loadTree({"data":g_data});
            }
        },
        foldItem:function(){
            if(curData){
                custFuns.treeSpread(curData,custFuns.treeSpread,false);
                custFuns.handleData(curData.id,g_data,custFuns.handleData,"spread");
                custFuns.loadTree({"data":g_data});
            }
        }
    };
    //加载入口函数
    init();
});