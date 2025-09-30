package com.cerbon.onlinelibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "withdrawal_date", nullable = false)
    private LocalDate withdrawalDate;

    @NotNull
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @AssertTrue(message = "withdrawalDate cannot be after returnDate")
    private boolean isValidDates() {
        if (withdrawalDate == null || returnDate == null) return true; // handled by @NotNull
        return !withdrawalDate.isAfter(returnDate);
    }
}
