package com.wekeepinmind.controller.user;

import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/google-login")
    public GoogleLoginResponse googleLogin(@RequestBody SaveGoogleLoginUserInfo request) {

        String email = request.getEmail();
        Optional<User> user = userService.getUser(email);
        if (user.isPresent()) {
            return new UserController.GoogleLoginResponse("USER_EXISTS", 200);
        }
        User newUser = new User(email,
                email,
                request.getName(),
                true,
                true,
                Collections.emptyList(),
                "");
        userService.registerNewUser(newUser);
        return new UserController.GoogleLoginResponse("USER_CREATED", 200);
    }

    @PostMapping("/update-push-token")
    public UpdateTokenResponse updateToken(@RequestBody UpdateTokenRequest request){
        String email = request.getEmail();
        Optional<User> user = userService.getUser(email);
        if (user.isEmpty()) {
            return new UserController.UpdateTokenResponse("USER_DOES_NOT_EXISTS", 200);
        }

        User currentUser = user.get();

        currentUser.setExpoPushToken(request.token);
        userService.updateUser(currentUser);
        return new UserController.UpdateTokenResponse("USER_UPDATED", 200);
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class UpdateTokenRequest {
        private String email;
        private String token;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class UpdateTokenResponse {
        private String message;
        private int statusCode;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class GoogleLoginResponse {
        private String message;
        private int statusCode;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class SaveGoogleLoginUserInfo {
        private String email;
        private String name;
    }
}

