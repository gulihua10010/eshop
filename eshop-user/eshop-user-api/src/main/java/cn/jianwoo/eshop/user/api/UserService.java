package cn.jianwoo.eshop.user.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getByKey(Map<String, Object> paramMap);
    LayuiResult getList(int page,int limit,String kw);
    EShopResult checkData(String param, Integer type);
    User  getById(Long id);
    EShopResult register(User user);
    EShopResult updateUser  (User user);
    EShopResult getUserByToken  (String  token);
    EShopResult login(String username, String password);
    EShopResult adminLogin(String username, String password);
    Integer getcount();
}
