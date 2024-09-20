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
                new User(request.getReminderSenderUser().getUserId(), request.getReminderSenderUser().getUserEmail(), request.getReminderSenderUser().getUserName(), true, true, null),
                request.getReminderMessage(),
                LocalDateTime.now(),
                request.getReminderDateTime(),
                request.getReminderUsers()
                        .stream()
                        .map(userView -> new User(userView.getUserId(), userView.getUserEmail(), userView.getUserName(), true, true, null)
                        )
                        .collect(Collectors.toList())
                ,
                emptyList(),
                false);
        reminderService.saveReminder(reminder);

        return new SendReminderResponse("REMINDER_SENT", 200);
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


}
