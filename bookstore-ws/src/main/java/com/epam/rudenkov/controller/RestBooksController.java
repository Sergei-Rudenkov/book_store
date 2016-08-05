package com.epam.rudenkov.controller;
import com.epam.rudenkov.model.Book;
import com.epam.rudenkov.service.intf.BuyerServiceInterface;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by sergei-rudenkov on 4.8.16.
 */
@Path("/books_json")
@Stateless
public class RestBooksController {

    @EJB(name = "BuyerServiceEJB")
    private BuyerServiceInterface buyerService;

    @GET
    @Produces("application/json")
    @Path("/get")
    public List<Book> get() {
        return buyerService.listAllBooks();
    }

//    @POST
//    @Produces("application/json")
//    public List<Book> post(String params) {
//        for (String paramValueRow : params.split("&")){
//            buyerService.buyBook(paramValueRow.split("=")[1],"rudikSergei");
//        }
//        return get();
//    }

    @POST
    @Produces("application/json")
    public List<Book> post(List<Book> books) {
        for (Book book : books){
            buyerService.buyBook(String.valueOf(book.getBook_id()), "rudikSergei");
        }
        return get();
    }

    @OPTIONS
    @Produces("application/json")
    public void post() {

    }

}