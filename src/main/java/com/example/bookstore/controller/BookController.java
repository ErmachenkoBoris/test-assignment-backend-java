package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAll();

        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/book-create")
    public String createBookForm(Book book) {
        return "book-create";
    }

    @PostMapping("/books")
    public String createBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book-delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/book-update/{id}")
    public String updateBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book-upd";
    }

    @PostMapping("/book-update")
    public String updateBook(Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }
}
