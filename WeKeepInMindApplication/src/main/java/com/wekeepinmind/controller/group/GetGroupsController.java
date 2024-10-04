package com.wekeepinmind.controller.group;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.group.GroupService;
import com.wekeepinmind.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class GetGroupsController {

    private final GroupService groupService;

    private final UserService userService;

    @GetMapping(value = "/get-groups-by-user")
    public GetGroupsByUserIdResponse getGroupsByUserId(@RequestParam("userId") String userId){
        Optional<User> user = userService.getUser(userId);

        List<String> groups = user.get().getGroupIds();

        if(groups == null || groups.isEmpty()){
            return new GetGroupsByUserIdResponse(Collections.emptyList());
        }

        List<Group> userGroups = groups.stream()
                .map(id -> groupService.getGroupByGroupId(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(toList());

        return new GetGroupsByUserIdResponse(userGroups);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetGroupsByUserIdResponse {
        private List<Group> groups;
    }

}
