package org.openpsn.api.service;

import lombok.RequiredArgsConstructor;
import org.openpsn.api.dao.UserDao;
import org.openpsn.api.exception.auth.UserDoesNotExistException;
import org.openpsn.api.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserService {
    private final UserDao userDao;

    public User getUser(String psnName) {
        return userDao.getUser(psnName).orElseThrow(() -> new UserDoesNotExistException(psnName));
    }
}
