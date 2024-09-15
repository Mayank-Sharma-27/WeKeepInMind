package com.wekeepinmind.reminder;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.reminder.Reminder;
import com.wekeepinmind.dao.reminder.ReminderDAO;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.dao.user.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderDAO reminderDAO;

    private final UserDAO userDAO;

    @Override
    public void saveReminder(Reminder reminder) {
        reminderDAO.saveReminder(reminder);
    }

    @Override
    public List<Reminder> getActiveRemindersForUser(String userId) {
        User user = userDAO.getUserByUserId(userId);

        List<String> groups = user.getGroupIds();

        return groups
                .stream()
                .flatMap(group -> reminderDAO.getReminderByGroupId(group)
                        .stream()
                        .filter(reminder -> reminder.getReminderUsers()
                                .stream()
                                .anyMatch(us -> us.getUserId().equals(userId))))
                .filter(reminder -> reminder.getReminderDateTime().isAfter(LocalDateTime.now()))
                .toList();

    }

    @Override
    public List<Reminder> getActiveRemindersForGroup(String groupId) {
        List<Reminder> reminders = reminderDAO.getReminderByGroupId(groupId);
        LocalDateTime currentTime = LocalDateTime.now();

        List<Reminder> upcomingReminders = reminders
                .stream()
                .filter(reminder -> reminder.getReminderDateTime().isAfter(currentTime))
                .toList();
        return upcomingReminders;

    }

    @Override
    public void updateReminder(Reminder reminder) {
// TODO
    }
}
