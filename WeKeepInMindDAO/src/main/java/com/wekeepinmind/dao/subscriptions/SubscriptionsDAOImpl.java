package com.wekeepinmind.dao.subscriptions;

import com.wekeepinmind.dao.dynamodb.DynamoDBMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubscriptionsDAOImpl implements SubscriptionsDAO {

    private final DynamoDBMapperService dynamoDBMapperService;

    @Override
    public void saveSubscriptions(Subscriptions subscriptions) {
        dynamoDBMapperService.saveItem(subscriptions);
    }

    @Override
    public Optional<Subscriptions> getSubscription(String groupId) {
        return Optional.ofNullable(dynamoDBMapperService
                .getItemByPrimaryKey(groupId, Subscriptions.class));
    }
}
