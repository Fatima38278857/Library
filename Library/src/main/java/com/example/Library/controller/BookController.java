package com.example.Library.controller;

import com.example.Library.entity.Book;
import com.example.Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

//    @PostMapping
//    public ResponseEntity<Book> postBook(@RequestBody Book book) {
//        Book post = bookService.saveBook(book);
//        return ResponseEntity.ok(post);
//    }


}
