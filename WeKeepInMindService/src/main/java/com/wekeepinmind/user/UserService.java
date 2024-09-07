package com.wekeepinmind.user;

import com.wekeepinmind.dao.user.User;

import java.util.Optional;

public interface UserService {

    void registerNewUser(User user);

    Optional<User> getUser(String userId);

}
