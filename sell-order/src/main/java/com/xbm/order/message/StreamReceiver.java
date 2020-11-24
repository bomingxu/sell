package com.xbm.order.message;

import com.xbm.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String revceiveMessage(OrderDTO message){
        log.info("receiveMessage:{}",message);
        return "received";
    }

    @StreamListener(StreamClient.INPUT2)
    public void revceiveMessage2(String message){
        log.info("revceiveMessage2:{}",message);
    }
}
