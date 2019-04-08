package cn.jianwoo.eshop.webconfig.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.manage.mapper.HomepptMapper;
import cn.jianwoo.eshop.webconfig.api.HomepptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeServiceImpl implements HomepptService {
    @Autowired
    HomepptMapper homepptMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public EShopResult insert(Homeppt homeppt) {
        if (homeppt!=null){
            homeppt.setStatus(1);
            homepptMapper.insert(homeppt);
            redisTemplate.opsForValue().set("Homeppt_all",homepptMapper.getHomepptlist());
            redisTemplate.opsForValue().set("Homeppt_all_ON",homepptMapper.getHomepptlistAndOn());
            redisTemplate.expire("Homeppt_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Homeppt_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult update(Homeppt homeppt) {
        if (homeppt!=null&&homeppt.getId()!=null){
            homepptMapper.update(homeppt);
            redisTemplate.opsForValue().set("Homeppt_all",homepptMapper.getHomepptlist());
            redisTemplate.opsForValue().set("Homeppt_all_ON",homepptMapper.getHomepptlistAndOn());
            redisTemplate.expire("Homeppt_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Homeppt_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }

        return EShopResult.error("不能为空");

    }

    @Override
    public EShopResult deleteAll() {
            homepptMapper.deleteAll();
            redisTemplate.opsForValue().set("Homeppt_all",homepptMapper.getHomepptlist());
            redisTemplate.opsForValue().set("Homeppt_all_ON",homepptMapper.getHomepptlistAndOn());
            redisTemplate.expire("Homeppt_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Homeppt_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();

    }

    @Override
    public List<Homeppt> getHomepptlist() {
        try {
            List<Homeppt> homeppts= (List<Homeppt>) redisTemplate.opsForValue().get("Homeppt_all");
            if (homeppts!=null){
    return  homeppts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Homeppt> homeppts=homepptMapper.getHomepptlist();
                redisTemplate.opsForValue().set("Homeppt_all",homeppts);
        redisTemplate.expire("Homeppt_all",24,TimeUnit.HOURS);
        return homeppts;
    }

    @Override
    public List<Homeppt> getHomepptlistAndOn() {
        try {
            List<Homeppt> homeppts= (List<Homeppt>) redisTemplate.opsForValue().get("Homeppt_all_ON");
            if (homeppts!=null){
                return  homeppts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Homeppt> homeppts=homepptMapper.getHomepptlistAndOn();
        redisTemplate.opsForValue().set("Homeppt_all_ON",homeppts);
        redisTemplate.expire("Homeppt_all_ON",24,TimeUnit.HOURS);
        return homeppts;
    }

    @Override
    public Homeppt getHomepptById(Integer id) {
        return homepptMapper.getHomepptById(id);
    }
}
