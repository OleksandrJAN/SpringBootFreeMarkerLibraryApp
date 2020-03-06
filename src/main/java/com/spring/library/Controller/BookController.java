package com.spring.library.Controller;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
    public String getBooksList(Model model) {
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
            @RequestParam Map<String, String> form
            /*@RequestParam("selectedWriter") Writer writer*/
            ) throws IOException {
        Set<String> allGenres = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toSet());

        Set<Genre> selectedGenres = new HashSet<>();
        for (String genreName : form.keySet()) {
            if (allGenres.contains(genreName)) {
                selectedGenres.add(Genre.valueOf(genreName));
            }
        }


        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }



        /*if (isNotPoster) {
            model.addAttribute("posterFileError", "There are must be poster file");
            isAnyError = true;

        }*/
        boolean isCorrectGenres = !selectedGenres.isEmpty();
        boolean noError = isCorrectGenres && !isBindingResultHasErrors;

        if (noError) {
//            bookService.addNewBook(book, posterFile, publicationDate, writer, genresSet);

            return "redirect:/bookList";
        }

        book.setGenres(selectedGenres);
        model.addAttribute("book", book);

        return getBookAddPage(model);
//        model.addAttribute("genres", Genre.values());
//        model.addAttribute("writers", writerService.getWriterList());
//        return "bookAdd";
    }
}
