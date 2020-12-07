package com.xbm.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.http.HttpRequestTemplate;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

//    @Autowired
//    RestTemplate restTemplate;


    /*@HystrixCommand(commandProperties = @HystrixProperty(
            name = "execution.isolation.thread.timeoutInMilliseconds",value = "4000"
    ))*/

    /*@HystrixCommand(commandProperties = {@HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")}
    )*/
    @HystrixCommand
    @GetMapping("/getProductInfo")
    public String getProductInfo(@RequestParam Integer number){
        if(number % 2 == 0){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8001/productController/listForOrder",Arrays.asList("123456"),String.class);
    }

    private String fallback(){
        return "服务器太拥挤了，请稍后再试~~";
    }

    private String defaultFallback(){
        return "默认提示：服务器太拥挤了，请稍后再试~~";
    }

}
