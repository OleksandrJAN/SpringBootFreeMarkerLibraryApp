package com.spring.library.service;

import com.spring.library.domain.Review;
import com.spring.library.domain.User;
import com.spring.library.repos.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;


    public List<Review> getAllBookReviews(Long bookId) {
        return reviewRepo.findByBookId(bookId);
    }

    public List<Review> getAllUserReviews(Long userId) {
        return reviewRepo.findByAuthorId(userId);
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

    public boolean isReviewBelongsUser(Long userId, Review review) {
        List<Review> userReviews = reviewRepo.findByAuthorId(userId);
        return userReviews.contains(review);
    }

    public void deleteUserReview(Review review){
        reviewRepo.delete(review);
    }

}
