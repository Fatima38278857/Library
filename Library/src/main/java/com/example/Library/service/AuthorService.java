package com.example.Library.service;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    TransactionRepository transactionRepository;


    public Author saveAuthorWithBook(Author author, Book book) {
        // Устанавливаем связь между книгой и автором
        book.setAuthor(author);

        // Добавляем книгу в список книг автора
        if (author.getBooks() == null) {
            author.setBooks(new ArrayList<>());
        }
        author.getBooks().add(book);

        // Сохраняем автора вместе с книгами (CascadeType.ALL)
        return authorRepository.save(author);
    }


    public Author findMostPopularAuthor(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Object[]> results = transactionRepository.findPopularAuthorsByTransactionCount(startDateTime, endDateTime);

        if (!results.isEmpty()) {
            return (Author) results.get(0)[0]; // возвращаем автора с наибольшим числом аренд
        }

        return null; // Если ничего не найдено, возвращаем null
    }
}

