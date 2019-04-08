package cn.jianwoo.eshop.user.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations="classpath:provider.xml")
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
@SpringBootApplication
public class ServiceUserApplication  {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class,args);

    }
}
//leixing