var WST_CURR_PAGE = 1;

function waitReceiveByPage(url){
	$('#loading').show();
	var params = {};
	params = WST.getParams('.u-query');
	params.key = $.trim($('#key').val());
    console.log(params)
	location.href=url+'?oid='+params.orderId



}
function waitAppraiseByPage(p){
	$('#loading').show();
	var params = {};
	params = WST.getParams('.s-query');
 console.log(params)
}
function cancel(id,type){

    layer.confirm('你确定要取消订单吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        loading = layer.load(2, {
            shade: [0.2, '#000']
        })
        $.ajax({
            url:window.conf.ORDER_URL+'/updatestatus?id='+id+"&status=0",
            type:'post',
            dataType:'json',
            xhrFields: {
                withCredentials: true
            },
            data:{
            },
            success:function (res) {
                layer.close(loading);
                layer.msg(res.msg, {icon: 1, time: 1000});
                setTimeout(function () {
                    layer.closeAll()
					location.reload()
                }, 1000)
            }
        })
    }, function(){
        layer.closeAll()
    });
}
function toReceive(id){

    layer.confirm('你确定收货吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        loading = layer.load(2, {
            shade: [0.2, '#000']
        })
        $.ajax({
            url:window.conf.ORDER_URL+'/updatestatus?id='+id+"&status=5",
            type:'post',
            dataType:'json',
            xhrFields: {
                withCredentials: true
            },
            data:{
            },
            success:function (res) {
                layer.close(loading);
                layer.msg(res.msg, {icon: 1, time: 1000});
                setTimeout(function () {
                    layer.closeAll()
                    location.reload()
                }, 1000)
            }
        })
    }, function(){
        layer.closeAll()
    });
}
function changeRejectType(v){
	if(v==10000){
		$('#rejectTr').show();
	}else{
		$('#rejectTr').hide();
	}
}

/******************** 评价页面 ***********************/
function appraisesShowImg(id){
  layer.photos({
      photos: '#'+id
    });
}

//文件上传
function upload(n){
    var uploader =WST.upload({
        pick:'#filePicker'+n,
        formData: {dir:'appraises',isThumb:1},
        fileNumLimit:5,
        accept: {extensions: 'gif,jpg,jpeg,png',mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif'},
        callback:function(f,file){
          var json = WST.toJson(f);
          if(json.status==1){
          var tdiv = $("<div style='width:75px;float:left;margin-right:5px;'>"+
                       "<img class='appraise_pic"+n+"' width='75' height='75' src='"+WST.conf.RESOURCE_PATH+"/"+json.savePath+json.thumb+"' v='"+json.savePath+json.name+"'></div>");
          var btn = $('<div style="position:relative;top:-80px;left:60px;cursor:pointer;" ><img src="'+WST.conf.ROOT+'/wstmart/home/view/default/img/seller_icon_error.png"></div>');
          tdiv.append(btn);
          $('#picBox'+n).append(tdiv);
          btn.on('click','img',function(){
            uploader.removeFile(file);
            $(this).parent().parent().remove();
            uploader.refresh();
          });
          }else{
            WST.msg(json.msg,{icon:2});
          }
      },
      progress:function(rate){
          $('#uploadMsg').show().html('已上传'+rate+"%");
      }
    });
}


/* 用户评价管理 */
function showImg(id){
  layer.photos({
      photos: '#img-file-'+id
    });
}
function userComplainInit(){
	 var uploader =WST.upload({
        pick:'#filePicker',
        formData: {dir:'complains',isThumb:1},
        fileNumLimit:5,
        accept: {extensions: 'gif,jpg,jpeg,png',mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif'},
        callback:function(f,file){
          var json = WST.toJson(f);
          if(json.status==1){
          var tdiv = $("<div style='width:75px;float:left;margin-right:5px;'>"+
                       "<img class='complain_pic"+"' width='75' height='75' src='"+WST.conf.RESOURCE_PATH+"/"+json.savePath+json.thumb+"' v='"+json.savePath+json.name+"'></div>");
          var btn = $('<div style="position:relative;top:-80px;left:60px;cursor:pointer;" ><img src="'+WST.conf.ROOT+'/wstmart/home/view/default/img/seller_icon_error.png"></div>');
          tdiv.append(btn);
          $('#picBox').append(tdiv);
          btn.on('click','img',function(){
            uploader.removeFile(file);
            $(this).parent().parent().remove();
            uploader.refresh();
          });
          }else{
            WST.msg(json.msg,{icon:2});
          }
      },
      progress:function(rate){
          $('#uploadMsg').show().html('已上传'+rate+"%");
      }
    });
}
