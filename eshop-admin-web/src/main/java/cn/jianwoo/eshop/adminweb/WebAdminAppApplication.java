package cn.jianwoo.eshop.adminweb;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.MultipartConfigElement;
@Configuration
@EnableDubbo
@SpringBootApplication
@ImportResource(value="classpath:consumer.xml")
@MapperScan(value ="cn.jianwoo.eshop.manage.mapper" )
public class WebAdminAppApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebAdminAppApplication.class);
    }
    public static void main(String[] args) throws InterruptedException {
  SpringApplication.run(WebAdminAppApplication.class,args);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
    }

    @Bean
    public MultipartConfigElement multipartConfigFactory(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize("10240MB");
        factory.setMaxRequestSize("102400MB");
        return factory.createMultipartConfig();
    }
}
