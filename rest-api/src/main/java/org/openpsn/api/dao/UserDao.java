package org.openpsn.api.dao;

import org.openpsn.api.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> authenticate(String psnName, String password);

    User getUser(String psnName);
}