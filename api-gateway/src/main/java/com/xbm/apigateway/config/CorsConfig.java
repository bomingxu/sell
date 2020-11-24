package com.xbm.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig  {

    @Bean
    public CorsFilter corsFilter(){

        final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        final  CorsConfiguration config = new CorsConfiguration();
        //允许cookie跨域
        config.setAllowCredentials(true);
        //允许所有的头跨域
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许所有的方法（post或get等等）跨域
        config.setAllowedMethods(Arrays.asList("*"));
        //允许所有的原始域跨域
        config.setAllowedOrigins(Arrays.asList("*"));
        //在300秒内同一跨域请求不检查
        config.setMaxAge(300L);
        corsConfigurationSource.registerCorsConfiguration("*",config);
        return new CorsFilter(corsConfigurationSource);
    }
}
