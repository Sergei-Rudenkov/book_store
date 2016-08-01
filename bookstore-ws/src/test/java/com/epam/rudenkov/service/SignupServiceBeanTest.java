package com.epam.rudenkov.service;

import com.epam.rudenkov.model.User;
import com.epam.rudenkov.model.UserRole;
import org.jboss.security.auth.spi.Util;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import javax.persistence.EntityManager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by sergei-rudenkov on 20.7.16.
 */
public class SignupServiceBeanTest {

    private EntityManager entityManager;
    private SignupServiceBean serviceBean;
    private static final String NAME = "Rudziankou";
    private static final String PLANE_PASSWORD = "1q2w3e4r";
    private static final UserRole.Role ROLE = UserRole.Role.SELLER;

    @Before
    public void precondition() {
        serviceBean = new SignupServiceBean();
        entityManager = Mockito.mock(EntityManager.class);
        Whitebox.setInternalState(serviceBean, "entityManager", entityManager);

    }

    @Test
    public void addUserWithRoleTest() {
        serviceBean.addUserWithRole(NAME, PLANE_PASSWORD, ROLE);
        User newUser = new User();
        newUser.setName(NAME);
        newUser.setPassword(Util.createPasswordHash("MD5", "base64", null, NAME, PLANE_PASSWORD));

        Mockito.verify(entityManager, Mockito.times(1)).persist(newUser);

        Mockito.verify(entityManager, Mockito.times(2)).persist(any(UserRole.class));
    }
}
