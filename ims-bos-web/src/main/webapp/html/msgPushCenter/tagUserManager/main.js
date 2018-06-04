//基于layui的模块加载
layui.link('/css/tagUserManager.min.css');
layui.use(['element', 'table', 'searchPanel', 'form','layer','laydate','upload'],function(element,table,searchPanel,form,layer,laydate,upload){
  element = layui.element;
  table = layui.table;
  searchPanel = layui.searchPanel;
  form = layui.form;
  layer = layui.layer;
  laydate = layui.laydate;
  upload = layui.upload;
    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
      custFuns.loadData();
      bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
      form.on("submit(search)",evtFuns.search);
      $(".layout-2").on("click",".layui-btn",evtFuns.opts);
    };
    //自定义函数
    var custFuns = {
      loadData:function(params){
        if(params == "undefined"){
            params = {};
        }   
        params = $.extend(params,orderBy);
        var columns = [[ //表头
            {type:'numbers'},
            {type: 'checkbox'},
            {field: 'accountNo', title: '客户账号'},
            {field: 'tagCode', title: '标签名称',width:180},
            {field: 'tagTypeName', title: '标签类型'},
            {field: 'importMethodName', title: '导入方式'},
            {field: 'mobile', title: '客户手机号'},
            {field: 'email', title: '邮箱'},
            {field: 'wxAccountNo', title: '微信号'},
            {field: 'platform', title: '平台'},
            {field: 'env', title: '账号类型'},
            {field: 'updateDate', title: '更新时间',sort:true,width:180},
        ]];
        //tagCustomerList
        table.render({
          url: "/js/api/msgPushCenterJson/tagCustomerList.json",
          where:params,
          cols: columns,
          cellMinWidth:120
        });
      },
      uploadItem:function(){
        var config = {
            type:1,
            title:"导入客户",
            btn:"上传",
            area:["auto","auto"]
        };
        var content = new EJS({url:"./ejs/upload.ejs"}).render();
        layer.confirm(content,config,function(index){
          $("#uploadBtn").trigger("click");
        }); 
        form.render();
        custFuns.uploadFile();
      },
      addItem:function(){
        var config = {
            type:1,
            title:"新建推送消息",
            btn:"新增",
            area:["auto","auto"],
          };
        var content = new EJS({url:"./ejs/pushMsg.ejs"}).render();
        layer.confirm(content,config,function(index){
          $(".node-submit").trigger("click");
        }); 
        form.render();
        laydate.render({
          elem:"#sendDate",
          type:"datetime"
        });
        custFuns.formElementSelect();
        //表单验证
        custFuns.formValidate();
        //提交
        form.on("submit(addPushMsgItem)",function(data){
          var isValid = custFuns.isCheckboxSelect();
          if(isValid){ 
            custFuns.submitForm();
            layer.closeAll();
          }
          return false;
        });
      },
      deleteItem:function(){
        var checkData = table.checkStatus('dataTable').data;
        if(checkData.length){
          var ids = "";
          $.each(checkData,function(index, item){
            ids+=item.id+",";
          });
          ids = ids.replace("/\,$/","");
          $.ajaxInterceptor.post($reqPostUrl.bb,ids,function(res){
            if(res.code != "OK"){
              layer.msg(res.msg); return;
            }
            layer.msg("新增成功");
            evtFuns.updateList();//刷新
          });
        }else{
          layer.msg("请选择要操作的记录");
        }
      },
      formElementSelect:function(){
        form.on("checkbox(channelSel)",function(data){
          var appDom = new EJS({url:"./ejs/app.ejs"}).render();
          var pcuiDom = new EJS({url:"./ejs/pcui.ejs"}).render();
          var isChecked = data.elem.checked;
          if(data.value == "APP"){
            isChecked ? $(appDom).insertAfter($(".channelItem")) : $(".appItem").remove();
          }else if(data.value == "PCUI"){
            isChecked ? $(pcuiDom).insertAfter($(".channelItem")) : $(".pcuiItem").remove();
          }
          form.render();
        });
      },
      isCheckboxSelect:function(){
        var isValid = false;
        var isChecked = $(".channelItem input[type=checkbox]").is(":checked");
        var isAppChecked = $(".channelItem input[type=checkbox][value=APP]").is(":checked");
        var isPcuiChecked = $(".channelItem input[type=checkbox][value=PCUI]").is(":checked");
        if(!isChecked){
          layer.msg("请至少选择一个渠道");
          isValid = false;
          return isValid;
        }
        isValid = custFuns.checkboxSelect('appItem',isAppChecked);
        isValid = custFuns.checkboxSelect('pcuiItem',isPcuiChecked);
        return isValid;
      },
      checkboxSelect:function(type,isSelect){
        if($(".layui-form ."+type).length){
          var isAppItemChecked = $("."+type+" input[type=checkbox]").is(":checked");
          if(isSelect && isAppItemChecked){
            isValid = true;
          }else{
            layer.msg("请至少选择一个"+(type == 'appItem' ? "APP" : "PCUI"));
            isValid = false;
            return isValid;
          }
        }
      },
      formValidate:function(){
        form.verify({
          tagCodeValid:function(val,item){
            if(val != '' &&  !new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(val)){
                return '账号不能包含特殊字符';
            }
            if(val != '' && val.length > 50){
                return '输入内容长度必须介于0-50之间';
            }
          }
        });
      },
      submitForm:function(data){
        $.ajaxInterceptor.post($reqPostUrl.aa,data,function(res){
          if(res.code != "OK"){
            layer.msg(res.msg); return;
          }
          layer.msg("新增成功");
          evtFuns.updateList();//刷新
        });
      },
      uploadFile:function(){
        upload.render({
          elem:"#uploadFile",
          url:$reqPostUrl.actWhiteListUpload,
          accept:'file',
          multiple: false,
          field:"file",
          auto:false,
          bindAction:"#uploadBtn",
          data:{},
          choose:function(obj){
            //将每次选择的文件追加到文件队列
            var files = obj.pushFile();
            obj.preview(function(index, file, result){
              console.log(index); //得到文件索引
              console.log(file.name); //得到文件对象
              $("#uploadFile").val(file.name);
            });
          },
          done:function(res,index,upload){
              if(res.code != "OK"){
                  layer.msg(res.msg); return;
              }
              layer.msg("上传成功");
          },
          error:function(err){
              layer.msg("上传失败"+err); 
          }
        });
      }
    };
    //事件处理函数
    var evtFuns = {
      opts:function(ev){
        var event = $(this).attr("data-opt");
        if(event == "upload"){
          custFuns.uploadItem();
        }else if(event == "add"){
          custFuns.addItem();
        }else if(event == "delete"){
          custFuns.deleteItem();
        }
      },
      search:function(data){
        custFuns.loadData(data.field);
        return false;
      },
      updateList:function(){
        $(".node-search").trigger("click");
      }
    };
    //加载入口函数
    init();
});