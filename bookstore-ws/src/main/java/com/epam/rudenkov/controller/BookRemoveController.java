package com.epam.rudenkov.controller;

import com.epam.rudenkov.service.intf.BuyerServiceInterface;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sergei-rudenkov on 28.6.16.
 */
public class BookRemoveController extends HttpServlet {

    @EJB(name = "BuyerServiceEJB")
    public BuyerServiceInterface buyerService;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        buyerService.removeBook(Long.parseLong(request.getParameter("id")));
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("books.jsp");
        request.setAttribute("books", buyerService.listAllBooks());
        view.forward(request, response);
    }
}
