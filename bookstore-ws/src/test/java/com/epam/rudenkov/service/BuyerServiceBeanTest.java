package com.epam.rudenkov.service;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.model.User;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import com.epam.rudenkov.service.intf.UserServiceInterface;
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
public class BuyerServiceBeanTest {

    private EntityManager entityManager;
    private UserServiceInterface userServiceBean;
    private BuyerServiceInterface buyerServiceBean;

    @Before
    public void precondition() {
        entityManager = Mockito.mock(EntityManager.class);
        userServiceBean = Mockito.mock(UserServiceInterface.class);
        buyerServiceBean = new BuyerServiceBean();
        Whitebox.setInternalState(buyerServiceBean, "entityManager", entityManager);
        Whitebox.setInternalState(buyerServiceBean, "userService", userServiceBean);
    }

    @Test
    public void updateBookTest(){
        when(entityManager.find(Book.class, 1L)).thenReturn(new Book());
        buyerServiceBean.updateBook(1L, new Book());
        Mockito.verify(entityManager, Mockito.times(1)).persist(any(User.class));
    }
}
