//基于layui的模块加载
layui.link("/css/keyValue.min.css");
layui.link("/css/actCreate.min.css");
layui.use(['element', 'table', 'searchPanel', 'form'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;

    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
      form.render();
      custFuns.loadData();
      bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-search").bind("click", evtFuns.searchData);
        $(".node-add").bind("click",evtFuns.addItem);
        $(".node-refresh").bind("click",evtFuns.refresh);
        table.on('tool(dataTable)', evtFuns.opts);//表格操作
    };
    //自定义函数
    var custFuns = {
        loadData:function(){
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/appMsgTempList.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox',field:"id"},
                    {title: '操作', toolbar: '#tableDataActive', width: 200 ,align:"center"},
                    {field:"code",title:"模板编号",align:"center",width:120},
                    {field:"lang",title:"语言",align:"center",width:100},
                    {field:"tplName",title:"名称",align:"center",width:120},
                    {field:"tplContentTypeTxt",title:"内容类型",align:"center",width:100},
                    {field:"tplContent",title:"内容",align:"center",width:100},
                    {field:"summary",title:"摘要",align:"center",width:100},
                    {field:"pushApp",title:"推送的APP",align:"center",width:100},
                    {field:"pushType",title:"推送对象",align:"center",width:100},
                    {field:"msgShowPosition",title:"展示位置",align:"center",width:100},
                    {field:"otherMsg",title:"其他信息",align:"center",width:100},
                    {field:"remark",title:"备注",align:"center",width:150},
                    {field:"enableFlag",title:"状态",align:"center",width:200},
                    {field:"updateDate",title:"更新时间",align:"center",width:100}
                ]],
                done:function(){
                }
            });
        },
        opts:function(type, data){
          if(type == "edit"){
            var confirmIndex = layer.confirm(new EJS({url: "./ejs/item.ejs"}).render(data), {
              title: "修改",
              area:["800px","350px"]    
            },function(){
                $(".node-submit").trigger("click");
            });
            form.render();
            form.on("submit(item)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });

          }else{
            var confirmIndex = layer.confirm(new EJS({url: "./ejs/item.ejs"}).render(), {
              title: "新增",
              area:["800px","550px"]
            },function(){
                $(".node-submit").trigger("click");
            });
            form.render();
            form.on("submit(item)",function(data){
                layer.msg(JSON.stringify(data.field));
                return false;
            });
          }
        }

    };
    //事件处理函数
    var evtFuns = {
        searchData:function(ev){
          form.on("submit(search)",function(data){
              layer.msg(JSON.stringify(data.field));
              return false;
          });
        },
        opts:function(ev){
          if(ev.event == "edit"){
              $.ajax({
                  url:"/api/appMsgTempDetail.json",
                  success:function(res){
                    custFuns.opts("edit",res);  
                  }
              });
          }else if(ev.event == "del"){
            var activeIndex = layer.confirm(new EJS({url:"./ejs/alt.ejs"}).render(),{
                    title:"删除",
                    area:["300px","250px"]
                },function(){//确定
                    $(".node-del").trigger("click");
                });
                form.render();
                form.on("submit(del)",function(data){
                    layer.msg(JSON.stringify(data.field));
                    return false;
                });
          }
          
        },
        addItem:function(){
          custFuns.opts("add")
        },
        refresh:function(){
          custFuns.loadData();
        }
    };
    //加载入口函数
    init();
});