package com.spring.library.repos;

import com.spring.library.domain.Book;
import com.spring.library.domain.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Book findByBookNameAndWriter(String bookName, Writer writer);
}
