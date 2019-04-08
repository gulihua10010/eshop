package cn.jianwoo.eshop.order.web.controller;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Address;
import cn.jianwoo.eshop.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping("/useraddress/{mode}")
    public EShopResult useraddress(@PathVariable("mode") String mode , @RequestBody Address address , HttpServletRequest request){

        System.out.println(address);
        System.out.println(mode);

        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
            address.setUid(uid);
            if (address.getIsDefault()==1){
                addressService.updateDefault(address);
            }
            if (mode.equals("add")){
                return   addressService.insert(address);
            }
            if (mode.equals("toEdit")){
                return  addressService.update(address);
            }
        }

        return EShopResult.error("请先登录");

    }
    @RequestMapping("/getaddresslist")
    public EShopResult getaddresslist(HttpServletRequest request){
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
            List<Address> addresses=addressService.getAddresslistByUid(uid);

        return EShopResult.ok(addresses);

        }

        return  EShopResult.error();
    }
    @RequestMapping("/getaddressbyid")
    public EShopResult getaddressbyid(Long id){

            return EShopResult.ok(addressService.getaddressbyid(id));

    }
    @RequestMapping("/setdefaultaddress")
    public EShopResult setdefaultaddress(Long id){
Address address=new Address();
address.setId(id);
return addressService.setDefault(address);

    }

    @RequestMapping("/deladdress")
    public EShopResult deladdress(Long id){
        Address address=new Address();
        address.setId(id);
        return addressService.delete(address);

    }
}
