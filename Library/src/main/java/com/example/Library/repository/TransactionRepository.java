package com.example.Library.repository;

import com.example.Library.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT a, COUNT(t) FROM Transaction t JOIN t.book b JOIN b.author a " +
            "WHERE t.transactionDate BETWEEN :startDateTime AND :endDateTime " +
            "GROUP BY a ORDER BY COUNT(t) DESC")
    List<Object[]> findPopularAuthorsByTransactionCount(@Param("startDateTime") LocalDateTime startDateTime,
                                                        @Param("endDateTime") LocalDateTime endDateTime);

}
