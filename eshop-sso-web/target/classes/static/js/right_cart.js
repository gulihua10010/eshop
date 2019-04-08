$(document).ready(function(){
	var cartHeight = WST.pageHeight()-120;
	$('.toolbar-tab').hover(function (){ $(this).find('.tab-text').addClass("tbar-tab-hover"); $(this).find('.footer-tab-text').addClass("tbar-tab-footer-hover"); $(this).addClass("tbar-tab-selected");},function(){ $(this).find('.tab-text').removeClass("tbar-tab-hover"); $(this).find('.footer-tab-text').removeClass("tbar-tab-footer-hover"); $(this).removeClass("tbar-tab-selected"); });
	$('.j-close').click(function(){
		if($('.toolbar-wrap').hasClass('toolbar-open')){
			$('.toolbar-wrap').removeClass('toolbar-open');
		}else{ 
			$('.toolbar-wrap').addClass('toolbar-open'); 
		}
	})
	$('.j-global-toolbar').siblings().click(function(){
		if($('.toolbar-wrap').hasClass('toolbar-open')){
			$('.toolbar-wrap').removeClass('toolbar-open');
		}
	})
	$('.tbar-tab-cart').click(function (){ 
		if($('.toolbar-wrap').hasClass('toolbar-open')){
			if($(this).find('.tab-text').length > 0){
				if(! $('.tbar-tab-follow').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>我的关注</em>";
					$('.tbar-tab-follow').append(info);
					$('.tbar-tab-follow').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-follow').css({'visibility':"hidden","z-index":"-1"});
				}
				if(! $('.tbar-tab-history').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>我的足迹</em>";
					$('.tbar-tab-history').append(info);
					$('.tbar-tab-history').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-history').css({'visibility':"hidden","z-index":"-1"});
				}
				$(this).addClass('tbar-tab-click-selected'); 
				$(this).find('.tab-text').remove();
				$('.tbar-panel-cart').css({'visibility':"visible","z-index":"1"});
				getRightCart();
			}else{
				var info = "<em class='tab-text '>我的关注</em>";
				$('.toolbar-wrap').removeClass('toolbar-open');
				$(this).append(info);
				$(this).removeClass('tbar-tab-click-selected');
				$('.tbar-panel-cart').css({'visibility':"hidden","z-index":"-1"});
			}
		}else{ 
			$(this).addClass('tbar-tab-click-selected'); 
			$(this).find('.tab-text').remove();
			$('.tbar-panel-cart').css({'visibility':"visible","z-index":"1"});
			$('.tbar-panel-follow').css('visibility','hidden');
			$('.tbar-panel-history').css('visibility','hidden');
			$('.toolbar-wrap').addClass('toolbar-open'); 
			$('#cart-panel').css('height',cartHeight+"px").css('overflow-y','auto');
			getRightCart();
		}
	});
	$('.tbar-tab-follow').click(function (){ 
		if($('.toolbar-wrap').hasClass('toolbar-open')){
			if($(this).find('.tab-text').length > 0){
				if(! $('.tbar-tab-cart').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>购物车</em>";
					$('.tbar-tab-cart').append(info);
					$('.tbar-tab-cart').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-cart').css({'visibility':"hidden","z-index":"-1"});
				}
				if(! $('.tbar-tab-history').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>我的足迹</em>";
					$('.tbar-tab-history').append(info);
					$('.tbar-tab-history').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-history').css({'visibility':"hidden","z-index":"-1"});
				}
				$(this).addClass('tbar-tab-click-selected'); 
				$(this).find('.tab-text').remove();
				$('.tbar-panel-follow').css({'visibility':"visible","z-index":"1"});
				
			}else{
				var info = "<em class='tab-text '>我的关注</em>";
				$('.toolbar-wrap').removeClass('toolbar-open');
				$(this).append(info);
				$(this).removeClass('tbar-tab-click-selected');
				$('.tbar-panel-follow').css({'visibility':"hidden","z-index":"-1"});
			}
			 
			
		}else{ 
			$(this).addClass('tbar-tab-click-selected'); 
			$(this).find('.tab-text').remove();
			$('.tbar-panel-cart').css('visibility','hidden');
			$('.tbar-panel-follow').css({'visibility':"visible","z-index":"1"});
			$('.tbar-panel-history').css('visibility','hidden');
			$('.toolbar-wrap').addClass('toolbar-open'); 
		}
	});
	$('.tbar-tab-history').click(function (){ 
		if($('.toolbar-wrap').hasClass('toolbar-open')){
			if($(this).find('.tab-text').length > 0){
				if(! $('.tbar-tab-follow').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>我的关注</em>";
					$('.tbar-tab-follow').append(info);
					$('.tbar-tab-follow').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-follow').css({'visibility':"hidden","z-index":"-1"});
				}
				if(! $('.tbar-tab-cart').find('.tab-text').length > 0){
					var info = "<em class='tab-text '>购物车</em>";
					$('.tbar-tab-cart').append(info);
					$('.tbar-tab-cart').removeClass('tbar-tab-click-selected'); 
					$('.tbar-panel-cart').css({'visibility':"hidden","z-index":"-1"});
				}
				$(this).addClass('tbar-tab-click-selected'); 
				$(this).find('.tab-text').remove();
				$('.tbar-panel-history').css({'visibility':"visible","z-index":"1"});
				getHistoryGoods();
			}else{
				var info = "<em class='tab-text '>我的足迹</em>";
				$('.toolbar-wrap').removeClass('toolbar-open');
				$(this).append(info);
				$(this).removeClass('tbar-tab-click-selected');
				$('.tbar-panel-history').css({'visibility':"hidden","z-index":"-1"});
			}
			
		}else{ 
			$(this).addClass('tbar-tab-click-selected'); 
			$(this).find('.tab-text').remove();
			$('.tbar-panel-cart').css('visibility','hidden');
			$('.tbar-panel-follow').css('visibility','hidden');
			$('.tbar-panel-history').css({'visibility':"visible","z-index":"1"});
			$('.toolbar-wrap').addClass('toolbar-open'); 
			getHistoryGoods();
		}
	});
});
function getRightCart(){
	//if(WST.conf.IS_LOGIN==0)return;
	$.post(WST.U('home/carts/getCart'),'',function(data) {
		var json = WST.toJson(data,true);
		if(json.status==1){
			json = json.data;
			if(json.carts && !json.carts.length){
				$('.j-cart-count').html(json.goodsTotalNum);
				if(json.goodsTotalNum>0)$('.j-cart-count').show();
				var gettpl = document.getElementById('list-rightcart').innerHTML;
				laytpl(gettpl).render(json.carts, function(html){
					$('#cart-panel').html(html);
				});
				$('#j-goods-count').html(json.goodsTotalNum);
				$('#j-goods-total-money').html(json.goodsTotalMoney);
			}else{
				$('#cart-panel').html('<p class="right-carts-empty">购物车空空如也，赶紧去选购吧～</p>');
			}
		}
	});
}
function delRightCart(obj,id){
	var dataval = $(obj).attr('dataid');
	dataval = dataval.split("|");
	if($('#shop-cart-'+dataval[0]).children().size()>2){
		$('.j-goods-item-'+dataval[1]).remove();
	}else{
		$('#shop-cart-'+dataval[0]).remove();
	}
	statRightCartMoney();
	$.post(WST.U('home/carts/delCart'),{id:dataval[1],rnd:Math.random()},function(data,textStatus){
	     var json = WST.toJson(data);
	     if(json.status!=1){
	    	 WST.msg(json.msg,{icon:2});
	     }
	});
}
function jumpSettlement(){
	if($('#cart-panel').children().size()==0){
		WST.msg("您的购物车没有商品哦，请先添加商品~",{icon:2});
		return;
	}
	location.href=WST.U('home/carts/settlement');
}
function getHistoryGoods(){
	$.post(WST.U('home/goods/historyByGoods'),{},function(data) {
		var json = WST.toJson(data);
		if(json.status==1){
			var gettpl = document.getElementById('list-history-goods').innerHTML;
			laytpl(gettpl).render(json.data, function(html){
				$('#history-goods-panel').html(html);
			});
			$('.jth-item').hover(function (){ $(this).find('.add-cart-button').show(); },function(){ $(this).find('.add-cart-button').hide(); });
		}
	});
}
function checkRightChks(cid,obj){
    WST.changeCartGoods(cid,$('#buyNum_'+cid).val(),obj.checked?1:0);
    statRightCartMoney();
}
function statRightCartMoney(){
	var cartId,goodsNum = 0,goodsMoney = 0,tmpGoodsNum = 0,tmpGoodsMoney = 0;
	$('.jtc-item-goods').each(function(){
		cartId = $(this).attr('dataval');
		if($('#rcart_'+cartId).prop('checked')){
			goodsNum = parseInt($('#buyNum_'+cartId).val(),10);
			goodsMoney = parseFloat($('#gprice_'+cartId).html(),10);
			tmpGoodsNum++;
			tmpGoodsMoney += goodsMoney*goodsNum;
		}
	})
	if(tmpGoodsNum==0){
		 $('#j-goods-count').html(0);
		 $('.j-cart-count').html(0).hide();
		 $('#j-goods-total-money').html(0);
	}else{
		$('.j-cart-count').html(tmpGoodsNum);
	    $('#j-goods-count').html(tmpGoodsNum);
	    $('#j-goods-total-money').html(tmpGoodsMoney);
	}
}	
