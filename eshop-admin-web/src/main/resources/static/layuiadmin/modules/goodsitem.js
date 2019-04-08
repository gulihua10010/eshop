

layui.define(['table', 'form'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form;

  //商品列表
  table.render({
    elem: '#LAY-goodsitem-manage'
    ,url:   '/Item/getItemList' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 150, title: 'ID', sort: true}
          ,{field: 'image', title: '缩略图', width: 100  ,style:'height:100px;',templet: '#imgTpl'}
          ,{field: 'title', title: '名称'}
      ,{field: 'price', title: '价格',width: 100,templet:function (d) {
                      return Formatprice(d.price);
                  }}
      ,{field: 'num', title: '库存数量',width: 120,}
      ,{field: 'status',  title: '状态' ,align:'center',width: 100,templet: statusTpl}
      ,{field: 'recommended',  title: '推荐' ,align:'center',width: 100,templet: recommendedTpl}
      ,{field: 'itemCat', title: '商品类别',templet: itemcatTpl,width: 100}
      ,{field: 'created', title: '添加时间', sort: true,width: 170,templet:function (d) {
              return Format(d.created,"yyyy-MM-dd hh:mm:ss");
}}
      ,{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
    ]]
    ,page: true
    ,limit: 30
    ,height: 'full-220'
    ,text: '对不起，加载出现异常！'
  });
  function Formatprice(price) {
      return '￥'+price/100
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
  table.on('tool(LAY-goodsitem-manage)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.prompt({
        formType: 1
        ,title: '敏感操作，请验证口令'
      }, function(value, index){
        layer.close(index);
        if (value!="111111"){
            layer.msg( '口令错误！',{icon: 5});
        }else{

            layer.confirm('真的删除这行数据么', function(index){
                loading = layer.load(2, {
                    shade: [0.2,'#000']
                });
                var url="/Item/delItem?id="+data.id;
                $.get(url,function(data){
                    console.log(data)
                    if(data.status == 200){
                        layer.close(loading);
                        layer.msg(data.msg, {icon: 1, time: 1000}, function(){
                            //  location.reload();
                            obj.del();

                        });
                    }else{
                        layer.close(loading);
                        layer.msg(data.msg, {icon: 2, anim: 6, time: 1000});
                    }
                });
                layer.close(index);
            });
        }
        

      });
    } else if(obj.event === 'edit'){
 location.href="/Item/editItem?id="+data.id

    }
  });



  

  exports('goodsitem', {})
});