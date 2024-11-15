package com.example.Library.entity;

import com.example.Library.enumm.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "reader")
public class Reader {
    @Id
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    @ManyToMany
    @JoinTable(
            name = "reader_books_read",
            joinColumns = @JoinColumn(name = "reader_phone_number"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> booksRead = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "reader_books_unreturned",
            joinColumns = @JoinColumn(name = "reader_phone_number"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> booksUnreturned = new HashSet<>();
}
