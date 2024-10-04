package com.wekeepinmind.controller.reminder;

import com.wekeepinmind.dao.reminder.Reminder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.wekeepinmind.reminder.ReminderService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GetReminderController {

    private final ReminderService reminderService;

    @GetMapping(value = "/get-by-user")
    public GetRemindersResponse getRemindersByUser(@RequestParam("userId") String userId){
        List<Reminder> upcomingReminders = reminderService.getActiveRemindersForUser(userId);
        List<ReminderView> reminderViews = upcomingReminders
                .stream()
                .map(reminder -> new ReminderView(reminder.getReminderMessage(),
                        reminder.getReminderSenderUser().getUserName(),
                        reminder.getReminderDateTime()))
                .collect(Collectors.toList());
        return new GetRemindersResponse(reminderViews, "", 200);
    }

   @GetMapping(value = "/get-group-reminders")
   public GetRemindersResponse getRemindersForTheGroup(@RequestParam("groupId") String groupId){
        List<Reminder> upcomingGroupReminders = reminderService.getActiveRemindersForGroup(groupId);

        List<ReminderView> reminderViews = upcomingGroupReminders
                .stream()
                .map(reminder -> new ReminderView(reminder.getReminderMessage(),
                        reminder.getReminderSenderUser().getUserName(),
                        reminder.getReminderDateTime()))
                .collect(Collectors.toList());
       return new GetRemindersResponse(reminderViews, "", 200);
   }

    @GetMapping(value = "/get-sent-reminders")
    public GetRemindersResponse getRemindersSentByUser(@RequestParam("userId") String userId){
        List<Reminder> upcomingGroupReminders = reminderService.getRemindersSentByUser(userId);

        List<ReminderView> reminderViews = upcomingGroupReminders
                .stream()
                .map(reminder -> new ReminderView(reminder.getReminderMessage(),
                        reminder.getReminderSenderUser().getUserName(),
                        reminder.getReminderDateTime()))
                .collect(Collectors.toList());
        return new GetRemindersResponse(reminderViews, "", 200);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetRemindersResponse {
        private List<ReminderView> reminders;
        private String message;
        private int status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReminderView {
        private String reminderMessage;
        private String reminderSenderUserName;
        private LocalDateTime reminderDateTime;
    }


}
