package com.spring.library.controller;

import com.spring.library.domain.*;
import com.spring.library.service.BookService;
import com.spring.library.service.ReviewService;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private ReviewService reviewService;


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



    /*Book Reviews*/

    @GetMapping("/books/{book:[\\d]+}/reviews")
    public String getBookReviewsPage(@PathVariable("book") Book reviewBook, Model model) {
        ControllerUtils.isBookExists(reviewBook);

        addAssessmentsToModel(model);
        addBookAndBookReviewsToModel(model, reviewBook);
        return "review/reviewList";
    }

    @PostMapping("/books/{book:[\\d]+}/reviews")
    public String addNewReview(
            @PathVariable("book") Book reviewBook,
            @AuthenticationPrincipal User currentUser,
            @Valid Review review,
            BindingResult bindingResult,
            Model model
    ) {
        ControllerUtils.isBookExists(reviewBook);

        boolean isAssessmentSelected = isAssessmentSelected(review, model);
        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);

        if (isAssessmentSelected && !isBindingResultHasErrors) {
            review.setAuthor(currentUser);
            review.setBook(reviewBook);
            if (reviewService.addNewReview(currentUser.getId(), reviewBook.getId(), review)) {
                return "redirect:/books/" + reviewBook.getId() + "/reviews";
            } else {
                model.addAttribute("reviewError", "You have already written a review of this book");
            }
        }

        addAssessmentsToModel(model);
        addBookAndBookReviewsToModel(model, reviewBook);
        addReviewToModel(model, review);
        return "review/reviewList";
    }


    @GetMapping("/books/{book:[\\d]+}/reviews/{review:[\\d]+}")
    public String getUserReviewPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("review") Review review,
            @PathVariable("book") Book book,
            Model model
    ) {
        checkCorrectRequest(book, review, currentUser);

        addReviewToModel(model, review);
        addReviewTextAndSelectedAssessmentToModel(model, review.getText(), review.getAssessment());
        addAssessmentsToModel(model);
        return "review/reviewEditPage";
    }

    @PutMapping("/books/{book:[\\d]+}/reviews/{currentReview:[\\d]+}")
    public String editUserReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("book") Book book,
            @PathVariable("currentReview") Review currentReview,
            @Valid Review editedReview,
            BindingResult bindingResult,
            Model model
    ) {
        checkCorrectRequest(book, currentReview, currentUser);

        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);
        if (!isBindingResultHasErrors) {
            reviewService.updateUserReview(currentReview, editedReview);
            return "redirect:/books/" + book.getId() + "/reviews";
        }

        addReviewToModel(model, currentReview);
        addReviewTextAndSelectedAssessmentToModel(model, editedReview.getText(), editedReview.getAssessment());
        addAssessmentsToModel(model);
        return "review/reviewEditPage";
    }

    @DeleteMapping("/books/{book:[\\d]+}/reviews/{review:[\\d]+}")
    public String deleteReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("book") Book book,
            @PathVariable("review") Review review
    ) {
        checkCorrectRequest(book, review, currentUser);

        reviewService.deleteUserReview(review);

        return "redirect:/books/" + book.getId() + "/reviews";
    }


    private void checkCorrectRequest(Book book, Review review, User currentUser) {
        ControllerUtils.isBookExists(book);
        ControllerUtils.isReviewExists(review);
        reviewService.checkBookContainsReview(book, review);
        reviewService.checkCurrentUserRights(currentUser, review.getAuthor());
    }

    private boolean isAssessmentSelected(Review review, Model model) {
        boolean isAssessmentSelected = review.getAssessment() != null;
        if (!isAssessmentSelected) {
            model.addAttribute("assessmentError", "Please, select an assessment");
        }

        return isAssessmentSelected;
    }


    private void addBookAndBookReviewsToModel(Model model, Book reviewBook) {
        model.addAttribute("reviews", reviewService.getAllBookReviews(reviewBook.getId()));
        model.addAttribute("book", reviewBook);
    }

    private void addAssessmentsToModel(Model model) {
        model.addAttribute("assessments", Assessment.values());
    }

    private void addReviewToModel(Model model, Review review) {
        model.addAttribute("review", review);
    }

    private void addReviewTextAndSelectedAssessmentToModel(Model model, String text, Assessment assessment) {
        model.addAttribute("displayedText", text);
        model.addAttribute("selectedAssessment", assessment);
    }
}
