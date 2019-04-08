$(function(){
	$("embed").css('width','100%');
	WST.dropDownLayer(".item",".dorp-down-layer");
	$('.item-more').click(function(){
		if($(this).attr('v')==1){
			$('.hideItem').show(300);
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
 index=0;
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
$('.imgss').click(function () {
    $('#preview').find('img').attr('src',$(this).find('img').attr('src'))
    index=$(this).data('id')
})
	if(goodsInfo.sku){
		var specs,dv;
		for(var key in goodsInfo.sku){
			if(goodsInfo.sku[key].isDefault==1){
				specs = key.split(':');
				$('.j-option').each(function(){
					dv = $(this).attr('data-val')
					if($.inArray(dv,specs)>-1){
						$(this).addClass('j-selected');
					}
				})
				$('#buyNum').attr('data-max',goodsInfo.sku[key].specStock);
			}
		}
	}else{
		$('#buyNum').attr('data-max',goodsInfo.goodsStock);
	}
	checkGoodsStock();
	imagesMove({id:'.goods-pics',items:'.items'});
	//选择规格
	$('.spec .j-option').click(function(){
		$(this).addClass('j-selected').siblings().removeClass('j-selected');
		checkGoodsStock();
	});
	// fixedbar();
	$('#tab').TabPanel({tab:0,callback:function(no){
		if(no==1)queryByPage();
		if(no==2)queryConsult();
	}});
});
function fixedbar(){
    var offsetTop = $("#goodsTabs").offset().top;  
    $(window).scroll(function() {  
        var scrollTop = $(document).scrollTop();  
        if (scrollTop > offsetTop){  
        	$('#addCart2').show();
            $("#goodsTabs").css("position","fixed");  
            $("#wx_qrcode").addClass('wx_qrcode_fixed');  
        }else{  
        	$('#addCart2').hide();
            $("#goodsTabs").css("position", "static");  
            $("#wx_qrcode").removeClass('wx_qrcode_fixed');  
        }  
    });   
}
function checkGoodsStock(){
	var specIds = [],stock = 0,goodsPrice=0,marketPrice=0;
	if(goodsInfo.isSpec==1){
		$('.j-selected').each(function(){
			specIds.push(parseInt($(this).attr('data-val'),10));
		});
		specIds.sort(function(a,b){return a-b;});
		if(goodsInfo.sku[specIds.join(':')]){
			stock = goodsInfo.sku[specIds.join(':')].specStock;
			marketPrice = goodsInfo.sku[specIds.join(':')].marketPrice;
			goodsPrice = goodsInfo.sku[specIds.join(':')].specPrice;
		}
	}else{
		stock = goodsInfo.goodsStock;
		marketPrice = goodsInfo.marketPrice;
		goodsPrice = goodsInfo.goodsPrice;
	}
	var iptv = parseInt($('#buyNum').val(),10);
	iptv = (iptv>stock)?stock:iptv;
	$('#buyNum').val(iptv);
	$('#buyNum').attr('data-max',stock);
	$('#goods-stock').html(stock);
	$('#j-market-price').html('￥'+marketPrice);
	$('#j-shop-price').html(goodsPrice);
	if(stock<=0){
		$('#addBtn').addClass('disabled');
		$('#buyBtn').addClass('disabled');
	}else{
		$('#addBtn').removeClass('disabled');
		$('#buyBtn').removeClass('disabled');
	}
}

function imagesMove(opts){
	var tempLength = 0; //临时变量,当前移动的长度
	var viewNum = 5; //设置每次显示图片的个数量
	var moveNum = 2; //每次移动的数量
	var moveTime = 300; //移动速度,毫秒
	var scrollDiv = $(opts.id+" "+opts.items+" ul"); //进行移动动画的容器
	var scrollItems = $(opts.id+" "+opts.items+" ul li"); //移动容器里的集合
	var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); //计算总长度,总个数*单个长度

	  var length=$('.imgss').length;
	//下一张
	$(opts.id+" .next").bind("click",function(){

        index++;
        if (index>=length) index=0;
	    $('#preview').find('img').attr('src',$('.imgss').eq(index).find('img').attr('src'))
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	//上一张
	$(opts.id+" .prev").bind("click",function(){
        index--;
        if (index<0) index=length;
        $('#preview').find('img').attr('src',$('.imgss').eq(index).find('img').attr('src'))
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
}


/****************** 商品评价 ******************/
function showImg(id){
  layer.photos({
      photos: '#img-file-'+id
    });
}
function apprfilter(type){
	$('.appr-filterbox li a').removeClass('curr');
	$('#filtertype').val(type);
	queryByPage(1);
}

var _first=true;
function queryByPage(p){
  var params = {};
  params.page = p;
  params.goodsId = goodsInfo.id;
  params.anonymous = 1;
  params.type = $('#filtertype').val();
  $.post(WST.U('home/goodsappraises/getById'),params,function(data,textStatus){
      var json = WST.toJson(data);
      if(json.status==1 && json.data.data){
          var gettpl = document.getElementById('tblist').innerHTML;
          laytpl(gettpl).render(json.data.data, function(html){
            $('#ga-box').html(html);
            for(var g=0;g<=json.data.data.length;g++){
              showImg(g);
            }
          });
          //  各评价数.
          $('#totalNum').html(json.data.sum);
          $('#bestNum').html(json.data.bestNum);
          $('#goodNum').html(json.data.goodNum);
          $('#badNum').html(json.data.badNum);
          $('#picNum').html(json.data.picNum);
          // 选中当前筛选条件
          $('#'+params.type).addClass('curr');
          if(_first && json.data.sum>0){
	          // 好、中、差评率
	          var best = parseInt(json.data.bestNum/json.data.sum*100);
	          var good = parseInt(json.data.goodNum/json.data.sum*100);
	          var bad = 100-best-good;
	          $('.best_percent').html(best);
	          $('.good_percent').html(good);
	          $('.bad_percent').html(bad);
	          // 背景色
	          $('#best_percentbg').css({width:best+'%'});
	          $('#good_percentbg').css({width:good+'%'});
	          $('#bad_percentbg').css({width:bad+'%'});
	          _first = false;
          }

          $('.j-lazyImg').lazyload({ effect: "fadeIn",failurelimit : 10,threshold: 200,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});
          $('.apprimg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.USER_LOGO});//会员默认头像
           laypage({
               cont: 'pager', 
               pages:json.data.last_page, 
               curr: json.data.current_page,
               skin: '#e23e3d',
               groups: 3,
               jump: function(e, first){
                    if(!first){
                      queryByPage(e.curr);
                    }
                  } 
            });
        }  
  });
}
function addCart(type,iptId,url){
	if(window.conf.IS_LOGIN==0){
		WST.loginWindow(url);
        // WST.msg('请先登录！',{icon:2})
		return;
	}
    var buyNum = $(iptId)[0]?$(iptId).val():1;
	var goodsSpecId = 0;
	if (type==0){

        $.post("/addcart",{goodsId:goodsInfo.id,goodsSpecId:goodsSpecId,buyNum:buyNum,type:type,rnd:Math.random()},function(res){

            if(res.status==200){
                WST.msg(res.msg,{icon:1},function(){
                });
            }else{
                WST.msg(res.msg,{icon:2});
            }
        });


    } else{
        $.post("/saveorderitem",{goodsId:goodsInfo.id,goodsSpecId:goodsSpecId,buyNum:buyNum,type:type,rnd:Math.random()},function(res){

            if(res.status==200){
            	var token=res.data.token;
            	var uid=res.data.uid;
                location.href=window.conf.ORDER_URL+"/order/settlement?type=1&token="+token;
            }else{
                WST.msg(res.msg,{icon:2});
            }
        });


	}

}