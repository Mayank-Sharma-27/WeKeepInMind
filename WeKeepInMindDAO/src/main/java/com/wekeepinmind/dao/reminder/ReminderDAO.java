package com.wekeepinmind.dao.reminder;

import java.util.List;
import java.util.Optional;

public interface ReminderDAO {

    void saveReminder(Reminder reminder);

    List<Reminder> getReminderByGroupId(String groupId);

    Optional<Reminder> getReminder(String reminderId);

}
