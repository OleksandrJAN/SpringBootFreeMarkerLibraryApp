package com.spring.library.Controller;

import com.spring.library.domain.Book;
import com.spring.library.domain.Genre;
import com.spring.library.domain.Writer;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    private WriterService writerService;


    @GetMapping
    public String getWritersList(Model model) {
        model.addAttribute("writers", writerService.getWriterList());
        return "writerList";
    }

    @GetMapping("{writer}")
    public String getBookPage(@PathVariable Writer writer, Model model) {
        model.addAttribute("writer", writer);
        return "book";
    }


    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getWriterAddPage(Model model) {
        return "writerAdd";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewWriter(
            @Valid Writer writer,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isBindingResultHasErrors = bindingResult.hasErrors();
        if (isBindingResultHasErrors) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("writer", writer);

            return getWriterAddPage(model);
        }

        if (!writerService.addNewWriter(writer)) {
            model.addAttribute("writerError", "Writer already exists");
            model.addAttribute("writer", writer);
            return getWriterAddPage(model);
        }

        return "redirect:/writerList";
    }
}
