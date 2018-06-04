layui.use(['tree','form'],function(tree,form){
    tree = layui.tree;
    form = layui.form;

    //全局变量申明
    var curData = null;
    var addData = null;
    var g_data = null;
    var curMenu = null;
    var curLi = null;
    var prevLi = null;
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
        $(".right .add").bind("click",evtFuns.sortAdd);
        $(".right .decrease").bind("click",evtFuns.sortDecrese);
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            var index = layer.load(2);
            $.ajaxInterceptor.post($reqPostUrl.sysMenuManTreeList,{},function(res){
                custFuns.loginTest(res);
                layer.close(index);
                if(res.code != "OK"){
                    layer.msg(res.msg);
                    return;
                }
                g_data = res.data;//文件夹数据
                custFuns.loadTree(res);
            });
        },
        loadTree:function(res){
            $("#tree").empty();//清空上次加载的dom
            layui.tree({
                elem:"#tree",
                skin: 'shihuang',
                nodes:res.data, 
                click:function(data,node){
                    console.log("click-data",data);
                    console.log("node",node);
                    curMenu = data;
                    custFuns.clearPrevLiStyle();
                    curLi = node;
                    prevLi = curLi;
                    custFuns.addActiveStyle();

                    if(data.attributes.menuType == "1"){
                        custFuns.getItemDetail({menuCode:data.id,menuType:1});                    
                    }else{
                        custFuns.getItemDetail({menuCode:data.id,menuType:2,parentMenuCode:data.parentId});                    
                    }
                }
            });
        },
        clearPrevLiStyle:function(){
            if($(prevLi).find("ul").length){
                $(prevLi).children("i").removeClass("active");
                $(prevLi).children("a").removeClass("active");
            }else{
                $(prevLi).removeClass("active");
            }
        },
        addActiveStyle:function(){
            if($(curLi).find("ul").length){
                $(curLi).children("i").addClass("active").siblings().removeClass("active");
                $(curLi).children("a").removeClass("active").addClass("active");
            }else{
                $(curLi).addClass("active");
            }
        },
        getItemDetail:function(pramas){//详情
            var index = layer.load(2);
            $.ajaxInterceptor.post($reqPostUrl.sysMenuManTreeItemDetail, pramas,function(res) {
                custFuns.loginTest(res);
                layer.close(index);
                if(res.code != "OK"){
                    layer.msg(res.msg);
                    return;
                }
                console.log("res",res.data);
                var curParentNodeName = $(curLi).parent().siblings("a").find("cite").text() + "/";
                curData = res.data;
                res.data = $.extend(res.data,{pattern:"normal",parentName:curParentNodeName});
                //填充数据
                var rightHtml = new EJS({url:"./ejs/main.ejs"}).render(res.data);
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
                    if(sort <= 0){
                        $(".right .sort").val(1);      
                        return;
                    }
                    $(".right .sort").val(sort);
                });
                form.render();
                $(".right").addClass("layui-show");
                form.on("submit(menu-form)",function(data){
                    //表单验证
                    if(!curMenu.children){
                        data.field = $.extend(data.field,{
                            menuId:curMenu.attributes.menuId,
                            parentMenuCode:curMenu.parentId,
                        });
                    }
                    custFuns.addMenu(data.field);
                    return false;//阻止页面刷新
                });
            });
        },
        addMenu:function(pramas){
            $.ajaxInterceptor.post($reqPostUrl.sysMenuManAddItem, pramas,function(res) {
                custFuns.loginTest(res);
                if(res.code != "OK"){
                    layer.msg(res.msg);
                    return;
                }
                layer.msg("操作成功");
                if(!!!pramas.menuId){
                    custFuns.loadData();
                } 
            });
        },
        delMenu:function(pramas){
            $.ajaxInterceptor.post($reqPostUrl.sysMenuManDelById,pramas,function(res){
                custFuns.loginTest(res);
                if(res.code != "OK"){
                    layer.msg(res.msg);
                    return;
                }
                layer.msg("操作成功");
                custFuns.loadData();
                $(".right").removeClass("layui-show");
            });
        },
        loginTest:function(res){
            if(res.code == '10009'){
                layer.msg(res.msg);
                setTimeout(function(){
                    parent.location.href = "/html/login.html";
                }, 500);
                return;
            };
        }
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            var data = {"pattern":"add"};
            if(curData){
                data = $.extend(data,{
                    parentMenuCode:curData.menuType == "1" ? curData.menuCode : curData.parentMenuCode,
                    menuCode:curData.menuCode,
                    menuId:'',
                });
            }
            var addTab = new EJS({url:"./ejs/main.ejs"}).render(data);
            var index = layer.open({
                type:1,
                area:["auto","auto"],
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
                        custFuns.addMenu(data.field);
                        layer.close(index);
                        return false;
                    });
                },
                btn:["新增"],
                yes:function(){//确定
                    //代理提交按钮
                    $(".realBtns .layui-btn").unbind("click").bind("click",function(){
                        console.log("delegate");
                    });
                    $(".realBtns .layui-btn").click();
                }

            });
            $(".layui-layer .add").bind("click",function(){
                var sort = $(".layui-layer .sort").val();
                sort++;
                $(".layui-layer .sort").val(sort);    
            });
            $(".layui-layer .decrease").bind("click",function(){
                var sort = $(".layui-layer .sort").val();
                sort--;
                if(sort <= 0){
                    $(".layui-layer .sort").val(1);      
                    return;
                }
                $(".layui-layer .sort").val(sort);
            });
            
        },
        delItem:function(){
            if(curMenu){
                if(curMenu.children){
                    layer.msg("请先删除子节点");
                }
                custFuns.delMenu({menuId:curMenu.attributes.menuId});
                return;
            }
            layer.msg("请选择一条数据");
        },
        unfoldItem:function(){//展开
            var parentNode = $(".layui-tree");
            if(curLi){
                parentNode = $(curLi);
            }
            if(!parentNode.find(".layui-tree-spread").hasClass("expanded")){
                parentNode.find(".layui-tree-spread").trigger("click").addClass("expanded");
            }
        },
        foldItem:function(){//收起
            var parentNode = $(".layui-tree");
            if(curLi){
                parentNode = $(curLi);
            }
            parentNode.find(".layui-tree-spread.expanded").trigger("click").removeClass("expanded");
        }
    };
    //加载入口函数
    init();
});