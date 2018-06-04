var orderBy = {sort:'updateDate',order:'desc'};
var  $pageNo = 20;
//设置初始化参数
layui.use(['table','form','element','laypage'],function(table,form,element,laypage){
  table = layui.table;
  form = layui.form;
  element = layui.element;
  laypage = layui.laypage;
  var tabH = 600;

  var init = function (){
    custFuns.layout();
    custFuns.tabInit();
    // custFuns.downlist();
  }
  var custFuns = {
    tabInit:function(){
      $(".layout-3").on("click",".layui-edge",evtFuns.orderByFn);
      //table默认option
      table.set({
        elem: '#data-table',
        height: "full-330",
        request:$request,
        cellMinWidth: 60,
        response:$response,
        id: 'dataTable',
        page: true, //开启分页
        limit:$pageNo,
        method:'POST',
        skin:"line",
        done:function(res, curr, count){
            tabbar();
            if(res.code == '10009'){
                //清除缓存数据
                localStorage.removeItem('companyList');
                localStorage.removeItem('userInfo');
                layer.msg(res.msg);
                setTimeout(function(){
                    parent.location.href = "/html/login.html";
                }, 500);
                return;
            };
        }
      }); 
    },
    layout:function(){
      // var contentH = $("body").height();
      // var tabOffsetTop = $(".layout-3").offset().top;
      // tabH = contentH - tabOffsetTop -123;
    },
    downlist:function(){
      //遍历渲染
      $("select.downlist").each(function(index,item) {
        var $this=$(this);
        var $select=$this.next(".layui-form-select");
        $select.addClass("downpanel");
        var $dl=$select.find("dl");
        $(".layui-select-title input",$select).val($this.attr("placeholder"));
        $dl.empty();
        var str="";
        $("option",$this).each(function() {
          str=["<dd>","<input type='checkbox' name='",$(this).val(),"' lay-skin='primary' title='",$(this).text(),"' value='true'>","</dd>"].join("");
          $dl.append(str);
        });
        form.render("checkbox");

      })
      
      $(".downpanel").on("click",".layui-select-title",function(e){
        var $select=$(this).parents(".layui-form-select");
        $(".layui-form-select").not($select).removeClass("layui-form-selected");
        $select.addClass("layui-form-selected");
        e.stopPropagation();
      }).on("click",".layui-form-checkbox",function(e){
        e.stopPropagation();
      });
    }

  }

  var evtFuns = {
    orderByFn:function(evt){
      //包含搜索条件
      if($(this).hasClass("layui-table-sort-asc")){//顺序
        orderBy.order = 'asc';
      }else{//倒序
        orderBy.order = 'desc';
      }
      var field=$(this).closest("th").attr("data-field");
      orderBy.sort=field;
      console.log("order by sort:"+orderBy.sort+",order:"+orderBy.order);
      $(".search").trigger("click");
      $(".node-search").trigger("click");
    }
  }
  init();
});

(function(){
  layui.config({
      base: '/js/extendModules/'
  }).extend({
      searchPanel: 'searchPanel'
  });
})();

