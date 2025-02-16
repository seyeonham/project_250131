package com.project_250131.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "secret.mail")
public class MailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
