package com.wekeepinmind.controller.group;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.group.GroupService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class CreateGroupController {

    private final GroupService groupService;

    @PostMapping(value = "/create-group")
    public CreateGroupResponse createGroup(@RequestBody CreateGroupRequest createGroupRequest) {
        Group group = new Group("1",
                createGroupRequest.getGroupName(),
                createGroupRequest.getAdminName(),
                false,
                1,
                Collections.emptyList(),
                createGroupRequest.getMaximumNumberOfUsers());
        groupService.saveGroup(group);
        return new CreateGroupResponse("GROUP_CREATED", 200);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateGroupResponse {
        private String message;
        private int status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateGroupRequest {
        private String groupName;
        private String adminName;
        private int maximumNumberOfUsers;
    }

}
