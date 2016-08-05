package com.epam.rudenkov.service;

import com.epam.rudenkov.model.Author;
import com.epam.rudenkov.service.intf.AuthorServiceInterface;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by sergei-rudenkov on 30.6.16.
 */
@Stateless(name = "AuthorServiceEJB")
public class AuthorServiceBean implements AuthorServiceInterface {

    @PersistenceContext(unitName = "book-unit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public AuthorServiceBean() {
    }

    @Override
    public Author findAuthorById(Long id) {
        return entityManager.find(Author.class, id);
    }
}
