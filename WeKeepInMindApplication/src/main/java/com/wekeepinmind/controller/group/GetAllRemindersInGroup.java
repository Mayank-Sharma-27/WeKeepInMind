package com.wekeepinmind.controller.group;

import com.wekeepinmind.dao.reminder.Reminder;
import com.wekeepinmind.reminder.ReminderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetAllRemindersInGroup {

    private final ReminderService reminderService;

    @GetMapping(value = "/get-upcoming-group-reminders")
    public GetAllUpcomingRemindersForGroupResponse getAllUpcomingRemindersForGroupResponse(@RequestParam("groupId") String groupId) {
        List<Reminder> reminders = reminderService.getActiveRemindersForGroup(groupId);

        return new GetAllUpcomingRemindersForGroupResponse(reminders, "");

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetAllUpcomingRemindersForGroupResponse {
        private List<Reminder> upcomingReminders;
        private String message;
    }

}
