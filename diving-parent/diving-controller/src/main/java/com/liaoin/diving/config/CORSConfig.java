package com.liaoin.diving.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 使用Filter处理跨域请求，即跨域资源共享（Cross-origin resource sharing）
 */
@Configuration
public class CORSConfig {

    @Bean
    public FilterRegistrationBean corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域访问的域名
        config.addAllowedOrigin("*");
        // 允许请求头
        config.addAllowedHeader("*");
        // 允许请求方法
        config.addAllowedMethod("*");
        // 预检请求的有效期，单位为秒
        config.setMaxAge(3600L);
        // 是否支持安全证书
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
