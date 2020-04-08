package com.spring.library.controller;

import com.spring.library.domain.Writer;
import com.spring.library.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getWriterPage(@PathVariable Writer writer, Model model) {
        ControllerUtils.isWriterExists(writer);

        model.addAttribute("writer", writer);
        return "writer/writerPage";
    }


    @GetMapping("/writers/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getWriterAddPage() {
        return "writer/writerAddPage";
    }

    @PostMapping("/writers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewWriter(
            @Valid Writer writer,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);

        if (!isBindingResultHasErrors) {
            if (writerService.addNewWriter(writer)) {
                return "redirect:/writers";
            } else {
                model.addAttribute("writerError", "Writer already exists");
            }
        }

        model.addAttribute("writer", writer);
        return "writer/writerAddPage";
    }


    @GetMapping("writers/{writer:[\\d]+}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getWriterEditPage(@PathVariable Writer writer, Model model) {
        ControllerUtils.isWriterExists(writer);

        model.addAttribute("currentWriter", writer);
        model.addAttribute("editedWriter", writer);
        return "writer/writerEditPage";
    }


    @PutMapping("/writers/{currentWriter:[\\d]+}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateWriter(
            @PathVariable("currentWriter") Writer currentWriter,
            @Valid Writer editedWriter,
            BindingResult bindingResult,
            Model model
    ) {
        ControllerUtils.isWriterExists(currentWriter);

        boolean isBindingResultHasErrors = ControllerUtils.mergeErrorsWithModel(bindingResult, model);
        if (!isBindingResultHasErrors) {
            writerService.updateWriter(currentWriter, editedWriter);
            return "redirect:/writers/" + currentWriter.getId();
        }

        model.addAttribute("currentWriter", currentWriter);
        model.addAttribute("editedWriter", editedWriter);
        return "writer/writerEditPage";
    }

    @DeleteMapping("/writers/{writer:[\\d]+}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteWriter(@PathVariable Writer writer) {
        ControllerUtils.isWriterExists(writer);

        writerService.deleteWriter(writer);

        return "redirect:/writers";
    }


    @GetMapping("/writers/{writer:[\\d]+}/books")
    public String getWriterBooksPage(@PathVariable Writer writer, Model model) {
        ControllerUtils.isWriterExists(writer);

        model.addAttribute("books", writer.getBooks());
        return "writer/writerBooks";
    }
}
