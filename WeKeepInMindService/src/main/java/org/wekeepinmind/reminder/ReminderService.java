package org.wekeepinmind.reminder;

import com.wekeepinmind.dao.reminder.Reminder;

import java.util.List;

public interface ReminderService {

    void saveReminder(Reminder reminder);

    List<Reminder> getRemindersForUser(String userId);

    void updateReminder(Reminder reminder);

}
