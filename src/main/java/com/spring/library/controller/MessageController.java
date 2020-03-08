package com.spring.library.controller;

import com.spring.library.domain.Message;
import com.spring.library.domain.User;
import com.spring.library.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;


    @GetMapping("/")
    public String getGreetingPage() {
        return "greeting";
    }

    @GetMapping("/messages")
    public String getMessagesPage(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        model.addAttribute("messages", messageService.getMessagesByFilter(filter));
        model.addAttribute("filter", filter);

        return "messages";
    }

    @PostMapping("/messages")
    public String addNewMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            messageService.addNewMessage(message, user);

            model.addAttribute("message", null);
        }

        model.addAttribute("messages", messageService.getAllMessages());
        return "messages";
    }
}
