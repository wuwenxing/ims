//基于layui的模块加载
layui.link('/css/userTag.min.css');
layui.use(['element', 'table', 'searchPanel', 'form','layer','laydate'],function(element,table,searchPanel,form,layer,laydate){
  element = layui.element;
  table = layui.table;
  searchPanel = layui.searchPanel;
  form = layui.form;
  layer = layui.layer;
  laydate = layui.laydate;
    //全局变量申明
    var curData = null;
    var companyPrefix = null;
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
            {field: 'tagCode', title: '标签名称'},
            {field: 'tagTypeName', title: '标签类型'},
            {field: 'remark', title: '备注'},
            {field: 'enableFlag', title: '状态',templet: function(d){
                return d.enableFlag == 'Y' ? '是':'否'; 
            }}
        ]];
        //
        table.render({
          url: "/js/api/msgPushCenterJson/tagList.json",
          where:params,
          cols: columns,
          cellMinWidth:100
        });

      },
      addItem:function(){
        var config = {
            type:1,
            title:"新建标签",
            btn:"新增",
            area:["auto","auto"],
          };
        companyPrefix = JSON.parse(localStorage.getItem("userInfo")).companyCode+"_";
        var content = new EJS({url:"./ejs/addItem.ejs"}).render({company:companyPrefix});
        layer.confirm(content,config,function(index){
          $(".node-submit").trigger("click");
        }); 
        custFuns.tagTypeSelect();

        laydate.render({
          elem:"#cashDate",
          type:"datetime"
        });
        laydate.render({
          elem:"#tradeDate",
          type:"datetime"
        });
        //保留前缀
        $("#tagCode").bind("input propertychange",evtFuns.tagCodeChange);

        form.render();
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
      tagTypeSelect:function(){
        form.on("select(tagTypeItem)",function(data){
          var rulesDom = new EJS({url:"./ejs/rules.ejs"}).render();
          if(data.value == "1"){
            $(rulesDom).insertAfter($(".tagStatusItem"));
            laydate.render({
              elem:"#cashDate",
              type:"datetime"
            });
            laydate.render({
              elem:"#tradeDate",
              type:"datetime"
            });
            form.render();
          }else{
            $(".ruleItem").remove();
          }
        });
        //表单验证
        custFuns.formValidate();
        //提交
        form.on("submit(addItem)",function(data){
          custFuns.submitForm();
          layer.close();
        })
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
          },
          daysItem:function(val, item){
            if(isNaN(val)){
              return "请输入正整数";
            }
            if(!isNaN(val) && val > 30){
              return "天数不得大于30天";
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
      }
    };
    //事件处理函数
    var evtFuns = {
      opts:function(ev){
        var event = $(this).attr("data-opt");
        if(event == "add"){
          custFuns.addItem();
        }else if(event == "delete"){
          custFuns.deleteItem();
        }else if(event == "view"){
          
        }else if(event == "refresh"){
          evtFuns.updateList();
        }
      },
      search:function(data){
        custFuns.loadData(data.field);
        return false;
      },
      updateList:function(){
        $(".node-search").trigger("click");
      },
      tagCodeChange:function(ev){
        var prefix = $(this).val().substring(0,3);
        if(prefix != companyPrefix){
          $(this).val(companyPrefix);
        }
      }
    };
    //加载入口函数
    init();
});