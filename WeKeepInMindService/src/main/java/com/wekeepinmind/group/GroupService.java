package org.wekeepinmind.group;

import com.wekeepinmind.dao.group.Group;

import java.util.Optional;

public interface GroupService {

    Optional<Group> getGroupByGroupId(String groupId);

}
