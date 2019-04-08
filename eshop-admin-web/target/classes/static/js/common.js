/** common.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn111 */
var $jq ;
layui.define(['layer'], function (exports) {
    "use strict";

    $jq = layui.jquery,
        layer = layui.layer;

    var common = {
        /**
         * 抛出一个异常错误信息
         * @param {String} msg
         */
        throwError: function (msg) {
            throw new Error(msg);
            return;
        },
        /**
         * 弹出一个错误提示
         * @param {String} msg
         */
        msgError: function (msg) {
            layer.msg(msg, {
                icon: 5
            });
            return;
        }
    };

    exports('common', common);
});

function getRootPath_web() {
    //获取当前网址，如： http://localhost:8083/kyw/user/login
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如：kyw/user/login.php
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

    return (localhostPaht + projectName);
}

function ajaxform(formdata, url, $, success , error  ) {
    if (error==null||error==undefined){
        error=function () {  }
    }
    $.ajax({
        data: formdata,
        type: 'post',
        async: true,
        dataType: 'json',
        url: url,
        contentType:"application/json",
        beforeSend: function () {
            loading = layer.load(2, {
                shade: [0.2, '#000']
            })
        },
        success: function (res) {
            layer.close(loading);
            console.log(res)
            if (res.status == 200) {
                layer.msg(res.msg, {icon: 1, time: 1000});
                setTimeout(function () {
                    success(res);
                }, 1000)

            } else {
                layer.msg(res.msg, {icon: 2, time: 1000});
                error();
            }

        }, error: function (res) {
            layer.close(loading);
            layer.msg('未知错误', {icon: 2, time: 1000});
            error();
        }


    })
}

function uploadImg(upload, elem, url, success  , error) {
    upload.render({
        url: url
        , elem: elem
        , exts: 'jpg|png|gif'
        , before: function (input) {
            loading = layer.load(2, {
                shade: [0.2, '#000']
            });
        }
        , done: function (res) {
            layer.close(loading);
            success(res);
        }, error: function () {
            error();
            layer.close(loading);
            layer.msg('上传出错：1', {
                title: '提示'
                //不自动关闭
                , time: 1000
                , icon: 5
                , offset: '400px'
            });
        }
    });
}


function converTime(sec) {
    var sec = parseInt(sec);
    if (sec < 60) {
        return sec + '秒';
    }
    var min = parseInt(sec / 60);
    sec = sec % 60;
    if (min < 60) {
        return min + '分' + sec + '秒';
    }
    var hour = parseInt(min / 60);
    min = min % 60;
    return hour + '时' + min + '分' + sec + '秒';

}

function getRandom() {
    var random = "qwertyuioplkjhgfdsazxcvbnm1234567890";
    var s = '';
    for (var i = 0; i < 10; i++) {
        var r = Math.round(Math.random() * 36);
        s += random[r];
    }
    return s;
}
var isf=1

function futext(id,power_paste) {
    if (power_paste==undefined||power_paste==''){
        power_paste='propmt';
    }
    tinymce.init({
        selector: id,
        max_height: 550,
        height:550,
        convert_urls: false,
        branding: false,
        plugins: [
            "advlist  autolink   link image lists   print preview hr anchor  ",
            "searchreplace wordcount     code   insertdatetime media nonbreaking",
            "table contextmenu directionality emoticons   textcolor paste fullpage  powerpaste toc   uploadvideo importcss textcolor colorpicker uploadimage  uploadvideo "
        ],
        toolbar1: "undo redo | cut copy paste | bold italic underline strikethrough |" +
        " alignleft aligncenter alignright alignjustify |   formatselect fontselect fontsizeselect",
        toolbar2: " searchreplace | bullist numlist | outdent indent blockquote | link unlink   uploadimage   uploadvideo  code |" +
        " inserttime preview | forecolor backcolor",
        toolbar3: "table | hr removeformat | subscript superscript |   emoticons |  " +
        "   nonbreaking    restoredraft  |  code      ",
        menubar: false,
        toolbar_items_size: 'small',
        font_formats: "宋体=宋体;微软雅黑=微软雅黑;新宋体=新宋体;微软雅黑='微软雅黑';黑体='黑体';仿宋='仿宋';楷体='楷体';隶书='隶书';幼圆='幼圆';" +
        "Arial='Arial';Times New Roman='Times New Roman'",
        automatic_uploads: true,
        uploadimage_url: "/uploadfile",
        uploadvideo_url: "/uploadfile",
        content_css:  "/css/tinymce.css",
        textcolor_map: [
            "000000", "Black", "993300", "Burnt orange", "333300", "Dark olive", "003300", "Dark green", "003366", "Dark azure", "000080", "Navy Blue",
            "333399", "Indigo", "333333", "Very dark gray", "800000", "Maroon", "FF6600", "Orange", "808000", "Olive", "008000",
            "Green", "008080", "Teal", "0000FF", "Blue", "666699", "Grayish blue", "808080", "Gray", "FF0000", "Red", "FF9900",
            "Amber", "99CC00", "Yellow green", "339966", "Sea green", "33CCCC", "Turquoise", "3366FF", "Royal blue", "800080",
            "Purple", "999999", "Medium gray", "FF00FF", "Magenta", "FFCC00", "Gold", "FFFF00", "Yellow", "00FF00", "Lime",
            "00FFFF", "Aqua", "00CCFF", "Sky blue", "993366", "Red violet", "FFFFFF", "White", "FF99CC", "Pink", "FFCC99", "Peach",
            "FFFF99", "Light yellow", "CCFFCC", "Pale green", "CCFFFF", "Pale cyan", "99CCFF", "Light sky blue", "CC99FF", "Plum"
        ],
        language: 'zh_CN',
        powerpaste_word_import: power_paste,// 参数可以是propmt, merge, clear，效果自行切换对比
        powerpaste_html_import: power_paste,// propmt, merge, clear
        powerpaste_allow_local_images: true,
        paste_data_images: true,
        paste_merge_formats:true,
        paste_preprocess: function (plugin, args) {
            function load(src) {
                loadImageToBlob(src, function (blobFile) {
                    var x = new XMLHttpRequest();
                    x.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            data = this.responseText;
                            // console.log('response data: ' + data);
                        }
                    };
                    x.open('POST', "/uploadImg");
                    x.send(blobFile);
                });
            }
        },
        images_upload_handler: function (blobInfo, success, failure) {
            var blob = blobInfo.blob();
            var formData = new FormData();
            formData.append('file', blob);
            $jq.ajax({
                url: "/uploadimg",
                crossDomain: true,
                data: formData,
                dataType: 'json',
                type: 'POST',
                contentType: false,
                processData: false,
                success: function (res) {
                    success(getRootPath_web() + res.path);
                }
            });
        }
    });
}
    function clearHtml(str) {
    str=str.replace(/<!DOCTYPE html>/g,"");
    str=str.replace(/<[/]?html>/g,"");
    str=str.replace(/<[/]?body>/g,"");
    str=str.replace(/<[/]?head>/g,"");
    return str;
    }
function clearHtmlexpImg(str) {
    str=str.replace(/<!DOCTYPE html>/g,"");
    str=str.replace(/<([^i][^>.]+)>/g,"");
    str=str.replace(/<([^i.]+)>/g,"");
    str=str.replace(/<i>/g,"");
    // str=str.replace(/<[\.]+>/g,"");

    return str;
}