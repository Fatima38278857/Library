package com.example.Library.controller;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    @PostMapping("/save-with-book")
    public ResponseEntity<Author> saveAuthorWithBook(@RequestBody AuthorAndBookRequest request) {
        // Сохраняем автора и книгу
        Author savedAuthor = authorService.saveAuthorWithBook(request.getAuthor(), request.getBook());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }


//    @PostMapping
//    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
//        System.out.println("Saving Author: " + author);
//        // Передаем сущность автора напрямую в сервис
//        Author savedAuthor = authorService.saveAuthor(author);
//        return ResponseEntity.ok(savedAuthor);
//    }
}

