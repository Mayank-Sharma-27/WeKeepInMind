package com.wekeepinmid.controller.reminder;

import com.wekeepinmind.dao.reminder.Reminder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wekeepinmind.reminder.ReminderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GetReminderController {

    private final ReminderService reminderService;

    @GetMapping(value = "/get-by-user")
    public GetRemindersResponse getRemindersByUser(@RequestParam("userId") String userId){
        List<Reminder> upcomingReminders = reminderService.getActiveRemindersForUser(userId);

        return new GetRemindersResponse(upcomingReminders, "", 200);
    }

   @GetMapping
   public GetRemindersResponse getRemindersForTheGroup(@RequestParam("groupId") String groupId){
        List<Reminder> upcomingGroupReminders = reminderService.getActiveRemindersForGroup(groupId);

       return new GetRemindersResponse(upcomingGroupReminders, "", 200);
   }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetRemindersResponse {
        private List<Reminder> reminders;
        private String message;
        private int status;
    }


}
