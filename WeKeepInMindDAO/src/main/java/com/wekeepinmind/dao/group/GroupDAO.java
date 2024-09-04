package com.wekeepinmind.dao.group;

import java.util.Optional;

public interface GroupDAO {

    Optional<Group> getGroupByGroupId(String groupId);

    void saveGroup(Group group);

}
