package com.epam.rudenkov.controller_test;

import com.epam.rudenkov.controller.BookRemoveController;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sergei-rudenkov on 19.7.16.
 */
public class BookRemoveControllerTest {

    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private BuyerServiceInterface buyerServiceInterface;
    private BookRemoveController bookRemoveController;

    @Before
    public void precondition(){
        bookRemoveController = new BookRemoveController();
        testRequest = Mockito.mock(HttpServletRequest.class);
        buyerServiceInterface = Mockito.mock(BuyerServiceInterface.class);
        Whitebox.setInternalState(bookRemoveController, "buyerService", buyerServiceInterface);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(testRequest.getParameter("id")).thenReturn("1");
        when(testRequest.getRequestDispatcher("books.jsp")).thenReturn(new RequestDispatcher() {
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
            public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
        });

        bookRemoveController.doPost(testRequest, testResponse);

        verify(buyerServiceInterface, times(1)).removeBook(1L);

    }
}
