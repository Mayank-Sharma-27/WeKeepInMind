package org.wekeepinmind.group;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.group.GroupDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;


    @Override
    public Optional<Group> getGroupByGroupId(String groupId) {
        return groupDAO.getGroupByGroupId(groupId);
    }
}
