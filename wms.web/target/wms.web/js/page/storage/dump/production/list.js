﻿var indexdata = [],kw="",strorder="", strsort=true;

function databindTable() {
    $("#id_div_grid").iGrid({
        columns: [
            {th: '生产转运单号', col: 'stock_transport_code', type:"link",
                link:{
                    href:"detail.html",
                    autocreate:false,
                    args:["stock_transport_id|id"]
                }
            } ,
            { th: '运输车辆号', col: 'vehicle'},
            { th: '发出工厂', col: 'fty_name'},
            { th: '发出库存地点', col: 'location_code_name'},
            { th: '创建人', col: 'user_name' },
            { th: '创建日期', col: 'create_time' },
            { th: '状态', col: 'status_name', sort:false }
        ],
        data: indexdata.body,
        percent:30,
        skin:"tablestyle4",
        rownumbers:false,
        csort:function(a,b){
		if(b=="asc"){
				strsort=true;
			}else{
				strsort=false;
			}
			strorder=a;
			actionpager();
        }
    });
}

// 初始化分页
function initPagination(total, current) {
    $("#J_ListPagination").unbind().pagination({
        total_pages: total,
        current_page: current,
        callback: function(event, page) {
            actionpager(page);
        }
    });
}

function actionpager(pageindex) {
    var status_list=[]
    var totalcount=pageindex?indexdata.head.total:-1
    $(".chkbox input:checked").each(function(){
        status_list.push($(this).val())
    })
    if(status_list.length==0){
        indexdata.body=[]
        $("#J_ListPagination").html("")
        databindTable();
        return false
    }
    showloading();
    var url = uriapi + '/biz/product/transport/head_list';
    var obj={
        page_index: pageindex||1,
        page_size: 20,
        total: totalcount,
        condition:kw,
        status_list:status_list,
        sort_column:strorder,
		sort_ascend:strsort
    }
    ajax(url, 'post', JSON.stringify(obj), function (data) {
        if(data.head.status==true){
            indexdata = data;
            if (indexdata.body.length>0) {
            	$.each(indexdata.body,function(i,item){
            		item.location_code_name=item.location_code+"-"+item.location_name;
            	})
                initPagination(Math.ceil(data.head.total / data.head.page_size), data.head.page_index);
            }else{
                $("#J_ListPagination").html("")
            }
            databindTable();
            setTimeout(function () { layer.close(indexloading); }, 50);
        }else{
            layer.close(indexloading)
        }
    }, function(e) {
        layer.close(indexloading);
    })
}


$(function () {
    actionpager();
    $("input.search").keyup(function (e) {
        var keycode = e.which;
        if (keycode == 13) {
            kw=$("input.search").val()
            actionpager();
        }
    });
    $("a.search").click(function () {
        kw=$("input.search").val()
        actionpager();
    });
    $(":checkbox").change(function () {
        actionpager();
    });
});