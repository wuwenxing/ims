layui
  .use([
    'form', 'table', 'layer'
  ], function (form) {
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer
    var $ = layui.$
    var tableId = 'J_DataList'

    // 初始化表单数据
    var tableEl = table.render({
      elem: '#' + tableId,
      id: tableId,
      height: 'full-200',
      url: './db.json',
      page: true,
      done: function (res, currentPage, count) {
        // console.log(res, currentPage, count)
      },
      cols: [
        [
          {
            type: 'numbers',
            fixed: 'left'
          }, {
            type: 'checkbox',
            fixed: 'left'
          }, {
            field: 'pno',
            title: '提案号',
            sort: true
          }, {
            field: 'activityPeriods',
            title: '活动编号'
          }, {
            field: 'activityName',
            title: '活动名称'
          }, {
            field: 'activityType',
            title: '活动类型'
          }, {
            field: 'enableFlag',
            title: '活动状态'
          }, {
            field: 'createUser',
            title: '提案人'
          }, {
            field: 'proposalDate',
            title: '提案时间'
          }, {
            field: '审批人',
            title: '提案时间'
          }, {
            field: 'approveDate',
            title: '审批时间',
            sort: true
          }, {
            field: 'remark',
            title: '备注'
          }
        ]
      ]
    })

    // 表单数据处理
    form.on('submit(querySubmit)', function (data) {
      console.log(data.field)
      return false;
    })
    // 取消原因提交
    form.on('submit(editFormComfirm)', function (data) {
      console.log(data.field)
      return false;
    })

    // 操作按钮处理函数
    $('#J_OptionsBar').on('click', '.layui-btn', function (e) {
      var type = $(this).attr('data-type')
      eventHandle[type]
        ? eventHandle[type](this)
        : null;
    })
    var eventHandle = {
      // 提示
      notice: function (text) {
        layer.open({
          type: 1,
          offset: 'auto',
          title: '操作记录',
          id: 'notice-' + Date.now(),
          content: '<div class="dialog-body">' + text + '</div>',
          btn: '确定',
          btnAlign: 'c',
          shade: 0.15,
          yes: function () {
            layer.closeAll();
          }
        });
      },
      // 确认弹窗
      comfirm: function (text, callback, title) {
        layer.open({
          type: 1,
          offset: 'auto',
          title: title || '确认',
          id: 'notice-' + Date.now(),
          content: '<div class="dialog-body">' + text + '</div>',
          btn: [
            '确定', '取消'
          ],
          btnAlign: 'c',
          shade: 0.15,
          yes: function () {
            callback && callback();
            layer.closeAll();
          },
          btn2: function () {
            layer.closeAll();
          }
        });
      },
      // 模板渲染
      getTemplate: function (url, data) {
        return new EJS({url: url}).render(data);
      },
      // 审批通过
      approve: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.comfirm('确认执行选择的记录?')
      },
      // 取消
      cancel: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        layer.open({
          type: 1,
          offset: 'auto',
          title: '确认',
          id: 'notice-' + Date.now(),
          content: '<div class="dialog-body">' + this.getTemplate('./template/cancel.ejs') + '</div>',
          btnAlign: 'c',
          shade: 0.15
        });
      },
      // 刷新
      refresh: function (e) {
        tableEl.reload({url: './db.json'})
      }
    }

  })