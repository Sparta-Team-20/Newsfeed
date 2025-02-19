package com.example.newsfeed.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /**
     * LocalDateTime을 "yyyy-MM-dd HH:mm:ss" 형식으로 변환 (직렬화 + 역직렬화)
     * DTO에서 @JsonFormat 없이도 정상적으로 동작하도록 설정
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        // JSON 직렬화 시 LocalDateTime을 Timestamp가 아닌 문자열로 변환
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 생성자 기반 역직렬화 지원
        objectMapper.registerModule(new ParameterNamesModule());

        // LocalDateTime 직렬화 및 역직렬화 설정
        SimpleModule module = new SimpleModule();

        // LocalDateTime → String (직렬화)
        module.addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(localDateTime.format(formatter));
            }
        });

        // String → LocalDateTime (역직렬화)
        module.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException {
                return LocalDateTime.parse(jsonParser.getText(), formatter);
            }
        });

        objectMapper.registerModule(module);

        return objectMapper;
    }
}
