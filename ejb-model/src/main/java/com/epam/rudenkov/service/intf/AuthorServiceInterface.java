package com.epam.rudenkov.service.intf;

import com.epam.rudenkov.model.Author;

import javax.ejb.Local;

/**
 * Created by sergei-rudenkov on 30.6.16.
 */
@Local
public interface AuthorServiceInterface {
    Author findAuthorById(Long id);
}
