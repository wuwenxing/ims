//基于layui的模块加载
layui.use(['element', 'table', 'searchPanel', 'form','upload'],function(element,table,searchPanel,form){
  element = layui.element;
  table = layui.table;
  searchPanel = layui.searchPanel;
  form = layui.form;
  upload = layui.upload;

    //全局变量申明
    var curData = null;
    var curField = null;
    var btns = '';
    var initParams={itemNumber:'',itemName:'',status:''};
    var json = null;
    //文件入口函数
    var init = function(){
      
     json=queryToJson(window.location.href);
   
     if(json.itemNumber!=undefined && json.itemName!=undefined){
    	 
    	 $("#itemNumber").val(json.itemNumber);
    	 $("#itemName").val(json.itemName);
    	 
    	initParams.itemNumber=json.itemNumber;
    	initParams.itemName=json.itemName;
    
      custFuns.loadData(initParams);
     }
     
      var uploadInst = upload.render({
          elem: '#upload', //绑定元素
          url: reqPostUrl.actStringCodeUpload+json.itemNumber,
          accept: 'file', //普通文件
          method: 'POST',
          exts: 'xlsx|xls', //只允许上传execl 文件
          done: function(res){
        	  if(res.code!=$response.statusCode){
            		layer.msg(res.msg);
            		return false;
              }
        	  layer.msg("串码导入完成");
        	  custFuns.loadData(initParams);
          },
          error:function(index, upload){
        	  console.log("err",index, upload);
          }
        });
      
      bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
      form.on("select(status)",evtFuns.statusChange);
    	form.on('submit(search)', evtFuns.search);
    	form.on('submit(export)', evtFuns.exportExcel);
      $(".node-edit").bind("click",evtFuns.edit);
      $(".node-del").bind("click",evtFuns.del);
      $(".node-template-download").bind("click",evtFuns.templateDownload);
      $(".node-refresh").bind("click",evtFuns.refresh);
    };
    //自定义函数
    var custFuns = {
      initLabVal:function(){
    	//拉取串码个数
     	//  $.ajax({
      //         url:reqPostUrl.actStringCodeCount,
      //         type: 'POST',
      //         data:{itemNumber:json.itemNumber,status:1},//未使用
      //         success:function(res){
      //        	 noUseStringCode=res.data;
      //        	 $("#labNoUse").text(noUseStringCode);
      //         }
      //     });
     	 
     	 $.ajaxInterceptor.ajax({
              url:reqPostUrl.actStringCodeCount,
              type: 'POST',
              data:{itemNumber:json.itemNumber},//全部
              success:function(res){
             	 totalStringCode=res.data;
  
             	 $("#labTotal").text(totalStringCode);
              }
          });
      },

      loadData:function(params){
          if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
          var columns =[
              {type:'numbers',width:60},
              {type:"checkbox"},
              {field:"stringCode",title:"串码",width:250},
              {field:"activityPeriods",title:"活动编号"},
              {field:"activityName",title:"活动名称"},
              {field:"accountNo",title:"客户账号"},
              {field:"status",title:"串码状态",templet:function(e){ return e.status=='1'?'未使用':'使用'}},
              {field:"createDate",title:"创建时间",align:"center",width:200},
              {field:"updateDate",title:"最后修改时间",align:"center",sort:true,width:200}
              ];
          table.render({
              where:params,
              url:reqPostUrl.actStringCodePage,
              cols: [columns]
          });
          
     	    custFuns.initLabVal();
      	}
    };
    //事件处理函数
    var evtFuns = {
      search:function(data){
    	  custFuns.loadData(data.field);
    	  curField = data.field;
    	  return false;
      },
      exportExcel:function(data){
    	  var itemNumber=$("#itemNumber").val();
    	  var itemName=$("#itemName").val();
    	  var status=$("#status").val();


    	  var url=reqPostUrl.actStringCodeExport+"?itemName="+itemName+"&itemNumber="+itemNumber+"&status="+status+"&order="+orderBy.order+"&sort="+orderBy.sort;;
    	  window.location.href = url;
    	  return false;
      },
      del:function(){
        var checkStatus = table.checkStatus("dataTable"),
        data = checkStatus.data;
    
        if(data.length){
          var list=[];
          $.each(data, function(key, obj) {
        	  list.push(obj.id);
          });
          $.ajaxInterceptor.ajax({
              url:reqPostUrl.actStringCodeDel+list.join(),
              type: 'POST',
              success:function(res){
                if(res.code!=$response.statusCode){
              		layer.msg(res.msg);
              		return false;
              	}
              	custFuns.loadData(initParams);
              	layer.msg(res.msg);
                layer.close();
                return true;
              },  
              error:function(err){
                  layer.msg(err);
                  return false;
              }
          });
        }else{
          layer.msg("请选择要删除的数据");
        }
        return false;

      },
      templateDownload:function(){
    	  //模板文件下载
    	  document.location.href=reqPostUrl.actStringCodeTemplate; 
      },
      refresh:function(){
        initParams.status = $("#status").val();
        custFuns.loadData(initParams);
    	  return false;
      },
      edit:function(){
        var checkStatus = table.checkStatus("dataTable"),
        data = checkStatus.data;
        if(data.length==1){
          if(data[0].status==1){
            //未使用状态才可以修改
        	  var config = {};
               config.title = '<div><i class="layui-icon">&#xe642;</i>&nbsp;串码修改</div>';
               config.btn = ['保存','取消'];
              
              $.extend(config, {
                  area: ['350px', '200px'],
                  resize: false
              });
              
        	  var confirmIndex = layer.confirm(new EJS({url:"./ejs/addCode.ejs"}).render({data:data[0]}),config,function(index){
                  $('.node-submit').trigger('click');
              });
       
              form.on('submit(editCode)', function (data) {
            	  
            	  $.ajaxInterceptor.ajax({
                      url:reqPostUrl.actStringCodeSave,
                      type:'POST',
                      data:{id:data.field.id,stringCode:data.field.stringCode},
                      success:function(res){ 
                    	  
                    	if(res.code!=$response.statusCode){
                      		layer.msg(res.msg);
                      		return false;
                      	}
                      	
                      	
                      	layer.msg(res.msg);
                        layer.close(confirmIndex);
                        custFuns.loadData();
                        
                      	
                      },  
                      error:function(err){
                      	layer.msg(err);
                      }
                  })
                  layer.close(confirmIndex);
                  return false;
              });
        
          }else{
        	layer.msg("只允许修改未使用的串码!");
            return false;
          }
        }else if(data==""){
        	layer.msg("请选择要修改的数据");
        	return false;
        }else{
        	layer.msg("一次只能修改一行数据");
        	return false;
        }
        return false;
      },
      statusChange:function(data){
        initParams.status = data.value;
        custFuns.loadData(initParams);
      }
    };
    //加载入口函数
    init();
});