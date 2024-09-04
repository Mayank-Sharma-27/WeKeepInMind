package com.wekeepinmind.dao.reminder;

import java.util.List;

public interface UserToReminderMappingDAO {

    UserToReminderMapping get(String userId);

    void save(UserToReminderMapping userToReminderMapping);
}
