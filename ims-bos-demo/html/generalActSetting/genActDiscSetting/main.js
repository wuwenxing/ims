layui
  .use([
    'form', 'table', 'layer', 'laydate'
  ], function (form) {
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer
    var laydate = layui.laydate
    var $ = layui.$
    var tableId = 'J_DataList'
    var url = './db.json'

    // 初始化表单数据
    var tableEl = table.render({
      elem: '#' + tableId,
      id: tableId,
      height: 'full-200',
      url: url,
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
            title: '操作',
            toolbar: '#toolbar'
          }, {
            field: 'activityCode',
            title: '活动编号'
          }, {
            field: 'activityName',
            title: '活动名称'
          }, {
            field: 'amount',
            title: '数量/倍数'
          }, {
            field: 'startTime',
            title: '活动开始时间',
            sort: true
          }, {
            field: 'endTime',
            title: '活动结束时间',
            sort: true
          }, {
            field: 'remark',
            title: '备注'
          }
        ]
      ]
    })

    var params = {
      amount: '',
      startTimeStr: '',
      endTimeStr: '',
      remark: '',
      edit: false
    }
    // 表单数据处理
    form.on('submit(querySubmit)', function (data) {
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
      // 新增弹窗
      editForm: function (title, text) {
        layer.open({
          type: 1,
          offset: 'auto',
          title: title,
          id: 'notice-' + Date.now(),
          content: '<div class="dialog-body">' + text + '</div>',
          maxWidth: 1200,
          shade: 0.15
        });
      },
      // 模板渲染
      getTemplate: function (url, data) {
        return new EJS({url: url}).render(data);
      },

      // 新增
      add: function (e) {
        this.editForm('新增活动优惠设置', this.getTemplate('./template/normal.ejs', params));
        form.render();
        laydate.render({elem: '#startTimeStr', type: "datetime"})
        laydate.render({elem: '#endTimeStr', type: "datetime"})
      },
      // 删除
      delete: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
      },
      // 刷新
      refresh: function (e) {
        tableEl.reload({url: url})
      }
    }

    // 表格内操作按钮
    table.on('tool(dataList)', function (obj) {
      var data = obj.data
      var event = obj.event
      if (event == 'edit') {
        obj.update({activityCode: 0})
      } else if (event == 'delete') {}
    })

  })