package com.epam.rudenkov.controller;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.AuthorServiceInterface;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import org.apache.log4j.Logger;

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
public class BookUpdateController extends HttpServlet {

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    @EJB(name = "AuthorServiceEJB")
    private AuthorServiceInterface authorService;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book updatedBook = new Book();
        updatedBook.setName(request.getParameter("name"));
        updatedBook.setAuthor(authorService.findAuthorById(Long.parseLong(request.getParameter("author_id"))));
        updatedBook.setBought(Boolean.parseBoolean(request.getParameter("sold")));
        updatedBook.setGenre(request.getParameter("genre"));
        updatedBook.setPrice(Integer.parseInt(request.getParameter("price")));
        buyerService.updateBook(Long.parseLong(request.getParameter("id")), updatedBook);
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("books.jsp");
        request.setAttribute("books", buyerService.listAllBooks());
        view.forward(request, response);
    }
}
