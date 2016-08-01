package com.epam.rudenkov.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by sergei-rudenkov on 30.6.16.
 */
@Entity
@XmlRootElement
@Table(name = "AuthorHibernate", uniqueConstraints = @UniqueConstraint(columnNames = "author_id"))
public class Author implements Serializable {

    private static final long serialVersionUID = 1235347L;

    @Id
    private Long author_id;

    @NotNull
    @Size(min = 1, max = 255)
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
    @Column(name = "name")
    private String name;

    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Book> books;


    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
