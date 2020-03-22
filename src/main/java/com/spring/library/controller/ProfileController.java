package com.spring.library.controller;

import com.spring.library.domain.Assessment;
import com.spring.library.domain.Review;
import com.spring.library.domain.Role;
import com.spring.library.domain.User;
import com.spring.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/users/{user:[\\d]+}")
    public String getUserProfilePage(@PathVariable("user") User userProfile, Model model) {
        ControllerUtils.isUserProfileExists(userProfile);

        model.addAttribute("userProfile", userProfile);
        model.addAttribute("roles", Role.values());
        return "user/userProfile";
    }

    @GetMapping("/users/{user:[\\d]+}/reviews")
    public String getUserReviewsPage(@PathVariable("user") User userProfile, Model model) {
        ControllerUtils.isUserProfileExists(userProfile);

        addUserAndUserReviewsToModel(model, userProfile);
        return "review/reviewList";
    }

    @GetMapping("/users/{user:[\\d]+}/reviews/{review:[\\d]+}")
    public String getUserReviewPage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("review") Review review,
            Model model
    ) {
        checkCorrectRequest(userProfile, review, currentUser);

        addReviewToModel(model, review);
        addReviewTextAndSelectedAssessmentToModel(model, review.getText(), review.getAssessment());
        addAssessmentsToModel(model);
        return "review/reviewEditPage";
    }

    @PutMapping("/users/{user:[\\d]+}/reviews/{userReview:[\\d]+}")
    public String editUserReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("userReview") Review currentReview,
            @Valid Review editedReview,
            BindingResult bindingResult,
            Model model
    ) {
        checkCorrectRequest(userProfile, currentReview, currentUser);

        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);
        if (!isBindingResultHasErrors) {
            reviewService.updateUserReview(currentReview, editedReview);
            return "redirect:/users/" + userProfile.getId() + "/reviews";
        }

        addReviewToModel(model, currentReview);
        addReviewTextAndSelectedAssessmentToModel(model, editedReview.getText(), editedReview.getAssessment());
        addAssessmentsToModel(model);
        return "review/reviewEditPage";
    }

    @DeleteMapping("/users/{user:[\\d]+}/reviews/{review:[\\d]+}")
    public String deleteReview(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User userProfile,
            @PathVariable("review") Review review
    ) {
        checkCorrectRequest(userProfile, review, currentUser);

        reviewService.deleteUserReview(review);

        return "redirect:/users/" + userProfile.getId() + "/reviews";
    }


    private void checkCorrectRequest(User userProfile, Review review, User currentUser) {
        ControllerUtils.isUserProfileExists(userProfile);
        ControllerUtils.isReviewExists(review);
        reviewService.checkReviewBelongsUser(userProfile, review);
        reviewService.checkCurrentUserRights(currentUser, userProfile);
    }

    private void addUserAndUserReviewsToModel(Model model, User userProfile) {
        model.addAttribute("reviews", reviewService.getAllUserReviews(userProfile.getId()));
        model.addAttribute("userProfile", userProfile);
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
