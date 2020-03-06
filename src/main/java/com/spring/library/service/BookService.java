package com.spring.library.service;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
import com.spring.library.domain.Writer;
import com.spring.library.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private BookRepo bookRepo;


    public List<Book> getBookList() {
        return bookRepo.findAll();
    }

    public void addNewBook(Book book, MultipartFile posterFile, Date publicationDate,
                           Writer writer, Set<String> genresSet) throws IOException {
        setBookPosterFile(book, posterFile);
        setBookGenres(book, genresSet);
        setBookWriter(book, writer);
        book.setPublicationDate(publicationDate);
        bookRepo.save(book);
    }

    private void setBookPosterFile(Book book, MultipartFile posterFile) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + posterFile.getOriginalFilename();

        posterFile.transferTo(new File(uploadPath + "/" + resultFilename));

        book.setFilename(resultFilename);
    }

    private void setBookGenres(Book book, Set<String> genresFromForm) {
        for (String genreKey : genresFromForm) {
            book.getGenres().add(Genre.valueOf(genreKey));
        }
    }

    private void setBookWriter(Book book, Writer writer) {
        book.setWriter(writer);
        writer.getBooks().add(book);
    }


}
