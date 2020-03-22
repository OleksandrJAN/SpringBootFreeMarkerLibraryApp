package com.spring.library.service;

import com.spring.library.domain.Book;
import com.spring.library.domain.Review;
import com.spring.library.domain.User;
import com.spring.library.repos.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;


    public List<Review> getAllBookReviews(Long bookId) {
        return reviewRepo.findByBook_Id(bookId);
    }

    public List<Review> getAllUserReviews(Long userId) {
        return reviewRepo.findByAuthor_Id(userId);
    }

    public boolean addNewReview(Long userId, Long bookId, Review review) {
        Review reviewFromUser = reviewRepo.findByAuthor_IdAndBook_Id(userId, bookId);

        if (reviewFromUser != null) {
            return false;
        }

        reviewRepo.save(review);
        return true;

    }

    public void updateUserReview(Review userReview, Review editedReview) {
        userReview.setText(editedReview.getText());
        userReview.setAssessment(editedReview.getAssessment());
        reviewRepo.save(userReview);
    }


    public void deleteUserReview(Review review){
        reviewRepo.delete(review);
    }


    public void checkCurrentUserRights(User currentUser, User userProfile) {
        // check that currentUser is viewing his own reviews or currentUser has rights to view other users reviews
        boolean isCurrentUserProfile = currentUser.equals(userProfile);
        boolean isCurrentUserAdmin = currentUser.isAdmin();
        if (!isCurrentUserProfile && !isCurrentUserAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "FORBIDDEN");
        }
    }


    public void checkReviewBelongsUser(User userProfile, Review review) {
        boolean isReviewBelongsUserProfile = isReviewBelongsUser(userProfile.getId(), review);
        if (!isReviewBelongsUserProfile) {
            String message = "user " + userProfile.getId() + " does not has review " + review.getId();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }

    private boolean isReviewBelongsUser(Long userId, Review review) {
        List<Review> userReviews = reviewRepo.findByAuthor_Id(userId);
        return userReviews.contains(review);
    }



    public void checkBookContainsReview(Book book, Review review) {
        // check that userProfile.reviews contains review
        boolean isReviewBelongsBook = isReviewBelongsBook(book.getId(), review);
        if (!isReviewBelongsBook) {
            String message = "book " + book.getId() + " does not has review " + review.getId();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
    }

    private boolean isReviewBelongsBook(Long bookId, Review review) {
        List<Review> bookReviews = reviewRepo.findByBook_Id(bookId);
        return bookReviews.contains(review);
    }
}
