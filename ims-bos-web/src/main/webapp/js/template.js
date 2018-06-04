//基于layui的模块加载
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

    };
    //自定义函数
    var custFuns = {
      loadData:function(pramas){
        $.post(url,pramas,function(res){
          if(res.code != "OK"){
            layer.msg(res.msg);
            return;
          }
        });
        table.render({
          elem: '#data-table',
          height: 'full-228',
          url: $reqPostUrl.sysUserManList,
          id: 'dataTable',
          page: true, //开启分页
          method:'POST',
          request:$request,
          where:params,//额外的data
          response:$response,
          cols: [[ //表头
              {type: 'checkbox', width: 60},
              {title: '操作', toolbar: '#tableDataActive', width: 400 ,align:"center"},
              {field: 'userNo', title: '账号',width:150},
              {field: 'userName', title: '姓名', sort: true,width:150},
              {field: 'roleName', title: '角色',  sort: true,width:100},
              {field: 'enableFlag', title: '状态', sort: true,width:80,templet: function(d){
                  return d.enableFlag == 'Y' ? '是':'否'; 
              }},
              {field: 'updateDate', title: '更新时间',  sort: true,width:200},
              {field: 'companyName', title: '所属公司', sort: true,width:200}
          ]],
          done:function(res, curr, count){
          }
      });
      }
    };
    //事件处理函数
    var evtFuns = {

    };
    //加载入口函数
    init();
});