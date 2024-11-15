package com.example.Library.controller;

import com.example.Library.entity.Reader;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "http://localhost:8080")
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;


    @PostMapping
    public ResponseEntity<Reader> postBook(@RequestBody Reader reader) {
        Reader post = readerService.saveReader(reader);
        return ResponseEntity.ok(post);
    }
}
