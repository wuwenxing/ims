(function($){
  $.extend({
    ajaxInterceptor:{
        post : function(url,data,success,error){
          var options = {
            url:url,
            data:data,
            success:function(res){
              if(res.code == '10009'){
                  $.ajaxInterceptor.filterFn(res);
              }
              success && success(res);
            },
            error:error,
            type:'POST'
          }
          $.ajax(options);
      },
      get : function(url,data,success,error){
        var options = {
          url:url,
          data:data,
          success:function(res){
            if(res.code == '10009'){
                $.ajaxInterceptor.filterFn(res);
            }
            success && success(res);
          },
          error:error,
          type:'GET'
        }
        $.ajax(options);
      },
      ajax : function(options){
        $.ajax(options);
      },
      filterFn:function(res){
        var maskDiv = document.createElement('div');
            maskDiv.style.width = '120px';
            maskDiv.style.height = '20px';
            maskDiv.style.position = 'fixed';
            maskDiv.style.left = '50%';
            maskDiv.style.top = '50%';
            maskDiv.style.lineHeight = '20px';
            maskDiv.style.padding = '10px 0';
            maskDiv.style.backgroundColor = 'rgba(0,0,0,0.35)';
            maskDiv.style.color = '#fff';
            maskDiv.style.textAlign = 'center';
            maskDiv.style.borderRadius = '4px';
            maskDiv.className = 'maskTips';
            maskDiv.innerHTML = '<div class="msg">'+res.msg+'</div>';
            document.body.appendChild(maskDiv);
            var timer = setTimeout(function(){
              document.body.removeChild(maskDiv);
            },500);
            //清除缓存数据
            localStorage.removeItem('companyList');
            localStorage.removeItem('userInfo');
            var outimer = setTimeout(function(){
                parent.location.href = "/html/login.html";
            }, 1000);
          return;
      }
    }
  })
}(jQuery));