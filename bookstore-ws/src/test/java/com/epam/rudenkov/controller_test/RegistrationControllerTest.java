package com.epam.rudenkov.controller_test;
import com.epam.rudenkov.controller.RegistrationController;
import com.epam.rudenkov.model.UserRole;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import com.epam.rudenkov.service.intf.SignupServiceInterface;
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
public class RegistrationControllerTest {

    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private RegistrationController registrationController;
    private SignupServiceInterface signupServiceInterface;
    private BuyerServiceInterface buyerServiceInterface;
    private static final String NAME = "Рудзянкоў";
    private static final String PASSWORD = "1q2w3e4r";
    private static final String ROLE = "SELLER";

    @Before
    public void precondition(){
        signupServiceInterface = Mockito.mock(SignupServiceInterface.class);
        buyerServiceInterface = Mockito.mock(BuyerServiceInterface.class);
        registrationController = new RegistrationController();
        testRequest = Mockito.mock(HttpServletRequest.class);
        testResponse = Mockito.mock(HttpServletResponse.class);
        Whitebox.setInternalState(registrationController, "signupService", signupServiceInterface);
        Whitebox.setInternalState(registrationController, "buyerService", buyerServiceInterface);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(testRequest.getParameter("username")).thenReturn(NAME);
        when(testRequest.getParameter("password")).thenReturn(PASSWORD);
        when(testRequest.getParameter("user_role")).thenReturn(ROLE);
        when(buyerServiceInterface.listAllBooks()).thenReturn(new ArrayList());
        when(testRequest.getRequestDispatcher("books.jsp")).thenReturn(new RequestDispatcher() {
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
            public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
        });
        registrationController.doPost(testRequest, testResponse);
        verify(signupServiceInterface, times(1)).addUserWithRole(NAME, PASSWORD, UserRole.Role.SELLER);
    }
}
