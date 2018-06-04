layui.link('/css/userSetting.min.css');
layui.use(['element', 'layer','table', 'searchPanel', 'form'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;
    var userIdArray = [];
    var roleList = [];
    var init = function () {
        custFuncs.loadData();
        custFuncs.getRoleList();
        bindEvents();
    };
    var bindEvents = function () {
        $('.node-add').bind('click', evtFuncs.addData);
        $('.node-delete').bind('click', evtFuncs.delete);
        $('.node-download').bind('click', evtFuncs.download);
        table.on('tool(dataTable)', evtFuncs.deleteRow);
        form.on('submit(search)', evtFuncs.search);
    };
    var custFuncs = {
        loadData:function(params){
            params && (params = Object.assign({sort:"updateDate",order:"desc"},params));
            table.render({
                elem: '#data-table',
                height: 'full-228',
                url: reqPostUrl.sysUserManList,
                id: 'dataTable',
                page: true, //开启分页
                method:'POST',
                request:$request,
                where:params,//额外的data
                response:$response,
                cols: [[ //表头
                    {type: 'checkbox', width: 60},
                    {title: '操作', toolbar: '#tableDataActive', width: 400 ,align:"center"},
                    {field: 'userNo', title: '账号',width:150},
                    {field: 'userName', title: '姓名', sort: true,width:150},
                    {field: 'roleName', title: '角色',  sort: true,width:100},
                    {field: 'enableFlag', title: '状态', sort: true,width:80,templet: "#enableFlagTpl"},
                    {field: 'updateDate', title: '更新时间',  sort: true,width:200},
                    {field: 'companyName', title: '所属公司', sort: true,width:200}
                ]],
                done:function(res, curr, count){
                }
            });
        },
        getRoleList:function(){
            // sysRoleManList
            $.ajax({
                url:$reqPostUrl.sysRoleManList,
                type:'POST',
                success:function(res){
                    console.log("res",res);
                    if(res.code != "OK"){
                        layer.msg(res.msg);
                        return;
                    }
                    roleList = res.data;
                },  
                error:function(err){
                    layer.msg(err);
                }
            });
        },     
        showData: function (type,data) {
            var res = data.data;
            var config = {};
            if (type == 'edit') {
                config.title = '<div><i class="layui-icon">&#xe642;</i>&nbsp;修改内容</div>';
            } else if (type == 'see') {
                config.title = '<div><i class="layui-icon">&#xe63c;</i>&nbsp;查看详情</div>';
                config.btn = ['关闭'];
            } else if (type == 'add') {
                config.title = '<div><i class="layui-icon">&#xe654;</i>&nbsp;新增用户</div>';
                config.btn = ['新增', '取消'];
            }
            $.extend(config, {
                area: ['600', 'auto'],
                resize: false
            });
            console.log("showDaaa",data);
            var confirmIndex = layer.confirm(
                new EJS({url: "./ejs/seeAddRow.ejs"}).render(data), config, function (index) {
                    if (type == 'see') {
                        layer.close(index);
                    } else {
                        $('.node-submit').trigger('click');
                    }
                });
            form.render();
            form.on('submit(seeAddRow)', function (data) {
                custFuncs.formValidate(data);
                if(type == 'edit'){
                    data.field = $.extend(data.field,{userId:res.userId});
                    custFuncs.editItem(data.field);
                }else if(type == 'add'){
                    custFuncs.editItem(data.field);
                }
                layer.close(confirmIndex);
                return false;
            });
        },
        formValidate:function(data){
            form.verify({
                password:function(val,item){
                    if(val.length < 6){
                        // result = true;
                        return "最少6位数的密码，支持大小写英文、数字"
                    }
                },
                repassword:function(val,item){
                    if(data.field.password != val){
                        // result = true;
                        return "两次输入的密码不一致！"
                    }
                },
                telephone:function(val,item){
                    
                },
                vemail:function(){

                }
            });
            // return result;
        },
        getDetail:function(type,params){//查看,编辑
            $.ajax({
                url:$reqPostUrl.sysUserManDetail,
                type:'POST',
                data:params,
                success:function(res){
                    console.log("res",res);
                    if(res.code != "OK"){
                        layer.msg(res.msg);
                        return;
                    }
                    custFuncs.showData(type, {data: res.data, type: type,roleList:roleList});
                },  
                error:function(err){
                    layer.msg(err);
                }
            });
        },
        editItem:function(params){//新增，修改
            $.ajax({
                url:$reqPostUrl.sysUserManEdit,
                type:'POST',
                data:params,
                success:function(res){
                    console.log("res",res);
                    if(res.code != "OK"){
                        layer.msg(res.msg);
                        return;
                    }
                    layer.msg("操作成功");
                    if(!params.userId){
                        custFuncs.loadData();
                    }
                },  
                error:function(err){
                    layer.msg(err);
                }
            });
        },
        delRow:function(params){//删除
            $.ajax({
                url:$reqPostUrl.sysUserManDelItem,
                type:'POST',
                data:params,
                success:function(res){
                    console.log("res",res);
                    if(res.code != "OK"){
                        layer.msg(res.msg);
                        return false;
                    }
                    layer.msg("删除成功");
                    custFuncs.loadData();
                    return true;
                },  
                error:function(err){
                    layer.msg(err);
                    return false;
                }
            });
        },
        resetPwd:function(params){//重置密码
            $.ajax({
                url:$reqPostUrl.sysUserManResetPwd,
                type:'POST',
                data:params,
                success:function(res){
                    if(res.code != "OK"){
                        layer.msg(res.msg);
                    }
                    layer.msg("密码修改成功");
                },  
                error:function(err){
                    layer.msg(err);
                }
            });
        }
    };
    var evtFuncs = {
        search:function (data) {
            custFuncs.loadData(data.field);
            return false;
        },
        addData: function () {
            custFuncs.showData('add', {data: {}, type: 'add',roleList:roleList});
        },
        delete: function () {
            var checkData = table.checkStatus('dataTable').data;
            if (checkData.length <= 0) {
                layer.alert('请选择要删除的记录!', {
                    title: '<div><i class="layui-icon">&#xe640;</i>&nbsp;删除用户</div>'
                });
            } else {
                layer.confirm('确定删除选中记录吗？', {
                    title: '<div><i class="layui-icon">&#xe640;</i>&nbsp;删除用户</div>',
                    btn: ['删除', '关闭']
                }, function (index) {
                    var userStr = '';
                    for(var i=0;i<checkData.length;i++){
                        userIdArray.push(checkData[i].userId);
                    }
                    userStr = userIdArray.join(',');
                    custFuncs.delRow({userIdArray:userStr});

                    //确定回调
                    layer.close(index);
                })
            }
        },
        download: function () {

        },
        deleteRow: function (ev) {
            // console.log(ev);
            if (ev.event == 'del') {
                layer.confirm('确定删除该条记录吗？', {
                    title: '<div><i class="layui-icon">&#xe640;</i>&nbsp;删除用户</div>',
                    btn: ['删除', '关闭']
                }, function (index) {
                    var result = custFuncs.delRow({userIdArray:ev.data.userId}) || '';
                    result && ev.del();
                    layer.close(index);
                });
            } else if (ev.event == 'see') {
                custFuncs.getDetail('see',{userId:ev.data.userId});
            } else if (ev.event == 'edit') {
                custFuncs.getDetail('edit',{userId:ev.data.userId});
            } else if (ev.event == 'reset') {
                var confirmIndex = layer.confirm(new EJS({url: "./ejs/resetpwd.ejs"}).render(ev.data), {
                    title: '<div><i class="layui-icon">&#xe631;</i>&nbsp;重置密码</div>',
                }, function (index) {
                    $('.node-resetpwdSubmit').trigger('click');
                });
                form.render();
                form.on('submit(layerDialogResetpwd)', function (data) {
                    // 
                    custFuncs.formValidate(data);
                    data.field = $.extend(data.field,{userId:ev.data.userId});
                    custFuncs.resetPwd(data.field);
                    layer.close(confirmIndex);
                    return false;
                });
            }
        }
    };
    init();
})
