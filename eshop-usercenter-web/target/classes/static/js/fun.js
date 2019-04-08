$(function() {
    $('.goodsImg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});//商品默认图片
    $('.shopsImg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.SHOP_LOGO});//店铺默认头像
    $('.usersImg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.USER_LOGO});//会员默认头像
});
WST.initVisitor = function(){
    WST.dropDownLayer(".j-dorpdown",".j-dorpdown-layer");
    WST.dropDownLayer(".drop-info",".wst-tag");
    WST.dropDownLayerCart(".wst-cart-box",".wst-cart-boxs");
    WST.searchIpt();
    WST.showCategoryNavs();
    WST.Sidebar();
    console.log(WST.conf.IS_LOGIN=='1')
    console.log(WST.conf.IS_LOGIN)
    if (WST.conf.IS_LOGIN=='1') {

        WST.checkCart();
    }
}
WST.initUserCenter = function(){
    WST.dropDownLayer(".j-dorpdown",".j-dorpdown-layer");
    WST.dropDownLayer(".drop-info",".wst-tag");
    WST.searchIpt();
    WST.dropDownLayerCart(".wst-lite-cart",".wst-lite-carts");
    WST.getSysMessages('message,cart,userorder');
    if(WST.conf.TIME_TASK=='1'){
        setInterval(function(){
            WST.getSysMessages('message,cart,userorder');
        },10000);
    }
}

WST.searchIpt = function(){
    $('.j-search-box').hover(function(){
        $(".j-type-list").show();
        $(this).find('i').removeClass('arrow').addClass('over');
        $(this).css({"border-left":"1px solid #D4D4D4;"});
    },function(){
        $(".j-type-list").hide();
        $(this).css({"border-left":"1px solid #D4D4D4;"});
        $(this).find('i').removeClass('over').addClass('arrow');
    });

    $('j-type-list').hover(function(){
        $(".j-type-list").show();
        $(this).find('i').removeClass('arrow').addClass('over');
        $(this).css({"border-left":"1px solid #D4D4D4;"});
    });

    $(".j-type-list div").click(function(){
        $("#search-type").val($(this).attr("data"));
        $(".j-search-type span").html($(this).html());
        if($(this).attr("data")==1){
            $(this).attr("data",0);
            $(this).html('商品');
            $('#search-ipt').attr('placeholder',$('#adsShopWordsSearch').val());
        }else{
            $(this).attr("data",1);
            $(this).html('店铺');
            $('#search-ipt').attr('placeholder',$('#adsGoodsWordsSearch').val());
        }
        $(".j-type-list").hide();
        $(".j-search-type").find('i').removeClass('over').addClass('arrow');
    });
}
WST.search = function(){
    location.href="http://localhost:8009//itemlist/search?keyword="+$('#search-ipt').val();

}
WST.showCategoryNavs = function(){
    if($('.wst-filters')[0]){
        $(".drop-down").hover(function(){
            $(this).addClass("hover");
        },function(){
            $(this).removeClass("hover");
        });
        $(".dorp-down-layer").hover(function(){
            $(this).prev().addClass("hover");
        },function(){
            $(this).prev().removeClass("hover");
        });
    }
}
WST.Sidebar = function(){
    if(!$('#wst-categorys')[0])return;

    if(!$('#wst-categorys').hasClass('j-index')){
        WST.dropDownLayer("#wst-categorys",".j-cate-dd");
    }
    $(".dd-inner").children(".item").hover(function() { //一级导航悬浮
        $('.categeMenuImg').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 100,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});//默认图片
        $(this).parent().find('.over-cat').show();

        $(this).addClass("hover").siblings(".item").removeClass("hover");
        var index = $(this).index();
        $(".dorpdown-layer").children(".item-sub").hide();
        $(".dorpdown-layer").children(".item-sub").eq(index).show();

        var start = $('.j-cate-dt').offset().top;
        var obj = $('#index_menus_sub');
        var sh = document.documentElement.scrollTop || document.body.scrollTop; // 滚动条距离顶部高度
        if(sh>start+36){
            var start = sh-start;
        }else{
            var start = 36;
        }
        //obj.stop().animate({ "top": start });
        obj.css('top',start);


    },function(){
        $(this).parent().find('.over-cat').hide();
    });



    $('.over-cat-icon').parent().mouseover(function(){
        $(this).find('.over-cat-icon').addClass('over-cat-icon-hover');
    });
    $('.over-cat-icon').parent().mouseout(function(){
        $(this).find('.over-cat-icon').removeClass('over-cat-icon-hover');
    });

    $(".dd-inner").children(".item").mouseover(function() {

        $('.dd-inner').find('.over-cat').show();

        var iCon = $(this).attr('id');
        $('.'+iCon).addClass(iCon+'-hover');
    });
    $(".dd-inner").children(".item").mouseout(function() {

        $('.dd-inner').find('.over-cat').hide();

        var iCon = $(this).attr('id');
        $('.'+iCon).removeClass(iCon+'-hover');
    });

    $("#index_menus_sub").hover(function(){
        $('.dd-inner').find('.over-cat').show();
        $(this).show();
    },function(){
        $(this).hide();
        $('.dd-inner').find('.over-cat').hide();
    });
    $(".dd-inner").hover(function() { //整个导航菜单悬浮，是否显示二级导航到出厂
        $("#index_menus_sub").show();

    }, function() {
        $("#index_menus_sub").hide();
        $('.item').removeClass("hover");
    })
    $("#index_menus_sub").children(".item-sub").hover(function() { //二级导航悬浮
        var index = $(this).index();
        $(".dd-inner").children(".item").eq(index).addClass("hover");
        $("#index_menus_sub").show();
        var i = index+1;
        $('.cat-icon-'+i).addClass('cat-icon-'+i+'-hover');
    }, function() {
        $("#index_menus_sub").hide();
        $(".dd-inner").children(".item").removeClass("hover");
        var index = $(this).index();
        var i = index+1;
        $('.cat-icon-'+i).removeClass('cat-icon-'+i+'-hover');

    });

    $('.fore2').hover(function(){
        $(this).children('dt').css('background-color','#ff6a53');
    },function(){
        $(this).children('dt').css('background-color','');
    });
}
WST.dropDownLayer = function(dropdown,layer){
    $(dropdown).hover(function () {
        $(this).find(layer).show();
    }, function () {
        $(this).find(layer).hide();
    });
    $(layer).hover(function () {
        $(this).find(layer).show();
    }, function () {
        $(this).find(layer).hide();
    });
}


WST.tips = function(content, selector, options){

    var opts = {};
    opts = $.extend(opts, {tips:1, time:2000, maxWidth: 260}, options);
    return layer.tips(content, selector, opts);
}
WST.open = function(options){
    var opts = {};
    opts = $.extend(opts, {offset:'100px'}, options);
    return layer.open(opts);
}
WST.confirm = function(options){
    var opts = {};
    opts = $.extend(opts, {title:'系统提示',offset:'200px'}, options);
    return layer.confirm(opts.content,{icon: 'wst3', title:opts.title,offset:opts.offset},options.yes,options.cancel);
}
WST.load = function(options){
    var opts = {};
    opts = $.extend(opts,{time:0,icon:'wstloading',shade: [0.4, '#000000'],offset: '200px',area: ['280px', '65px']},options);
    return layer.msg(opts.msg, opts);
}
WST.msg = function(msg, options, func){
    var opts = {};
    if(options){
        if(options.icon==1){
            options.icon='wst1';
        }else if(options.icon==2 || options.icon==5){
            options.icon='wst2';
        }else if(options.icon==3){
            options.icon='wst3';
        }
    }
    //有抖動的效果,第二位是函數
    if(typeof(options)!='function'){
        opts = $.extend(opts,{time:1000,shade: [0.4, '#000000'],offset: '200px'},options);
        return layer.msg(msg, opts, func);
    }else{
        return layer.msg(msg, options);
    }
}
WST.toJson = function(str,noAlert){
    var json = {};
    try{
        if(typeof(str )=="object"){
            json = str;
        }else{
            json = eval("("+str+")");
        }
        if(typeof(noAlert)=='undefined'){
            if(json.status && json.status=='-999'){
                WST.msg('对不起，您已经退出系统！请重新登录',{icon:5},function(){
                    if(window.parent){
                        window.parent.location.reload();
                    }else{
                        location.reload();
                    }
                });
            }else if(json.status && json.status=='-998'){
                WST.msg('对不起，您没有操作权限，请与管理员联系');
                return;
            }
        }
    }catch(e){
        WST.msg("系统发生错误:"+e.getMessage,{icon:5});
        json = {};
    }
    return json;
}

WST.logout = function(logouturl,indexurl){
    $.ajax({
        url: logouturl,
        xhrFields: {
            withCredentials: true
        },
        success: function( result ) {
           location.reload()
        }
    });
}

/**
 * 上传图片
 */
WST.upload = function(opts){
    var _opts = {};
    _opts = $.extend(_opts,{duplicate:true,auto: true,swf: WST.conf.STATIC +'/plugins/webuploader/Uploader.swf',server: "/uploadfile"},opts);
    var uploader = WebUploader.create(_opts);
    uploader.on('uploadSuccess', function( file,response ) {
        var json = WST.toJson(response._raw);
        if(_opts.callback)_opts.callback(json,file);
    });
    uploader.on('uploadError', function( file ) {
        if(_opts.uploadError)_opts.uploadError();
    });
    uploader.on( 'uploadProgress', function( file, percentage ) {
        percentage = percentage.toFixed(2)*100;
        if(_opts.progress)_opts.progress(percentage);
    });
    return uploader;
}

WST.goTo = function(obj){
    location.href = $(obj).attr('data');
}
WST.loginWindow = function(url){

    $.post(url+"/boxLogin",{},function(data){
        WST.open({type:1,area:['550px','360px'],offset:'auto',title:'用户登录',content:data});
    });
}

/********************* 选项卡切换隐藏 **********************/
$.fn.TabPanel = function(options){
    var defaults = {tab: 0};
    var opts = $.extend(defaults, options);
    var t = this;

    $(t).find('.wst-tab-nav li').click(function(){
        $(this).addClass("on").siblings().removeClass();
        var index = $(this).index();
        $(t).find('.wst-tab-content .wst-tab-item').eq(index).show().siblings().hide();
        if(opts.callback)opts.callback(index);
    });
    $(t).find('.wst-tab-nav li').eq(opts.tab).click();
}
/**
 * 去除url中指定的参数(用于分页)
 */
WST.splitURL = function(spchar){
    var url = location.href;
    var urlist = url.split("?");
    var furl = new Array();
    var fparams = new Array();
    furl.push(urlist[0]);
    if(urlist.length>1){
        var urlparam = urlist[1];
        params = urlparam.split("&");
        for(var i=0; i<params.length; i++){
            var vparam = params[i];
            var param = vparam.split("=");
            if(param[0]!=spchar){
                fparams.push(vparam);
            }
        }
        if(fparams.length>0){
            furl.push(fparams.join("&"));
        }

    }
    if(furl.length>1){
        return furl.join("?");
    }else{
        return furl.join("");
    }
}

WST.delCart = function(id){
    WST.confirm({content:'您确定要删除该商品吗？',yes:function(index){
            $.post("/delcats",{id:id,rnd:Math.random()},function(res){
                if(res.status==200){
                    WST.msg(res.msg,{icon:1});
                    location.reload();
                }else{
                    WST.msg(res.msg,{icon:2});
                }
            });
        }});
}
WST.changeCartGoods = function(id,buyNum,isCheck){
    console.log(id)
    console.log(buyNum)
    console.log(isCheck)
}
WST.dropDownLayerCart = function(dropdown,layer){
    $(dropdown).hover(function () {
        $(this).find(layer).show();
        WST.checkCart();
    }, function () {
        $(this).find(layer).hide();
    });
    $(layer).hover(function (event) {
        event.stopPropagation();
        $(this).show();
    }, function (event) {
        event.stopPropagation();
        $(this).hide();
    });
}
WST.delCheckCart = function(id,func){
    $.post(window.conf.ITEM_URL+"/delcart",{id:id,rnd:Math.random()},function(res){
        if(res.status==200){
            WST.msg(res.msg,{icon:1});
            WST.checkCart();
        }else{
            WST.msg(res.msg,{icon:2});
        }
    });
}
WST.checkCart = function(){
    $('#list-carts2').html('');
    $('#list-carts3').html('');
    $('#list-carts').html('<div style="padding:32px 0px 77px 112px;"><img src="'+'/img/loading.gif">正在加载数据...</div>');
    $.ajax({
        url:window.conf.ITEM_URL+'/getcatlist',
        type:'post',
        dataType:'json',
        xhrFields: {
            withCredentials: true
        },
        data:{  },
        success:function (res) {
            $('#goodsTotalNum').hide();
            if(res.status==200){
                var  data = res.data;
                if(data.count>0){
                    $('#goodsTotalNum').show();
                    var gettpl = document.getElementById('list-cart').innerHTML;
                    laytpl(gettpl).render(data, function(html){
                        $('#list-carts').html(html);
                    });
                    $('#list-carts2').html('<div class="comm" id="list-comm">&nbsp;&nbsp;共<span>'+data.count+'</span>件商品<span class="span2">￥'+data.totalprice+'</span></div>');
                    $('#list-carts3').html('<a href="'+window.conf.ORDER_URL+'/carts/mycatlist" class="btn btn-3">去购物车结算</a>');

                    $('.goodsImgc').lazyload({ effect: "fadeIn",failurelimit : 10,skip_invisible : false,threshold: 200,placeholder:window.conf.RESOURCE_PATH+'/'+window.conf.GOODS_LOGO});//商品默认图片
                    if(data.count>5){
                        $('#list-carts').css('overflow-y','scroll').css('height','416');
                    }
                }else{
                    $('#list-carts').html('<p class="carts">购物车中空空如也，赶紧去选购吧～</p>');
                }
                $('#goodsTotalNum').html(data.count);
            }else{
                $('#list-carts').html('<p class="carts">购物车中空空如也，赶紧去选购吧～</p>');
                $('#goodsTotalNum').html(0);
            }
        }
    })
}
WST.changeIptNum = function(diffNum,iptId,btnId,id,func){
    var suffix = (id)?"_"+id:"";
    var iptElem = $(iptId+suffix);
    // console.log(suffix)
    // console.log(iptElem)
    var minVal = parseInt(iptElem.attr('data-min'),10);
    var maxVal = parseInt(iptElem.attr('data-max'),10);
    var tmp = 0;
    if(maxVal<minVal){
        tmp = maxVal;
        maxVal = minVal;
        minVal = tmp;
    }
    var num = parseInt(iptElem.val(),10);
    num = num?num:1;
    num = num + diffNum;
    btnId = btnId.split(',');
    $(btnId[0]+suffix).css('color','#666');
    $(btnId[1]+suffix).css('color','#666');
    if(minVal>=num){
        num=minVal;
        $(btnId[0]+suffix).css('color','#ccc');
    }
    if(maxVal<=num){
        num=maxVal;
        $(btnId[1]+suffix).css('color','#ccc');
    }
    iptElem.val(num);
    if(suffix!='')WST.changeCartGoods(id,num,-1);
    if(func){
        var fn = window[func];
        fn();
    }
}



/**
 * 获取最后已选分类的id
 */
WST.ITGetAllGoodsCatVals = function(srcObj,className){
    var goodsCatId = '';
    $('.'+className).each(function(){
        if($(this).attr('lastgoodscat')=='1')goodsCatId = $(this).attr('id')+'_'+$(this).val();
    });
    goodsCatId = goodsCatId.replace(srcObj+'_','');
    return goodsCatId.split('_');
}
/**
 * 获取最后分类值
 */
WST.ITGetGoodsCatVal = function(className){
    var goodsCatId = '';
    $('.'+className).each(function(){
        if($(this).attr('lastgoodscat')=='1')goodsCatId = $(this).val();
    });
    return goodsCatId;
}
/**
 * 获取最后地区的值
 */
WST.ITGetAreaVal = function(className){
    var areaId = '';
    $('.'+className).each(function(){
        if($(this).attr('lastarea')=='1')areaId = $(this).val();
    });
    return areaId;
}
/**
 * 获取最后已选分类的id
 */
WST.ITGetAllAreaVals = function(srcObj,className){
    var areaId = '';
    $('.'+className).each(function(){
        if($(this).attr('lastarea')=='1')areaId = $(this).attr('id')+'_'+$(this).val();
    });
    areaId = areaId.replace(srcObj+'_','');
    return areaId.split('_');
}

/**
 * 获取用户信息
 */
WST.getSysMessages = function(val){

}
//关闭顶部广告
WST.closeAds = function(t){
    $(t).parent().remove();
}
WST.closeIframe = function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

WST.slides = function(objId){
    var slide = $(objId), li = slide.find("li");
    var slidecontrols = $(objId+'-controls').eq(0),
        span = slidecontrols.find("span");
    var index = 1, _self = null;
    span.bind("mouseover", function() {
        _self = $(this);
        index = span.index(_self);
        span.removeClass("curr");
        span.eq(index).addClass("curr");
        li.addClass("hide");
        li.css("z-index", -1);
        li.css("display", "none");
        li.eq(index).css("display", "");
        li.eq(index).css("z-index", 1);
        li.eq(index).removeClass("hide");
        clearInterval(timer);
    });
    var timer = setInterval(function() {
        span.removeClass("curr");
        span.eq(index).addClass("curr");
        li.addClass("hide");
        li.css("z-index", -1);
        li.css("display", "none");
        li.eq(index).fadeToggle(500);
        li.eq(index).css("z-index", 1);
        li.eq(index).removeClass("hide");
        index++;
        if (index >= span.length)
            index = 0;
    }, 4000);
    span.bind("mouseout", function() {
        index++;
        if (index >= span.length)
            index = 0;
        timer = setInterval(function() {
            span.removeClass("curr");
            span.eq(index).addClass("curr");
            li.addClass("hide");
            li.css("z-index", -1);
            li.css("display", "none");
            li.eq(index).fadeToggle(500);
            li.eq(index).css("z-index", 1);
            li.eq(index).removeClass("hide");
            index++;
            if (index >= span.length)
                index = 0;
        }, 4000);
    });
}

/*! Lazy Load 1.9.3 - MIT license - Copyright 2010-2013 Mika Tuupola */
!function(a,b,c,d){var e=a(b);a.fn.lazyload=function(f){function g(){var b=0;i.each(function(){var c=a(this);if(!j.skip_invisible||c.is(":visible"))if(a.abovethetop(this,j)||a.leftofbegin(this,j));else if(a.belowthefold(this,j)||a.rightoffold(this,j)){if(++b>j.failure_limit)return!1}else c.trigger("appear"),b=0})}var h,i=this,j={threshold:0,failure_limit:0,event:"scroll",effect:"show",container:b,data_attribute:"original",skip_invisible:!0,appear:null,load:null,placeholder:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC"};return f&&(d!==f.failurelimit&&(f.failure_limit=f.failurelimit,delete f.failurelimit),d!==f.effectspeed&&(f.effect_speed=f.effectspeed,delete f.effectspeed),a.extend(j,f)),h=j.container===d||j.container===b?e:a(j.container),0===j.event.indexOf("scroll")&&h.bind(j.event,function(){return g()}),this.each(function(){var b=this,c=a(b);b.loaded=!1,(c.attr("src")===d||c.attr("src")===!1)&&c.is("img")&&c.attr("src",j.placeholder),c.one("appear",function(){if(!this.loaded){if(j.appear){var d=i.length;j.appear.call(b,d,j)}a("<img />").bind("load",function(){var d=c.attr("data-"+j.data_attribute);c.hide(),c.is("img")?c.attr("src",d):c.css("background-image","url('"+d+"')"),c[j.effect](j.effect_speed),b.loaded=!0;var e=a.grep(i,function(a){return!a.loaded});if(i=a(e),j.load){var f=i.length;j.load.call(b,f,j)}}).attr("src",c.attr("data-"+j.data_attribute))}}),0!==j.event.indexOf("scroll")&&c.bind(j.event,function(){b.loaded||c.trigger("appear")})}),e.bind("resize",function(){g()}),/(?:iphone|ipod|ipad).*os 5/gi.test(navigator.appVersion)&&e.bind("pageshow",function(b){b.originalEvent&&b.originalEvent.persisted&&i.each(function(){a(this).trigger("appear")})}),a(c).ready(function(){g()}),this},a.belowthefold=function(c,f){var g;return g=f.container===d||f.container===b?(b.innerHeight?b.innerHeight:e.height())+e.scrollTop():a(f.container).offset().top+a(f.container).height(),g<=a(c).offset().top-f.threshold},a.rightoffold=function(c,f){var g;return g=f.container===d||f.container===b?e.width()+e.scrollLeft():a(f.container).offset().left+a(f.container).width(),g<=a(c).offset().left-f.threshold},a.abovethetop=function(c,f){var g;return g=f.container===d||f.container===b?e.scrollTop():a(f.container).offset().top,g>=a(c).offset().top+f.threshold+a(c).height()},a.leftofbegin=function(c,f){var g;return g=f.container===d||f.container===b?e.scrollLeft():a(f.container).offset().left,g>=a(c).offset().left+f.threshold+a(c).width()},a.inviewport=function(b,c){return!(a.rightoffold(b,c)||a.leftofbegin(b,c)||a.belowthefold(b,c)||a.abovethetop(b,c))},a.extend(a.expr[":"],{"below-the-fold":function(b){return a.belowthefold(b,{threshold:0})},"above-the-top":function(b){return!a.belowthefold(b,{threshold:0})},"right-of-screen":function(b){return a.rightoffold(b,{threshold:0})},"left-of-screen":function(b){return!a.rightoffold(b,{threshold:0})},"in-viewport":function(b){return a.inviewport(b,{threshold:0})},"above-the-fold":function(b){return!a.belowthefold(b,{threshold:0})},"right-of-fold":function(b){return a.rightoffold(b,{threshold:0})},"left-of-fold":function(b){return!a.rightoffold(b,{threshold:0})}})}(jQuery,window,document);

