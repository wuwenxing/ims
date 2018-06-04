layui.use(['layer','form','element'], function(layer,form,element){
  var layer = layui.layer
 ,form = layui.form
 ,element = layui.element;
   //遍历渲染
  //  $("select.downlist").each(function(index,item) {
  //    var $this=$(this);
  //    var $select=$this.next(".layui-form-select");
  //    $select.addClass("downpanel");
  //    var $dl=$select.find("dl");
  //    $(".layui-select-title input",$select).val($this.attr("placeholder"));
  //    $dl.empty();
  //    var str="";
  //    $("option",$this).each(function() {
  //      str=["<dd>","<input type='checkbox' name='",$(this).val(),"' lay-skin='primary' title='",$(this).text(),"' value='true'>","</dd>"].join("");
  //      $dl.append(str);
  //    });
  //    form.render("checkbox");
  //  });
   
  //  $(".downpanel").on("click",".layui-select-title",function(e){
  //    var $select=$(this).parents(".layui-form-select");
  //    $(".layui-form-select").not($select).removeClass("layui-form-selected");
  //    $select.addClass("layui-form-selected");
  //    e.stopPropagation();
  //  }).on("click",".layui-form-checkbox",function(e){
  //    e.stopPropagation();
  //  });

  $("select.downlist").each(function(index,item) {
    var $this=$(this);
    var $select=$this.next(".layui-form-select");
    $select.addClass("downpanel");
    var $dl=$select.find("dl");
    $(".layui-select-title input",$select).val($this.attr("placeholder"));
    $dl.empty();
    var str="";
    $this.find("option").each(function(idx,itm) {
      if(idx != 0){
        str=["<dd>","<input type='checkbox' name='",$(this).val(),"' lay-skin='primary' title='",$(this).text(),"' value='true'>","</dd>"].join("");
        $dl.append(str);    
      }
    });
    form.render("checkbox");
  });
  
  var vals = [];
  $(".downpanel").on("click",".layui-select-title",function(e){
    var $select=$(this).parents(".layui-form-select");
    $(".layui-form-select").not($select).removeClass("layui-form-selected");
    $select.addClass("layui-form-selected");
    e.stopPropagation();
  }).on("click",".layui-form-checkbox",function(e){
    e.stopPropagation();
    var $select=$(this).parents(".layui-form-select");
    var selectTitle = $select.find(".layui-select-title input");
    if($(this).parent().find("input").prop("checked")){
        vals.push($(this).find("span").text());
    }else{
        var delIndex = vals.indexOf($(this).find("span").text());
        vals.splice(delIndex,1);
    }
    selectTitle.val(vals.join(','));
    $select.prev().attr("data-value",vals.join(','));//给select赋值
  });

});