package com.example.library.repository;

import com.example.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
    List<BookEntity> findByPublishedDateAfter(LocalDate date);
    List<BookEntity> findByAuthorId(Long id);

    boolean existsByAuthorId(Long id);
}
