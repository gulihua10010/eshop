package cn.jianwoo.eshop.user.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.manage.mapper.UserMapper;
import cn.jianwoo.eshop.user.api.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.Escape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.apache.tomcat.jni.SSL.setPassword;

public class UserserviceImpl implements UserService {

    private static  final  Logger log=LoggerFactory.getLogger(UserserviceImpl.class);
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Value("${DEFAULT_AVATAR}")
    private  String DEFAULT_AVATAR;
    @Value("${USER_KEY}")
    private  String USER_KEY;
    @Value("${USER_KEY_EXPIRE}")
    private Integer USER_KEY_EXPIRE;
    @Value("${REDIS_SESSION_KEY_LOGIN}")
    private String REDIS_SESSION_KEY_LOGIN;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    @Override
    public List<User> getByKey(Map<String, Object> paramMap) {

        return null;
    }

    @Override
    public LayuiResult getList(int page, int limit, String kw) {
        PageHelper.startPage(page,limit);
        List<User> users=null;
        if (kw==null||kw==""){
            users=userMapper.getList();
        }else{
            Map<String,Object> param=new HashMap<>();
            param.put("type","likename");
            param.put("params",kw);
            users=userMapper.getUserListByWithMap(param);


        }
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return         LayuiResult.ok(pageInfo.getTotal(),users) ;
    }

    @Override
    public EShopResult checkData(String param, Integer type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("param", param);
        paramMap.put("type", type);
       List<User> users = userMapper.getByKey(paramMap);
       if (users==null||users.isEmpty()){
           return  EShopResult.ok(true);
       }else{
           return EShopResult.ok(false);
       }
    }

    @Override
    public User getById(Long id) {
        try {
            User user= (User) redisTemplate.opsForValue().get(USER_KEY+":"+id);
            if (user!=null) {
                log.info("read reids  item base ..");
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user=userMapper.getById(id);
        redisTemplate.opsForValue().set(USER_KEY+":"+id,user);
        redisTemplate.expire(USER_KEY+":"+id,USER_KEY_EXPIRE,TimeUnit.HOURS);
        log.info("save reids  item base ..");

        return user;
    }

    @Transactional
    @Override
    public EShopResult register (User user) {
        System.out.println(user);
        if (user!=null&&user.getUsername()!=null){

            if (StringUtils.isBlank(user.getPassword())||StringUtils.isBlank(user.getUsername())){
                return  EShopResult.error("用户名或密码不能为空");
            }

            EShopResult result = checkData(user.getUsername(), 1) ;
            if (!(boolean) result.getData()) {
                return EShopResult.error("用户名重复");
            }
            if(user.getPhone()!=null){
                  result = checkData(user.getPhone(), 2);
                if (!(boolean) result.getData()) {
                    return EShopResult.error("手机号重复");
                }
            }
            if (user.getEmail()!=null){
                result = checkData(user.getEmail(), 3);
                if (!(boolean) result.getData()) {
                    return EShopResult.error("邮箱    重复");
                }
            }
        user.setCreated(new Timestamp(new Date().getTime()));
        user.setUpdated(new Timestamp(new Date().getTime()));
        user.setStatus(1);
        user.setSex(0);
        user.setAvatar(DEFAULT_AVATAR);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int r=userMapper.insert(user);
            System.out.println(user);
        if (r>0){
//            ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
//            jmsMessagingTemplate.convertAndSend(itemAddTopic, user.getId());
            return  EShopResult.ok();
        }else{
            return  EShopResult.error();
        }
        }else{
            return EShopResult.error("用户不能为空");

        }
    }

    @Override
    public EShopResult updateUser(User user) {
        if (user!=null&&user.getId()!=null){
            user.setUpdated(new Timestamp(new Date().getTime()));
            System.out.println(2112222);
            System.out.println(user);
            int r=userMapper.update(user);
            if (r>0){
                redisTemplate.opsForValue().set(USER_KEY+":"+user.getId(),userMapper.getById(user.getId()));
                redisTemplate.expire(USER_KEY+":"+user.getId(),USER_KEY_EXPIRE,TimeUnit.HOURS);

                return  EShopResult.ok();
            }else{
                return  EShopResult.error();
            }
        }else{
            return EShopResult.error("用户名不能为空");

        }
    }


    @Override
    public EShopResult getUserByToken(String token) {
        User tbUser = (User) redisTemplate.opsForValue().get(REDIS_SESSION_KEY_LOGIN + ":" + token);
        if (tbUser == null) {
            return EShopResult.error( "用户登录信息已经过期！");
        }
        redisTemplate.expire(REDIS_SESSION_KEY_LOGIN + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);
        return EShopResult.ok(tbUser);
    }
    @Override
    public EShopResult login(String username, String password) {
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return  EShopResult.error("用户名或密码不能为空");
        }
        ///(?:^1[3456789]|^9[28])\d{9}$
        //(?:^1[3456789]|^9[28])\d{9}$
//^1([38]\d|5[0-35-9]|7[3678])\d{8}$
        String pattern = "^1([38]\\d|5[0-35-9]|7[3678])\\d{8}$";
        List<User> users=null;
        boolean isMatch = Pattern.matches(pattern, username);
        System.out.println(isMatch);
        if (isMatch){
            users   =userMapper.getByPhone(username);
        }else {
            users   =userMapper.getByName(username);
        }
        System.out.println(users);
        if (users == null || users.isEmpty()) {
            return EShopResult.error( "该用户不存在");
        }
        User user = users.get(0);
        // 校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return EShopResult.error( "密码错误");
        }
        // 登录成功
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        redisTemplate.opsForValue().set(REDIS_SESSION_KEY_LOGIN + ":" + token, user);
        redisTemplate.expire(REDIS_SESSION_KEY_LOGIN + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);

        return EShopResult.ok(token);
    }

    @Override
    public EShopResult adminLogin(String username, String password) {
        List<User> users=null;
        users   =userMapper.getByName(username);
        System.out.println(users);
        if (users == null || users.isEmpty()) {
            return EShopResult.error( "该用户不存在");
        }
        User user = users.get(0);
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return EShopResult.error( "密码错误");
        }
        if (user.getType()!=0){
            return  EShopResult.error("非法用户");
        }
        String token = UUID.randomUUID().toString();
        return EShopResult.ok(token);

    }

    @Override
    public Integer getcount() {
         return  userMapper.getcount();
    }
}
