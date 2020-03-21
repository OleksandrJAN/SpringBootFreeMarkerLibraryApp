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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/books/{bookId:[\\d]+}/reviews")
    public String getBookReviewsPage(
            @PathVariable("bookId") Book book,
            Model model
    ) {
        ControllerUtils.isBookExists(book);

        model.addAttribute("reviews", reviewService.getAllBookReviews(book.getId()));
        model.addAttribute("assessments", Assessment.values());
        model.addAttribute("book", book);
        return "review/reviewList";
    }

    @PostMapping("/books/{bookId:[\\d]+}/reviews")
    public String addNewReview(
            @PathVariable("bookId") Book book,
            @AuthenticationPrincipal User user,
            @Valid Review review,
            BindingResult bindingResult,
            Model model
    ) {
        ControllerUtils.isBookExists(book);

        boolean isAssessmentSelected = isAssessmentSelected(review, model);
        boolean isBindingResultHasErrors = isBindingResultHasErrors(bindingResult, model);

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



    @GetMapping("/users/{userId:[\\d]+}/reviews")
    public String getUserReviewsPage(
            @PathVariable("userId") User user,
            Model model
    ) {
        ControllerUtils.isUserProfileExists(userProfile);

        model.addAttribute("reviews", reviewService.getAllUserReviews(user.getId()));
        model.addAttribute("user", user);
        return "review/reviewList";
    }


    @GetMapping("/users/{userId:[\\d]+}/reviews/{reviewId:[\\d]+}")
    public String getUserReviewPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("userId") User userProfile,
            @PathVariable("reviewId") Review review,
            Model model
    ) {
        /*For each user : ReviewTable???*/
        isCurrentUserProfile(currentUser, userProfile);
        isReviewExists(review);
        isReviewBelongsUser(currentUser.getId(), review);


        model.addAttribute("review", review);
        model.addAttribute("reviewId", review.getId());
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewPage";
    }


    private void isCurrentUserProfile(User currentUser, User userProfile) {
        if (!currentUser.equals(userProfile)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FORBIDDEN");
        }
    }

    private void isReviewExists(Review review) {
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND");
        }
    }

    private void isReviewBelongsUser(Long userId, Review review) {
        if (!reviewService.isReviewBelongsUser(userId, review)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FORBIDDEN");
        }
    }


    @PostMapping("/users/{userId:[\\d]+}/reviews/{reviewId:[\\d]+}")
    public String editUserReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("userId") User userProfile,
            @PathVariable("reviewId") Review userReview,
            @Valid Review editedReview,
            BindingResult bindingResult,
            Model model
    ) {
        /*For each user : ReviewTable???*/
        isCurrentUserProfile(currentUser, userProfile);
        isReviewExists(userReview);
        isReviewBelongsUser(currentUser.getId(), userReview);


        boolean isBindingResultHasErrors = isBindingResultHasErrors(bindingResult, model);

        if (!isBindingResultHasErrors) {
            reviewService.updateUserReview(userReview, editedReview);
            return "redirect:/users/" + currentUser.getId() + "/reviews";
        }

        model.addAttribute("review", editedReview);
        model.addAttribute("reviewId", userReview.getId());
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewPage";
    }


    private boolean isBindingResultHasErrors(BindingResult bindingResult, Model model) {
        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        return isBindingResultHasErrors;
    }

    private boolean isAssessmentSelected(Review review, Model model) {
        boolean isAssessmentSelected = review.getAssessment() != null;
        if (!isAssessmentSelected) {
            model.addAttribute("assessmentError", "Please, select an assessment");
        }

        return isAssessmentSelected;
    }

}