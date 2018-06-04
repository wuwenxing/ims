layui
  .use([
    'form', 'table', 'layer', 'upload'
  ], function (form) {
    var form = layui.form;
    var table = layui.table;
    var layer = layui.layer
    var upload = layui.upload
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
            field: 'activityPeriods',
            title: '活动编号',
            sort: true
          }, {
            field: 'activityName',
            title: '活动名称',
            sort: true
          }, {
            field: 'activityTypeStr',
            title: '活动类型'
          }, {
            field: 'startTime',
            title: '活动开始时间',
            sort: true
          }, {
            field: 'endTime',
            title: '活动结束时间',
            sort: true
          }, {
            field: 'enableFlag',
            title: '活动状态'
          }, {
            field: 'createDate',
            title: '创建时间',
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
      // 修改弹窗
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
      // 编辑
      edit: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length || selectItems.data.length > 1) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.editForm('修改活动提案信息', this.getTemplate('./template/normal.ejs', selectItems.data[0]))
      },
      // 上传白名单
      uploadWhiteList: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length || selectItems.data.length > 1) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.editForm('上传白名单', this.getTemplate('./template/whiteList.ejs'))
        upload.render({
          elem: '#uploadWhiteList',
          url: '',
          auto: false,
          field: 'whiteListFilePath',
          bindAction: "#uploadWhiteListBtn",
          done: function (res) {},
          error: function (error) {}
        })

      },
      // 上传黑名单
      uploadBackList: function (e) {
        var selectItems = table.checkStatus(tableId);
        if (!selectItems.data.length || selectItems.data.length > 1) {
          this.notice('请选择一条记录进行操作!');
          return;
        }
        this.editForm('上传黑名单', this.getTemplate('./template/blackList.ejs'))
        upload.render({
          elem: '#uploadBlackList',
          url: '',
          auto: false,
          field: 'blackListFilePath',
          bindAction: "#uploadBlackListBtn",
          done: function (res) {},
          error: function (error) {}
        })

      },
      // 刷新
      refresh: function (e) {
        tableEl.reload({url: url})
      }
    }

  })