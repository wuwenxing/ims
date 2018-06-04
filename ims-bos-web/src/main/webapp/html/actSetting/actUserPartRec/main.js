//基于layui的模块加载
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
        var isFisrt=true;
        var params={};
        custFuns.searchEx();
        custFuns.loadData(params,isFisrt);
        bindEvents();
    };
    //事件绑定
    var bindEvents = function(){
        $(".node-export").bind("click",evtFuns.export);
        $(".node-refresh").bind("click",evtFuns.refresh);
        $(".node-search").bind("click",evtFuns.search);
        $(".node-manualSettlement").bind("click",evtFuns.manualSettlement);
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
            ActCom.loadDataSetLinkage(function(){
                custFuns.downlist();
            });
            ActCom.loadDictData('ActPlatforms','platform',function(){
                custFuns.downlist();
            });
            //
            laydate.render({
                elem:"#startTimeStr",
                type:"datetime"
            });
            laydate.render({
                elem:"#endTimeStr",
                type:"datetime",
                done: function(value, date, endDate){
                    $("#endTimeStr").val(addTime(value));
                }
            });
        },
        loadData:function(params,isFisrt){
            if(params == "undefined"){
                params = {};
            }   
            params = $.extend(params,orderBy);
            console.log("活动参与用户查询条件:"+JSON.stringify(params));
            if(isFisrt){
                var columns = [ //表头
                    {type:'numbers'},
                    {type:'checkbox'},
                    {field:"accountNo",title:"客户账号"},
                    {field:"custName",title:"客户姓名"},
                    {field:"custMobile",title:"手机号"},
                     {field:"actName",title:"活动名称",width:200},
                    {field:"actNo",title:"活动编号",width:200},
                    {field:"platform",title:"活动平台"},
                    {field:"whitelist",title:"白名单活动"},
                    {field:"depositAmount",title:"入金金额"},
                    {field:"beforeGold",title:"先赠金额"},
                    {field:"beforeRequiredLot",title:"要求平仓手数"},
                    {field:"beforeFinishLot",title:"完成手数"},
                    {field:"afterGold",title:"后赠金额"},
                    {field:"afterFinishLot",title:"完成手数"},
                    {field:"discountGold",title:"回扣金额"},
                    {field:"startTime",title:"活动开始时间",sort:true,width:160},
                    {field:"endTime",title:"活动结束时间",sort:true,width:160},
                    {field:"joinTime",title:"参与活动时间",sort:true,width:160},
                    {field:"realSettleTime",title:"清算时间",sort:true,width:160}
                ];
                table.render({
                    data:{},
                    cols: [columns],
                    cellMinWidth:120
                });
            }else{

                if((params.accountNo==undefined||params.accountNo=='')&&params.custMobile==''&&
                    params.actName==''&&params.actNo==''){
                    layer.msg("客户账号、客户手机号、活动名称、活动编号不能同时为空，请确认!");
                    return;
                }
                var columns = [ //表头
                    {type:'numbers'},
                    {type:'checkbox'},
                    {field:"accountNo",title:"客户账号"},
                    {field:"custName",title:"客户姓名"},
                    {field:"custMobile",title:"手机号"},
                    {field:"actName",title:"活动名称",width:200},
                    {field:"actNo",title:"活动编号",width:200},
                    {field:"platform",title:"活动平台"},
                    {field:"whitelist",title:"白名单活动"},
                    {field:"depositAmount",title:"入金金额"},
                    {field:"beforeGold",title:"先赠金额"},
                    {field:"beforeRequiredLot",title:"要求平仓手数"},
                    {field:"beforeFinishLot",title:"完成手数"},
                    {field:"afterGold",title:"后赠金额"},
                    {field:"afterFinishLot",title:"完成手数"},
                    {field:"discountGold",title:"回扣金额"},
                    {field:"actStartTime",title:"活动开始时间",sort:true,width:160},
                    {field:"actEndTime",title:"活动结束时间",sort:true,width:160},
                    {field:"joinTime",title:"参与活动时间",sort:true,width:160},
                    {field:"realSettleTime",title:"清算时间",sort:true,width:160},
                    {field:"settleMode",title:"清算类型",templet:function(e){
                            var settleMode=e.settleMode;
                        if(settleMode==1){
                            return "人工清算";
                        }else if(settleMode==2){
                            return "自动清算";
                        }else{
                            return settleMode;
                        }

                    }
                    }
                ];
                table.render({
                    where:params,
                    url:baseUrl+"mis/act/accountactivity/page",
                    cols: [columns],
                    cellMinWidth:120
                });
            }
            
        }
    };
    //事件处理函数
    var evtFuns = {
        export:function(){
          var actName=$("#actName").val();
          var actNo=$("#actNum").val();
          var accountNo=$("#accountNo").val();
          var custMobile=$("#custMobile").val();
          var settleStatus=$("#settleStatus").val();
          if(settleStatus==null||settleStatus==undefined){
             settleStatus='';
          }
          var platform='';
          var whitelist=$("#whitelist").val();
          form.on("submit(search)",function(data){
                platform=$("select.downlist").attr("data-value")
            });
          var startTimeStr=$("#startTimeStr").val();
          var endTimeStr=$("#endTimeStr").val();
          var url=baseUrl+"mis/act/accountactivity/export"+"?actName="+actName+"&actNo="+actNo+"&accountNo="+accountNo
          +"&custMobile="+custMobile+"&settleStatus="+settleStatus+"&platform="+platform+"&whitelist="+whitelist+"&endTimeStr="+endTimeStr
          +"&startTimeStr="+startTimeStr+"&order="+orderBy.orderBy+"&sort="+orderBy.sort;
          console.log("活动参与用户导出条件:"+url);
          window.location.href = url;
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
                custFuns.loadData(data.field);
                return false;
            });
        },
        manualSettlement:function(){
            console.log("----开始清算---");
            var url = baseUrl + "/mis/act/accountactivity/manualSettlement";
            var checkStatus = table.checkStatus("dataTable"),
            data = checkStatus.data;
            if(data.length){
                layer.confirm("确实要清算选中的记录吗?",function(){
                    var ids = "";
                    for(var i=0;i<data.length;i++){
                        ids+=data[i].id+",";
                    }
                    ids=ids.substr(0,ids.length-1);
                    $.ajaxInterceptor.post(url,{"ids":ids},function(res){
                        layer.msg(res.msg);
                    })
                })
                
            }else{
                layer.msg("请先选择要清算的记录");
            }
            return false;
        }
    };
    //加载入口函数
    init();
});