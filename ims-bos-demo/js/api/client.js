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
var $reqGetUrl = {
};
var $reqPostUrl = {
  login:"/ims/ims-mis-gateway/login",
  sysUserManList:'/ims/ims-mis-gateway/SystemUserController/pageList',//用户-列表
  sysUserManDetail:'/ims/ims-mis-gateway/SystemUserController/findById',//详情
  sysUserManEdit:'/ims/ims-mis-gateway/SystemUserController/saveOrUpdate',//新增，修改
  sysUserManDelItem:'/ims/ims-mis-gateway/SystemUserController/deleteById',//删除
  sysUserManResetPwd:'/ims/ims-mis-gateway/SystemUserController/resetPassword',//重置密码
  sysMenuManList:'/ims/ims-mis-gateway/SystemMenuController/list',//菜单列表
  sysRoleManList:'/ims/ims-mis-gateway/SystemRoleController/list',//角色列表
  sysRoleManEdit:'/ims/ims-mis-gateway/SystemRoleController/saveOrUpdate',//角色-新增、修改
  sysRoleManDelItem:'/ims/ims-mis-gateway/SystemRoleController/deleteById',//角色-删除
  sysRoleManDetail:'/ims/ims-mis-gateway/SystemRoleController/findById',//角色-查看
  sysRoleManSubList:'/ims/ims-mis-gateway/SystemMenuController/findMenuListByMenuId',//角色-分配用户-列表
  sysLogList:'/ims/ims-mis-gateway/SystemLogController/pageList',//系统日志-列表
};

