package com.spring.library.controller;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
import com.spring.library.domain.Writer;
import com.spring.library.service.BookService;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;


    @GetMapping("books")
    public String getBookList(Model model) {
        model.addAttribute("books", bookService.getBookList());
        return "book/bookList";
    }

    @GetMapping("books/{book:[\\d]+}")
    public String getBookPage(@PathVariable Book book, Model model) {
        ControllerUtils.isBookExists(book);

        model.addAttribute("book", book);
        return "book/bookPage";
    }


    @GetMapping("books/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getBookAddPage(Model model) {
        model.addAttribute("genres", Genre.values());
        model.addAttribute("writers", writerService.getWriterList());
        return "book/bookAdd";
    }

    @PostMapping("books")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewBook(
            @Valid Book book,
            BindingResult bindingResult,
            Model model,
            @RequestParam Map<String, String> form,
            @RequestParam(name = "selectedWriter", required = false) Writer writer,
            @RequestParam(name = "posterFile") MultipartFile posterFile
    ) {
        Set<Genre> selectedGenres = bookService.getSelectedGenresFromForm(form);
        boolean isCorrectGenres = !selectedGenres.isEmpty();
        if (!isCorrectGenres) {
            model.addAttribute("genresError", "Please, select a book genres");
        }

        boolean isWriterSelected = writer != null;
        if (isWriterSelected) {
            model.addAttribute("selectedWriter", writer);
        } else {
            model.addAttribute("selectedWriterError", "Please, select an author or create a new one");
        }

        boolean isPublicationDateSelected = book.getPublicationDate() != null;
        if (!isPublicationDateSelected) {
            model.addAttribute("publicationDateError", "Please, select the publication date");
        }

        boolean isCorrectPoster = !StringUtils.isEmpty(posterFile.getOriginalFilename()) && bookService.isImage(posterFile);
        String posterFilename = "";

        if (isCorrectPoster) {
            posterFilename = bookService.getPosterFilename(posterFile);
        } else {
            model.addAttribute("posterFileError", "There are must be correct poster file");
        }

        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        book.setGenres(selectedGenres);
        book.setWriter(writer);
        book.setFilename(posterFilename);
        model.addAttribute("book", book);

        boolean anyError = !isCorrectGenres || !isWriterSelected || !isPublicationDateSelected ||
                !isCorrectPoster || isBindingResultHasErrors;
        if (!anyError) {
            try {
                bookService.loadPosterFile(posterFile, posterFilename);

                if (bookService.addNewBook(book)) {
                    return "redirect:/books";
                } else {
                    model.addAttribute("bookError", "Book already exists");
                }
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("posterFileError", "Incorrect file");
            }
        }

        return getBookAddPage(model);
    }
}
