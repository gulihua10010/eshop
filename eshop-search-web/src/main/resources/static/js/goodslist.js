$(function(){
	$('.goodsImg2').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});//商品默认图片
	WST.dropDownLayer(".item",".dorp-down-layer");
	$('.item-more').click(function(){
		if($(this).attr('v')==1){
			$('.hideItem').show(300,'swing',function(){
				showMoreBtn();
			});
			$(this).find("span").html("收起");
			$(this).find("i").attr({"class":"drop-up"});
			$(this).attr('v',0);
		}else{
			$('.hideItem').hide(300);
			$(this).find("span").html("更多选项");
			$(this).find("i").attr({"class":"drop-down-icon"});
			$(this).attr('v',1);
		}
	});
	
	$(".item-more").hover(function(){
		if($(this).find("i").hasClass("drop-down-icon")){
			$(this).find("i").attr({"class":"down-hover"});
		}else{
			$(this).find("i").attr({"class":"up-hover"});
		}
		
	},function(){
		if($(this).find("i").hasClass("down-hover")){
			$(this).find("i").attr({"class":"drop-down"});
		}else{
			$(this).find("i").attr({"class":"drop-up"});
		}
	});
	$('.img_list li img').mouseover(function(){
		// 商品列表小图切换
		$(this).parent().siblings().removeClass('curr');
		$(this).parent().addClass('curr');
		var oldImgDom = $(this).parent().parent().parent().children('.img').children().children();
		oldImgDom.attr('src',this.src.replace('_thumb',''));
	})
});

function goodsFilter(obj,vtype){
	if(vtype==1){
		$('#brand').val($(obj).attr('v'));
	}else if(vtype==2){
		var price = $(obj).attr('v');
		price = price.split('_');
		$('#minPrice').val(price[0]);
		$('#maxPrice').val(price[1]);
	}else if(vtype==3){
		$('#v_'+$(obj).attr('d')).val($(obj).attr('v'));
		var vs = $('#vs').val();
		vs = (vs!='')?vs.split(','):[];
		vs.push($(obj).attr('d'));
		$('#vs').val(vs.join(','));
	}
	var ipts = WST.getParams('.sipt');
	if(vtype==4)ipts['order']='1';
	var params = [];
	for(var key in ipts){
		if(ipts[key]!='')params.push(key+"="+ipts[key]);
	}
	location.href=WST.U('home/goods/lists',params.join('&'),true);
}
function goodsOrder(orderby){
	if($('#orderBy').val()!=orderby){
		$('#order').val(1);
	}
	$('#orderBy').val(orderby);
	goodsFilter(null,0);
}



function removeFilter(id){
	if(id!='price'){
		$('#'+id).val('');
		if(id.indexOf('v_')>-1){
			id = id.replace('v_','');
			var vs = $('#vs').val();
			vs = (vs!='')?vs.split(','):[];
			var nvs = [];
			for(var i=0;i<vs.length;i++){
				if(vs[i]!=id)nvs.push(vs[i]);
			}
			$('#vs').val(nvs.join(','));
		}
	}else{
		$('#minPrice').val('');
		$('#maxPrice').val('');
	}
	var ipts = WST.getParams('.sipt');
	var params = [];
	for(var key in ipts){
		if(ipts[key]!='')params.push(key+"="+ipts[key]);
	}
	location.href=WST.U('home/goods/lists',params.join('&'),true);
}
/*搜索列表*/
function searchFilter(obj,vtype){
	if(vtype==1){
		$('#brand').val($(obj).attr('v'));
	}else if(vtype==2){
		var price = $(obj).attr('v');
		price = price.split('_');
		$('#minPrice').val(price[0]);
		$('#maxPrice').val(price[1]);
	}else if(vtype==3){
		$('#v_'+$(obj).attr('d')).val($(obj).attr('v'));
		var vs = $('#vs').val();
		vs = (vs!='')?vs.split(','):[];
		vs.push($(obj).attr('d'));
		$('#vs').val(vs.join(','));
	}
	var ipts = WST.getParams('.sipt');
	if(vtype==4)ipts['order']='1';
	var params = [];
	for(var key in ipts){
		if(ipts[key]!='')params.push(key+"="+ipts[key]);
	}
	location.href=WST.U('home/goods/search',params.join('&'),true);
}
function searchOrder(orderby){
	if($('#orderBy').val()!=orderby){
		$('#order').val(1);
	}
	$('#orderBy').val(orderby);
	searchFilter(null,0);
}


/*加入购物车*/
$('.goods').hover(function(){
	$(this).find('.sale-num').hide();
	$(this).find('.p-add-cart').show();
},function(){
	$(this).find('.sale-num').show();
	$(this).find('.p-add-cart').hide();
})



/*发货地*/
function gpanelOver(obj){
	var sid = $(obj).attr("id");

	var index = $(obj).attr('c');

	var ids = sid.split("_");
	var preid = ids[0]+"_"+ids[1];
	if(ids[2]==1){
		$("li[id^="+preid+"_]").hide();
		$("#"+sid).show();
	}else if(ids[2]==2){
		$('#fl_1_3').hide();
	}

	$("li[id^="+preid+"_]").removeClass("j-tab-selected"+index);
	$("#"+sid).addClass("j-tab-selected"+index);
	
	$("ul[id^="+preid+"_]").hide();
	$("#"+sid+"_pl").show();
}
function choiceArea(t,pid){
	var areaName = $(t).find('a').html();
	var parent = $(t).parent().attr('id');
	var ids = parent.split("_");
	var preid = "#"+ids[0]+"_"+ids[1]+"_"+ids[2];
	if(ids[2]==3){
		$(preid).find('a').html(areaName);
		// 执行发货地筛选
		$('#areaId').val(pid);
		var ipts = WST.getParams('.sipt');
		var params = [];
		for(var key in ipts){
			if(ipts[key]!='')params.push(key+"="+ipts[key]);
		}
		var url = ($(t).attr('search')==1)?'home/goods/search':'home/goods/lists';
		location.href=WST.U(url,params.join('&'));
	}else{
		// 替换当前选中地区
		$(preid).find('a').html(areaName);
		$(preid).removeClass('j-tab-selected'+ids[1]);


		var next = parseInt(ids[2])+1;
		var nextid = "#"+ids[0]+"_"+ids[1]+"_"+next;
		$(nextid).show();
		$(nextid).addClass("j-tab-selected"+ids[1]);
		// 替换下级地图标题
		$(nextid).html('<a href="javascript:void(0)">请选择</a>');

		// 获取下级地区信息
		$.post(WST.U('home/areas/listQuery'),{parentId:pid},function(data){
			// 判断搜索页面
			var search = $(t).attr('search');
			if(search==1){search = 'search="1"';}
			
			var json = WST.toJson(data);
			if(json.status==1){
				var html = '';
				$(json.data).each(function(k,v){

					html +='<li onclick="choiceArea(this,'+v.areaId+')" '+search+' ><a href="javascript:void(0)">'+v.areaName+'</a></li>';
				});
				$(nextid+"_pl").html(html);
			}
		});

		// 隐藏当前地区,显示下级地区
		var preid = ids[0]+"_"+ids[1];
		$("ul[id^="+preid+"_]").hide();
		$(nextid+"_pl").show();
	}
}

/*************************************** 筛选 ******************************************/
function showMoreBtn(){
	// 判断是否需要显示【更多】
	$('.item .content').each(function() {
	    if (this.scrollHeight > 34) {
	        $(this).parent().find('.extra .extra_more').css({visibility:'visible'});
	    }
	});
}
$(function(){
	showMoreBtn();
})
function extra_show(obj){
	$(obj).addClass('extra_more_on');
	$(obj).parent().parent().find('ul').css({height:'auto'});
	$(obj).html('收起<i></i>');
	$(obj).attr('onClick','extra_hide(this)');
}
function extra_hide(obj){
	//  修改点击事件
	$(obj).removeClass('extra_more_on');
	$(obj).parent().parent().find('ul').css({height:'30px'});
	$(obj).attr('onClick','extra_show(this)');
	$(obj).html('更多<i></i>');
}
var _preObj;
function multibox_show(obj,type){
	var _topParent = $(obj).parent().parent();
	// 显示被隐藏的选项
	_topParent.find('ul').css({height:'auto'});
	// 给每个li绑定事件
	_topParent.find('ul li').each(function(){
		$(this).attr('_onclick',$(this).attr('onclick'));
		$(this).removeAttr('onclick');
		$(this).click(function(){
			$(this).toggleClass('selected');
			($('li.selected').length>0)?_topParent.find('a.confirm_btn').css({visibility:'visible'}):_topParent.find('a.confirm_btn').css({visibility:'hidden'});
		})
	})
	// 隐藏右侧按钮
	$(obj).parent().hide();
	// 显示多选盒子
	_topParent.addClass('multi_on');
	_topParent.append('<div class="multi_btns"><a class="confirm_btn" href="javascript:void(0)" onClick="multi_done(this,\''+type+'\')">确定</a><a onClick="multibox_hide(this)" href="javascript:void(0)">取消</a></div>')
	// 隐藏上一个多选盒子
	if($('.multi_on').length>1){multibox_hide(_preObj)}
	_preObj = obj;
}
// 完成多选
function multi_done(obj,type){
	var ids = [],attrId=0;
	// 获取选中的值
	$(obj).parent().parent().find('ul li.selected').each(function(){
		ids.push($(this).attr('v'));
		if(type=='attr')attrId = $(this).attr('d');
	})
	if(ids.length==0){
		WST.msg('请选择要筛选的选项');
		return false;
	}
	if(type=='brand'){
		$('#brand').val(ids.join(','));
	}else if(type=='attr'){
		$('#v_'+attrId).val(ids.join('、'));// 属性名称 -> 双卡双4G_电信4G_移动4G
		var vs = $('#vs').val();// 属性id【不需要再拼凑】
		vs = (vs!='')?vs.split(','):[];
		vs.push(attrId);
		$('#vs').val(vs.join(','));

	}
	var ipts = WST.getParams('.sipt');
	var params = [];
	for(var key in ipts){
		if(ipts[key]!='')params.push(key+"="+ipts[key]);
	}
	location.href=WST.U('home/goods/lists',params.join('&'),true);


}
// 多选隐藏
function multibox_hide(obj){
	var _topParent = $(obj).parent().parent();
	// 显示被隐藏的选项
	_topParent.find('ul').css({height:'30px'});
	// 给每个li绑定事件
	_topParent.find('ul li').each(function(){
		$(this).unbind('click');
		$(this).attr('onclick',$(this).attr('_onclick'));
		$(this).removeAttr('_onclick');
		$(this).removeClass('selected');
	})
	// 显示右侧按钮
	_topParent.find('div.extra').show();
	// 隐藏多选盒子
	_topParent.removeClass('multi_on');
	_topParent.find('div.multi_btns').remove();
	// 设置【更多】按钮
	var _moreBtn = _topParent.find('a.extra_more');
	_moreBtn.removeClass('extra_more_on');
	_moreBtn.attr('onClick','extra_show(this)');
	_moreBtn.html('更多<i></i>');
}

//对比商品
function contrastGoods(show,id,type){
	if(show==1){
		$.post(WST.U('home/goods/contrastGoods'),{id:id},function(data,textStatus){
			var json = WST.toJson(data);
			if(json.status==1){
				if(type==2 && json.data)$("#j-cont-frame").addClass('show');
				var gettpl = document.getElementById('colist').innerHTML;
				laytpl(gettpl).render(json, function(html){
					$('#contrastList').html(html);
				});
				$('.contImg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 200,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});//商品默认图片
			}else{
				WST.msg(json.msg,{icon:2});
			}
			if(type==1)$("#j-cont-frame").addClass('show');
		});
	}else{
		$("#j-cont-frame").removeClass('show');
	}
}
//删除
function contrastDels(id){
	$.post(WST.U('home/goods/contrastDel'),{id:id},function(data,textStatus){
		var json = WST.toJson(data);
		if(json.status==1){
			contrastGoods(1,0,1);
		}
	});
}
//对比商品列表
//滑动
function fixedGoods(){
    var offsetTop = $("#goodsTabs").offset().top;
    $(window).scroll(function() {  
        var scrollTop = $(document).scrollTop();  
        if (scrollTop > offsetTop){
            $("#goodsTabs").addClass('goods-fixed');
            $("#goodsTabs2").show();
        }else{  
            $("#goodsTabs").removeClass('goods-fixed');
            $("#goodsTabs2").hide();
        }  
    });   
}
//删除
function contrastDel(id){
	$.post(WST.U('home/goods/contrastDel'),{id:id},function(data,textStatus){
		var json = WST.toJson(data);
		if(json.status==1){
			location.href=WST.U('home/goods/contrast');
		}
	});
}
//筛选条件
function screenContrast(obj,id){
	if ($(obj).is(':checked')) {
		$(".identical_"+id).addClass('active');
	}else{
		$(".identical_"+id).removeClass('active');
	}
}
//筛选规格
function choiceContrast(obj,itemId,catId,goodsId){
	$(obj).addClass('active').siblings('.list-box li').removeClass('active');
	$("#defaultSpec_"+goodsId+"_"+catId).val(itemId);
	var specIds = [];
	$(".defaultSpec_"+goodsId).each(function(){
		specIds.push(parseInt($(this).val(),10));
	});
	specIds.sort(function(a,b){return a-b;});
	if(saleSpec.sku[goodsId][specIds.join(':')]){
		stock = saleSpec.sku[goodsId][specIds.join(':')].specStock;
		marketPrice = saleSpec.sku[goodsId][specIds.join(':')].marketPrice;
		goodsPrice = saleSpec.sku[goodsId][specIds.join(':')].specPrice;
	}
	$('#goods-price-'+goodsId).html('¥ <span>'+goodsPrice+'</span>');
}