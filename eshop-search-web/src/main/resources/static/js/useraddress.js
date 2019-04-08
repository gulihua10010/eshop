function userAddrEditInit(){
 /* 表单验证 */
    $('#useraddressForm').validator({
          fields: {
              receiverAddress: {
                  rule:"required;length[~60, true]",
                  msg:{required:"请输入您的收货地址"},
                  tip:"请输入您的收货地址",
                  ok:"",
                },
              receiverName: {
                  rule:"required;length[~12, true]",
                  msg:{required:"请输入您的名称"},
                  tip:"请输入您的名称",
                  ok:"",
                },
              receiverPhone: {
                  rule:"required;length[~50, true]",
                  msg:{required:"联系电话"},
                  tip:"请输入您的联系电话",
                  ok:"",
                },
                isDefault: {
                    rule:"checked;",
                    msg:{checked:"至少选择一项"},
                    tip:"是否作为默认地址",
                    ok:"",
                }
          },
          valid: function(form){
        	var isNoSelected = false;
        	$('.j-eipt').each(function(){
        		isSelected = true;
        		if($(this).val()==''){
        		    isNoSelected = true;
        			return;
        		}
        	});
        	if(isNoSelected){
        		WST.msg('请选择完整区域！',{icon:2});
        		return;
        	}
              var phonerex=/^1([38]\d|5[0-35-9]|7[3678])\d{8}$/
              if(!phonerex.test($('#userPhone').val())){
                  WST.msg('请输入正确的手机号码！',{icon:2});
                  return;
              }
            var params = WST.getParams('.ipt');
            var loading = WST.msg('正在提交数据，请稍后...', {icon: 16,time:60000});

              WST.ajaxform(JSON.stringify(params),"/useraddress/"+((params.id>0)?'toEdit':'add') ,function (res) {
                  location.href="/usercenter/address"
            return false;

              })

      }

    });
 }
function listQuery(){
   $.post(WST.U('Home/Useraddress/listQuery'),'',function(data,textStatus){
    var json = WST.toJson(data);
    if(json.status==1 && json.data){
      json = json.data;
	    var count = json.length;//已添加的记录数
	    $('.g1').html(count);
	    var gettpl = document.getElementById('address').innerHTML;
	    laytpl(gettpl).render(json, function(html){
	        $('#address_box').html(html);
	    });
    }else{
    	$('#address_box').empty();
    }
});
}

function editAddress(id){
   location.href="/usercenter/addaddress?id="+id;
}

function delAddress(id,t){
    WST.confirm({content:'您确定要删除该地址吗？',yes:function(index){
            $.post("/deladdress",{id:id},function(res){
                if(res.status==200){
                    WST.msg(res.msg,{icon:1});
                   location.reload()
                }else{
                    WST.msg(res.msg,{icon:2});
                }
            });
        }});

}
function setDefault(id){
    $.post("/setdefaultaddress",{id:id},function(res){
        if(res.status==200){
            location.reload()

        }
    });
}
