package cn.jianwoo.eshop.home.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
@EnableCaching
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
