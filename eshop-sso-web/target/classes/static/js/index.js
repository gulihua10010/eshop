$.fn.TabiPanel = function(options){
	var defaults = {tab: 0}; 
	var opts = $.extend(defaults, options);
	var t = this;
	
	$(t).find('.wst-tab-nav .tab').click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		var index = $(this).index();
		$(t).find('.wst-tab-content .wst-tab-item').eq(index).show().siblings().hide();
		if(opts.callback)opts.callback(index);
	});
	$(t).find('.wst-tab-nav .tab').eq(opts.tab).click();
}
$(function(){
	WST.slides('.wst-slide');
	$('#index-tab').TabiPanel({tab:0,callback:function(no){}});
});



/*楼层*/
function gpanelOver(obj){
	var sid = $(obj).attr("id");

	var index = $(obj).attr('c');

	var ids = sid.split("_");
	var preid = ids[0]+"_"+ids[1];
	
	$("li[id^="+preid+"_]").removeClass("j-tab-selected"+index);
	$("#"+sid).addClass("j-tab-selected"+index);
	
	$("div[id^="+preid+"_]").hide();
	$("#"+sid+"_pl").show();
}


/*楼层商品 加入购物车*/
$('.goods').hover(function(){
	$(this).find('.sale-num').hide();
	$(this).find('.f-add-cart').show();
},function(){
	$(this).find('.sale-num').show();
	$(this).find('.f-add-cart').hide();
})

$(function(){
	//执行右侧底部商品切换js
	var fBgoods = $(this).find("ul[id^='styleMain']").length;
	for(var i=1;i<=fBgoods;++i){
		var li = $('#styleMain'+i).find('li');
		if(li.length>5){
			fBGoods(i);
		}else{
			li.each(function(){$(this).css('padding-bottom','0')})
		}

	}
})




function fBGoods(id){
	$('#styleMain'+id).bxCarousel({
		display_num: 5, 
		move: 1, 
		auto: 0, 
		controls: true,
		prev_image: conf.ROOT+'/wstmart/home/view/default/img/btn_slide_left.png',
    	next_image: conf.ROOT+'/wstmart/home/view/default/img/btn_slide_right.png',
		margin: 10,
		auto_hover: true
	});
}

$(function(){
	var g_list_time;
	var g_obj = $('.recomgd_view');
	var g_step = g_obj.width();// 步进值
	var g_list = $('.recomgd_list'); // 轮播对象
	// 设置长度
	var g_list_w = $('.recomgd_list li').length;
	g_list.css({width:g_list_w*100+'%'});
	var g_list_i = 0;

	// 按钮
	$('.recomgd_btns span').each(function(k,v){
		$(this).mouseover(function(){
			clearInterval(g_list_time);
			g_list_i = k;
			g_list_auto();
		})
	});
	$('.recomgd_btns').mouseout(function(){glistautoplay();})
	function glistautoplay(){
			clearInterval(g_list_time);
			g_list_time = setInterval(function(){
				g_list_i++; 
		        if (g_list_i + 1 > g_list_w) { g_list_i = 0 } 
		        g_list_auto();
			},3000);
	}
	function g_list_auto(){
		loadImg('.J_rarimg');
		g_list.stop().animate({left:-g_list_i*g_step});
		$('.recomgd_btns span').removeClass('curr').eq(g_list_i).addClass('curr')
	}
	glistautoplay();
});

function loadImg(_target){
	$(_target).lazyload({ failurelimit : 10,skip_invisible : false,threshold: 200,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});
}

/*左侧楼层导航*/
$(function() {
	loadImg('.fImg');
 });
$('.lnav').click(function(){
	var i = $(this).index()+1;
	i = i+'F';
	$("html,body").animate({scrollTop: $("a[name='"+i+"']").offset().top-7}, 500);
})

function leftNav(){
	//内容距离左边空白处宽度
	var containerW = $('.wst-container').offset().left;
	left = containerW-40;
	$('#screen-left-nav').css('left', left);
}
$(window).resize(function(){leftNav()});


var currF,first=true;

function cf(){
	var sumFloor = $('.floor-box').length;
	for(var f=sumFloor;f>=1;--f){
	var id = '#c'+f;
	if($(id).offset().top+500-$(window).scrollTop()>0){
		currF = f;
		first = false;
		lcurr(f)
		}
	}
}


//内容高度
var containerH = parseInt($('.wst-main').css('height'));
$(window).scroll(function(){
leftNav();
//滚动条当前高度
var scrollHeight = $(window).scrollTop();

// 楼层选中
if(first){
	cf();
}else{
	var cfh = $('#c'+currF).offset().top+500-$(window).scrollTop();
	if(cfh<0 || cfh>1200)cf();
	
}

if(scrollHeight>=462 && scrollHeight<containerH+200){
	$('#screen-left-nav').show();
}else{
	$('#screen-left-nav').hide();
}

});
function lcurr(F){
	$('#F'+F).siblings().removeClass('lcurr');
	$('#F'+F).addClass('lcurr');
}

/*签到*/
function inSign(){
	$("#j-sign").attr('disabled', 'disabled');
	$.post(WST.U('home/userscores/signScore'),{},function(data){
		var json = WST.toJson(data,true);
		if(json.status==1){
			$("#j-sign .plus").html('+'+json.data.score);
			$("#currentScore").html(json.data.totalScore);
			$("#j-sign").addClass('active');
			setInterval(function(){
				$("#j-sign").addClass('actives').html('已签到');
			},600);
			WST.msg(json.msg, {icon: 1});
		}else{
			WST.msg(json.msg, {icon: 5});
			$("#j-sign").removeAttr('disabled');
		}
	});
}
WST.countDown = function(opts){
	var f = {
		zero: function(n){
			var n = parseInt(n, 10);
			if(n > 0){
				if(n <= 9){
					n = n;	
				}
				return String(n);
			}else{
				return "0";	
			}
		},
		count: function(){
			if(opts.nowTime){
				var d = new Date();
				d.setTime(opts.nowTime.getTime()+1000);
				opts.nowTime = d;
				d = null;
			}else{
				opts.nowTime = new Date();
			}
			//现在将来秒差值
			var dur = Math.round((opts.endTime.getTime() - opts.nowTime.getTime()) / 1000), pms = {
				sec: "0",
				mini: "0",
				hour: "0",
				day: "0"
			};
			if(dur > 0){
				pms.sec = f.zero(dur % 60);
				pms.mini = Math.floor((dur / 60)) > 0? f.zero(Math.floor((dur / 60)) % 60) : "0";
				pms.hour = Math.floor((dur / 3600)) > 0? f.zero(Math.floor((dur / 3600)) % 24) : "0";
				pms.day = Math.floor((dur / 86400)) > 0? f.zero(Math.floor(dur / 86400)) : "0";
			}
			pms.last = dur;
			pms.nowTime = opts.nowTime;
			opts.callback(pms);
			if(pms.last>0)setTimeout(f.count, 1000);
		}
	};	
	f.count();
};
// 拍卖轮播
$(function(){
	var _p = $('.aution_list');
	var step = _p.width();// 步进值
	var totalItem = $('.aution_list .aution_main').length;// 总拍卖商品数
	if(totalItem==0)return;
	var au_lbtn = $('.au_l_btn');
	var au_rbtn = $('.au_r_btn');

	$('.ws-right-user').css({height:'85px',overflow:'hidden'});// 确保右侧不溢出轮播区域
	var nowTime = new Date(Date.parse($('.aution_list').attr('sc').replace(/-/g, "/")));
	// 倒计时
	$('.aution_main').each(function(){
		var g = $(this);
        var startTime = new Date(Date.parse(g.attr('sv').replace(/-/g, "/")));
        var endTime = new Date(Date.parse(g.attr('ev').replace(/-/g, "/")));
        if(startTime.getTime()<= nowTime && endTime.getTime() >=nowTime){
            var opts = {
	            nowTime: nowTime,
			    endTime: endTime,
			    callback: function(data){
			    	if(data.last>0){
				        g.find('.aution_time .aution_h').html(data.hour);
				        g.find('.aution_time .aution_i').html(data.mini);
				        g.find('.aution_time .aution_s').html(data.sec);
			    	}else{
			    		g.find('.aution_time').html('拍卖活动已结束');
			    	}
			    	
			    }
			};
			WST.countDown(opts);
        }else{
        	g.find('.aution_time').html('拍卖活动已结束');
        }
	})

	// 设置父容器宽度
	_p.css({width:totalItem+'00%'});
	var _curr = 0;// 当前显示的index
	// 右切换按钮
	au_rbtn.click(function(){
		_curr++; 
        if (_curr + 1 > totalItem) { _curr = 0 } 
		au_s();
		return false;
	});
	// 左切换按钮
	au_lbtn.click(function(){
		_curr--; 
        if (_curr + 1 < 1) { 
            _curr = totalItem - 1 
        } 
		au_s();
		return false;
	});
	function au_auto_swiper() { y = setInterval(function () { au_rbtn.click() }, 5000) } 
    au_auto_swiper();
    $('.aution_out').bind({ mouseover: function () { clearInterval(y);au_lbtn.show();au_rbtn.show(); }, mouseout: function () { au_auto_swiper();au_lbtn.hide();au_rbtn.hide(); } })
	// 执行切换
	function au_s(){
		_p.stop().animate({left:-_curr*step});
	}
})

// 楼层商品轮播
$(function(){
        var a = $(".floor_silder"); 
        a.each(function () {
            var q = $(this); 
            var s = q.find("ul"); 
            var t = q.find("ul li"); 
            var c = q.find(".prev_btn"); 
            var w = q.find(".next_btn");
            var u = t.length; 
            // q.find(".show_num").find("em").html(u); 
            if (u == 1) { return } 
            var p = 0; 
            var y = 0; 
            var x = 3000; // 轮播间隔
            w.click(function () { // 下一张
                p++; 
                if (p + 1 > u) { p = 0 } 
                z(p); 
                v(); 
                return false 
            }); 
            c.click(function () { // 上一张
                p--; 
                if (p + 1 < 1) { 
                    p = u - 1 
                } 
                z(p); 
                v(); 
                return false 
            }); 
            function z(e) {// 执行轮播
                switch (e) {
                    case 2: 
                        t.eq(e).css("z-index", 100).stop().animate({ width: 155, height: 225, left: 20, top: 0 }); 
                        t.eq(e - 1).css("z-index", 80).stop().animate({ width: 195, height: 170, left: 0, top:30 }); 
                        t.eq(e - 2).css("z-index", 90).stop().animate({ width: 175, height: 200, left: 10, top: 15 }); 
                        t.eq(e).find(".color_mask").stop().animate({ opacity: 0 }); 
                        t.eq(e - 1).find(".color_mask").stop().animate({ opacity: 0.7 }); 
                        t.eq(e - 2).find(".color_mask").stop().animate({ opacity: 0.5 }); 
                        break;
                    default: 
                        t.eq(e).css("z-index", 100).stop().animate({ width: 155, height: 225, left: 20, top: 0 }); 
                        t.eq(e - 1).css("z-index", 80).stop().animate({ width: 195, height: 170, left: 0, top: 30 });
                        t.eq(e + 1).css("z-index", 90).stop().animate({ width: 175, height: 200, left: 10, top: 15 }); 
                        t.eq(e).find(".color_mask").stop().animate({ opacity: 0 }); 
                        t.eq(e - 1).find(".color_mask").stop().animate({ opacity: 0.7 }); 
                        t.eq(e + 1).find(".color_mask").stop().animate({ opacity: 0.5 });
                }
            } 
            function v() { 
                // 当前显示张数
                q.find(".show_num").find("span").removeClass('curr').eq(p).addClass('curr');
            } 
            function r() { y = setInterval(function () { w.click() }, x) } 
            r();
            $(this).bind({ mouseover: function () { clearInterval(y) }, mouseout: function () { r() } })
        })
    })