package com.wekeepinmind.dao.user;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBHashKey
    private String userId;

    @DynamoDBAttribute
    private String userEmail;

    @DynamoDBAttribute
    private String userName;

    @DynamoDBAttribute
    private String isVerified;

    @DynamoDBAttribute
    private boolean isActive;

}
