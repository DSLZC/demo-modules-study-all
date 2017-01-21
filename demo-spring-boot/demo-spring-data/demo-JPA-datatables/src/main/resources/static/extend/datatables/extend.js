/**
 * Created by dongsilin on 2016/10/27.
 */

/**
 * DataTable自定义扩展工具方法
 */
var dataTableExtend = {
    table: {},
    rowIndex: 1,
    /**
     * 创建DataTable，请求json数据
     * @param tableId table的id
     * @param columns DataTable列对象
     * @param ajaxUrl 异步请求json数据url
     * @param searchFormId 搜索表单id
     */
    init: function (tableId, columns, ajaxUrl, searchFormId) {
        this.table = $('#'+tableId).DataTable({
            language: {
                url: '/plugins/datatables/1.10.13/zh-CN.json'
            },
            ordering: true,//是否允许DataTables排序。
            ordering: true,//是否允许表格分页。
            processing: true,//是否显示正在处理的状态。
            scrollX: false,//是否允许水平滚动，开启会失能autoWidth。
            scrollY: true,//是否允许垂直滚动。
            searching: true,//是否允许Datatables开启本地搜索。
            stateSave: true,// 保存状态
            serverSide: true,//开启Datatables服务器模式
            autoWidth: true,//自动宽度
            responsive: true,//自适应
            columns: columns,
            //order: [[0, 'desc']],
            ajax: {
                contentType: 'application/json;charset=UTF-8',
                url: ajaxUrl,
                type: 'POST',
                data: function (data) {//增加或修改通过Ajax提交到服务端的请求数据
                    // 附加搜索参数
                    if(searchFormId) data.searchParams = $('#'+searchFormId).serializeArray();
                    // 行序号
                    dataTableExtend.rowIndex = 1;
                    return JSON.stringify(data);
                }
            }
        });
    },
    /**
     * 批量操作
     * @param batchBtnId 批量操作按钮的id
     * @param callback 回调操作方法
     */
    batchAction: function (batchBtnId, callback) {
        $('#'+batchBtnId).click(function () {
            var ids = [];
            dataTableExtend.table.rows('.selected').data().each(function (data) {
                ids.push(data.id);
            });
            if (ids.length == 0) layer.alert('请至少选择一条数据...');
            else if(typeof callback == 'function') callback(ids.join(','));
        });
    },
    /**
     * 选中/取消选中一行
     * @param o
     */
    selectRow: function (o) {
        if($(o).prop("checked")) $(o).parent().parent().addClass('selected');
        else $(o).parent().parent().removeClass('selected');
    },
    /**
     * checkbox 全选/反选
     * @param tableId table的id
     * @param checkAllId checkAll checkbox的id
     * @param checkBoxName 每一行开头的checkbook的name
     */
    checkAllOrNot: function (tableId, checkAllId, checkBoxName) {
        $("#"+checkAllId).on("click", function () {
            var checkAll = $(this).prop("checked");
            $("input[name='"+checkBoxName+"']").prop("checked", checkAll);
            if(checkAll) $('#'+tableId+' tbody tr').addClass('selected');
            else $('#'+tableId+' tbody tr').removeClass('selected');
        });
    },
    /**
     * 重新加载dataTables数据
     */
    reload: function () {
        this.table.ajax.reload();
    },
    /**
     * 日期格式化
     * @param longMils 毫秒数
     * @param formatStr 日期格式
     */
    dateFormatToStr: function (longMils, formatStr) {
        return longMils > 0? moment(longMils).format(formatStr) : '';
    },
    /**
     * 日期格式化
     * @param dateStr 时间字符串
     * @param formatStr 日期格式
     */
    dateFormatStrToStr: function (dateStr, formatStr) {
        return dateStr?moment(dateStr, 'YYYYMMDD').format(formatStr):'';
    }

};

/**
 * form表单中的带有name的标签序列化Object
 * @returns {{}}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/**
 * form表单中的带有name的标签序列化为DataTables参数
 * @returns {{}}
 */
$.fn.serializeDatatablesParams = function () {
    var a = [];
    $.each(this.serializeArray(), function () {
        a.push({name: this.name||'', value: this.value||''});
    });
    return a;
};



