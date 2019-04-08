package cn.jianwoo.eshop.sso.web.controller;

import cn.jianwoo.eshop.manage.entity.WebConfig;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    @Autowired
    WebConfigService webConfigService;

    @Value("${SSO_URL}")
    private  String SSO_URL;
    @Value("${HOME_URL}")
    private  String HOME_URL;
    @RequestMapping("/login")
    public  String login(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);
        model.addAttribute("homeurl",HOME_URL);

        return  "login";
    }

    @RequestMapping("/register")
    public  String register(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
        session.setAttribute("webconfig",web);

        model.addAttribute("webconfig",web);
        model.addAttribute("homeurl",HOME_URL);

        return  "register";
    }

    @RequestMapping("/boxLogin")
    public  String boxLogin(Model model,HttpServletRequest request){
        HttpSession session=request.getSession();
        Map<String,String> web=null;
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        return  "box_login";
    }
}
