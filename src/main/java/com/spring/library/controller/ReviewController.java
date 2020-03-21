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


    @GetMapping("/books/{book:[\\d]+}/reviews")
    public String getBookReviewsPage(
            @PathVariable("book") Book book,
            Model model
    ) {
        ControllerUtils.isBookExists(book);

        model.addAttribute("reviews", reviewService.getAllBookReviews(book.getId()));
        model.addAttribute("assessments", Assessment.values());
        model.addAttribute("book", book);
        return "review/reviewList";
    }

    @PostMapping("/books/{book:[\\d]+}/reviews")
    public String addNewReview(
            @PathVariable("book") Book book,
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


    @GetMapping("/users/{user:[\\d]+}/reviews")
    public String getUserReviewsPage(
            @PathVariable("user") User userProfile,
            Model model
    ) {
        ControllerUtils.isUserProfileExists(userProfile);

        model.addAttribute("reviews", reviewService.getAllUserReviews(userProfile.getId()));
        return "review/reviewList";
    }


    @GetMapping("/users/{user:[\\d]+}/reviews/{review:[\\d]+}")
    public String getUserReviewPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("review") Review userReview,
            Model model
    ) {
        ControllerUtils.isUserProfileExists(userProfile);
        ControllerUtils.isReviewExists(userReview);
        checkCurrentUserRights(currentUser, userProfile, userReview);

        model.addAttribute("review", userReview);
        // reviewId and reviewAuthorId for correct url (see PostMapping(editUserReview))
        model.addAttribute("reviewId", userReview.getId());
        model.addAttribute("reviewAuthorId", userProfile.getId());
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewPage";
    }

    @Transactional
    @PostMapping("/users/{user:[\\d]+}/reviews/{userReview:[\\d]+}")
    public String editUserReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("userReview") Review userReview,
            @Valid Review editedReview,
            BindingResult bindingResult,
            Model model
    ) {
        ControllerUtils.isUserProfileExists(userProfile);
        ControllerUtils.isReviewExists(userReview);
        checkCurrentUserRights(currentUser, userProfile, userReview);

        boolean isBindingResultHasErrors = isBindingResultHasErrors(bindingResult, model);
        if (!isBindingResultHasErrors) {
            reviewService.updateUserReview(userReview, editedReview);
            return "redirect:/users/" + userProfile.getId() + "/reviews";
        }

        // review for assessment and text, reviewId and reviewAuthorId for correct url
        // coz editedReview.id == null and editedReview.author == null
        model.addAttribute("review", editedReview);
        model.addAttribute("reviewId", userReview.getId());
        model.addAttribute("reviewAuthorId", userProfile.getId());
        model.addAttribute("assessments", Assessment.values());
        return "review/reviewPage";
    }


    @GetMapping("/users/{user:[\\d]+}/reviews/{review:[\\d]+}/delete")
    public String deleteReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("review") Review userReview
    ) {
        ControllerUtils.isUserProfileExists(userProfile);
        ControllerUtils.isReviewExists(userReview);
        checkCurrentUserRights(currentUser, userProfile, userReview);

        reviewService.deleteUserReview(userReview);

        return "redirect:/users/" + userProfile.getId() + "/reviews";
    }


    private void checkCurrentUserRights(User currentUser, User userProfile, Review userReview) {
        // check that userProfile.reviews contains userReview
        boolean isReviewBelongsUserProfile = reviewService.isReviewBelongsUser(userProfile.getId(), userReview);
        if (!isReviewBelongsUserProfile) {
            String message = "user " + userProfile.getId() + " does not has review " + userReview.getId();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        // check that currentUser is viewing his own reviews or currentUser has rights to view other users reviews
        boolean isCurrentUserProfile = currentUser.equals(userProfile);
        boolean isCurrentUserAdmin = currentUser.isAdmin();
        if (!isCurrentUserProfile && !isCurrentUserAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FORBIDDEN");
        }
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