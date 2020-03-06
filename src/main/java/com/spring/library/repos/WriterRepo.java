package com.spring.library.repos;

import com.spring.library.domain.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepo extends JpaRepository<Writer, Long> {
    Writer findByFirstNameAndLastName(String firstName, String lastName);
}
