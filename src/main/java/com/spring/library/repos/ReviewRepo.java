package com.spring.library.repos;

import com.spring.library.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long bookId);
    Review findByAuthorId(Long userId);
}
