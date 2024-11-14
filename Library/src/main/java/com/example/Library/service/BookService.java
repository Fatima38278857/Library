package com.example.Library.service;

import com.example.Library.entity.Book;
import com.example.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


//    public Book saveBook(Book book){
//       return bookRepository.save(book);
//    }
}
