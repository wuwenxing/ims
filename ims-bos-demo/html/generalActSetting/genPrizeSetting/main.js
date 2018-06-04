layui
  .use([
    'form', 'table', 'layer', 'laydate', 'upload'
  ], function (form) {
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer
    var laydate = layui.laydate
    var $ = layui.$
    var upload = layui.upload
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
            field: 'activityPeriods',
            title: '活动编号'
          }, {
            field: 'activityName',
            title: '活动名称'
          }, {
            field: 'giftNumber',
            title: '物品编号',
            sort: true
          }, {
            field: 'giftName',
            title: '物品名称',
            sort: true
          }, {
            field: 'giftAmount',
            title: '物品数量'
          }, {
            field: 'giftRate',
            title: '物品中奖率'
          }, {
            field: 'startDate',
            title: '开始时间',
            sort: true
          }, {
            field: 'endDate',
            title: '结束时间',
            sort: true
          }, {
            field: 'enableFlag',
            title: '物品状态'
          }, {
            title: '操作',
            toolbar: '#toolbar'
          }
        ]
      ]
    })
    var params = {
      giftNumber: 'fx_',
      activityPeriodsAdd: '',
      giftName: '',
      giftAmount: '',
      giftRate: '',
      enableFlag: '',
      startDateSearch: '',
      edit: false
    }

    // 表单数据处理
    form.on('submit(querySubmit)', function (data) {
      console.log(data.field)
      return false;
    })
    // 新增
    form.on('submit(editFormSubmit)', function (data) {
      console.log(data)
      layer.closeAll()
      return false;
    })
    // 批量新增
    form.on('submit(editFormMutipleSubmit)', function (data) {
      console.log(data)
      layer.closeAll()
      return false;
    })
    // 修改
    form.on('submit(editFormEdit)', function (data) {
      tableEl.reload({url: url})
      layer.closeAll()
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
        this.editForm('新增物品设置', this.getTemplate('./template/normal.ejs', params))
        form.render()
        laydate.render({elem: '#startDateSearch', type: 'datetime', range: true})
      },
      // 批量新增
      mutipleAdd: function (e) {
        this.editForm('批量添加活动物品', this.getTemplate('./template/mutipleAdd.ejs'))
        upload.render({
          elem: '#normalItem',
          url: '',
          auto: false,
          field: 'normalItem',
          done: function (res) {},
          error: function (error) {}
        })
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

    // 表格内按钮监听
    table.on('tool(dataList)', function (obj) {
      var data = obj.data
      var event = obj.event
      if (event == 'edit') {
        data.startDateSearch = '';
        data.edit = true;
        if (data.startDate && data.endDate) {
          data.startDateSearch = data.startDate + ' - ' + data.endDate;
        }
        eventHandle.editForm('修改活动物品设置', eventHandle.getTemplate('./template/normal.ejs', data))
        form.render();
        laydate.render({elem: '#startDateSearch', type: 'datetime', range: true})
        // obj.update({activityName: "修改更新"})
      } else if (event == 'delete') {
        layer
          .confirm('确认删除该行记录吗?', {
            icon: 3,
            title: '确认'
          }, function (index) {
            //do something

            layer.close(index);
          });
      }
    })
  })