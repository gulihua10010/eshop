

layui.define(['table', 'form'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form;

  //用户管理 userlist
  table.render({
    elem: '#LAY-user-manage'
    ,url: '/getuserlist' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'  ,style:'height:60px'  }
      ,{field: 'id', width: 100, title: 'ID', sort: true}
      ,{field: 'username', title: '用户名', }
      ,{field: 'nickName', title: '昵称', minWidth: 150}
      ,{field: 'avatar', title: '头像', style:'height:100%' , width: 100, templet: '#imgTpl'}
      ,{field: 'phone', title: '手机'}
      ,{field: 'sex', width: 80, title: '性别',templet:function (d) {
                  return d.sex==0?"未知":(d.sex==1?"男":"女");
      }}
        ,{field: 'status',  title: '状态' ,align:'center',width: 100,templet: statusTpl}
        ,{field: 'created', title: '加入时间', width: 160, sort: true,templet:function (d) {
                  return Format(d.created,"yyyy-MM-dd hh:mm:ss");
              }}
      ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
    ]]
    ,page: true
    ,limit: 30
    ,height: 'full-220'
    ,text: '对不起，加载出现异常！'
  });
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
  table.on('tool(LAY-user-manage)', function(obj){
    var data = obj.data;
      if(obj.event === 'edit'){
          location.href="/user/detail?id="+data.id

      }
  });




  exports('userlist', {})
});