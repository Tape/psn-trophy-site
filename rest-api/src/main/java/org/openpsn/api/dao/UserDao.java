package org.openpsn.api.dao;

import org.openpsn.api.model.User;

public interface UserDao {
    boolean authenticate(String psnName, String password);

    User getUser(String psnName);
}
