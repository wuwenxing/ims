//基于layui的模块加载
layui.link("/css/keyValue.min.css");
layui.use(['element', 'table', 'searchPanel', 'form'],function(element,table,searchPanel,form){
    element = layui.element;
    table = layui.table;
    searchPanel = layui.searchPanel;
    form = layui.form;
    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-add").bind("click",evtFuns.addItem);
        $(".node-delete").bind("click",evtFuns.delItem);
        $(".node-refresh").bind("click",evtFuns.update);
        table.on("tool(dataTable)",evtFuns.tabOpts);
    };
    //自定义函数
    var custFuns = {
        loadData:function(params){
        	params && (params = Object.assign({sort:"updateDate",order:"desc"},params));
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: reqPostUrl.baseKeyValList,
                //url: '/api/goodsManage.json',
                id: 'dataTable',
                page: true, //开启分页
                method:'POST',
                request:$request,
                //where:params,//额外的data
                response:$response,
                cols:[[
                    {type:'checkbox'},
                    {title:"操作",toolbar:"#tableDataActive",align:"center"},
                    {field:"dataKey",title:"键",align:"center"},
                    {field:"dataVal",title:"值",align:"center"},
                    {field:"tag",title:"标签"},
                    {field:"remark",title:"备注"},
                    {field:"enableFlag",title:"状态",align:"center",sort:true,width:80},
                    {field:"createUser",title:"创建人",align:"center"},
                    {field:"createDate",title:"创建时间",align:"center",sort:true},
                    {field:"updateUser",title:"最后修改人",align:"center"},
                    {field:"updateDate",title:"最后修改时间",align:"center",sort:true}
                ]]
            })
        },
        opts:function(type ,data){
        	 var config = {};
             if (type == 'edit') {
                 config.title = '<div><i class="layui-icon">&#xe642;</i>&nbsp;修改内容</div>';
                 config.btn = ['保存','取消'];
             } else if (type == 'detail') {
                 config.title = '<div><i class="layui-icon">&#xe63c;</i>&nbsp;查看详情</div>';
                 config.btn = ['关闭','取消'];
             } else if (type == 'add') {
                 config.title = '<div><i class="layui-icon">&#xe654;</i>&nbsp;新增</div>';
                 config.btn = ['新增', '取消'];
             } 
             
             $.extend(config, {
                 area: ['750px', '400px'],
                 resize: false
             });
        	
            var confirmIndex = layer.confirm(new EJS({url:"./addItem.ejs"}).render(data),{
                area:["700px","450px"]
            },function(index){
                $('.node-submit').trigger('click');
            });
            form.render("select");
      
            form.on('submit(seeAddRow)', function (data) {
                
            	if(type == "add"  || type=="edit"){
            		custFuns.save(data);
            	}
                layer.close(confirmIndex);
                return false;
            });
        },
        save:function(data){
        	$.ajax({
                url:reqPostUrl.baseKeyValSave,
                type:'POST',
                data:data.field,
                success:function(res){
                	custFuns.loadData();
                	layer.msg("保存成功");
                    layer.close();
                    
                },  
                error:function(err){
                	layer.msg("保存失败");
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        addItem:function(){
            custFuns.opts("add",{});
        },
        delItem:function(){
            var checkStatus = table.checkStatus("dataTable"),
                data = checkStatus.data;
            if(data.length){
                var index = layer.confirm("确定删除?",function(){
                    layer.close(index);
                });
            }else{
                layer.msg("请先选择要删除的数据");
            }
        },
        update:function(){
            custFuns.loadData();
        },
        tabOpts:function(obj){
            if(obj.event == "edit"){
                custFuns.opts("edit",{});
            }else if(obj.event == "del"){

            }
        }
    };
    //加载入口函数
    init();
});