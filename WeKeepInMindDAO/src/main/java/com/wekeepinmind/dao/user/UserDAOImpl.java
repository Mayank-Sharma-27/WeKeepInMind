package com.wekeepinmind.dao.user;

import com.wekeepinmind.dao.dynamodb.DynamoDBMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDAOImpl implements UserDAO {

    private final DynamoDBMapperService dynamoDBMapperService;

    @Override
    public void saveUser(User user) {
        dynamoDBMapperService.saveItem(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        return dynamoDBMapperService.getItemByPrimaryKey(userId, User.class);
    }
}
