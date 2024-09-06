package com.wekeepinmid.controller.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class GetSentReminderByUser {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetSentReminderByUserResponse {
        private String reminderMessage;
        private LocalDateTime reminderTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetSentReminderByUserRequest {
        private int userId;
    }
}
