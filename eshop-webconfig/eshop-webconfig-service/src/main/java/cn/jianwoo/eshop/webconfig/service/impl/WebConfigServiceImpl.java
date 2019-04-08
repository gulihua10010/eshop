package cn.jianwoo.eshop.webconfig.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.manage.entity.WebConfig;
import cn.jianwoo.eshop.manage.mapper.WebConfigMapper;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebConfigServiceImpl  implements WebConfigService {
    @Autowired
    WebConfigMapper webConfigMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public EShopResult insert(WebConfig webConfig) {
        if (webConfig!=null){
            webConfig.setStatus(1);
            webConfigMapper.insert(webConfig);
            redisTemplate.opsForValue().set("WebConifg_all",webConfigMapper.geWebConfiglist());
            redisTemplate.opsForValue().set("WebConifg_all_ON",webConfigMapper.geWebConfiglistAndOn());
            redisTemplate.expire("WebConifg_all",24,TimeUnit.HOURS);
            redisTemplate.expire("WebConifg_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult update(WebConfig webConfig) {
        if (webConfig!=null){
            webConfigMapper.update(webConfig);
            redisTemplate.opsForValue().set("WebConifg_all",webConfigMapper.geWebConfiglist());
            redisTemplate.opsForValue().set("WebConifg_all_ON",webConfigMapper.geWebConfiglistAndOn());
            redisTemplate.expire("WebConifg_all",24,TimeUnit.HOURS);
            redisTemplate.expire("WebConifg_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult deleteAll() {
//        if (webConfig!=null){
            webConfigMapper.deleteAll();
            redisTemplate.opsForValue().set("WebConifg_all",webConfigMapper.geWebConfiglist());
            redisTemplate.opsForValue().set("WebConifg_all_ON",webConfigMapper.geWebConfiglistAndOn());
            redisTemplate.expire("WebConifg_all",24,TimeUnit.HOURS);
            redisTemplate.expire("WebConifg_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
//        }
//        return EShopResult.error("不能为空");
    }

    @Override
    public List<WebConfig> geWebConfiglist() {
        try {
            List<WebConfig> webConfigs= (List<WebConfig>) redisTemplate.opsForValue().get("WebConifg_all");
            if (webConfigs!=null){
                return  webConfigs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<WebConfig> webConfigs=webConfigMapper.geWebConfiglist();
        redisTemplate.opsForValue().set("WebConifg_all",webConfigs);
        redisTemplate.expire("WebConifg_all",24,TimeUnit.HOURS);
        return webConfigs;
    }

    @Override
    public List<WebConfig> geWebConfiglistAndOn() {
        try {
            List<WebConfig> webConfigs= (List<WebConfig>) redisTemplate.opsForValue().get("WebConifg_all_ON");
            if (webConfigs!=null){
                return  webConfigs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<WebConfig> webConfigs=webConfigMapper.geWebConfiglistAndOn();
        redisTemplate.opsForValue().set("WebConifg_all_ON",webConfigs);
        redisTemplate.expire("WebConifg_all_ON",24,TimeUnit.HOURS);
        return webConfigs;
    }

    @Override
    public WebConfig getWebConfigById(Integer id) {
        return webConfigMapper.getWebConfigById(id);
    }
}
