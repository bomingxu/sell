package com.xbm.sell.user.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SellUserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellUserCenterApplication.class, args);
    }

}
