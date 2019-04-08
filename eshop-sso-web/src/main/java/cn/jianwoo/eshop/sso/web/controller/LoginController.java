package cn.jianwoo.eshop.sso.web.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.CookieUtils;
import cn.jianwoo.eshop.common.utils.HttpClientUtil;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.sso.web.common.GeetestLib;
import cn.jianwoo.eshop.sso.web.config.GeetestConfig;
import cn.jianwoo.eshop.user.api.UserService;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
//@CrossOrigin(origins = "http://localhost:8010",maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserService userService;
    @PostMapping("/sendPhoneCode")
    public EShopResult sendCode(  String userPhone, HttpServletRequest request){
        HttpSession session=request.getSession();
        String code="";
        Random random=new Random();
        for (int i=0;i<6;i++){
            code+=random.nextInt(10);
        }
        //18360866171
        System.out.println(userPhone);
        session.setAttribute("code_value",code);
        System.out.println(session.getAttribute("code_value"));
        String host ="http://cowsms.market.alicloudapi.com";
        String path= "/intf/smsapi";
        String appcode = "fedd26144fc1458faf48bfd9274a9f8b";
        Map<String  ,String> head=new HashMap<>();
        head.put("Authorization","APPCODE "+appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", userPhone);
        querys.put("paras", code+",10");
        querys.put("sign", "微服务电子商城");
        querys.put("tpid", "331");
        String res=HttpClientUtil.doGetByHead(host+path,querys,head);
        System.out.println(res);
        return  EShopResult.ok();
    }

    @RequestMapping("/checkUser")
    public  Map<String,String> checkUser(String loginName){

        System.out.println(loginName);
Map<String,String> map=new HashMap<>();
//map.put("name","")  user phone email
       EShopResult eShopResult=  userService.checkData(loginName,1);
       boolean res= (boolean) eShopResult.getData();
       if (res==true){
           map.put("ok","");
           return  map;
       }else{
           map.put("error","已经存在用户");
           return  map;
       }


    }
    @RequestMapping("/checkPhone")
    public  Map<String,String> checkPhone(String phone){
        Map<String,String> map=new HashMap<>();

        System.out.println(phone);
        EShopResult eShopResult=  userService.checkData(phone,2);

        boolean res= (boolean) eShopResult.getData();
        if (res==true){
            map.put("ok","");
            return  map;
        }else{
            map.put("error","已经存在用户");
            return  map;
        }
    }
//注册
    @RequestMapping("/toRegister")
    public EShopResult toRegister(@RequestBody User user, HttpServletRequest request){
        System.out.println(user);
        HttpSession session=request.getSession();
        String code= (String) session.getAttribute("code_value");
        if (!code.equals(user.getCode())){
            return  EShopResult.error("验证码错误");
        }
        if (!user.getPassword().equals(user.getRePassword())){
              return  EShopResult.error("两次密码不一致");

        }
        return  userService.register(user);
    }
    //登录
    @RequestMapping("/toLogin")
    public EShopResult toLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        EShopResult result= userService.login(user.getUsername(),user.getPassword());
        if (result.getStatus()==200){
            System.out.println(result);
            String token=result.getData().toString();
            System.out.println(token);
            HttpSession session=request.getSession();
            System.out.println(session.getId());
            session.setAttribute("LOGIN_TOKEN_ON",token);
//            redisTemplate.opsForValue().set("LOGIN_TOKEN_ON",token);
//            CookieUtils.setCookie(request,response,"LOGIN_TOKEN_ON",token );

        }
        return result;

    }
    //登录
    @RequestMapping("/loginout")
    public EShopResult loginout(  HttpServletRequest request, HttpServletResponse response){
//       String token= CookieUtils.getCookieValue(request,"LOGIN_TOKEN" );
        HttpSession session=request.getSession();
     String token= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(session.getId());
//     if (token==null){
//         token= (String) redisTemplate.opsForValue().get("LOGIN_TOKEN_ON");
//     }
        System.out.println(token);
        redisTemplate.opsForValue().set("REDIS_SESSION_KEY_LOGIN" + ":" + token, null);
        User  user= (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
        System.out.println(user);
//       redisTemplate.delete(key
       session.setAttribute("LOGIN_TOKEN_ON",null);
//       CookieUtils.setCookie(request,response,"LOGIN_TOKEN",null);
//          token= CookieUtils.getCookieValue(request,"LOGIN_TOKEN" );
        return EShopResult.ok("退出成功",null);

    }
    @PostMapping("/VerifyLogin")
    public EShopResult  VerifyLogin(HttpServletRequest request){

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
    @GetMapping("/GeetestStart")
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
}
