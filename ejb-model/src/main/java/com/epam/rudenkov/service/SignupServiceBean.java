package com.epam.rudenkov.service;

import com.epam.rudenkov.model.User;
import com.epam.rudenkov.model.UserRole;
import com.epam.rudenkov.service.intf.SignupServiceInterface;
import org.jboss.security.auth.spi.Util;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Created by sergei-rudenkov on 12.7.16.
 */
@Stateless(name = "SignupServiceEJB")
public class SignupServiceBean implements SignupServiceInterface {

    @PersistenceContext(unitName = "book-unit", type = PersistenceContextType.TRANSACTION)
    public EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addUserWithRole(String name, String planePassword, UserRole.Role role) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(Util.createPasswordHash("MD5", "base64", null, name, planePassword));
        entityManager.persist(newUser);
        UserRole userRole = new UserRole();
        userRole.setUser_id(newUser.getUser_id());
        userRole.setRole(role.name());
        entityManager.persist(userRole);
    }
}
