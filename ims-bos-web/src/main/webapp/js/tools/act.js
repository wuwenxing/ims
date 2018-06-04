var ActCom={
	initApprovedActivityPeriod:function(){
        var params={};
        params.proposalStatus="ActHasApproved";
        ActCom.loadData(params);
    },

    initAllActivity:function(callback){
        ActCom.loadData(callback);
    },

    loadData:function(params){
            var callback = '';
            if(typeof(params) == 'function'){
                callback = params;
            }
            params = $.extend(params,{sort:"updateDate",order:"desc"});
            $.ajaxInterceptor.post(baseUrl+"mis/act/list",params,function(res){
                if(res.code != "OK"){
                    layui.layer.msg(res.msg);return;
                }
                var acts=res.data;
                // console.log("res:"+JSON.stringify(res));
                var actName = "<option value=''>--请选择--</option>";
                var actNum = "<option value=''>--请选择--</option>";
                for(var i=0;i<acts.length;i++){
                    actName+="<option value="+acts[i].activityName+">"+acts[i].activityName+"</option>";
                    actNum +="<option value="+acts[i].activityPeriods+">"+acts[i].activityPeriods+"</option>";
                }
                $("#actName").html(actName);
                $("#actNum").html(actNum);
                layui.form.render("select");
                layui.form.on("select(actNum)",function(data){
                    $("#actName").val(data.value);
                    layui.form.render("select");
                    callback && callback(res);
                });
                layui.form.on("select(actName)",function(data){
                    $("#actNum").val(data.value);
                    layui.form.render("select");
                    callback && callback(res);
                });
                callback && callback(res);
            });
    },
    
    loadDataSetLinkage:function(params){
            var callback = '';
            if(typeof(params) == 'function'){
                callback = params;
            }
            params = $.extend(params,{sort:"updateDate",order:"desc"});
            $.ajaxInterceptor.post(baseUrl+"mis/act/list",params,function(res){
                if(res.code != "OK"){
                    layui.layer.msg(res.msg);return;
                }
                var acts=res.data;
                // console.log("res:"+JSON.stringify(res));
                var actName = "<option value=''>--请选择--</option>";
                var actNum = "<option value=''>--请选择--</option>";
                for(var i=0;i<acts.length;i++){
                    actName+="<option value="+acts[i].activityPeriods+">"+acts[i].activityName+"</option>";
                    actNum +="<option value="+acts[i].activityPeriods+">"+acts[i].activityPeriods+"</option>";
                }
                $("#actName").html(actName);
                $("#actNum").html(actNum);
                layui.form.render("select");
                layui.form.on("select(actNum)",function(data){
                    $("#actName").val(data.value);
                    layui.form.render("select");
                    callback && callback(res);
                });
                layui.form.on("select(actName)",function(data){
                    $("#actNum").val(data.value);
                    layui.form.render("select");
                    callback && callback(res);
                });
                callback && callback(res);
            });
	},
	loadDictData:function(ParentDictCode,htmlId,callback){
		//ParentDictCode =MallItemType,
		//htmlId =itemType
        $.ajaxInterceptor.post(
            reqPostUrl.listByParentDictCode,
            {parentDictCode:ParentDictCode},
            function(res){ 
                if(res.code != $response.statusCode){
                    layer.msg(res.msg);
                    return;
                }
                var data =  res.data;
                itemList = res.data;
                var html = "<option value=''>--请选择--</option>";
                for(var i=0;i<data.length;i++){
                    html+="<option value='"+data[i].dictCode+"'>"+data[i].dictNameCn+"</option>";
                }
                $("#"+htmlId).html(html);
                layui.form.render("select");        
                callback && callback(res.data);//回调函数
                return itemList;
            }
        );
	},
    loadInputPlatForm:function(ParentDictCode,htmlId){
        //ParentDictCode =MallItemType,
        //htmlId =itemType
        $.ajaxInterceptor.ajax(
            reqPostUrl.listByParentDictCode,
            {parentDictCode:ParentDictCode},
            function(res){ 
                if(res.code != $response.statusCode){
                    layer.msg(res.msg);
                    return;
                }
                var data =  res.data;
                itemList = res.data;
                var html = "<option value=''>--请选择--</option>";
                for(var i=0;i<data.length;i++){
                    html+='<input type="checkbox"   class="combobox-checkbox">'+data[i].dictCode;
                }
                $("#"+htmlId).html(html);
                layui.form.render("select");           
            }
        )
    }
}

