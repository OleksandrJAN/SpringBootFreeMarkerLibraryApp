package com.spring.library.service;

import com.spring.library.domain.Message;
import com.spring.library.domain.User;
import com.spring.library.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;


    public Iterable<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public Iterable<Message> getMessagesByFilter(String filter) {
        return StringUtils.isEmpty(filter) ? messageRepo.findAll() : messageRepo.findByTag(filter);
    }

    public void addNewMessage(Message message, User author) {
        message.setAuthor(author);
        messageRepo.save(message);
    }
}
