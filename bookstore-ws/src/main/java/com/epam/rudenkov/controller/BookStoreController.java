package com.epam.rudenkov.controller;

import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import com.epam.rudenkov.service.intf.UserServiceInterface;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by sergei-rudenkov on 21.6.16.
 */
@SuppressWarnings("serial")
public class BookStoreController extends HttpServlet {

    final static Logger logger = Logger.getLogger(BookStoreController.class);

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    @EJB(name = "UserServiceEJB")
    private UserServiceInterface userService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Thanks for buying books!</h1>");
        int totalPrice = 0;
        Map params = request.getParameterMap();
        Iterator uriParameter = params.keySet().iterator();
        String userName = request.getUserPrincipal().getName();

        while (uriParameter.hasNext()) {
            String key = (String) uriParameter.next();
            String paramValue = ((String[]) params.get(key))[0];
            if (key.contains("book")) {
                totalPrice += buyerService.buyBook(paramValue, userName);
            }
        }
//        out.println(String.format("Total price is: %d", totalPrice));
//        out.println(String.format("User is: %s", userName));
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("books.jsp was requested");
        RequestDispatcher view = request.getRequestDispatcher("books.jsp");
        request.setAttribute("books", buyerService.listAllBooks());
        view.forward(request, response);
    }
}
