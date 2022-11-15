package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.ResponseModel;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    //    GET /books - Get array of all books
    @GetMapping("/books")
    public ResponseModel findAll() {
        List<Book> books = bookService.findAll();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus(true);
        responseModel.setMessage("Data is found");
        responseModel.setData(books);

        return responseModel;
    }

    //    GET /books/:id - Get one book by ID
    @GetMapping("/books/{id}")
    public ResponseModel getBookById(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        ResponseModel responseModel = new ResponseModel();

        if (book != null) {
            bookService.updateBook(book);
            responseModel.setStatus(true);
            responseModel.setMessage("Book Saved");
            responseModel.setData(book);

            return responseModel;
        }
        responseModel.setStatus(false);
        responseModel.setMessage("Book Not Found");
        return responseModel;
    }

    //    POST /books - Create new book
    @PostMapping("/books")
    public ResponseModel createBook(Book book) {
        ResponseModel responseModel = new ResponseModel();

        if(book.getUuid() != null) {
            bookService.saveBook(book);
            responseModel.setStatus(true);
            responseModel.setMessage("Book Saved");
            responseModel.setData(book);
        }

        responseModel.setStatus(false);
        responseModel.setMessage("Bad Data");
        return responseModel;
    }

    //    PUT /books/:id - Update book by ID
    @PutMapping("/books/{id}")
    public ResponseModel updateBook(@PathVariable("id") Long id, Book bookUpdated) {
        Book book = bookService.findById(id);
        ResponseModel responseModel = new ResponseModel();

        if (book != null && bookUpdated.getUuid() != null) {
            bookService.updateBook(book);
            responseModel.setStatus(true);
            responseModel.setMessage("Book Saved");
            responseModel.setData(bookUpdated);
            return responseModel;
        }
        responseModel.setStatus(false);
        responseModel.setMessage("Book not found or wrong parameters");
        return responseModel;
    }

    //    DELETE /books/:id - Delete book by ID
    @DeleteMapping ("/books/{id}")
    public ResponseModel deleteBook(@PathVariable("id") Long id) {
        ResponseModel responseModel = new ResponseModel();

        try {
            bookService.deleteById(id);
            responseModel.setStatus(true);
            responseModel.setMessage("Book Deleted");
        }
        catch(Exception e) {
            responseModel.setStatus(false);
            responseModel.setMessage("Book Not Found");
        }

        return responseModel;
    }
}
