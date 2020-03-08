package com.spring.library.controller;

import com.spring.library.domain.Writer;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/writers")
public class WriterController {

    @Autowired
    private WriterService writerService;


    @GetMapping
    public String getWriterList(Model model) {
        model.addAttribute("writers", writerService.getWriterList());
        return "writerList";
    }

    @GetMapping("{writer}")
    public String getBookPage(@PathVariable Writer writer, Model model) {
        model.addAttribute("writer", writer);
        return "writer";
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

        return "redirect:/writers";
    }
}
