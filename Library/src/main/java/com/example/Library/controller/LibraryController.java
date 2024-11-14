package com.example.Library.controller;

import com.example.Library.entity.Author;
import com.example.Library.entity.Reader;
import com.example.Library.entity.Transaction;
import com.example.Library.enumm.TransactionType;
import com.example.Library.service.AuthorService;
import com.example.Library.service.ReaderService;
import com.example.Library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ReaderService readerService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> performTransaction(@RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.performTransaction(
                request.getBookId(),
                request.getReaderPhoneNumber(),
                TransactionType.valueOf(request.getTransactionType())
        );
        return ResponseEntity.ok(transaction);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/popular-author")
    public ResponseEntity<Author> getMostPopularAuthor(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        System.out.println("StartDateTime: " + startDateTime);
        System.out.println("EndDateTime: " + endDateTime);
        Author author = authorService.findMostPopularAuthor(startDateTime, endDateTime);
        return ResponseEntity.ok(author);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/most-reading-client")
    public ResponseEntity<Reader> getMostReadingClient() {
        Reader mostReadingReader = readerService.findMostReadingClient();
        if (mostReadingReader != null) {
            return ResponseEntity.ok(mostReadingReader);
        }
        return ResponseEntity.noContent().build(); //  данных нет

    }

    @GetMapping("/readers-unreturned-books")
    public ResponseEntity<List<Reader>> getReadersSortedByUnreturnedBooks() {
        List<Reader> readers = readerService.getAllReadersSortedByUnreturnedBooks();
        return ResponseEntity.ok(readers);
    }
}
