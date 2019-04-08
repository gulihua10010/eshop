package cn.jianwoo.eshop.item.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
//@EnableDubbo(scanBasePackages="cn.jianwoo.eshop")
@ImportResource(locations="classpath:provider.xml")
@MapperScan(value = "cn.jianwoo.eshop.manage.mapper")
public class ServiceItemApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServiceItemApplication.class,args);
//        new SpringApplicationBuilder().sources(ServiceItemApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
    }
}
