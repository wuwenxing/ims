//基于layui的模块加载
layui.link('/css/');//加载css文件
layui.use(['element'],function(element){
    element = layui.element;

    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
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