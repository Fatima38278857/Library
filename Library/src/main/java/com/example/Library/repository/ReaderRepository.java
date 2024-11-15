package com.example.Library.repository;

import com.example.Library.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {

    @Query(value = "SELECT r.* " +
            "FROM reader r " +
            "JOIN reader_books_read b ON r.phone_number = b.reader_phone_number " +
            "GROUP BY r.phone_number, r.birth_date, r.first_name, r.gender, r.last_name " +
            "ORDER BY COUNT(b.book_id) DESC " +
            "LIMIT 1", nativeQuery = true)
    Reader findTopReader();

    @Query("SELECT r FROM Reader r LEFT JOIN r.booksUnreturned b GROUP BY r ORDER BY COUNT(b) DESC")
    List<Reader> findAllReadersSortedByUnreturnedBooks();
}

