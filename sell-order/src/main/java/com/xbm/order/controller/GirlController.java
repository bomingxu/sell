package com.xbm.order.controller;


import com.xbm.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/girlController")
public class GirlController {

    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/girInfo")
    public String girlInfo(){
        return "name:"+girlConfig.getName()+",age:"+girlConfig.getAge();
    }

}
