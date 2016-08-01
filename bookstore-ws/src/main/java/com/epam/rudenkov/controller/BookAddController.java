package com.epam.rudenkov.controller;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.AuthorServiceInterface;
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
public class BookAddController extends HttpServlet {

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    @EJB(name = "AuthorServiceEJB")
    public AuthorServiceInterface authorService;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book newBook = new Book();
        newBook.setName(request.getParameter("name"));
        newBook.setAuthor(authorService.findAuthorById(Long.parseLong(request.getParameter("author_id"))));
        newBook.setBought(Boolean.parseBoolean(request.getParameter("sold")));
        newBook.setGenre(request.getParameter("genre"));
        newBook.setPrice(Integer.parseInt(request.getParameter("price")));
        buyerService.addNewBook(newBook);
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("books.jsp");
        request.setAttribute("books", buyerService.listAllBooks());
        view.forward(request, response);
    }
}
