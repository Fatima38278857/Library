package com.example.Library.entity;

import com.example.Library.enumm.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TransactionType transactionType; // Забрали или вернули
    private LocalDateTime transactionDate;

    @ManyToOne
    private Reader reader;

    @ManyToOne
    private Book book;
}
