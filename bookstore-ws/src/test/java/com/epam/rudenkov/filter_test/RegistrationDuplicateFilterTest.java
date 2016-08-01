package com.epam.rudenkov.filter_test;

import com.epam.rudenkov.filter.RegistrationDuplicateNameFilter;
import com.epam.rudenkov.model.User;
import com.epam.rudenkov.service.intf.UserServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sergei-rudenkov on 20.7.16.
 */
public class RegistrationDuplicateFilterTest {

    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private UserServiceInterface userServiceInterface;
    private RegistrationDuplicateNameFilter registrationDuplicateNameFilter;
    private FilterChain filterChain;
    private static final String NAME = "Рудзянкоў";

    @Before
    public void precondition(){
        filterChain = Mockito.mock(FilterChain.class);
        testRequest = Mockito.mock(HttpServletRequest.class);
        testResponse = Mockito.mock(HttpServletResponse.class);
        userServiceInterface = Mockito.mock(UserServiceInterface.class);
        registrationDuplicateNameFilter = new RegistrationDuplicateNameFilter();
        Whitebox.setInternalState(registrationDuplicateNameFilter, "userService", userServiceInterface);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(testRequest.getParameter("username")).thenReturn(NAME);
        when(userServiceInterface.getUserByName(NAME)).thenReturn(new User());
        when(testRequest.getRequestDispatcher("registration.jsp")).thenReturn(new RequestDispatcher() {
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
            public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
        });
        registrationDuplicateNameFilter.doFilter(testRequest, testResponse, filterChain);
        verify(testRequest, times(1)).getRequestDispatcher("registration.jsp");
    }
}
