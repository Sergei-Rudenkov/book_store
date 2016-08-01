package com.epam.rudenkov.service.intf;

import com.epam.rudenkov.model.Book;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by sergei-rudenkov on 28.6.16.
 */
@Local
public interface BuyerServiceInterface {
    List listAllBooks();
    void addNewBook(Book book);
    void removeBook(long id);
    void updateBook(long id, Book book);
    int buyBook(String paramValue, String userName);
}
