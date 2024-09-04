package com.wekeepinmind.dao.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.wekeepinmind.clients.dynamodb.DynamoDBMapperService;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupDAOImpl implements GroupDAO {

    private final DynamoDBMapperService dynamoDBMapperService;

    @Override
    public Optional<Group> getGroupByGroupId(final String groupId) {
        return Optional.ofNullable(dynamoDBMapperService.getItemByPrimaryKey(groupId, Group.class));
    }

    @Override
    public void saveGroup(Group group) {
        dynamoDBMapperService.saveItem(group);
    }

}
