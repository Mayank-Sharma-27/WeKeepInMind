package com.wekeepinmind.dao.reminder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.wekeepinmind.clients.dynamodb.DynamoDBMapperService;

import java.util.List;
import java.util.Optional;

import static com.wekeepinmind.dao.reminder.Reminder.GROUP_ID_INDEX;

@Repository
@AllArgsConstructor
public class ReminderDAOImpl implements ReminderDAO {

    private final DynamoDBMapperService dynamoDBMapperService;

    @Override
    public void saveReminder(Reminder reminder) {
        dynamoDBMapperService.saveItem(reminder);
    }

    @Override
    public List<Reminder> getReminderByGroupId(String groupId) {
        return dynamoDBMapperService.getItemsByIndex(GROUP_ID_INDEX, "groupId", groupId,Reminder.class);

    }

    @Override
    public Optional<Reminder> getReminder(String reminderId) {
        return Optional.ofNullable(dynamoDBMapperService.getItemByPrimaryKey(reminderId, Reminder.class));
    }
}
