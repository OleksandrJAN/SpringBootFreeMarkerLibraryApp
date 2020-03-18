package com.spring.library.controller;

import com.spring.library.domain.Assessment;
import com.spring.library.domain.Book;
import com.spring.library.domain.Review;
import com.spring.library.domain.User;
import com.spring.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/books/{bookId}/reviews")
    public String getBookReviewsPage(@PathVariable("bookId") Book book, Model model) {
        model.addAttribute("reviews", reviewService.getAllBookReviews(book.getId()));
        model.addAttribute("assessments", Assessment.values());
        model.addAttribute("book", book);
        return "reviewList";
    }

    @GetMapping("/users/{userId}/reviews")
    public String getUserReviewsPage(@PathVariable("userId") User user, Model model) {
        model.addAttribute("reviews", reviewService.getAllUserReviews(user.getId()));

        /*NO ASSESSMENTS*/
        model.addAttribute("assessments", Assessment.values());

        model.addAttribute("user", user);
        return "reviewList";
    }

    @PostMapping("/books/{bookId}/reviews")
    public String addNewReview(@PathVariable("bookId") Book book,
                               @AuthenticationPrincipal User user,
                               @Valid Review review,
                               BindingResult bindingResult,
                               Model model
    ) {
        boolean isAssessmentSelected = review.getAssessment() != null;
        if (isAssessmentSelected) {
            model.addAttribute("selectedAssessment", review.getAssessment());
        } else {
            model.addAttribute("assessmentError", "Please, select an assessment");
        }

        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        if (isAssessmentSelected && !isBindingResultHasErrors) {
            review.setAuthor(user);
            review.setBook(book);
            if (reviewService.addNewReview(user.getId(), book.getId(), review)) {
                return "redirect:/books/" + book.getId() + "/reviews";
            } else {
                model.addAttribute("reviewError", "You have already written a review of this book");
            }
        }

        model.addAttribute("review", review);
        return getBookReviewsPage(book, model);
    }

}
