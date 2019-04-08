package cn.jianwoo.eshop.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@EnableDubbo(scanBasePackages="cn.jianwoo.eshop")
@ImportResource(locations="classpath:provider.xml")
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
public class ServiceSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSearchApplication.class,args);
    }
}
