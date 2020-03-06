package com.spring.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "writer")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
