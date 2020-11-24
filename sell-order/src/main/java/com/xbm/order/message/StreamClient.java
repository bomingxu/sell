package com.xbm.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {

    String INPUT = "myMessage";

    String INPUT2 = "myMessage2";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(INPUT)
    MessageChannel output();

    @Input(INPUT2)
    SubscribableChannel input2();

    @Output(INPUT2)
    MessageChannel output2();

}
