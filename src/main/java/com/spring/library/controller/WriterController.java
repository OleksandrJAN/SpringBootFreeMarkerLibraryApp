package com.spring.library.controller;

import com.spring.library.domain.Writer;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class WriterController {

    @Autowired
    private WriterService writerService;


    @GetMapping("/writers")
    public String getWriterList(Model model) {
        model.addAttribute("writers", writerService.getWriterList());
        return "writer/writerList";
    }

    @GetMapping("/writers/{writer:[\\d]+}")
    public String getBookPage(@PathVariable Writer writer, Model model) {
        if (writer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WRITER NOT FOUND");
        }

        model.addAttribute("writer", writer);
        return "writer/writerPage";
    }


    @GetMapping("/writers/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getWriterAddPage() {
        return "writer/writerAdd";
    }


    @PostMapping("/writers/add")
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
        }

        if (!isBindingResultHasErrors) {
            if (writerService.addNewWriter(writer)) {
                return "redirect:/writers";
            } else {
                model.addAttribute("writerError", "Writer already exists");
            }
        }

        model.addAttribute("writer", writer);
        return "writer/writerAdd";
    }
}
