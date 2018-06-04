// var $request = {
//   pageName: 'page' //页码的参数名称，默认：page
//   ,limitName: 'rows' //每页数据量的参数名，默认：limit
// };
// var $response =  {
//   statusCode: "OK", //成功的状态码，默认：0
//   msgName: 'msg', //状态信息的字段名称，默认：msg
//   countName: 'extendData', //数据总数的字段名称，默认：count
//   dataName: 'data' //数据列表的字段名称，默认：data
// }; 
var baseUrl = "/ims-mis-gateway/";
var $reqGetUrl = {
};
var $reqPostUrl = {
  login:baseUrl + "login",//登录
  logout:baseUrl + 'login/out',//退出登录
  resetpwd:baseUrl + 'mis/system/user/password/update',//修改密码
  companyList: baseUrl + 'mis/common/companyList',//公司列表
  commonGlobalBlacklist:baseUrl +'mis/gw/blacklist/upload',//全局黑名单
  menuList:baseUrl + 'mis/common/menu/treeList',//权限列表
  commonRoleGroup:baseUrl + 'mis/common/rolelist/group',//角色公司分组
  commonTemplateDown:baseUrl+ 'mis/common/down/tpl/',//{key}black_white_tpl/prize_record_tpl 模板下载
  globalBlackList: baseUrl + 'mis/gw/blacklist/page',//全局黑名单
  sysUserManList:baseUrl + 'mis/system/user/page',//用户-列表
  sysUserManDetail:baseUrl + 'mis/system/user/find',//详情
  sysUserManEdit:baseUrl + 'mis/system/user/save',//新增，修改
  sysUserManDelItem:baseUrl + 'mis/system/user/delete',//删除
  sysUserManResetPwd:baseUrl + 'mis/system/user/password/reset',//重置密码
  sysUserManExport:baseUrl + 'mis/system/user/export',//用户-导出Excel
  sysMenuManList:baseUrl + 'mis/system/menu/list',//菜单列表
  sysMenuManTreeList:baseUrl + 'mis/system/menu/listMenuTree',//菜单列表-树形菜单
  sysMenuManTreeItemDetail:baseUrl + 'mis/system/menu/findByMenuCode',//菜单列表-树形菜单详情
  sysMenuManAddItem:baseUrl + 'mis/system/menu/save',//菜单列表-新增、修改
  sysMenuManDelItem:baseUrl + 'mis/system/menu/deleteByMenuCode',//菜单列表-删除
  sysMenuManDelById:baseUrl + 'mis/system/menu/delete',//菜单列表-删除
  // sysMenuManTreeItemDetail:baseUrl + 'SystemMenuController/findById',//菜单列表-树形菜单详情
  sysRoleManList:baseUrl + 'mis/system/role/page',//角色列表
  sysRoleManEdit:baseUrl + 'mis/system/role/save',//角色-新增、修改
  sysRoleManDelItem:baseUrl + 'mis/system/role/delete',//角色-删除
  sysRoleManDetail:baseUrl + 'mis/system/role/find',//角色-查看
  sysRoleManAllotUser:baseUrl + 'mis/system/user/userrole/save',//角色-分配用户-保存
  sysRoleManMenuList: baseUrl + 'mis/system/menu/findMenuListByMenuId',//角色-菜单权限
  sysRoleManAllotPower: baseUrl + 'mis/system/role/columnauth/save',//角色-分配栏位
  sysRoleManGetPower: baseUrl + 'mis/system/role/columnauth/find',//角色-获取分配栏位
  sysRoleManEditMenu: baseUrl + 'mis/system/role/menu/save',//角色-菜单权限-保存
  sysLogList:baseUrl + 'mis/system/log/page',//系统日志-列表
  sysDictSubList:baseUrl + 'mis/system/dict/findListByParentDictCode',//数据字典-子节点列表
  sysDictDetail:baseUrl + 'mis/system/dict/find',//数据字典-分组详情/字典详情
  sysDictDelItem:baseUrl + 'mis/system/dict/delete',//数据字典-删除
  basePrdsList: baseUrl + 'mis/act/product/page',//基础设置-产品维护
  basePrdsSave: baseUrl + 'mis/act/product/save',//基础设置-产品维护-新增，修改
  basePrdsDel: baseUrl + 'mis/act/product/delete',//基础设置-产品维护-删除
  basePrdsDetail: baseUrl + 'mis/act/product/find/',//{ids}基础设置-产品维护-详情
  actPrizeList:baseUrl +'mis/act/items/list',//物品列表(奖品列表)
  actTimeSetSave: baseUrl + 'mis/act/maintenance/save',//活动维护时间-新增，修改
  actTimeSetBatchSave: baseUrl + 'mis/act/maintenance/batch/effective',//活动维护时间-批量生效
  actTimeSetList: baseUrl + 'mis/act/maintenance/page',//活动维护时间-列表
  actTimeSetDelItem: baseUrl + 'mis/act/maintenance/delete/',//{id} 活动维护时间-删除
  actTimeSetItemDetail: baseUrl + 'mis/act/maintenance/',//{id} 活动维护时间-查看

  actAddConfig:baseUrl + 'mis/act/getActConfig/',//{activityType}活动提案-新增（获取活动配置信息）
  actList: baseUrl + 'mis/act/list',//活动列表--查询
  actPage: baseUrl + 'mis/act/page',//活动列表--列表
  actApprove: baseUrl +'mis/act/approve/',//{ids}活动审批通过
  actSave:baseUrl +'mis/act/save',//活动新增.修改
  actDetail:baseUrl + 'mis/act/',//活动-查看{activityPeriods}
  actCancelById: baseUrl + 'mis/act/cancel/',//{ids}活动提案--取消活动
  actInfoAndActConfig: baseUrl + 'mis/act/getActInfoAndConfig/',//{activityPeriods}查看活动信息-活动模板
  actApproveList:baseUrl +'mis/act/proposal/modify/page',//活动修改提案分页
  actEditApproveDetail: baseUrl + 'mis/act/proposal/modify/',//{pno}活动修改提案详情
  actApproveCancelById: baseUrl + 'mis/act/proposal/modify/cancel/',//{pno}活动修改提案--取消
  actApproveActById:baseUrl + 'mis/act/proposal/modify/approve/',//{pno}活动修改提案--审核通过
  actTradePrdsList:baseUrl + 'mis/act/tradeProducts',//accountType 活动交易产品列表

  actWhiteListUpload:baseUrl + 'mis/act/whitelist/batch/save',//活动白名单上传
  actWhiteListSave:baseUrl + 'mis/act/whitelist/save',//活动白名单单个上传
  actWhiteListApprove:baseUrl + 'mis/act/whitelist/proposal/',//{ids}活动白名单审核通过
  actWhiteListCancel:baseUrl + 'mis/act/whitelist/cancel/',//{ids}活动白名单取消
  actWhiteListDelItem:baseUrl + 'mis/act/whitelist/delete/',//{ids} 活动白名单删除
  actWhiteListExport:baseUrl + 'mis/act/whitelist/export', //活动白名单导出

  actBlackListUpload:baseUrl + 'mis/act/blacklist/batch/save',//活动黑名单上传
  actBlackListSave:baseUrl + 'mis/act/blacklist/save',//活动黑名单单个上传
  actBlackListApprove:baseUrl + 'mis/act/blacklist/proposal/',//{ids}活动黑名单审核通过
  actBlackListCancel:baseUrl + 'mis/act/blacklist/cancel/',//{ids}活动黑名单取消
  actBlackListDelItem:baseUrl + 'mis/act/blacklist/delete/',//{ids} 活动黑名单删除
  actBlackListExport:baseUrl + 'mis/act/blacklist/export', //活动黑名单导出

  actPrizeoutRecSave:baseUrl + 'mis/act/prizerecord/save',//活动-物品发放记录-新增，修改
  actPrizeoutRecPage:baseUrl + 'mis/act/prizerecord/page',//活动-物品发放记录-分页查询
  actPrizeoutRecExport:baseUrl + 'mis/act/prizerecord/export',//活动-物品发放记录-导出
  actPrizeoutRecOutList:baseUrl +'mis/act/prizerecord/waitsend/page',//活动-物品发放记录-应发列表-分页查询
  actPrizeoutRecBatOpts:baseUrl +'mis/act/prizerecord/batch/',//{action}活动-物品发放记录-批量操作
  actPrizeoutRecFindById:baseUrl +'mis/act/prizerecord/',//{id}活动-物品发放记录-详情

  marketToolsRechargeList: baseUrl +'mis/recharge/page',//话费，流量查询分页
  marketToolsRechargeSend: baseUrl +'mis/recharge/send',//话费，流量充值
  marketToolsRechargeUpload: baseUrl +'mis/recharge/upload',//话费，流量充值 导入充值
  marketToolsRechargeTemplate:baseUrl+"mis/common/down/tpl/recharge_add_tpl",//充值记录导入模板
  marketToolsRechargeExport:baseUrl+"mis/recharge/export"//导出


};

