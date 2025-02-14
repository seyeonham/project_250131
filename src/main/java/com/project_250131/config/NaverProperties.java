package com.project_250131.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "naver")
public class NaverProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String loginUrl;
    private String tokenUrl;
    private String userInfoUrl;
}
