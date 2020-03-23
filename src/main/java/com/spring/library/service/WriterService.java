package com.spring.library.service;

import com.spring.library.domain.Writer;
import com.spring.library.repos.WriterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterService {

    @Autowired
    private WriterRepo writerRepo;


    public List<Writer> getWriterList() {
        return writerRepo.findAll();
    }

    public boolean addNewWriter(Writer writer) {
        if (isWriterExists(writer)) {
            return false;
        }

        writerRepo.save(writer);
        return true;
    }

    private boolean isWriterExists(Writer writer) {
        Writer writerFromDb = writerRepo.findByFirstNameAndLastName(writer.getFirstName(), writer.getLastName());
        return writerFromDb != null;
    }

    public void updateWriter(Writer editedWriter) {
        writerRepo.save(editedWriter);
    }

    public void deleteWriter(Writer writer) {
        writerRepo.delete(writer);
    }
}
