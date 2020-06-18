package com.app.library.config;

import com.app.library.models.UserRequest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(value = "spring.email")
public class MailingConfig {

    private String apiKey;
    private String baseUrl;
    private String domain;

    @SneakyThrows
    public JsonNode sendSimpleMessage(UserRequest userRequest) {
        HttpResponse<JsonNode> request = Unirest.post(baseUrl + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "admin@backendpty.co.za")
                .queryString("to", userRequest.getEmail())
                .queryString("subject", "Welcome")
                .queryString("text", "Hi " + userRequest.getUsername() + ", \nWe would like to welcome you to our app." +
                        "\n\nRegards\nLibrary Team")
                .asJson();
        return request.getBody();
    }
}
