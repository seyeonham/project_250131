package com.project_250131.user.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_250131.config.NaverProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NaverLoginService {

    private final NaverProperties naverProperties;

    public String getAccessToken(String code, String state) {
        String tokenUrl = naverProperties.getTokenUrl() +
                "?grant_type=authorization_code" +
                "&client_id=" + naverProperties.getClientId() +
                "&client_secret=" + naverProperties.getClientSecret() +
                "&code=" + code +
                "&state=" + state;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, null, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonNode.get("access_token").asText();
    }

    public Map<String, String> getUserInfo(String accessToken) {
        String url = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers); // 요청 본문 없이 헤더만 포함

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
            JsonNode responseNode = jsonNode.get("response");

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", responseNode.get("name").asText());
            userInfo.put("email", responseNode.get("email").asText());

            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
