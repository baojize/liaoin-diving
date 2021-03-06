package com.liaoin.diving;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * springboot启动类
 *
 * @author 张权立
 * @date 2018/06/07
 */
@SpringBootApplication
@ServletComponentScan
@EnableJpaRepositories(basePackages = {"com.liaoin.diving.dao"})
@EnableElasticsearchRepositories(basePackages = {"com.liaoin.diving.search"})
@MapperScan({"com.liaoin.diving.mapper","com.liaoin.manager.dao.UserMapper"})
public class DivingApplication {

    public static void main(String[] args) {

        SpringApplication.run(DivingApplication.class, args);

    }
}
