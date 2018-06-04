//基于layui的模块加载
layui.link("/css/prizeOutRec.min.css");
layui.use(['element','form','layer','table','laydate','searchPanel'],function(element,form,layer,table,laydate){
    element = layui.element;
    form = layui.form;
    layer = layui.layer;
    table = layui.table;
    laydate = layui.laydate;
    searchPanel = layui.searchPanel;
    //全局变量申明
    var curData = null;

    //文件入口函数
    var init = function(){
        custFuns.searchEx();
        custFuns.loadData();
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-export").bind("click",evtFuns.export);
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-search").bind("click",evtFuns.search);
    };
    //自定义函数
    var custFuns = {
        downlist:function(){  
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
        },
        searchEx:function(){
            //活动名称
            $.ajaxInterceptor.ajax({
                url:"/api/listApprovedAct.json",
                success:function(res){
                    var actName = "<option>--请选择--</option>";
                    var actNum = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        actName+="<option value="+res[i].data+">"+res[i].data+"</option>";
                        actNum +="<option value="+res[i].extName+">"+res[i].extName+"</option>";
                    }
                    $("#actName").html(actName);
                    $("#actNum").html(actNum);
                    form.render('select');
                    custFuns.downlist();
                }
            });
            //物品种类
            $.ajaxInterceptor.ajax({
                url:"/api/listActItem.json",
                success:function(res){
                    var prizeName = "<option>--请选择--</option>";
                    for(var i=0;i<res.length;i++){
                        prizeName+="<option>"+res[i].name+"</option>";
                    }
                    $("#prizeName").html(prizeName);
                    form.render('select');
                    custFuns.downlist();
                }
            });
            //
            laydate.render({
                elem:"#startDate",
                type:"datetime"
            });
            laydate.render({
                elem:"#endDate",
                type:"datetime"
            });
        },
        loadData:function(params){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: '/api/actCreate.json',
                id: 'dataTable',
                page: true, //开启分页
                limit: 50,
                cols:[[
                    {type:'checkbox'},
                    {field:"activityPeriods",title:"活动编号",align:"center",sort:true,width:250},
                    {field:"activityName",title:"活动名称",align:"center",sort:true,width:250},
                    {field:"accountNo",title:"客户账号",align:"center",sort:true,width:250},
                    {field:"chineseName",title:"客户姓名",align:"center",sort:true,width:250},
                    {field:"email",title:"客户邮箱",align:"center",sort:true,width:250},
                    {field:"mobile",title:"客户电话",align:"center",sort:true,width:250},
                    {field:"env",title:"账号类型",align:"center",sort:true,width:250},
                    {field:"platform",title:"平台",sort:true,width:250},
                    {field:"joinTime",title:"参与时间",align:"center",sort:true,width:250},
                    {field:"taskName",title:"任务名称",sort:true,width:120},
                    {field:"reqNum",title:"要求完成",align:"center",sort:true,width:250},
                    {field:"finishedNum",title:"已完成",align:"center",sort:true,width:250},
                    {field:"unFinishedNum",title:"待完成",align:"center",sort:true,width:250},
                    {field:"updateDate",title:"最后更新时间",align:"center",sort:true,width:250}
                ]],
                done:function(){
                }
            })
        }
    };
    //事件处理函数
    var evtFuns = {
        exportExcel:function(){

            return false;
        },
        refresh:function(){
        	$('.node-search').trigger('click');
            return false;
        },
        search:function(){
            form.on("submit(search)",function(data){
                $.extend(data.field,{
                    "platform":$("select.downlist").attr("data-value")
                });
                layer.msg(JSON.stringify(data.field));
                return false;
            });
        }    
    };
    //加载入口函数
    init();
});