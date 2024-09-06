package com.wekeepinmind.dao.reminder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.wekeepinmind.dao.dynamodb.DynamoDBMapperService;

@Repository
@RequiredArgsConstructor
public class UserToReminderMappingImpl implements UserToReminderMappingDAO {

    private final DynamoDBMapperService dynamoDBMapperService;

    @Override
    public UserToReminderMapping get(String userId) {
        return dynamoDBMapperService.getItemByPrimaryKey(userId, UserToReminderMapping.class);
    }

    @Override
    public void save(UserToReminderMapping userToReminderMapping) {
        dynamoDBMapperService.saveItem(userToReminderMapping);
    }
}
