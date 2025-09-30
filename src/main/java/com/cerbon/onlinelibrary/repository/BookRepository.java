package com.cerbon.onlinelibrary.repository;

import com.cerbon.onlinelibrary.model.Book;
import com.cerbon.onlinelibrary.model.type.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByStatus(BookStatus status);
}
