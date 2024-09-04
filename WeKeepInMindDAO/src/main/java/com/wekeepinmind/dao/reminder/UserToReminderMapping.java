package com.wekeepinmind.dao.reminder;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@DynamoDBTable(tableName = "UserToReminderMapping")
public class UserToReminderMapping {

    @DynamoDBHashKey
    private String userId;

    @DynamoDBAttribute
    private List<Reminder> upcomingReminders;

}
