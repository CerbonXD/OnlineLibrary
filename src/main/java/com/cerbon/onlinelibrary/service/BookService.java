package com.cerbon.onlinelibrary.service;

import com.cerbon.onlinelibrary.model.Book;
import com.cerbon.onlinelibrary.model.type.BookStatus;
import com.cerbon.onlinelibrary.repository.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAvailable() {
        return bookRepository.findByStatus(BookStatus.AVAILABLE);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book create(@Valid Book book) {
        if (book.getStatus() == null) {
            book.setStatus(BookStatus.AVAILABLE);
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, @Valid Book updated) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setAuthor(updated.getAuthor());
                    existing.setPublicationYear(updated.getPublicationYear());
                    // status normally controlled by loan/return operations
                    return bookRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void setStatus(Long id, BookStatus status) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setStatus(status);
            bookRepository.save(book);
        });
    }
}
