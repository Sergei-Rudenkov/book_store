package com.epam.rudenkov.controller;

import com.epam.rudenkov.model.UserRole;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import com.epam.rudenkov.service.intf.SignupServiceInterface;
import com.epam.rudenkov.service.intf.UserServiceInterface;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergei-rudenkov on 12.7.16.
 */
public class RegistrationController extends HttpServlet {

    @EJB(name = "SignupServiceEJB")
    private SignupServiceInterface signupService;

    @EJB(name = "UserServiceEJB")
    private UserServiceInterface userService;

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserRole.Role role = UserRole.Role.valueOf(request.getParameter("user_role"));
        signupService.addUserWithRole(username, password, role);
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("books.jsp");
        request.setAttribute("books", buyerService.listAllBooks());
        view.forward(request, response);
    }
}
