package com.wekeepinmid.controller.reminder;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.group.GroupDAO;
import com.wekeepinmind.dao.reminder.Reminder;
import com.wekeepinmind.dao.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SendReminderController {

    private final GroupDAO groupDAO;


    @PostMapping("/send-reminder")
    public SendReminderResponse sendReminder(final SendReminderRequest request) {
        final Optional<Group> group = groupDAO.getGroupByGroupId(request.getGroupId());
        if (group.isEmpty()) {
            return new SendReminderResponse("INVALID_GROUP_ID", 500);
        }

        List<User> users = group.get().getGroupUsers();

        List<User> reminderUsers = request.getReminderUsers();

        Optional<User> invalidUser = reminderUsers
                .stream()
                .filter(reminderUser -> !users.contains(reminderUser))
                .findFirst();

        if(invalidUser.isPresent()){
            return new SendReminderResponse("INVALID_USER_PRESENT", 500);
        }

        Reminder reminder = new Reminder("1", request.getGroupId(),
                request.getReminderSender(),
                request.getReminderMessage(),
                LocalDateTime.now(),
                request.getReminderDateTime(),
                request.getReminderUsers(),
                request.getReminderEditorUsers(),
                false);



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
        private String reminderSender;
        private String reminderMessage;
        private List<User> reminderUsers;
        private List<User> reminderEditorUsers;
        private LocalDateTime reminderCreatedDateTime;
        private LocalDateTime reminderDateTime;

    }

}
