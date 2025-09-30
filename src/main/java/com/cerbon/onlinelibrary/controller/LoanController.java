package com.cerbon.onlinelibrary.controller;

import com.cerbon.onlinelibrary.model.Book;
import com.cerbon.onlinelibrary.model.Loan;
import com.cerbon.onlinelibrary.model.User;
import com.cerbon.onlinelibrary.service.BookService;
import com.cerbon.onlinelibrary.service.LoanService;
import com.cerbon.onlinelibrary.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    @GetMapping
    public String listActive(Model model) {
        model.addAttribute("loans", loanService.findActiveLoans());
        return "loans/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("loan", Loan.builder().withdrawalDate(LocalDate.now()).returnDate(LocalDate.now().plusWeeks(2)).build());
        model.addAttribute("books", bookService.findAvailable());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("action", "/loans");
        return "loans/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("loan") Loan loan, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.findAvailable());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("action", "/loans");
            return "loans/form";
        }
        loanService.createLoan(loan);
        return "redirect:/loans";
    }

    @PostMapping("/{loanId}/return")
    public String returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return "redirect:/loans";
    }
}
