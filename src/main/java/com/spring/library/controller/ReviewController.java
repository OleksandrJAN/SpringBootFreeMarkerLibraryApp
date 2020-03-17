package com.spring.library.controller;

import com.spring.library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String getReviewsPage(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("reviews", reviewService.getAllReviewsForBook(bookId));
        return "reviewList";
    }

}
