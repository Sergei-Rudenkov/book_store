package com.epam.rudenkov.controller_test;

import com.epam.rudenkov.controller.BookAddController;
import com.epam.rudenkov.model.Author;
import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.UserServiceBean;
import com.epam.rudenkov.service.intf.AuthorServiceInterface;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import org.apache.log4j.Logger;
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

import static org.mockito.Mockito.*;


/**
 * Created by sergei-rudenkov on 19.7.16.
 */
public class BookAddControllerTest {
    private Book testBook = new Book();
    private Author testAuthor = new Author();
    private BookAddController bookAddController;
    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private BuyerServiceInterface buyerServiceInterface;
    private AuthorServiceInterface authorServiceInterface;

    @Before
    public void precondition(){
        testBook.setName("Возера Радасці");
        testAuthor.setAuthor_id(1L);
        testBook.setAuthor(testAuthor);
        testBook.setBought(false);
        testBook.setGenre("postmodernism");
        testBook.setPrice(15);
        bookAddController = new BookAddController();
        testRequest = Mockito.mock(HttpServletRequest.class);
        testResponse = Mockito.mock(HttpServletResponse.class);
        buyerServiceInterface = Mockito.mock(BuyerServiceInterface.class);
        authorServiceInterface = Mockito.mock(AuthorServiceInterface.class);
        Whitebox.setInternalState(bookAddController, "authorService", authorServiceInterface);
        Whitebox.setInternalState(bookAddController, "buyerService", buyerServiceInterface);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(testRequest.getParameter("name")).thenReturn("Возера Радасці");
        when(testRequest.getParameter("author_id")).thenReturn("1");
        when(testRequest.getParameter("sold")).thenReturn("false");
        when(testRequest.getParameter("genre")).thenReturn("postmodernism");
        when(testRequest.getParameter("price")).thenReturn("15");
        when(testRequest.getRequestDispatcher("books.jsp")).thenReturn(new RequestDispatcher() {
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
            public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
        });

        when(authorServiceInterface.findAuthorById(1L)).thenReturn(testAuthor);


        bookAddController.doPost(testRequest, testResponse);

        verify(buyerServiceInterface, times(1)).addNewBook(testBook);
        verify(authorServiceInterface, times(1)).findAuthorById(Long.parseLong("1"));
    }
}
