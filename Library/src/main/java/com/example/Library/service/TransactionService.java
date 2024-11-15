package com.example.Library.service;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.entity.Reader;
import com.example.Library.entity.Transaction;
import com.example.Library.enumm.TransactionType;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.ReaderRepository;
import com.example.Library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    // Метод для транзакции с книгой (взятие/возврат)
       public Transaction performTransaction(Long bookId, String readerPhoneNumber, TransactionType transactionType) {
            // Найти книгу
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Книга не найдена"));

            // Найти читателя
            Reader reader = readerRepository.findById(readerPhoneNumber)
                    .orElseThrow(() -> new RuntimeException("Читатель не найден"));

            // Создать новую транзакцию
            Transaction transaction = new Transaction();
            transaction.setBook(book);
            transaction.setReader(reader);
            transaction.setTransactionType(transactionType);
            transaction.setTransactionDate(LocalDateTime.now());

            // Логика для CHECKOUT
            if (transactionType == TransactionType.CHECKOUT) {
                // Если книга не находится в "не сданных", добавляем её
                if (!reader.getBooksUnreturned().contains(book)) {
                    reader.getBooksUnreturned().add(book);
                }
                // Если книга ещё не была добавлена в "прочитанные", добавляем её
                if (!reader.getBooksRead().contains(book)) {
                    reader.getBooksRead().add(book);
                }
            }

            // Логика для RETURN
            else if (transactionType == TransactionType.RETURN) {
                // Убираем книгу из "не сданных", если она там есть
                if (reader.getBooksUnreturned().contains(book)) {
                    reader.getBooksUnreturned().remove(book);
                } else {
                    throw new RuntimeException("Эта книга не числится среди не сданных");
                }
            }

            // Сохранение изменений читателя и транзакции
            readerRepository.save(reader);
            return transactionRepository.save(transaction);
        }
    }
//        Book book = bookRepository.findById(bookId)
//                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
//
//        Reader reader = readerRepository.findById(readerPhoneNumber)
//                .orElseThrow(() -> new RuntimeException("Читатель не найден"));
//
//        // Создать транзакцию
//        Transaction transaction = new Transaction();
//        transaction.setBook(book);
//        transaction.setReader(reader);
//        transaction.setTransactionType(transactionType); // Преобразуем Enum в строку, если необходимо
//        transaction.setTransactionDate(LocalDateTime.now());
//
//        // Обновление истории читателя
//        if (transactionType == TransactionType.CHECKOUT) {
//            // Добавить книгу в список прочитанных, если её ещё нет
//            reader.getBooksRead().add(book);
//        } else if (transactionType == TransactionType.RETURN) {
//            System.out.println("ЭТО ТУТ");
//        }
//        // Сохранение изменений
//        readerRepository.save(reader);
//        return transactionRepository.save(transaction);
//    }
