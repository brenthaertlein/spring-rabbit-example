package com.nodemules.spring.example.rabbit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author brent
 * @since 12/2/17.
 */
@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq")
class RabbitProperties {

  private String username;
  private String password;
  private String host;
  private String port;
}
