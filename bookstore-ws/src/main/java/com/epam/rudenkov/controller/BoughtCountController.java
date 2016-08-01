package com.epam.rudenkov.controller;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sergei-rudenkov on 21.7.16.
 */
public class BoughtCountController extends HttpServlet {

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<Book> bookList = buyerService.listAllBooks();
        String jsonArrayString =  "{\"books_count\":[";
        for (int i = 0; i < bookList.size(); i++){
            jsonArrayString += bookList.get(i).getUsers().size() + ",";
        }
        jsonArrayString = jsonArrayString.substring(0, jsonArrayString.length()-1);// replace last ','
        jsonArrayString += "]}";
        out.println(jsonArrayString) ;
        out.close();
    }
}
