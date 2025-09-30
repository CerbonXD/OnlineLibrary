package com.cerbon.onlinelibrary.service;

import com.cerbon.onlinelibrary.model.Book;
import com.cerbon.onlinelibrary.model.Loan;
import com.cerbon.onlinelibrary.model.type.BookStatus;
import com.cerbon.onlinelibrary.repository.BookRepository;
import com.cerbon.onlinelibrary.repository.LoanRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public List<Loan> findActiveLoans() {
        return loanRepository.findByBookStatus(BookStatus.LOANED);
    }

    @Transactional
    public Loan createLoan(@Valid Loan loan) {
        // ensure book is available
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + loan.getBook().getId()));
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for loan");
        }
        book.setStatus(BookStatus.LOANED);
        bookRepository.save(book);
        return loanRepository.save(loan);
    }

    @Transactional
    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found: " + loanId));
        Book book = loan.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
        loanRepository.delete(loan);
    }
}
