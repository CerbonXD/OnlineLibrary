package com.cerbon.onlinelibrary.repository;

import com.cerbon.onlinelibrary.model.Loan;
import com.cerbon.onlinelibrary.model.type.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByBookStatus(BookStatus status);
}
