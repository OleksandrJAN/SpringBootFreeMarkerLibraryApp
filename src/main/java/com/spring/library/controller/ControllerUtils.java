package com.spring.library.controller;

import com.spring.library.domain.Book;
import com.spring.library.domain.Review;
import com.spring.library.domain.User;
import com.spring.library.domain.Writer;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static boolean mergeErrorsWithModel(BindingResult bindingResult, Model model) {
        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }

        return isBindingResultHasErrors;
    }

    static void isBookExists(Book book) {
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BOOK NOT FOUND");
        }
    }

    static void isWriterExists(Writer writer) {
        if (writer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WRITER NOT FOUND");
        }
    }

    static void isUserProfileExists(User userProfile) {
        if (userProfile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
        }
    }

    static void isReviewExists(Review review) {
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND");
        }
    }

}
