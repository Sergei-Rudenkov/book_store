package com.epam.rudenkov.controller_test;

import com.epam.rudenkov.controller.BookUpdateController;
import com.epam.rudenkov.model.Author;
import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.AuthorServiceInterface;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sergei-rudenkov on 20.7.16.
 */
public class UpdateBookControllerTest {
    private static final long BOOK_ID = 5L;
    private Book testBook = new Book();
    private Author testAuthor = new Author();
    private BookUpdateController bookUpdateController;
    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private BuyerServiceInterface buyerServiceInterface;
    private AuthorServiceInterface authorServiceInterface;
    private static final String BOOK_NAME = "Возера Радасці";
    private static final String GENRE = "postmodernism";
    private static final Long AUTHOR_ID = 1L;
    private static final int PRICE = 15;
    private static final boolean IS_BOUGHT = false;

    @Before
    public void precondition(){
        testBook.setName(BOOK_NAME);
        testAuthor.setAuthor_id(AUTHOR_ID);
        testBook.setAuthor(testAuthor);
        testBook.setBought(IS_BOUGHT);
        testBook.setGenre(GENRE);
        testBook.setPrice(PRICE);
        bookUpdateController = new BookUpdateController();
        testRequest = Mockito.mock(HttpServletRequest.class);
        testResponse = Mockito.mock(HttpServletResponse.class);
        buyerServiceInterface = Mockito.mock(BuyerServiceInterface.class);
        authorServiceInterface = Mockito.mock(AuthorServiceInterface.class);
        Whitebox.setInternalState(bookUpdateController, "authorService", authorServiceInterface);
        Whitebox.setInternalState(bookUpdateController, "buyerService", buyerServiceInterface);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(testRequest.getParameter("id")).thenReturn(String.valueOf(BOOK_ID));
        when(testRequest.getParameter("name")).thenReturn(BOOK_NAME);
        when(testRequest.getParameter("author_id")).thenReturn(String.valueOf(AUTHOR_ID));
        when(testRequest.getParameter("sold")).thenReturn(String.valueOf(IS_BOUGHT));
        when(testRequest.getParameter("genre")).thenReturn(GENRE);
        when(testRequest.getParameter("price")).thenReturn(String.valueOf(PRICE));
        when(testRequest.getRequestDispatcher("books.jsp")).thenReturn(new RequestDispatcher() {
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
            public void include(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {}
        });

        when(authorServiceInterface.findAuthorById(1L)).thenReturn(testAuthor);

        bookUpdateController.doPost(testRequest, testResponse);

        verify(buyerServiceInterface, times(1)).updateBook(BOOK_ID, testBook);
        verify(authorServiceInterface, times(1)).findAuthorById(AUTHOR_ID);
    }
}
