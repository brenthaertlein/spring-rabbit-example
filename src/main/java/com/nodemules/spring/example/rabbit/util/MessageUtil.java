package com.nodemules.spring.example.rabbit.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;

/**
 * @author brent
 * @since 12/2/17.
 */
@Slf4j
public final class MessageUtil {

  private MessageUtil() {
    /* empty private constructor */
  }

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> T parse(Message message, Class<T> type) {
    T parsed = null;
    try {
      parsed = objectMapper.readValue(message.getBody(), type);
    } catch (IOException e) {
      log.error("Error parsing HelloMessage: {}", e.getMessage());
    }
    return parsed;
  }
}
