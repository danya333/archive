package com.mrv.archive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.format.DateTimeFormatter;

@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer
    jacksonObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

            @Override
            public void customize(
                    Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
                jacksonObjectMapperBuilder
                        .serializers(
                                new LocalDateTimeSerializer(
                                        DateTimeFormatter.ofPattern(dateTimeFormat)))
                        .deserializers(
                                new LocalDateTimeDeserializer(
                                        DateTimeFormatter.ofPattern(dateTimeFormat)));
            }
        };
    }
}
