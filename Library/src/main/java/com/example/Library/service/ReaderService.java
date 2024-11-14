package com.example.Library.service;

import com.example.Library.entity.Reader;
import com.example.Library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;


    public List<Reader> getAllReadersSortedByUnreturnedBooks() {
        return readerRepository.findAllReadersSortedByUnreturnedBooks();
    }

    public Reader findMostReadingClient() {
        return readerRepository.findTopReader(); // Запрос вернёт первого по количеству взятых книг
    }



    public Reader saveReader(Reader reader){
        return readerRepository.save(reader);
    }
}
