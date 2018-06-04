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
var reqGetUrl = {
};
var reqPostUrl = {
  login:"/ims/ims-mis-gateway/login",
  sysUserManList:'/ims/ims-mis-gateway/SystemUserController/pageList',
  baseKeyValList:'/george/ims-mis-gateway/mis/keyval/page',
  baseKeyValSave:'/george/ims-mis-gateway/mis/keyval/saveOrUpdate',
  sysDictList:'/george/ims-mis-gateway/SystemDictController/pageList',
  sysSaveDict:'/george/ims-mis-gateway/SystemDictController/saveOrUpdate',
  sysDelDict:'/george/ims-mis-gateway/SystemDictController/deleteById'

};

