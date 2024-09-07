package com.wekeepinmind.user;

import com.wekeepinmind.dao.user.User;
import com.wekeepinmind.dao.user.UserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Override
    public void registerNewUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
    public Optional<User> getUser(String userId) {
        return Optional.ofNullable(userDAO.getUserByUserId(userId));
    }
}
