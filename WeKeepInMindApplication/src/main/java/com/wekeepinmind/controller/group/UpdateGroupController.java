package com.wekeepinmind.controller.group;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.group.GroupService;
import com.wekeepinmind.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UpdateGroupController {

    private final GroupService groupService;

    private final UserService userService;

    @PostMapping(value = "/add-user-to-group")
    public AddUserToGroupResponse addUserToGroup(@RequestBody AddUserToGroupRequest request) {

        Optional<Group> group = groupService.getGroupByGroupId(request.getGroupId());

        if (group.isEmpty()) {
            return new AddUserToGroupResponse("INVALID_GROUP", 404);
        }

        int maximumMembers = group.get().getMaximumNumberOfAllowedUsers();
        int currentMembers = group.get().getNumberOfUsers();
        if (maximumMembers == currentMembers) {
            return new AddUserToGroupResponse("GROUP_FULL", 403);
        }
        Optional<User> user = userService.getUser(request.getUserId());
        if(user.isEmpty()){
            return new AddUserToGroupResponse("INVALID_USER", 403);
        }
        group.get().getGroupUsers().add(user.get());
        group.get().setNumberOfUsers(currentMembers + 1);
        groupService.saveGroup(group.get());
        return new AddUserToGroupResponse("GROUP_UPDATED", 200);
    }

    @PostMapping(value = "/remove-user-from-group")
    public RemoveUserFromGroupResponse removeUserToGroup(@RequestBody RemoveUserFromGroupRequest request) {

        Optional<Group> group = groupService.getGroupByGroupId(request.getGroupId());

        if (group.isEmpty()) {
            return new RemoveUserFromGroupResponse("INVALID_GROUP", 404);
        }

        Optional<User> user = userService.getUser(request.getUserId());
        if(user.isEmpty()){
            return new RemoveUserFromGroupResponse("INVALID_USER", 403);
        }
        group.get().getGroupUsers().remove(user.get());
        group.get().setNumberOfUsers(group.get().getNumberOfUsers() - 1);
        groupService.saveGroup(group.get());
        return new RemoveUserFromGroupResponse("GROUP_UPDATED", 200);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddUserToGroupRequest {
        private String groupId;
        private String userId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddUserToGroupResponse {
        private String message;
        private int status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RemoveUserFromGroupResponse {
        private String message;
        private int status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RemoveUserFromGroupRequest {
        private String groupId;
        private String userId;
    }
}
