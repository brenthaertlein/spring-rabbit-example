package com.nodemules.spring.example.rabbit.api;

import com.nodemules.spring.example.rabbit.bean.HelloMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brent
 * @since 12/2/17.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

  private RabbitTemplate rabbitTemplate;

  @Autowired
  public MessageController(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @RequestMapping("/drop")
  public void dropMessage(@RequestParam String name) {
    rabbitTemplate.convertAndSend(new HelloMessage(name));
  }
}
