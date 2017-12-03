package com.nodemules.spring.example.rabbit.consumer;

import com.nodemules.spring.example.rabbit.bean.HelloMessage;
import com.nodemules.spring.example.rabbit.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author brent
 * @since 12/2/17.
 */
@Slf4j
@Component
public class HelloMessageConsumer implements MessageListener {

  @Override
  public void onMessage(Message message) {
    HelloMessage helloMessage = MessageUtil.parse(message, HelloMessage.class);
    if (helloMessage == null) {
      log.error("Unable to process message: {}", message);
      return;
    }
    log.info("Hello {}!", helloMessage.getName());
  }
}
