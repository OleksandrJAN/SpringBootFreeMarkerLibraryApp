package com.spring.library.service;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
import com.spring.library.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
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


    private boolean isBookExists(Book book) {
        Book bookFromDb = bookRepo.findByBookNameAndWriter(book.getBookName(), book.getWriter());
        return bookFromDb != null;
    }

    public List<Book> getBookList() {
        return bookRepo.findAll();
    }

    public Set<Genre> getSelectedGenresFromForm(Map<String, String> form) {
        Set<String> allGenres = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toSet());

        Set<Genre> selectedGenres = new HashSet<>();
        for (String genreName : allGenres) {
            if (form.containsKey(genreName)) {
                selectedGenres.add(Genre.valueOf(genreName));
            }
        }

        return selectedGenres;
    }

    public String getPosterFilename(MultipartFile posterFile) {
        String uuidFile = UUID.randomUUID().toString();
        return uuidFile + "." + posterFile.getOriginalFilename();
    }


    public boolean addNewBook(Book book) {
        if (isBookExists(book)) {
            return false;
        }

        bookRepo.save(book);
        return true;
    }

    public void loadPosterFile(MultipartFile file, String resultFilename) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        file.transferTo(new File(uploadPath + "\\" + resultFilename));
    }

    public boolean isImage(MultipartFile file) {
        try {
            return ImageIO.read(file.getInputStream()) != null;
        } catch (IOException e) {
            return false;
        }
    }

}
