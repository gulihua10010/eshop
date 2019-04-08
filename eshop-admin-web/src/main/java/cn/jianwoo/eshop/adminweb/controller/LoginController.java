package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.adminweb.common.GeetestLib;
import cn.jianwoo.eshop.adminweb.config.GeetestConfig;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
@Autowired
    UserService userService;

    @PostMapping("/api/VerifyLogin")
    public EShopResult VerifyLogin(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
        System.out.println(gtSdk.gtServerStatusSessionKey);
        System.out.println(request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey));
        //从session中获取gt-server状态
//        int gt_server_status_code = (Integer)redisTemplate.opsForValue().get(gtSdk.gtServerStatusSessionKey);
        int gt_server_status_code = (Integer)request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");
//        String userid = (String)redisTemplate.opsForValue().get("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        System.out.println(gtResult);
        System.out.println('d');
        if (gtResult == 1) {
            // 验证成功

            EShopResult.ok(gtSdk.getVersionInfo());
        }
        else {
            EShopResult.error( gtSdk.getVersionInfo());
        }

        return  EShopResult.ok();
    }
    @GetMapping("/api/GeetestStart")
    public EShopResult startCaptcha(HttpServletRequest request){


        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中0.232.1.0
        request.getSession().setAttribute("userid", userid);
        System.out.println(request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey));
        request.getSession().setAttribute("userid",userid);
//        redisTemplate.opsForValue().set(gtSdk.gtServerStatusSessionKey,gtServerStatus);
//        redisTemplate.opsForValue().set("userid",userid);
        resStr = gtSdk.getResponseStr();

        com.alibaba.fastjson.JSONObject  jsonObject= com.alibaba.fastjson.JSONObject.parseObject(resStr);
        Map<String,Object> map= com.alibaba.fastjson.JSONObject.toJavaObject(jsonObject,Map.class);
        return  EShopResult.ok(map) ;
    }

    @RequestMapping("/api/toLogin")
    public  EShopResult toLogin(@RequestBody User user,HttpServletRequest request){
        System.out.println(user);
      EShopResult result=  userService.adminLogin(user.getUsername(),user.getPassword());
if (result.getStatus()==200){
    request.getSession().setAttribute("admin",result.getData());

        }
return  result;
    }
    @RequestMapping("/logout")
    public  EShopResult logout( HttpServletRequest request){

        request.getSession().setAttribute("admin",null);

        return  EShopResult.ok("退出成功",null);
    }
}
