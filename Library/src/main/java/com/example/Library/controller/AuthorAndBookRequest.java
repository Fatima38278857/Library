package com.example.Library.controller;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorAndBookRequest {
    private Author author;
    private Book book;


}
