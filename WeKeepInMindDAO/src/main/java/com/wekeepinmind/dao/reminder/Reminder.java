package com.wekeepinmind.dao.reminder;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.wekeepinmind.dao.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Reminders")
public class Reminder {

    public static final String GROUP_ID_INDEX = "GroupIdIndex";

    @DynamoDBHashKey
    private String reminderId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = GROUP_ID_INDEX)
    private String groupId;

    @DynamoDBAttribute
    private String reminderSenderUser;

    @DynamoDBAttribute
    private String reminderMessage;

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime reminderCreatedDateTime;

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime reminderDateTime;

    @DynamoDBAttribute
    private List<User> reminderUsers;

    @DynamoDBAttribute
    private List<User> reminderEditors;

    @DynamoDBAttribute
    private boolean isDeleted;

    public static class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert(final LocalDateTime localDateTime){
            return localDateTime.toString();
        }

        @Override
        public LocalDateTime unconvert(String string){
            return LocalDateTime.parse(string);
        }
    }

}
