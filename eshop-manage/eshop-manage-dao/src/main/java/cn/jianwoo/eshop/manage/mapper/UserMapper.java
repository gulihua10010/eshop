package cn.jianwoo.eshop.manage.mapper;


import cn.jianwoo.eshop.manage.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    List<User> getByKey(Map<String, Object> paramMap);
    List<User> getUserListByWithMap(Map<String, Object> paramMap);
    List<User> getList();
    User  getById(Long id);
    int insert(User user);
    int update(User user);
    List<User> getByName(String name);
    List<User> getByPhone(String phone);
    List<User> getByMap(Map<String, Object> paramMap);
    Integer getcount();
}
