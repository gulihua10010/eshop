var promotionMethod = {};
function checkChks(obj,cobj){
	// cosn cson
	if (cobj=='.j-chk'){

        WST.checkChks(obj,cobj);
	}
	var ids = [];
	$(cobj).each(function(){
		if(obj.checked){
			$(this).addClass('selected');
		}else{
			$(this).removeClass('selected');
		}
		var cid = $(this).val();
        console.log(cid)
		if(cid!='' && typeof(cid)!='undefined'){
			ids.push(cid);
		    statCartMoney();
	    }
	});
}


function  changeprice(id) {	 
  var ids=$('#ids').val();
  var idss=ids.splice(',')
}

function selement(id) {
    var sel=$('#gchk_'+id)
    var id=sel.val();
    var price=sel.attr('price');

    var ids='buyNum_'+id;
    var num=$('#'+ids).val()
    $('#totalMoney').text(num*price)
    $('#payment').val(num*price)
}
function statCartMoney(){
	var cartMoney = 0,goodsTotalPrice,id;
	$('.j-gchk').each(function(){
		id = $(this).val();
		goodsTotalPrice = parseFloat($(this).attr('mval'))*parseInt($('#buyNum_'+id).val());
		$('#tprice_'+id).html(goodsTotalPrice.toFixed(2));
		if($(this).prop('checked')){	
			cartMoney = cartMoney + goodsTotalPrice;
		}
	});
	var minusMoney = 0;
	for(var key in promotionMethod){
        minusMoney = window[key](cartMoney);
        cartMoney = cartMoney - minusMoney;
	}
	$('#totalMoney').html(cartMoney.toFixed(2));
	checkGoodsBuyStatus();
}
function checkGoodsBuyStatus(){
	var cartNum = 0,stockNum = 0,cartId = 0;
	$('.j-gchk').each(function(){
		cartId = $(this).val();
		cartNum = parseInt($('#buyNum_'+cartId).val(),10);
		stockNum = parseInt($(this).attr('sval'),10);;
		if(stockNum < 0 || stockNum < cartNum){
			if($(this).prop('checked')){
				$(this).parent().parent().css('border','2px solid red');
			}else{
				$(this).parent().parent().css('border','0px solid #eeeeee');
				$(this).parent().parent().css('border-bottom','1px solid #eeeeee');
			}
			if(stockNum < 0){
				$('#gchk_'+cartId).attr('allowbuy',0);
				$('#err_'+cartId).css('color','red').html('库存不足');
			}else{
				$('#gchk_'+cartId).attr('allowbuy',1);
				$('#err_'+cartId).css('color','red').html('购买量超过库存');
			}
		}else{
			$('#gchk_'+cartId).attr('allowbuy',10);
			$(this).parent().parent().css('border','0px solid #eeeeee');
			$(this).parent().parent().css('border-bottom','1px solid #eeeeee');
			$('#err_'+cartId).html('');
		}
	});
}
function toSettlement(){
	var isChk = false;
	$('.j-gchk').each(function(){
		if($(this).prop('checked'))isChk = true;
	});
	if(!isChk){
		WST.msg('请选择要结算的商品!',{icon:1});
		return;
	}
	var msg = '';
	$('.j-gchk').each(function(){
		if($(this).prop('checked')){
			if($(this).attr('allowbuy')==0){
				msg = '所选商品库存不足';
				return;
			}else if($(this).attr('allowbuy')==1){
				msg = '所选商品购买量大于商品库存';
				return;
			}
		}
	})
	if(msg!=''){
		WST.msg(msg,{icon:2});
		return;
	}
	var data=[]
//j-g
	$('.j-g').each(function () {
		var id=$(this).val()
		var  num=$('#buyNum_'+id).val();
		var ischeck=$('#gchk_'+$(this).val() ).is(':checked')
		console.log(id)
		console.log(num)
		if (ischeck){
			data.push({'id':id,'selnum':num})
		}

    })
    WST.ajaxform(JSON.stringify(data),"/setitemorder",function (res) {
		if (res.status==200) {

			location.href="/order/settlement?type=2&token="+res.data
		}
    })
}

function addrBoxOver(t){
	$(t).addClass('radio-box-hover');
	$(t).find('.operate-box').show();
}
function addrBoxOut(t){
	$(t).removeClass('radio-box-hover');
	$(t).find('.operate-box').hide();
}



function setDeaultAddr(id){
	$.post("/setdefaultaddress",{id:id},function(res){
		if(res.status==200){
			getAddressList();
			changeAddrId(id);
		}
	});
}


function changeAddrId(id){

	$.post("/getaddressbyid",{id:id},function(res){

		if(res.status==200&&res.data!=null){
			inEffect($('#addr-'+id),1);
			$('#s_addressId').val(res.data.id);
            $('.j-edit-box').hide();
            $('.j-list-box').hide();
            $('.j-show-box').show();
            var params=res.data;
            var areaNames = [];
            var areaNames = [];

            $('#s_userName').html(params.receiverName+'<i></i>');
            $('#s_address').html(params.receiverName+'&nbsp;&nbsp;&nbsp;'+params.receiverProvinces+params.receiverCity+params.receiverDistrict+'&nbsp;&nbsp;'+params.receiverAddress+'&nbsp;&nbsp;'+params.receiverPhone);

            $('#s_address').siblings('.operate-box').find('a').attr('onclick','toEditAddress('+params.id+',this,1,1,1)');

            if(params.isDefault==1){
                $('#isdefault').html('默认地址').addClass('j-default');
            }else{
                $('#isdefault').html('').removeClass('j-default');
            }

		}
	})
	console.log(	$('#s_addressId').val())
}

function delAddr(id){
	WST.confirm({content:'您确定要删除该地址吗？',yes:function(index){
		$.post("/deladdress",{id:id},function(res){
		     if(res.status==200){
		    	 WST.msg(res.msg,{icon:1});
		    	 getAddressList();
		     }else{
		    	 WST.msg(res.msg,{icon:2});
		     }
		});
	}});
}

function getAddressList(obj){
	var id = $('#s_addressId').val();
	var load = WST.load({msg:'正在加载记录，请稍后...'});
	$.post("/getaddresslist",{rnd:Math.random()},function(res){
		 layer.close(load);
	     if(res.status==200){
	    	 if(res.data   && res.data.length>0){
	    		 var html = [],tmp;
	    		 for(var i=0;i<res.data.length;i++){
	    			 tmp = res.data[i];
	    			 var selected = (id==tmp.id)?'j-selected':'';
	    			 console.log(tmp.id)
	    			 html.push(
	    					 '<div class="wst-frame1 '+selected+'" onclick="javascript:changeAddrId('+tmp.id+')" id="addr-'+tmp.id+'" >'+tmp.receiverName+'<i></i></div>',
	    					 '<li class="radio-box" onmouseover="addrBoxOver(this)" onmouseout="addrBoxOut(this)">',
	    					 tmp.receiverName,
	    					 '&nbsp;&nbsp;',
	    					 tmp.receiverProvinces+tmp.receiverCity+tmp.receiverDistrict+tmp.receiverAddress,
	    					 '&nbsp;&nbsp;&nbsp;&nbsp;',
	    					 tmp.receiverPhone
	    					 )
	    			if(tmp.isDefault==1){
	    				html.push('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="j-default">默认地址</span>')
	    			}		
	    			html.push('<div class="operate-box">');
	    			if(tmp.isDefault!=1){
	    				html.push('<a href="javascript:;" onclick="setDeaultAddr('+tmp.id+')">设为默认地址</a>&nbsp;&nbsp;');
	    			}
	    			html.push('<a href="javascript:void(0)" onclick="javascript:toEditAddress('+tmp.id+',this,1,1)">编辑</a>&nbsp;&nbsp;');
	    			if(res.data.length>1){
	    				html.push('<a href="javascript:void(0)" onclick="javascript:delAddr('+tmp.id+',this)">删除</a></div>');
	    			}
	    			html.push('<div class="wst-clear"></div>','</li>');
	    		 }
	    		 html.push('<a style="color:#1c9eff"  href="javascript:;" onclick="shou()">收起地址</a>');


	    		 $('#addressList').html(html.join(''));
	    	 }else{
	    		 $('#addressList').empty();
	    	 }
	     }else{
	    	 $('#addressList').empty();
	     }
	})
}




function toEditAddress(id,obj,n,flag,type){

    inEffect(obj,n);
    id = (id>0)?id:$('#s_addressId').val();
    if(type!=1){
        $('.j-list-box').show();
        $('.j-show-box').hide();
    }
    if (id>0){



        $.post("/getaddressbyid",{id:id},function(res){
            if(res.status==200){
                if(flag){
                    layerbox =	layer.open({
                        title:'用户地址',
                        type: 1,
                        area: ['800px', '300px'], //宽高
                        content: $('.j-edit-box')
                    });
                }

                WST.setValues(res.data);
                $("#distpicker").distpicker('destroy')
                $("#distpicker").distpicker({
                    province:res.data.receiverProvinces,
                    city: res.data.receiverCity,
                    district: res.data.receiverDistrict,
                });
                // $('#city').data('city',res.data.receiverDistrict)
                // $('#district').data('district',res.data.receiverDistrict)
                $('input[name="addrUserPhone"]').val(res.data.receiverPhone)
                var tmp=res.data

            }else{
                WST.msg(json.msg,{icon:2});
            }
        });
    }
}
function shou(){
    $('.j-list-box').hide();
    $('.j-show-box').show();
}
function inEffect(obj,n){
	$(obj).addClass('j-selected').siblings('.wst-frame'+n).removeClass('j-selected');
}


function editAddress(){
	var isNoSelected = false;
	$('.j-eipt').each(function(){
		isSelected = true;
		if($(this).val()==''){
			isNoSelected = true;
			return;
		}
	})

	if(isNoSelected){
		WST.msg('请选择完整收货地址！',{icon:2});
		return;
	}
	var phonerex=/^1([38]\d|5[0-35-9]|7[3678])\d{8}$/
    if(!phonerex.test($('#userPhone').val())){
        WST.msg('请输入正确的手机号码！',{icon:2});
        return;
    }
	layer.close(layerbox);
	var load = WST.load({msg:'正在提交数据，请稍后...'});
	var params = WST.getParams('.j-eipt');
	console.log(params)
    WST.ajaxform(JSON.stringify(params),"/useraddress/"+((params.id>0)?'toEdit':'add') ,function (res) {
        if(res.status==200){
                	 $('.j-edit-box').hide();
                	 $('.j-list-box').hide();
                	 $('.j-show-box').show();
                	 if(params.id==0){
                		 $('#s_addressId').val(res.data.id);
                	 }else{
                		 $('#s_addressId').val(params.id);
                	 }
                	 var areaIds = WST.ITGetAllAreaVals('area_0','j-areas');
                	 $('#s_areaId').val(areaIds[1]);
                	 // getCartMoney();
                	 var areaNames = [];
                	 $('.j-areas').each(function(){
                		 areaNames.push($('#'+$(this).attr('id')+' option:selected').text());
                	 })
                	 $('#s_userName').html(params.receiverName+'<i></i>');
                	 $('#s_address').html(params.receiverName+'&nbsp;&nbsp;&nbsp;'+areaNames.join('')+'&nbsp;&nbsp;'+params.receiverAddress+'&nbsp;&nbsp;'+params.receiverPhone);

                	 $('#s_address').siblings('.operate-box').find('a').attr('onclick','toEditAddress('+params.id+',this,1,1,1)');

                	 if(params.isDefault==1){
                		 $('#isdefault').html('默认地址').addClass('j-default');
                	 }else{
                		 $('#isdefault').html('').removeClass('j-default');
                	 }
                 }else{
                	 WST.msg(json.msg,{icon:2});
                 }
    })

}
var layerbox;
function showEditAddressBox(){
	getAddressList();
	toEditAddress();
}
function emptyAddress(obj,n){
	inEffect(obj,n);
	$('#addressForm')[0].reset();
	$('#s_addressId').val(0);
	$('#addressId').val(0);
    layerbox =	layer.open({
					title:'用户地址',
					type: 1,
					area: ['800px', '300px'],
					content: $('.j-edit-box')
					});


}
function getCartMoney(){
	var params = {};
	params.isUseScore = $('#isUseScore').prop('checked')?1:0;
	params.useScore = $('#useScore').val();
	params.areaId2 = $('#s_areaId').val();
	params.rnd = Math.random();
	params.deliverType = $('#deliverType').val();
	var couponIds = [];
	$('.j-shop').each(function(){
		couponIds.push($(this).attr('dataval')+":"+$('#couponId_'+$(this).attr('dataval')).val());
	});
	params.couponIds = couponIds.join(',');
	var load = WST.load({msg:'正在计算订单价格，请稍后...'});
	$.post(WST.U('home/carts/getCartMoney'),params,function(data,textStatus){
		layer.close(load);  
		var json = WST.toJson(data);
		if(json.status==1){
		    json = json.data;
		    var shopFreight = 0;
		    for(var key in json.shops){
		    	// 设置每间店铺的运费及总价格
		    	$('#shopF_'+key).html(json.shops[key]['freight']);
		    	$('#shopC_'+key).html(json.shops[key]['goodsMoney']);
		    	shopFreight = shopFreight + json.shops[key]['freight'];
		    }
		    $('#maxScoreSpan').html(json.maxScore);
		    $('#maxScoreMoneySpan').html(json.maxScoreMoney);
		    $('#isUseScore').attr('dataval',json.maxScore);
		    $('#deliverMoney').html(shopFreight);
		    $('#useScore').val(json.useScore);
		    $('#scoreMoney2').html(json.scoreMoney);
		 	$('#totalMoney').html(json.realTotalMoney);
		}
	});
}
function changeDeliverType(n,index,obj){
	changeSelected(n,index,obj);
	getCartMoney();
}
function submitOrder(){
	if ($('#s_addressId').val()==''||$('#s_addressId').val()==0){
        layer.msg('无效的地址', {icon: 2, time: 1000});
	}
	var params = WST.getParams('.j-ipt');
	var load = WST.load({msg:'正在提交，请稍后...'});
	console.log(params)
	WST.ajaxform(JSON.stringify(params),"/orders/submit",function (res) {
        layer.close(load);
        location.href="/forpayment?orderid="+res.data
    })

}


var invoicebox;
function layerclose(){
  layer.close(invoicebox);
}


function changeInvoiceItem(t,obj){
	$(obj).addClass('inv_li_curr').siblings().removeClass('inv_li_curr');
	$('.inv_editing').remove();// 删除正在编辑中的发票信息
	$('.inv_add').show();
	$('#invoiceId').val(t);
	if(t==0){
		// 为个人时，隐藏识别号
		$('.inv_codebox').css({display:'none'});
		$('#invoice_num').val(' ');
	}else{
		$('#invoice_num').val($('#invoiceCode_'+t).val());
		$('.inv_codebox').css({display:'block'});
	}
	$("#invoice_obj").val(t);
}
// 是否需要开发票
function changeInvoiceItem1(t,obj){
	$(obj).addClass('inv_li_curr').siblings().removeClass('inv_li_curr');
	$('#isInvoice').val(t);
}
// 显示发票增加
function invAdd(){
	$("#invoiceId").val(0);
	$("#invoice_obj").val(1);
	$('#invoice_num').val('');
	$('.inv_li').removeClass('inv_li_curr');// 移除当前选中样式
	$('.inv_ul').append('<li class="inv_li inv_li_curr inv_editing"><input type="text" id="invoiceHead" placeholder="新增单位发票抬头" value="" style="width:65%;height:21px;padding:1px;"><i></i><div style="top:8px;" class="inv_opabox"><a href="javascript:void(0)" onCLick="addInvoice()">保存</a></div></li>');
	$('.inv_ul').scrollTop($('.inv_ul')[0].scrollHeight);// 滚动到底部
	$('.inv_add').hide();// 隐藏新增按钮
	$('.inv_codebox').css({display:'block'});// 显示`纳税人识别号`
}
// 执行发票抬头新增
function addInvoice(){
	var head = $('#invoiceHead').val();
	if(head.length==0){
		WST.msg('发票抬头不能为空');
		return;
	}
	var loading = WST.load({msg:'正在提交数据，请稍后...'});
	$.post(WST.U('home/Invoices/add'),{invoiceHead:head},function(data){
		var json = WST.toJson(data);
		layer.close(loading);
		if(json.status==1){
			$('#invoiceId').val(json.data.id);
			WST.msg(json.msg,{icon:1});
			$('.inv_editing').remove();
			var code = [];
			code.push('<li class=\'inv_li inv_li_curr\' onClick="changeInvoiceItem(\''+json.data.id+'\',this)">');
     		code.push('<input type="text" value="'+head+'" readonly="readonly" class="invoice_input" id="invoiceHead_'+json.data.id+'" />');
			code.push('<input type="hidden" id="invoiceCode_'+json.data.id+'" value=""} /><i></i>');
			code.push('<div class="inv_opabox">');
			code.push('<a href=\'javascript:void(0)\' onClick="invEdit(\''+json.data.id+'\',this)" class="edit_btn">编辑</a>');
			code.push('<a href=\'javascript:void(0)\' onClick="editInvoice(\''+json.data.id+'\',this)" style="display:none;" class="save_btn">保存</a>');
			code.push('<a href=\'javascript:void(0)\' onClick="delInvoice(\''+json.data.id+'\',this)">删除</a></div></li>');
			$('.inv_li:first').after(code.join(''));
			// 显示新增按钮
			$('.inv_add').show();
		}else{
			WST.msg(json.msg,{icon:2});
		}
	});
}
// 显示发票修改
function invEdit(id,obj){
	var input = $(obj).parent().parent().find('.invoice_input');
	input.removeAttr('readonly').focus();
	input.mouseup(function(){return false});
	$(obj).parent().parent().mouseup(function(){
		input.attr('readonly','readonly');
		$(obj).show().siblings('.save_btn').hide();
	});
	$(obj).hide().siblings('.save_btn').show();
	var invoice_code = $('#invoiceCode_'+id).val();
	$('.inv_codebox').css({display:'block'})
	$('#invoice_num').val(invoice_code);// 显示`纳税人识别号`)
}
// 完成发票修改
function editInvoice(id,obj){
	var head = $('#invoiceHead_'+id).val();
	if(head.length==0){
		WST.msg('发票抬头不能为空');
		return;
	}
	var loading = WST.load({msg:'正在提交数据，请稍后...'});
	$.post(WST.U('home/Invoices/edit'),{invoiceHead:head,id:id},function(data){
		var json = WST.toJson(data);
		layer.close(loading);
		if(json.status==1){
			var input = $(obj).parent().parent().find('.invoice_input');
			input.attr('readonly','readonly')
			$(obj).hide().siblings('.edit_btn').show();
			WST.msg(json.msg,{icon:1});
		}else{
			WST.msg(json.msg,{icon:2});
		}
	});
}

// 设置页面显示值
function setInvoiceText(invoiceHead){
	var isInvoice  = $('#isInvoice').val();
	var invoiceObj = $('#invoice_obj').val();// 发票对象
	var text = '不开发票';
	if(isInvoice==1){
		text = (invoiceObj==0)?'普通发票（纸质）  个人   明细':'普通发票（纸质）'+invoiceHead+' 明细';
	}
	$('#invoice_info').html(text);
	layerclose();
}

// 保存纳税人识别号
function saveInvoice(){
	var isInv = $('#isInvoice').val();
	var num = $('#invoice_num').val();
	var id = $('#invoiceId').val();
	var invoiceHead = $('#invoiceHead').val();// 发票抬头
	var url = WST.U('home/Invoices/add');
	var params = {};
	if(id>0){
		url = WST.U('home/Invoices/edit');
		invoiceHead = $('#invoiceHead_'+id).val();// 发票抬头
		params.id = id;
	}
	params.invoiceHead = invoiceHead;
	params.invoiceCode = num;
	if($('#invoice_obj').val()!=0){
		var loading = WST.load({msg:'正在提交数据，请稍后...'});
		$.post(url,params,function(data){
			var json = WST.toJson(data);
			layer.close(loading);
			if(json.status==1){
				// 判断用户是否需要发票
				setInvoiceText(invoiceHead);
				if(id==0)$('#invoiceId').val(json.data.id)
			}else{
				WST.msg(json.msg,{icon:2});
			}
		});
	}else{
		setInvoiceText('');
	}
}
// 删除发票信息
function delInvoice(id,obj){
	WST.confirm({content:'您确定要删除该发票信息吗？',yes:function(index){
		$.post(WST.U('home/invoices/del'),{id:id},function(data,textStatus){
		     var json = WST.toJson(data);
		     if(json.status==1){
		    	 WST.msg(json.msg,{icon:1});
		    	 $(obj).parent().parent().remove();
		    	 $('#invoiceId').val(0);
		    	 // 选中 `个人`
		    	 $('.inv_li:first').click();
		     }else{
		    	 WST.msg(json.msg,{icon:2});
		     }
		});
	}});
}





function changeSelected(n,index,obj){
	$('#'+index).val(n);
	inEffect(obj,2);
}

function getPayUrl(){
	var params = {};
		params.payObj = "orderPay";
		params.orderNo = $("#orderNo").val();
		params.isBatch = $("#isBatch").val();
		params.payCode = $.trim($("#payCode").val());
	if(params.payCode==""){
		WST.msg('请先选择支付方式', {icon: 5});
		return;
	}

	jQuery.post(WST.U('home/'+params.payCode+'/get'+params.payCode+"Url"),params,function(data) {
		var json = WST.toJson(data);
		if(json.status==1){
			if(params.payCode=="weixinpays" || params.payCode=="wallets"){
				location.href = json.url;
			}else{
				if(params.payCode=="unionpays"){
					location.href = WST.U('home/unionpays/tounionpays',params);
				}else{
					$("#alipayform").html(json.result);
				}
			}
		}else{
			WST.msg('您的订单已支付!', {icon: 5,time:1500},function(){
				window.location = WST.U('home/orders/waitReceive');
			});
		}
	});
}

function payByWallet(){
    var params = WST.getParams('.j-ipt');
    if(window.conf.IS_CRYPT=='1'){
        var public_key=$('#token').val();
        var exponent="10001";
   	    var rsa = new RSAKey();
        rsa.setPublic(public_key, exponent);
        params.payPwd = rsa.encrypt(params.payPwd);
    }
	var load = WST.load({msg:'正在核对支付密码，请稍后...'});
	$.post(WST.U('home/wallets/payByWallet'),params,function(data,textStatus){
		layer.close(load);   
		var json = WST.toJson(data);
	    if(json.status==1){
	    	WST.msg(json.msg, {icon: 1,time:1500},function(){
                window.location = WST.U('home/orders/waitReceive');
	    	});
	    }else{
	    	WST.msg(json.msg,{icon:2,time:1500});
	    }
	});
}

function checkScoreBox(v){
    if(v){
    	var val = $('#isUseScore').attr('dataval');
    	$('#useScore').val(val);
        $('#scoreMoney').show();

    }else{
    	$('#scoreMoney').hide();
    }
    getCartMoney();
}

function setPaypwd(){
	layerbox =	layer.open({
		title:['设置支付密码','text-align:left'],
		type: 1,
		area: ['450px', '240px'],
		content: $('.j-paypwd-box'),
		btn: ['设置支付密码，并支付订单', '关闭'],
		yes: function(index, layero){
			var newPass = $.trim($("#payPwd").val());
			var reNewPass = $.trim($("#reNewPass").val());
			if(newPass==""){
				WST.msg("请输入支付密码！");
				return false;
			}
			if(reNewPass==""){
				WST.msg("请输入确认支付密码！");
				return false;
			}
			if(newPass!=reNewPass){
				WST.msg("密码不一致！");
				return false;
			}
		    if(window.conf.IS_CRYPT=='1'){
		        var public_key=$('#token').val();
		        var exponent="10001";
		   	    var rsa = new RSAKey();
		        rsa.setPublic(public_key, exponent);
		        newPass = rsa.encrypt(newPass);
		        reNewPass = rsa.encrypt(reNewPass);
		    }
			var load = WST.load({msg:'正在提交支付密码，请稍后...'});
			$.post(WST.U('home/users/payPassEdit'),{newPass:newPass,reNewPass:reNewPass},function(data,textStatus){
				layer.close(load);   
				var json = WST.toJson(data);
			    if(json.status==1){
			    	WST.msg(json.msg, {icon: 1,time:1500},function(){
			    		layer.close(layerbox);
		                payByWallet();
			    	});
			    }else{
			    	WST.msg(json.msg,{icon:2,time:1500});
			    }
			});
			
	    	return false;
	  	},
	  	btn2: function(index, layero){}
	});
}