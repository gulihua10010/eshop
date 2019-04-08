package cn.jianwoo.eshop.sso.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDubbo
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
@ImportResource(value = "classpath:consumer.xml")
public class WebSsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSsoApplication.class,args);
    }
}
