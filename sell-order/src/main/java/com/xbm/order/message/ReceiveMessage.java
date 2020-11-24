package com.xbm.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiveMessage {

    //@RabbitListener(queues = "myQueue")
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String receiveMessage){
        log.info("receiveMessage:{}",receiveMessage);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerOrder"),
            exchange = @Exchange("myOrder"),
            key = "computer"
    ))
    public void computerOrder(String receiveMessage){
        log.info("computerOrder receiveMessage:{}",receiveMessage);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            exchange = @Exchange("myOrder"),
            key = "fruit"
    ))
    public void fruitOrder(String receiveMessage){
        log.info("fruitOrder receiveMessage:{}",receiveMessage);
    }

}
