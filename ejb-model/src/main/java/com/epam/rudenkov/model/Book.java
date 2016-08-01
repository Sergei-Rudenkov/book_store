package com.epam.rudenkov.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by sergei-rudenkov on 28.6.16.
 */

@Entity
@XmlRootElement
@Table(name = "BookHibernate", uniqueConstraints = @UniqueConstraint(columnNames = "book_id"))
public class Book implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long book_id;

    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    @Column(name = "name")
    private String name;

    @Column(name = "bought")
    private boolean bought;

    @Column(name = "genre")
    private String genre;

    @NotNull
    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "User_BookHibernate",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "book_id", nullable = false, updatable = false) })
    private Set<User> users;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return this.author;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bought != book.bought) return false;
        if (price != book.price) return false;
        if (book_id != null ? !book_id.equals(book.book_id) : book.book_id != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) return false;
        return users != null ? users.equals(book.users) : book.users == null;
    }

    @Override
    public int hashCode() {
        int result = book_id != null ? book_id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bought ? 1 : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }


}

