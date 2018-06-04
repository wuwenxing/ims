layui
  .use([
    'form', 'table', 'layer'
  ], function (form) {
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer
    var $ = layui.$
    var tableId = 'J_DataList'
    var url = ''

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
            field: 'activityPeriods',
            title: '活动编号',
            sort: true
          }, {
            field: 'activityName',
            title: '活动名称',
            sort: true
          }, {
            field: 'accountNo',
            title: '客户账号',
            sort: true
          }, {
            field: 'env',
            title: '账号类型',
            sort: true
          }, {
            field: 'platform',
            title: '平台',
            sort: true
          }, {
            field: 'updateDate',
            title: '最后更新时间',
            sort: true
          }
        ]
      ]
    })

    // 表单数据处理
    form.on('submit(querySubmit)', function (data) {
      console.log(data.field)
      return false;
    })

    // 操作按钮处理函数
    $('#J_OptionsBar').on('click', '.layui-btn', function (e) {
      var type = $(this).attr('data-type')
      optionsBarHandle[type]
        ? optionsBarHandle[type](this)
        : null;
    })
    var optionsBarHandle = {
      refresh: function (e) {
        tableEl.reload({url: url})
      }
    }

  })