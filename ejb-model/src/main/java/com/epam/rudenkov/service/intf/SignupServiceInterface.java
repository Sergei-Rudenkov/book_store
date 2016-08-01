package com.epam.rudenkov.service.intf;

import com.epam.rudenkov.model.UserRole;

/**
 * Created by sergei-rudenkov on 12.7.16.
 */
public interface SignupServiceInterface {
    void addUserWithRole(String name, String purePassword, UserRole.Role role);
}
