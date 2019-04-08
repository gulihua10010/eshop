package cn.jianwoo.eshop.home;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@EnableDubbo
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
@ImportResource(value = "classpath:consumer.xml")
@SpringBootApplication
public class WebHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebHomeApplication.class,args);
    }
}
