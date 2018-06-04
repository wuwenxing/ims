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

    laydate.render({elem: '#createDateStartTime', type: 'datetime', range: true})
    laydate.render({elem: '#createDateEndTime', type: 'datetime', range: true})

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
            type: 'checkbox',
            fixed: 'left'
          }, {
            title: '操作',
            toolbar: '#toobar',
            width: 80
          }, {
            field: 'activityPeriods',
            title: '活动编号',
            width: 110
          }, {
            field: 'activityName',
            title: '活动名称',
            width: 200
          }, {
            field: 'accountNo',
            title: '客户账号',
            width: 100
          }, {
            field: 'guestPhone',
            title: '客户手机号',
            width: 120
          }, {
            field: 'deposit',
            title: '入金'
          }, {
            field: 'tradeLot',
            title: '交易手数'
          }, {
            field: 'productCode',
            title: '交易产品Code',
            width: 150
          }, {
            field: 'giftName',
            title: '发放物品',
            width: 150
          }, {
            field: 'giftProbability',
            title: '发放物品概率',
            width: 120
          }, {
            field: 'lotteryTime',
            title: '完成时间',
            sort: true,
            width: 180
          }, {
            field: 'lotteryStatus',
            title: '完成状态',
            width: 100
          }, {
            field: 'isGived',
            title: '是否发放',
            width: 100
          }, {
            field: 'guestIp',
            title: '访问IP',
            width: 140
          }, {
            field: 'guestFrom',
            title: '访问来源',
            width: 120
          }, {
            field: 'addFromBos',
            title: '是否从后台添加',
            width: 100
          }, {
            field: 'enableFlag',
            title: '是否有效',
            width: 100
          }, {
            field: 'createDate',
            title: '登记时间',
            sort: true,
            width: 180
          }, {
            field: 'otherMsg',
            title: '其他信息',
            width: 420
          }
        ]
      ]
    })

    var params = {
      accountNo: '',
      guestPhone: '',
      deposit: '',
      tradeLot: '',
      lotterySort: '',
      lotteryTime: '',
      giftNumber: '',
      giftProbability: '',
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
      // 新增类型选择弹窗
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
        this.editForm('新增活动发放记录', this.getTemplate('./template/normal.ejs', params))
        form.render()
        laydate.render({elem: '#lotteryTime', type: 'datetime'})
      },
      // 导出Excel
      exportExcel: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
      },
      // 物品未发放
      passed: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.comfirm('您确定要设置选中的记录为物品发放?')
      },
      // 物品已发放
      released: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.comfirm('您确定要设置选中的记录为物品已发放?')
      },
      // 数据有效
      valid: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.comfirm('您确定要设置选中的记录为数据有效?')
      },
      // 数据无效
      novalid: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.comfirm('您确定要设置选中的记录为数据无效?')
      }
    }

    // 表格按钮监听
    table.on('tool(dataList)', function (obj) {
      var data = obj.data
      var event = obj.event

      if (event == 'edit') {
        data.edit = true
        eventHandle.editForm('修改活动发放记录', eventHandle.getTemplate('./template/normal.ejs', data))
        form.render()
        laydate.render({elem: '#lotteryTime', type: 'datetime'})
        // obj.update({activityPeriods: 0})
      }
    })

  })