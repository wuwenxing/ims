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
      bindEvents();
    };
    //事件绑定
    var bindEvents = function(){

    };
    //自定义函数
    var custFuns = {

    };
    //事件处理函数
    var evtFuns = {

    };
    //加载入口函数
    init();
});