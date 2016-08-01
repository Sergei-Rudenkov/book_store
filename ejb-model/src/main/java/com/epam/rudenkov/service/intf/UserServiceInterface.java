package com.epam.rudenkov.service.intf;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by sergei-rudenkov on 29.6.16.
 */
@Local
public interface UserServiceInterface {
    List listAllUsers();
    User getUserByName(String name);
}