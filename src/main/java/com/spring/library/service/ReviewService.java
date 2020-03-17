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


    public List<Review> getAllReviewsForBook(Long bookId) {
        return reviewRepo.findByBookId(bookId);
    }

    public boolean addNewReview(User user, Review review) {
        Review reviewFromUser = reviewRepo.findByAuthorId(user.getId());

        if (reviewFromUser != null) {
            return false;
        }

        reviewRepo.save(review);
        return true;

    }

}
