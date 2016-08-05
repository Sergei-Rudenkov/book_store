package com.epam.rudenkov.service;

import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;
import com.epam.rudenkov.service.intf.UserServiceInterface;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sergei-rudenkov on 28.6.16.
 */
@Stateless(name = "BuyerServiceEJB")
public class BuyerServiceBean implements BuyerServiceInterface {

    final static Logger logger = Logger.getLogger(BuyerServiceBean.class);

    @PersistenceContext(unitName = "book-unit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @EJB(name = "UserServiceEJB")
    UserServiceInterface userService;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Book> listAllBooks() {
        Query query = entityManager.createQuery("SELECT B FROM Book B");
        return query.getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addNewBook(Book book) {
        entityManager.persist(book);
        logCreation(book);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeBook(long id) {
        logger.info(String.format("Book with id '%d' going to be removed", id));
        Book book = entityManager.find(Book.class, id);
        entityManager.remove(book);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateBook(long id, Book editedBook) {
        logger.info(String.format("Book with id '%d' going to be updated", id));
        Book currentBook = entityManager.find(Book.class, id);
        currentBook.setBought(editedBook.isBought());
        currentBook.setName(editedBook.getName());
        currentBook.setAuthor(editedBook.getAuthor());
        currentBook.setGenre(editedBook.getGenre());
        currentBook.setPrice(editedBook.getPrice());
        currentBook.setBuyCount(editedBook.getBuyCount());
        entityManager.persist(currentBook);
    }

    /**
     * Establish relations between books and users
     *
     * @param paramValue
     * @return total price for selected books
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int buyBook(String paramValue, String userName){
        List<Book> books;
            books = listAllBooks();
            for (Book book : books) {
                if (book.getBook_id().toString().equals(paramValue)) {
                    logger.info(String.format("User '%s' going to buy book '%s'", userName, book.getName()));
                    book.setBuyCount(book.getBuyCount() + 1);
                    userService.getUserByName(userName).getBooks().add(book);
                    updateBook(book.getBook_id(), book);
                    entityManager.persist(book);
                    return book.getPrice();
                }
            }
        return 0;
    }

    /**
     * Just to see usage of TransactionAttributeType.SUPPORTS
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private void logCreation(Book book){
        logger.info(String.format("Book %s was added", book.getName()));
    }
}
