/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });



  layui.use('table', function(){
    var $ = layui.$
    ,table = layui.table;

    table.render({
      elem: '#LAY-index-items'
      ,url: '/getnewitems'
      ,page: false
      ,cols: [[
         {field: 'title', title: '名称', minWidth: 120, templet: '<div><a href="http://localhost:8003/item/detail?id={{ d.id }}" target="_blank" class="layui-table-link">{{ d.title }}</div>'}
        ,{field: 'price', title: '价格', minWidth: 120, sort: true}
        ,{field: 'monSales', title: '月销量', sort: true}
      ]]
      ,skin: 'line'
    });


  });
  
  exports('console', {})
});