package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAll(Integer take, Integer skip, String sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (!sort.equals("") && sort!=null) {
            String[] rules = sort.split(",");
            orders = findAllSort(rules);
        }

        if (take != 0 && take!=null) {
            Integer skipPages = skip / take;

            Pageable pagination =
                    PageRequest.of(skipPages, take, Sort.by(orders));
            Page<Book> pagedResult = bookRepository.findAll(pagination);
            if (pagedResult.hasContent()) {
                return pagedResult.getContent();
            }
            return new ArrayList<Book>();
        }

        return bookRepository.findAll(Sort.by(orders));
    }

    private List<Sort.Order> findAllSort(String[] rules) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        for (int i = 0; i < rules.length; i++) {
            String[] dict = rules[i].split(":");
            if(dict[1].equals("desc")) {
                Sort.Order TmpOrder = new Sort.Order(Sort.Direction.DESC, dict[0]);
                orders.add(TmpOrder);
            }
            if(dict[1].equals("asc")) {
                Sort.Order TmpOrder = new Sort.Order(Sort.Direction.ASC, dict[0]);
                orders.add(TmpOrder);
            }
        }
        return orders;
    }
}
