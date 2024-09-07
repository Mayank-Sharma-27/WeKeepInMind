package com.wekeepinmind.dao.subscriptions;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.wekeepinmind.dao.reminder.Reminder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Subscriptions")
public class Subscriptions {

    @DynamoDBHashKey
    private String groupId;

    @DynamoDBAttribute
    private boolean isTrialVersion;

    @DynamoDBTypeConverted(converter = Reminder.LocalDateTimeConverter.class)
    private LocalDateTime expirationTime;

    @DynamoDBTypeConverted(converter = Reminder.LocalDateTimeConverter.class)
    private LocalDateTime subscriptionStartTime;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName="Frequency")
    private Frequency frequency;

    @DynamoDBAttribute
    private int subscriptionValue;

    @DynamoDBAttribute
    private boolean isActive;

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

    public enum Frequency {
        MONTHLY,
        YEARLY,
        WEEKLY
    }

}
