package com.spring.library.controller;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
import com.spring.library.domain.Writer;
import com.spring.library.service.BookService;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/books")
public class BookController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;


    @GetMapping
    public String getBookList(Model model) {
        model.addAttribute("books", bookService.getBookList());
        return "bookList";
    }

    @GetMapping("{book}")
    public String getBookPage(@PathVariable Book book, Model model) {
        model.addAttribute("book", book);
        return "book";
    }


    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getBookAddPage(Model model) {
        model.addAttribute("genres", Genre.values());
        model.addAttribute("writers", writerService.getWriterList());
        return "bookAdd";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewBook(
            @Valid Book book,
            BindingResult bindingResult,
            Model model,
            @RequestParam Map<String, String> form,
            @RequestParam(name = "selectedWriter", required = false) Writer writer
    ) throws IOException {
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

//        boolean isCorrectPoster (request param multipart file!!!)
        /*if (isNotPoster) {
            model.addAttribute("posterFileError", "There are must be poster file");
            isAnyError = true;

        }*/

        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        boolean noError = isCorrectGenres && isWriterSelected && isPublicationDateSelected
                && !isBindingResultHasErrors;

        if (noError) {
//            bookService.addNewBook(book, posterFile, publicationDate, writer, genresSet);

//            return "redirect:/books";
        }

        book.setGenres(selectedGenres);
        book.setWriter(writer);
        model.addAttribute("book", book);

        return getBookAddPage(model);
    }
}
