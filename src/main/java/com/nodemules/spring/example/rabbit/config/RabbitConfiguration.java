package com.nodemules.spring.example.rabbit.config;

import com.nodemules.spring.example.rabbit.consumer.HelloMessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author brent
 * @since 12/2/17.
 */
@Slf4j
@Configuration
public class RabbitConfiguration {

  private static final String SIMPLE_MESSAGE_QUEUE = "simple.queue.name";
  private RabbitProperties rabbitProperties;

  @Autowired
  public RabbitConfiguration(RabbitProperties rabbitProperties) {
    this.rabbitProperties = rabbitProperties;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    log.info("Connecting to rabbit: {}@{}", rabbitProperties.getUsername(),
        rabbitProperties.getHost());
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
        rabbitProperties.getHost());
    connectionFactory.setUsername(rabbitProperties.getUsername());
    connectionFactory.setPassword(rabbitProperties.getPassword());
    return connectionFactory;
  }

  @Bean
  public Queue simpleQueue() {
    return new Queue(SIMPLE_MESSAGE_QUEUE);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate template = new RabbitTemplate(connectionFactory());
    template.setRoutingKey(SIMPLE_MESSAGE_QUEUE);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }

  @Bean
  public SimpleMessageListenerContainer listenerContainer() {
    SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
    listenerContainer.setConnectionFactory(connectionFactory());
    listenerContainer.setQueues(simpleQueue());
    listenerContainer.setMessageConverter(jsonMessageConverter());
    listenerContainer.setMessageListener(new HelloMessageConsumer());
    listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
    return listenerContainer;
  }

}
