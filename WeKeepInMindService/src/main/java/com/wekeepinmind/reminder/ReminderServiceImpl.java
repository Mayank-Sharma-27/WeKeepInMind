package com.wekeepinmind.reminder;

import com.wekeepinmind.dao.reminder.Reminder;
import com.wekeepinmind.dao.reminder.ReminderDAO;
import com.wekeepinmind.dao.reminder.UserToReminderMapping;
import com.wekeepinmind.dao.reminder.UserToReminderMappingDAO;
import com.wekeepinmind.dao.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderDAO reminderDAO;

    private final UserToReminderMappingDAO userToReminderMappingDAO;

    @Override
    public void saveReminder(Reminder reminder) {
        List<User> usersToInform = reminder.getReminderUsers();

        for (User user : usersToInform) {

            UserToReminderMapping userToReminderMapping = userToReminderMappingDAO.get(user.getUserId());

            if (userToReminderMapping == null){
                userToReminderMapping = new UserToReminderMapping(user.getUserId(), asList(reminder));
            } else {
                List<Reminder> currentReminders = userToReminderMapping.getUpcomingReminders();
                currentReminders.add(reminder);
            }
            userToReminderMappingDAO.save(userToReminderMapping);


        }

        reminderDAO.saveReminder(reminder);
    }

    @Override
    public List<Reminder> getActiveRemindersForUser(String userId) {
        UserToReminderMapping userToReminderMapping = userToReminderMappingDAO.get(userId);

        if (userToReminderMapping == null) {
            return Collections.emptyList();
        }
       return userToReminderMapping.getUpcomingReminders();
    }

    @Override
    public List<Reminder> getActiveRemindersForGroup(String groupId) {
        return reminderDAO.getReminderByGroupId(groupId);
    }

    @Override
    public void updateReminder(Reminder reminder) {
// TODO
    }
}
