package com.example.Library.repository;

import com.example.Library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
