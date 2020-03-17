package com.spring.library.service;

import com.spring.library.domain.Review;
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

}
