function login(tologinurl,forwardurl){
	// if (forwardurl==null||forwardurl==""||forwardurl==undefined){
     //    forwardurl=
	// }

var usernmae=$('input[name=loginName]').val()
	var  password=$('input[name=loginPwd]').val();
if(usernmae==""){
    layer.msg('用户名不能为空', {icon: 2, time: 1000});
   return false
}
    if(usernmae==""){
        layer.msg('密码不能为空', {icon: 2, time: 1000});
        return false;
    }

	var params = WST.getParams('.ipt');

// var load=layer.
//     var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    var user={
        'username':usernmae,
        'password':password
    }

    $.ajax({
        data: JSON.stringify(user),
        type: 'post',
        async: true,
        dataType: 'json',
        url:tologinurl,
        contentType:"application/json",
        beforeSend: function () {
            loading = layer.load(2, {
                shade: [0.2, '#000']
            })
        },
        xhrFields: {
            withCredentials: true
        },
        success: function (res) {
            layer.close(loading);
            if (res.status == 200) {
                WST.msg('登录成功!', {icon: 1}, function(){
                    if (forwardurl==null||forwardurl==""||forwardurl==undefined){
                       location.reload();
                    }else{
                        location.href=forwardurl

                    }
                });

            } else {
                layer.msg(res.msg, {icon: 2, time: 1000});

            }

        }, error: function (res) {
            layer.close(loading);
            layer.msg('未知错误', {icon: 2, time: 1000});

        }


    })
	return true;
}


var time = 0;
var isSend = false;
var isUse = false;
var index2 = null;
function getVerifyCode(){
	var params = {};
		params.userPhone = $.trim($("#phone").val());
		if ( $.trim($("#loginName").val())==''){
            WST.msg('请输入用户名!', {icon: 5});
            return;
		}
    var pattern = /^[a-zA-Z][0-9a-zA-Z_-]{5,}/;
    if (! pattern.test($.trim($("#loginName").val()) )){
        WST.msg('!用户名格式不准确', {icon: 5});
        return;
    }

    if ( $.trim($("#nickName").val())==''){
        WST.msg('请输入昵称!', {icon: 5});
        return;
    }
	if(params.userPhone==''){
		WST.msg('请输入手机号码!', {icon: 5});
		return;
	}
	if(isSend )return;
	isSend = true;
    getPhoneVerifyCode(params);
}
//哦那个湖面
function getPhoneVerifyCode(params){
	WST.msg('正在发送短信，请稍后...',{time:600000});
	$.post('/sendPhoneCode',params,function(data,textStatus){
		var json = WST.toJson(data);
		if(json.status!=200){
			WST.msg(json.msg, {icon: 5});
			time = 0;
			isSend = false;
		}if(json.status==200){
			WST.msg('短信已发送，请注意查收');
			time = 120;
			$('#timeTips').html('获取验证码(120)');
			$('#mobileCode').val(json.phoneVerifyCode);
			var task = setInterval(function(){
				time--;
				$('#timeTips').html('获取验证码('+time+")");
				if(time==0){
					isSend = false;						
					clearInterval(task);
					$('#timeTips').html("重新获取验证码");
				}
			},1000);
		}
		if(json.status!=-2)layer.close(index2);
	});
}
function initRegist(){
	// 阻止按下回车键时触发短信验证码弹窗
	document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];        
             if(e && e.keyCode==13){ // enter 键
             	$('#reg_butt').submit();
             	return false;
            }
    }
	$('#reg_form').validator({
	    rules: {
	    	phone: function(element) {
	    		if(this.test(element, "mobile")===true){
	    			$("#mobileCodeDiv").show();
	    			$("#refreshCode").hide();
	    			$("#authCodeDiv").hide();
	    			$("#nameType").val('3');
	    		}
	            return this.test(element, "mobile")===true || '请填写有效的手机号';
	        },
            loginName: function(element) {
                var loginName=$.trim($("#loginName").val());
                if (loginName=="" ) {
                	return '请填写用户名';
				}
                var pattern = /^\w.{5,}/;
                if (! pattern.test($.trim($("#loginName").val()) )){
                    return '!用户名格式不准确';
                }
            },
	        mobileCode: function(element){
	        	if(this.test(document.getElementById("phone"), "mobile")===true){
	        		return true;
	    		}
	        	return false;
	        },
	        verifyCode: function(element){
	        	if(this.test(document.getElementById("phone"), "mobile")===false){
	        		return true;
	    		}else{
	    			return false;
	    		}
	        },
	        //自定义remote规则（注意：虽然remote规则已经内置，但这里的remote会优先于内置）
            remoteLogin: function(element){
	        	return $.post('/checkUser',{"loginName":element.value},function(data,textStatus){
	        	});

	        },
            remotePhone: function(element){
                return $.post('/checkPhone',{"phone":element.value},function(data,textStatus){
                });

            }
	    },
	    fields: {
	        'loginName': 'required; loginName; remoteLogin;',
	        'phone': 'required;    remotePhone;',
	        'loginPwd' : '密码:required; password;',
	        'reUserPwd': '确认密码:required; match(loginPwd);',
	        // 'mobileCode': {rule:"required(mobileCode)",msg:{required:'请输入短信验证码'}},
	        // 'verifyCode': {rule:"required(verifyCode)",msg:{required:'请输入验证码'}}
	    },
	    // 表单验证通过后，ajax提交
	    valid: function(form){
	        var me = this;
	        // ajax提交表单之前，先禁用submit
	        me.holdSubmit();

	        // onfocusout()
	        $("#reg_butt").css('color', '#999').text('正在提交..');
var user={
	'username':$('input[name=loginName]').val(),
	'password':$('input[name=loginPwd]').val(),
	'rePassword': $('input[name=reUserPwd]').val(),
	'phone':$('input[name=phone]').val(),
	'code':$('input[name=mobileCode]').val(),
	'nickName':$('input[name=nickName]').val()
}
            $.ajax({
                data: JSON.stringify(user),
                type: 'post',
                async: true,
                dataType: 'json',
                url: '/toRegister',
                contentType:"application/json",
                beforeSend: function () {
                    loading = layer.load(2, {
                        shade: [0.2, '#000']
                    })
                },
                success: function (res) {
                    layer.close(loading);
                    if (res.status == 200) {
        	WST.msg('注册成功，请登录!', {icon: 1}, function(){
location.href="/login"
                 });

                    } else {
                        layer.msg(res.msg, {icon: 2, time: 1000});

                    }

                }, error: function (res) {
                    layer.close(loading);
                    layer.msg('未知错误', {icon: 2, time: 1000});

                }


            })
            // $.post('/toRegister',JSON.stringify(params),function(data,textStatus){
	    	// 	var json = WST.toJson(data);
	    	// 	if(json.status==200){
	    	// 		WST.msg('注册成功，正在跳转登录!', {icon: 1}, function(){
	    	// 			var url = json.url;
	         //        	if(WST.blank(url)){
	         //        		location.href = url;
	         //        	}else{
	         //        	}
	       	// 		});
	    	// 	}else{
	    	// 		me.holdSubmit(false);
	    	// 		WST.msg(json.msg, {icon: 5});
	    	// 	}
            //
	    	// });
	    }
	});
}