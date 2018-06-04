var $request = {
  pageName: 'page' //页码的参数名称，默认：page
  ,limitName: 'rows' //每页数据量的参数名，默认：limit
};
var $response =  {
  statusCode: "OK", //成功的状态码，默认：0
  msgName: 'msg', //状态信息的字段名称，默认：msg
  countName: 'extendData', //数据总数的字段名称，默认：count
  dataName: 'data' //数据列表的字段名称，默认：data
}; 
var baseUrl = "/ims-mis-gateway/";
var $menuConfig = {};//菜单权限json
var reqGetUrl = {
};
var reqPostUrl = {

  failPrizeOutRecPage:baseUrl+'mis/act/prizerecordredo/page',//活动-失败列表查询
  msgAppTplList:baseUrl+"/mis/ims/msg/app/tpl/list",//营销-app消息模板列表
		
  baseKeyValList:baseUrl +'mis/base/keyval/page/',
  baseKeyValSave:baseUrl +'mis/base/keyval/save/',
  baseKeyValDel:baseUrl +'mis/base/keyval/delete/',
  
  productList:baseUrl+'mis/act/product/page/',//产品维护-列表
  productSave:baseUrl+'mis/act/product/save/',//产品维护-保存修改
  productDel:baseUrl+'mis/act/product/delete/',//产品维护-删除 
  
  findByDictCode:baseUrl +'mis/system/dict/findByDictCode/',//数据字典获取下拉列表
  listByParentDictCode:baseUrl +'mis/common/listByParentDictCode/',//查询数据字典子节点列表 ，公共接口
  
  actItemsList:baseUrl +'mis/act/items/page/',//物品管理列表 分页
  actItems:baseUrl +'mis/act/items/list/',//物品管理列表

  actItemsSave:baseUrl +'mis/act/items/save',//物品保存
  actItemsDetails:baseUrl +'mis/act/items/',//详情
  actItemsDel:baseUrl +'mis/act/items/delete/',//删除
  getNewItemNumber:baseUrl +'mis/act/items/getNewItemNumber/',//获取最新编号
  
  actItemNumber:baseUrl +'mis/act/items/itemNumber',//根据物品编号查询物品
  
  actStringCodePage:baseUrl +'mis/act/stringcode/page/',  //物品管理--串码列表
  actStringCodeSave:baseUrl +'mis/act/stringcode/save/',  //物品管理--串码修改
  actStringCodeDel:baseUrl +'mis/act/stringcode/delete/',  //物品管理--串码删除
  actStringCodeCount:baseUrl +'mis/act/stringcode/count/',  //物品管理--串码个数统计
  
  actStringCodeUpload:baseUrl +'mis/act/stringcode/upload/',  //物品管理--串码上传
  actStringCodeExport:baseUrl +'mis/act/stringcode/export',  //物品管理--串码导出
  actStringCodeTemplate:baseUrl+"mis/common/down/tpl/act_stringCode_tpl",//串码模板

  
  actWhitelist:baseUrl +"act/whitelist",//黑白名单接口
  actWhiteSave:baseUrl +"act/save",//保存
  actWhiteBatchSave:baseUrl +"act/batch/save",//批量保存
  actWhiteDel :baseUrl +"act/delete",// 删除
  
  									  
  userTagList:baseUrl +'mis/act/tag/page/',//用户标签列表
  userTagSave:baseUrl +'mis/act/tag/save/',//用户标签保存
  userTagDel:baseUrl +'mis/act/tag/delete/',//用户标签删除
  						
  actBlacklist:baseUrl+'mis/act/blacklist/page/',//黑名单列表
  actBlackSave:baseUrl+'mis/act/blacklist/save/',//黑名单保存
  actBlackDel:baseUrl+'mis/act/blacklist/delete/',//黑名单删除
  actBlackBatchSave:baseUrl+'mis/act/blacklist/batch/save/',//黑名单批量保存
  
  whitelist:baseUrl+'mis/act/whitelist/page/',//白名单列表
  whiteSave:baseUrl+'mis/act/whitelist/save/',//白名单保存
  whiteDel:baseUrl+'mis/act/whitelist/delete/',//白名单删除
  whiteBatchSave:baseUrl+'/act/whitelist/batch/save/',//白名单批量保存
  
  actCustomList:baseUrl+'mis/act/task/custom/page/',//任务管理列表
  actTaskList:baseUrl+"mis/act/task/list/",//活动任务列表
  actTaskItemsList:baseUrl+"mis/act/taskItems/list",//活动物品列表
  actCustomSave:baseUrl+'mis/act/task/custom/save/',//任务保存
  actCustomDel:baseUrl+'mis/act/task/custom/delete/',//任务删除
  actCustomEnable:baseUrl+'mis/act/task/custom/enable/',//任务启用禁用
  
  
 
  					
  msgTplList:baseUrl+'mis/ims/msg/sms/tpl/page/',//短信模板列表
  msgTplSave:baseUrl+'mis/ims/msg/sms/tpl/save/',//短信模板保存
  msgTplDel:baseUrl+'mis/ims/msg/sms/tpl/delete/',//短信模板删除
  						 
  appTplList:baseUrl+'mis/ims/msg/app/tpl/page/',//app消息模板列表
  appTplSave:baseUrl+'mis/ims/msg/app/tpl/save/',//app消息模板保存
  appTplDel:baseUrl+'mis/ims/msg/app/tpl/delete/',//app消息模板删除

  login:baseUrl + "login",
  sysUserManList:baseUrl + 'SystemUserController/pageList',
  sysDictList:baseUrl + 'mis/system/dict/page',
  sysSaveDict:baseUrl + 'mis/system/dict/save',
  sysDelDict:baseUrl + 'SystemDictController/deleteById',
  
  orderList:baseUrl + 'mis/order/page',//订单列表
  orderExpressInfo:baseUrl + 'mis/order/express/{id}',//查看订单快递信息
  saveShippingAddress:baseUrl + 'mis/order/address/batch/save',//保存订单地址信息
  updateExpress:baseUrl + 'mis/order/express/save',//保存订单快递信息
  
  goodsListPage:baseUrl + 'mall/item/page',//商品列表页
  goodsSave:baseUrl+ 'mall/item/save',//商品信息保存
  goodsInfo:baseUrl+ 'mall/item/{id}',//查看商品信息
  goodsBatchDel:baseUrl+ 'mall/item/batch/del',//批量删除
  goodsEnableStatusSave:baseUrl+ 'mall/item/{id}/status/change',//商品启动停用状态改变
  goodsTypeList:baseUrl+'mall/item/type/list',//商品类型列表
  imageUpload:baseUrl+'mis/image/upload',//上传图片
  valiAccount:baseUrl+'/mis/act/customerinfo/', //验证账号

  userExportList:baseUrl+"mis/system/user/export",//用户导出



};