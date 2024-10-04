package com.wekeepinmind.group;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.dao.group.GroupDAO;
import com.wekeepinmind.dao.user.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;

    private final UserDAO userDAO;

    @Override
    public Optional<Group> getGroupByGroupId(String groupId) {
        return groupDAO.getGroupByGroupId(groupId);
    }

    @Override
    public void createGroup(Group group) {
        String userId = group.getAdminUser();

        User user = userDAO.getUserByUserId(userId);
        List<String> groups = user.getGroupIds();

        if(groups == null){
            groups = new ArrayList<>();
        }
        groups.add(group.getGroupId());
        user.setGroupIds(groups);
        userDAO.saveUser(user);

        groupDAO.saveGroup(group);
    }

    @Override
    public void saveGroup(Group group) {

        groupDAO.saveGroup(group);
    }
}
