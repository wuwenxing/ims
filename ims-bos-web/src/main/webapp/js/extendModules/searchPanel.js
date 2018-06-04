layui.define(function(exports){
    var ELEM = 'layui-searchPanel';
    var eleTitle =  $('.'+ ELEM).find('.layui-searchPanel-title'),elemCont = $('.'+ ELEM).find('.layui-searchPanel-content'),isNone = elemCont.css('display') === 'none'
    eleTitle.find('.layui-searchPanelMore-icon').remove();
    eleTitle.append('<i class="layui-icon layui-searchPanelMore-icon">'+ (isNone ? '&#xe61a;' : '&#xe619;') +'</i>');
    // eleTitle.off('click', call.collapse).on('click', call.collapse);
    eleTitle.bind('click',function () {
        elemCont.toggle();
        eleTitle.find('.layui-searchPanelMore-icon').remove();
        var isNone = elemCont.css('display') === 'none';
        eleTitle.append('<i class="layui-icon layui-searchPanelMore-icon">'+ (isNone ? '&#xe61a;' : '&#xe619;') +'</i>');
    });

    exports('searchPanel');
});