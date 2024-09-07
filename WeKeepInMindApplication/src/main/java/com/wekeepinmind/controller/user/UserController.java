package com.wekeepinmind.controller.user;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/google-login")
    public GoogleLoginResponse googleLogin(@RequestParam("code") String code, @RequestParam("scope") String scope, @RequestParam("authuser") String authUser, @RequestParam("prompt") String prompt) {
        log.info("Code : {}, scope : {}, prompt {}", code, scope, prompt);
        String accessToken = getAccessToken(code);
        Map<String, Object> userDetails = getProfileDetailsGoogle(accessToken);
        String email = userDetails.get("email").toString();

        Optional<User> user = userService.getUser(email);
        if (user.isPresent()) {
            return new UserController.GoogleLoginResponse("USER_EXISTS", 200);
        }
        User newUser = new User(email,
                email,
                userDetails.get("name").toString(),
                Boolean.parseBoolean(userDetails.get("verified_email").toString()),
                true);
        userService.registerNewUser(newUser);
        return new UserController.GoogleLoginResponse("USER_CREATED", 200);
    }

    private Map getProfileDetailsGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return new Gson().fromJson(response.getBody(), Map.class);
    }

    private String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/google-login");
        params.add("client_id", "999713381835-fog5jamno603lnn175g9buc15bpkfk7v.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        String response = restTemplate.postForObject(url, requestEntity, String.class);
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);

        return jsonObject.get("access_token").getAsString();
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class GoogleLoginResponse {
        private String message;
        private int statusCode;
    }
}

