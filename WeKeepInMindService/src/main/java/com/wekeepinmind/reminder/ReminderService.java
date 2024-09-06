package com.wekeepinmind.reminder;

import com.wekeepinmind.dao.reminder.Reminder;

import java.util.List;

public interface ReminderService {

    void saveReminder(Reminder reminder);

    List<Reminder> getActiveRemindersForUser(String userId);

    List<Reminder> getActiveRemindersForGroup(String groupId);

    void updateReminder(Reminder reminder);

}
