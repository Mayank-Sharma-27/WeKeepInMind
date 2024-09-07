package com.wekeepinmind.dao.user;

public interface UserDAO {

    void  saveUser(User user);

    User getUserByUserId(String userId);

}
