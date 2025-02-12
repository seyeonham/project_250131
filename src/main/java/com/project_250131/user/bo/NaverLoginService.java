package com.project_250131.user.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverLoginService {

    private static final String CLIENT_ID = "qOmR_kGT5NAw084OznZs";
    private static final String CLIENT_SECRET = "eJpq4i3BAt";
    private static final String REDIRECT_URI = "http://localhost:80/user/naver/callback";

    public String getAccessToken(String code, String state) {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + code
                + "&state=" + state;

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
