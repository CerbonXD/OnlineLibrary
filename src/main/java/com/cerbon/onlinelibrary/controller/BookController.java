package com.cerbon.onlinelibrary.controller;

import com.cerbon.onlinelibrary.model.Book;
import com.cerbon.onlinelibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @GetMapping("/available")
    public String listAvailable(Model model) {
        model.addAttribute("books", bookService.findAvailable());
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("formTitle", "New Book");
        model.addAttribute("action", "/books");
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "New Book");
            model.addAttribute("action", "/books");
            return "books/form";
        }
        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("formTitle", "Edit Book");
        model.addAttribute("action", "/books/" + id);
        return "books/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Edit Book");
            model.addAttribute("action", "/books/" + id);
            return "books/form";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
