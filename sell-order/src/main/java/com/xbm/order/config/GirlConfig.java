package com.xbm.order.config;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "girl")
@RefreshScope
public class GirlConfig {

    private String name;

    private String age;

}
