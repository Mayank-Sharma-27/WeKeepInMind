package com.wekeepinmind.dao.group;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.wekeepinmind.dao.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@DynamoDBTable(tableName = "Groups")
public class Group {

    @DynamoDBHashKey
    private String groupId;

    @DynamoDBAttribute
    private String groupName;

    @DynamoDBAttribute
    private String adminUser;

    @DynamoDBAttribute
    private boolean isActive;

    @DynamoDBAttribute
    private int numberOfUsers;

    @DynamoDBAttribute
    private List<User> groupUsers;

    @DynamoDBAttribute
    private int maximumNumberOfAllowedUsers;

}

