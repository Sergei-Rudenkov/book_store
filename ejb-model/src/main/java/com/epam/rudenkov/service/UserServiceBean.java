package com.epam.rudenkov.service;

import com.epam.rudenkov.model.User;
import com.epam.rudenkov.service.intf.UserServiceInterface;
import org.apache.log4j.Logger;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sergei-rudenkov on 29.6.16.
 */
@Stateless(name = "UserServiceEJB")
public class UserServiceBean implements UserServiceInterface {

    final static Logger logger = Logger.getLogger(UserServiceBean.class);

    @PersistenceContext(unitName = "book-unit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Override
    public List listAllUsers() {
        Query query = entityManager.createQuery("SELECT U FROM User U");
        return query.getResultList();
    }

    @Override
    public User getUserByName(String name) {
        logger.info("getUserByName() method was called");
        Query query = entityManager.createQuery("SELECT object(U) FROM User U WHERE U.name = :name", User.class);
        query.setParameter("name", name);
        if (!query.getResultList().isEmpty()) {
            for (User u : (List<User>) query.getResultList()) {
                u.getBooks().size();
            }
            return (User) query.getResultList().get(0);
        }
        return null;
    }
}
