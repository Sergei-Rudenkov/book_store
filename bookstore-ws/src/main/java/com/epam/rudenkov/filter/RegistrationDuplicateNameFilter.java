package com.epam.rudenkov.filter;

import com.epam.rudenkov.model.User;
import com.epam.rudenkov.service.intf.UserServiceInterface;

import javax.ejb.EJB;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by sergei-rudenkov on 13.7.16.
 */
public class RegistrationDuplicateNameFilter implements Filter {

    @EJB(name = "UserServiceEJB")
    UserServiceInterface userService;

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        User user = userService.getUserByName(req.getParameter("username"));
        if(user != null){
            RequestDispatcher view = req.getRequestDispatcher("registration.jsp");
            req.setAttribute("error", String.format("User '%s' already exist", req.getParameter("username")));
            view.forward(req, resp);
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }
}
