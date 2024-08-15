package de.unims.acse2024.mymakler.svc.frauddetection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Map;

import de.unims.acse2024.mymakler.svc.frauddetection.service.model.UserDto;
import de.unims.acse2024.mymakler.svc.frauddetection.service.model.ViewingOfferDto;

@EnableJms
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter.setTypeIdMappings(Map.of(
        "ViewingOfferDto", ViewingOfferDto.class,
        "UserDto", UserDto.class
    ));
    return converter;
  }
}
