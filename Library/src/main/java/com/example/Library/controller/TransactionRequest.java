package com.example.Library.controller;

import lombok.Data;

@Data
public class TransactionRequest {

    private Long bookId;
    private String readerPhoneNumber;
    private String transactionType; // Если `TransactionType` — enum, используйте его.

}

