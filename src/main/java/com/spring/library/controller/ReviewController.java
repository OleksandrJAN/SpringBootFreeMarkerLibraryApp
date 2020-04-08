package com.spring.library.controller;

import com.spring.library.domain.Assessment;
import com.spring.library.domain.Book;
import com.spring.library.domain.Review;
import com.spring.library.domain.User;
import com.spring.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
public class ReviewController {
    private enum Url {
        Book, User
    }

    @Autowired
    private ReviewService reviewService;


    /*ALL REVIEWS*/

    @GetMapping(value = {
            "/books/{book:[\\d]+}/reviews",
            "/users/{user:[\\d]+}/reviews"
    })
    public String getBookReviewsPage(
            @PathVariable(value = "book", required = false) Book book,
            @PathVariable(value = "user", required = false) User userProfile,
            Model model
    ) {
        Url url = checkCorrectRequest(userProfile, book);

        switch (url) {
            case Book:
                model.addAttribute("book", book);
                model.addAttribute("reviews", book.getReviews());
                model.addAttribute("assessments", Assessment.values());
                model.addAttribute("reviewCardAction", "/books/" + book.getId() + "/reviews");
                break;
            case User:
                model.addAttribute("userProfile", userProfile);
                model.addAttribute("reviews", userProfile.getReviews());
                model.addAttribute("reviewCardAction", "/users/" + userProfile.getId() + "/reviews");
                break;
        }

        return "review/reviewList";
    }


    @PostMapping("/books/{book:[\\d]+}/reviews")
    public String addNewReview(
            @PathVariable("book") Book book,
            @AuthenticationPrincipal User currentUser,
            @Valid Review review,
            BindingResult bindingResult,
            Model model
    ) {
        ControllerUtils.isBookExists(book);

        boolean isAssessmentSelected = review.getAssessment() != null;
        if (!isAssessmentSelected) {
            model.addAttribute("assessmentError", "Please, select an assessment");
        }
        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);

        if (isAssessmentSelected && !isBindingResultHasErrors) {
            review.setAuthor(currentUser);
            review.setBook(book);
            if (reviewService.addNewReview(currentUser.getId(), book.getId(), review)) {
                return "redirect:/books/" + book.getId() + "/reviews";
            } else {
                model.addAttribute("reviewError", "You have already written a review of this book");
            }
        }

        model.addAttribute("book", book);
        model.addAttribute("reviews", book.getReviews());
        model.addAttribute("assessments", Assessment.values());
        model.addAttribute("reviewCardAction", "/books/" + book.getId() + "/reviews");
        model.addAttribute("review", review);
        return "review/reviewList";
    }


    /*CURRENT REVIEW*/

    @GetMapping(value = {
            "/books/{book:[\\d]+}/reviews/{review:[\\d]+}",
            "/users/{user:[\\d]+}/reviews/{review:[\\d]+}"
    })
    public String getReviewPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(value = "book", required = false) Book book,
            @PathVariable(value = "user", required = false) User userProfile,
            @PathVariable("review") Review review,
            Model model
    ) {
        Url url = checkCorrectRequest(review, userProfile, book, currentUser);

        addReviewActionToModel(url, review, model);
        model.addAttribute("review", review);
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewEditPage";
    }


    @PutMapping(value = {
            "/books/{book:[\\d]+}/reviews/{currentReview:[\\d]+}",
            "/users/{user:[\\d]+}/reviews/{currentReview:[\\d]+}"
    })
    public String editUserReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(value = "book", required = false) Book book,
            @PathVariable(value = "user", required = false) User userProfile,
            @PathVariable("currentReview") Review currentReview,
            @Valid Review editedReview,
            BindingResult bindingResult,
            Model model
    ) {
        Url url = checkCorrectRequest(currentReview, userProfile, book, currentUser);

        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);
        if (!isBindingResultHasErrors) {
            reviewService.updateUserReview(currentReview, editedReview);
            switch (url) {
                case Book:
                    return "redirect:/books/" + book.getId() + "/reviews";
                case User:
                    return "redirect:/users/" + userProfile.getId() + "/reviews";
            }
        }


        addReviewActionToModel(url, currentReview, model);
        model.addAttribute("review", editedReview);
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewEditPage";
    }


    @DeleteMapping(value = {
            "/books/{book:[\\d]+}/reviews/{review:[\\d]+}",
            "/users/{user:[\\d]+}/reviews/{review:[\\d]+}"
    })
    public String deleteReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable(value = "book", required = false) Book book,
            @PathVariable(value = "user", required = false) User userProfile,
            @PathVariable("review") Review review
    ) {
        Url url = checkCorrectRequest(review, userProfile, book, currentUser);

        reviewService.deleteUserReview(review);

        switch (url) {
            case Book:
                return "redirect:/books/" + book.getId() + "/reviews";
            case User:
                return "redirect:/users/" + userProfile.getId() + "/reviews";
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    private void addReviewActionToModel(Url url, Review review, Model model) {
        switch (url) {
            case Book:
                model.addAttribute("reviewAction", "/books/" + review.getBook().getId() + "/reviews/" + review.getId());
                break;
            case User:
                model.addAttribute("reviewAction", "/users/" + review.getAuthor().getId() + "/reviews/" + review.getId());
                break;
        }
    }

    private Url checkCorrectRequest(User userProfile, Book book) {
        boolean onBookUrl = userProfile == null && book != null;
        boolean onUserProfileUrl = userProfile != null && book == null;
        if (onBookUrl) {
            ControllerUtils.isBookExists(book);
            return Url.Book;
        } else if (onUserProfileUrl) {
            ControllerUtils.isUserProfileExists(userProfile);
            return Url.User;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Url checkCorrectRequest(Review review, User userProfile, Book book, User currentUser) {
        boolean onBookUrl = userProfile == null && book != null;
        boolean onUserProfileUrl = userProfile != null && book == null;
        if (onBookUrl) {
            checkCorrectBookRequest(book, review, currentUser);
            return Url.Book;
        } else if (onUserProfileUrl) {
            checkCorrectUserProfileRequest(userProfile, review, currentUser);
            return Url.User;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void checkCorrectBookRequest(Book book, Review review, User currentUser) {
        ControllerUtils.isBookExists(book);
        ControllerUtils.isReviewExists(review);
        reviewService.checkBookContainsReview(book, review);
        reviewService.checkCurrentUserRights(currentUser, review.getAuthor());
    }

    private void checkCorrectUserProfileRequest(User userProfile, Review review, User currentUser) {
        ControllerUtils.isUserProfileExists(userProfile);
        ControllerUtils.isReviewExists(review);
        reviewService.checkReviewBelongsUser(userProfile, review);
        reviewService.checkCurrentUserRights(currentUser, userProfile);
    }

}
