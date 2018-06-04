//基于layui的模块加载
layui.link("/css/dataDic.min.css");
layui.use(['element', 'table', 'searchPanel', 'laytpl', 'form','tree','layer'], function (element, table, searchPanel, form, layer) {
    element = layui.element;
    table = layui.table;
    form = layui.form;
    layer = layui.layer;
    var init = function () {
        custFuncs.loadData();
        bindEvents();
     
    };
    
    
    //监听工具条
    table.on('tool(dataTable)', function(obj){
      var data = obj.data;
      if(obj.event === 'detail'){
 
    	custFuncs.showData('detail', {data:data,disabled:'disabled', type: 'detail'});
    	  //evtFuncs.detail();
      } else if(obj.event === 'del'){
        layer.confirm('真的删除行数据么?', function(index){
	    	 $.ajax({
	             url:reqPostUrl.sysDelDict,
	             type:'POST',
	             data:{dictId:data.dictId},
	             success:function(res){
	            	 obj.del();
	                 layer.close(index);
	                 layer.msg('删除成功');
	             },  
	             error:function(err){
	            	 layer.msg('删除失败'+err);
	             }
	         })
        	
         
        });
      } else if(obj.event === 'edit'){
    	  
        //layer.alert('编辑行：<br>'+ JSON.stringify(data))
        
        custFuncs.showData('edit', {data:data, type: 'edit'});
        	
      }
    });

    //事件绑定
    var bindEvents = function(){
        $(".node-addDic").bind("click",evtFuncs.addDic);
        $(".node-addDicItem").bind("click",evtFuncs.addDicItem);
       
        table.on('tool(dataTable)', evtFuncs.opts);
        form.on('submit(search)', evtFuncs.search);
    };

    //自定义函数
    var custFuncs = {
		 loadData:function(params){
	            params && (params = Object.assign({sort:"updateDate",order:"desc"},params));
	            table.render({
	            	elem: '#data-table',
	                height: 'full-228',
	                url: reqPostUrl.sysDictList,
	                id: 'dataTable',
	                page: true, //开启分页
	                method:'POST',
	                request:$request,
	                where:params,//额外的data
	                response:$response,
	                cols: [[ //表头
	                	{title: '操作', toolbar: '#tableDataActive', width: 400 ,align:"center"},
	                	{field: 'dictId', title: 'id',width:150},
	                    {field: 'dictCode', title: '编号', width:100},
	                    {field: 'dictNameEn', title: '名称(英文)',width:200},
	                    {field: 'dictNameCn', title: '名称(简体)',width:200},
	                    {field: 'dictNameTw', title: '名称(繁体)',  width:200},
	                    {field: 'enableFlag', title: '状态',width:200},
	                    {field: 'experience', title: '所属公司',width:200}
	                ]]
	           })
	           
	          
		 },
        showData:function(type,data){
            var config = {};
            if (type == 'edit') {
                config.title = '<div><i class="layui-icon">&#xe642;</i>&nbsp;修改内容</div>';
                config.btn = ['保存','取消'];
            } else if (type == 'detail') {
                config.title = '<div><i class="layui-icon">&#xe63c;</i>&nbsp;查看详情</div>';
                config.btn = ['关闭','取消'];
            } else if (type == 'addDic') {
                config.title = '<div><i class="layui-icon">&#xe654;</i>&nbsp;新增字典分组</div>';
                config.btn = ['新增', '取消'];
            } else if (type == 'addDicItem') {
                config.title = '<div><i class="layui-icon">&#xe654;</i>&nbsp;新增字典</div>';
                config.btn = ['新增', '取消'];
            }
            $.extend(config, {
                area: ['750px', '400px'],
                resize: false
            });
            var confirmIndex = layer.confirm(
                new EJS({url: "./ejs/item.ejs"}).render(data), config, function (index) {
                    if (type == 'detail') {
                        layer.close(confirmIndex);
                    } else {
                        $('.node-submit').trigger('click');
                    }
                });
            form.render();
            form.on('submit(seeAddRow)', function (data) {
            	 
                custFuncs.formValidate(data);
            	if(type == "addDic" || type=="addDicItem" || type=="edit"){
            		custFuncs.save(data);
            	}
                layer.close(confirmIndex);
                return false;
            });
        },
        formValidate:function(data){
            form.verify({
            	dictCode:function(value,item){
                    if(value.length<= 0){
                        // result = true;
                        return "编号不能为空"
                    }
                },
                dictNameEn:function(value,item){
                    if(value.length<= 0){
                        // result = true;
                        return "英文名称不能为空"
                    }
                }
                ,dictNameCn:function(value,item){
                	if(value.length<= 0){
                        // result = true;
                        return "简体名称不能为空"
                    }
                },
                dictNameTw:function(value,item){
                	if(value.length<= 0){
                        // result = true;
                        return "繁体名称不能为空"
                    }
                },
                orderCode:function(value,item){
                	if(value.length<= 0){ 
                        return "请输入排序号!"
                    }
                	
                	var reg = new RegExp("^[0-9]*$");
	                 if(!reg.test(value)){
	                	 return "排序号数字!"
	                 }  
                }
            });
        },
        save:function(data){
        	$.ajax({
                url:reqPostUrl.sysSaveDict,
                type:'POST',
                data:data.field,
                success:function(res){
                	custFuncs.loadData();
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
    var evtFuncs = {
            search:function (data) {
                custFuncs.loadData(data.field);
                return false;
            },
            addDic: function () {
                custFuncs.showData('addDic', {data: {dictType:1}, type: 'addDic'});
            },
             
            addDicItem:function () {
                custFuncs.showData('addDicItem', {data: {dictType:2}, type: 'addDicItem'});
            },
            
            download: function () {
            		
            }
        };
    //加载入口函数
    init();
});