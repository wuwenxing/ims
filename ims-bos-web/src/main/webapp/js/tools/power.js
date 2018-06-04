
var path,queryJson,original,powerlist;
    path = parseUrl(location.href);
    queryJson = path.queryJson;
    if(typeof(queryJson.parentId) != 'undefined'){
      original = parent.that.menuConfig[queryJson.parentId][queryJson.id];
    }else{
      original = filterInnerPage();
    }
    powerlist = null;

    filterPower();  
    
$(".layout-3").off("click").on("click",".layui-edge",orderByFn);

//表格操作
function tabbar(powerlist){
  getOptions($(".layui-table").eq(1).find(".layui-btn"));
}

function orderByFn(){
  //包含搜索条件
  if($(this).hasClass("layui-table-sort-asc")){//顺序
    orderBy.order = 'asc';
  }else{//倒序
    orderBy.order = 'desc';
  }
}

//获取操作权限
function getOptions(dom){
  if(powerlist){
    for(var attr in powerlist){
      dom.each(function(idx, item){
        item = $(item);
        if( typeof(powerlist[item.attr("data-opt")]) == "undefined" ){
          item.removeClass("hide");
        }else{
          powerlist[item.attr("data-opt")] && (item.removeClass("hide"));
        }
      });
    }
  }else{
    dom.removeClass("hide");
  }
}

// function getOptions(dom){
//   if(powerlist){
//     for(var attr in powerlist){
//       dom.each(function(idx, item){
//         item = $(item);
//         if( typeof(powerlist[item.attr("data-opt")]) == "undefined" ){
//           item.removeClass("hide");
//         }else{
//           powerlist[item.attr("data-opt")] && (item.removeClass("hide"));
//         }
//       });
//     }
//   }else{
//     dom.removeClass("hide");
//   }
// }

function filterPower(){
  if(original.children){
    powerlist = {};
    $.each(original.children,function(index, item){
      powerlist[item.id] = item.checked;
    });
    getOptions($(".layout-2 .layui-btn"));
  }else{
    $(".layout-2 .layui-btn").removeClass("hide");
    $(".layui-table").eq(1).find(".layui-btn").removeClass("hide");
  }
}

//筛选掉未定义的field
function filterTableColumns(data,cols){
  for(var i=0;i<cols.length;i++){
      if(cols[i].field){
          var notHas = data.every(function(element, index, array){
              return (typeof(element[cols[i].field]) == "undefined");//代表没有这一列
          });
          if(notHas){
              cols.splice(i,1);
              continue;
          }
      }
  };
  return cols;    
}

//查找内页权限列表
function filterInnerPage(){
 var result = null;
 for(var attr in parent.that.menuConfig){ 
  for(var item in parent.that.menuConfig[attr]){
    if(typeof(parent.that.menuConfig[attr][item]["attributes"]) != "undefined"){
      var menuUrl = parent.that.menuConfig[attr][item]["attributes"]["menuUrl"];
      if(menuUrl != "" && location.pathname.indexOf(menuUrl) != -1){
        result = parent.that.menuConfig[attr][item];
        break;
      }
    }
  }
 }
//  console.log("result",result);
 return result;
}

function loginTest(res){

}