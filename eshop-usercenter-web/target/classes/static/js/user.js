var userPic;
var falg = true;
$(function () {
  $('#tab').TabPanel({tab:0,callback:function(no){
    if(no==1 && falg){
      uploadUserPhoto();falg = false;
    }
    
  }});
});


function uploadUserPhoto()
{
  WST.upload({
    pick:'#userPhotoPicker',
    formData: {dir:'users',isCut:1,isLocation:1},
    accept: {extensions: 'gif,jpg,jpeg,png',mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif'},
    callback:function(f){
      console.log(f)
      if(f.status==200){
        /*上传成功*/


        $('#photoSrc').val(f.data.url );
        $('#userPic').val(f.data.url );
        $('.usersImg').attr('src',f.data.url );
     console.log(f.data.url)

      }else{
        WST.msg(f.msg,{icon:2});
      }
      },
      progress:function(rate){
          $('#uploadMsg').show().html('已上传'+rate+"%");
      }
      });
}
function returnPhotoPage(){
    $('#userPhotoCut').hide();
    $('#userPhoto').show();
}

var jcrop_api;
function jcropInit(){
    var boundx,
        boundy,

        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize = $pcnt.width(),
        ysize = $pcnt.height();

    $('#target').Jcrop({
      onChange: updatePreview,
      onSelect: updatePreview,
      aspectRatio: 1,
    },function(){
      // Use the API to get the real image size
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      //设置宽度以使文字居中
      $('#img-src').css('width',boundx+'px');

      // Store the API in the jcrop_api variable
      jcrop_api = this;
      jcrop_api.setSelect([0,0,150,150]);

      // Move the preview into the jcrop container for css positioning
      $preview.appendTo(jcrop_api.ui.holder);
    });

    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = xsize / c.w;
        var ry = ysize / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
      }
        //设置裁剪的图片数据
      $('#x').val(c.x);
      $('#y').val(c.y);
      $('#w').val(c.w);
      $('#h').val(c.h);
    };
}


$(function(){
  /* 表单验证 */
  $('#userEditForm').validator({
          fields: {
              userName: {rule:"required",msg:{required:"请输入昵称"},tip:"请输入昵称"},
              userSex:  {rule:"checked;",msg:{checked:"至少选择一项"},tip:"请选择您的性别"}
          },
        valid: function(form){
          var params = WST.getParams('.ipt');
          if(!userPic){
            userPic = $('#userPic').val();
          }
          //接收上传的头像路径
          params.userPhoto = userPic;
          var loading = WST.msg('正在提交数据，请稍后...', {icon: 16,time:60000});

          WST.ajaxform(JSON.stringify(params),'/saveuser',function () {
              layer.close(loading);
              return;
          })

  },
    
  });

});