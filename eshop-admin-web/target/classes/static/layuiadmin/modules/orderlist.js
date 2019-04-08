

layui.define(['table', 'form'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form;

  //用户管理 userlist
  table.render({
    elem: '#LAY-order-manage'
    ,url: '/getorderlist' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'  ,style:'height:60px'  }
      ,{field: 'orderId', width: 150, title: 'ID', sort: true}
      ,{field: 'user', title: '用户',templet: userTpl, minWidth: 150}
      ,{field: 'user', title: '用户手机号',templet: userphoneTpl,minWidth: 150 }
      ,{field: 'paymentType', title: '支付类型', minWidth: 70,templet: function (d) {
                  return    d.paymentType==1?'在线支付':'货到付款';
              }}
      ,{field: 'payment', width: 120, title: '下单金额'  }
      ,{field: 'buyerMessage',    title: '买家留言'  }
        ,{field: 'status',  title: '状态' ,align:'center',width: 100,templet: function (d) {
                  return   status(d.status)
              }}
        ,{field: 'createTime', title: '创建时间', width: 160, sort: true,templet:function (d) {
                  return Format(d.createTime,"yyyy-MM-dd hh:mm:ss");
              }}
      ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
    ]]
    ,page: true
    ,limit: 30
    ,height: 'full-220'
    ,text: '对不起，加载出现异常！'
  });
  //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、已收获 未评价 6已评价 0订单取消
  function  status(d) {
      var msg=''
      switch (d){
          case  0:msg='订单取消';break;
          case  1:msg='未付款';break;
          case  2:msg='已付款';break;
          case  3:msg='未发货';break;
          case  4:msg='已发货';break;
          case  5:msg='已收货';break;
          case  6:msg='已评价';break;

      }
      return msg;
  }
  function  submsg(s) {
      if (s!=null&&s!=""){
     return    s.substring(1.10)+'...';

      }
      return "暂无留言"
  }
    function Format(datetime,fmt) {
        // console.log(parseInt(datetime));
        // console.log(1=="1")
        if (parseInt(datetime)==datetime) {
            if (datetime.length==10) {
                datetime=parseInt(datetime)*1000;
            } else if(datetime.length==13) {
                datetime=parseInt(datetime);
            }
        }
        datetime=new Date(datetime);
        var o = {
            "M+" : datetime.getMonth()+1,                 //月份
            "d+" : datetime.getDate(),                    //日
            "h+" : datetime.getHours(),                   //小时
            "m+" : datetime.getMinutes(),                 //分
            "s+" : datetime.getSeconds(),                 //秒
            "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
            "S"  : datetime.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
  //监听工具条

    table.on('tool(LAY-order-manage)', function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
            location.href="/order/detail?oid="+data.orderId

        }
    });


  exports('orderlist', {})
});