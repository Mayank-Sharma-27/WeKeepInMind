package com.wekeepinmind.controller.reminder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.reminder.Reminder;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.group.GroupService;
import com.wekeepinmind.reminder.ReminderService;
import com.wekeepinmind.utils.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RestController
@RequiredArgsConstructor
public class SendReminderController {

    private final ReminderService reminderService;

    private final GroupService groupService;

    private final String EXPO_NOTIFICATION_URL = "https://exp.host/--/api/v2/push/send";


    @PostMapping("/send-reminder")
    public SendReminderResponse sendReminder(@RequestBody SendReminderRequest request) {
        final Optional<Group> group = groupService.getGroupByGroupId(request.getGroupId());
        if (group.isEmpty()) {
            return new SendReminderResponse("INVALID_GROUP_ID", 500);
        }

        List<User> users = group.get().getGroupUsers();

        List<UserView> reminderUsers = request.getReminderUsers();

//        Optional<User> invalidUser = reminderUsers
//                .stream()
//                .filter(reminderUser -> !users.contains(reminderUser))
//                .findFirst();

//        if (invalidUser.isPresent()) {
//            return new SendReminderResponse("INVALID_USER_PRESENT", 500);
//        }

        Reminder reminder = new Reminder("2", request.getGroupId(),
                new User(request.getReminderSenderUser().getUserId(), request.getReminderSenderUser().getUserEmail(), request.getReminderSenderUser().getUserName(), true, true, null, null),
                request.getReminderMessage(),
                LocalDateTime.now(),
                request.getReminderDateTime(),
                request.getReminderUsers()
                        .stream()
                        .map(userView -> new User(userView.getUserId(), userView.getUserEmail(), userView.getUserName(), true, true, null, null)
                        )
                        .collect(Collectors.toList())
                ,
                emptyList(),
                false);
        reminderService.saveReminder(reminder);

        for (User receiver : reminder.getReminderUsers()) {
            try {
                sendExpoNotification(receiver.getExpoPushToken(), "New Reminder", "You have a new reminder: " + reminder.getReminderMessage());
            } catch (Exception e) {
                // Log the error and continue with other users
                e.printStackTrace();
            }
        }


        return new SendReminderResponse("REMINDER_SENT", 200);
    }

    private void sendExpoNotification(String expoPushToken, String title, String message) {
        RestTemplate restTemplate = new RestTemplate();
        PushNotificationRequest pushRequest = new PushNotificationRequest(expoPushToken, title, message);
        restTemplate.postForObject(EXPO_NOTIFICATION_URL, pushRequest, Void.class);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendReminderResponse {
        private String responseMessage;
        private int responseCode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SendReminderRequest {
        private String groupId;
        private UserView reminderSenderUser;
        private String reminderMessage;
        private List<UserView> reminderUsers;
        private List<UserView> reminderEditorUsers;
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime reminderDateTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserView {
        private String userId;
        private String userEmail;
        private String userName;
    }

    @Data
    @AllArgsConstructor
    public static class PushNotificationRequest {
        private String to;
        private String title;
        private String body;
    }

}
