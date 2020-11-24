package com.xbm.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class SendMessage {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMessage(){
        amqpTemplate.convertAndSend("myQueue","now:"+new Date());
    }

    @Test
    public void sendComputerMessage(){
        amqpTemplate.convertAndSend("myOrder","computer","now:"+new Date());
    }
}
