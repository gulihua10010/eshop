package cn.jianwoo.eshop.webconfig.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
@ImportResource(locations = "classpath:provider.xml")
public class ServiceWebconfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceWebconfigApplication.class,args);
    }
}
