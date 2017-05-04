package com.ddubson.routers;

import com.ddubson.models.Book;
import com.ddubson.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookRouter {
    private final BookRepository bookRepository;

    @Autowired
    public BookRouter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public Flux<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping(path = "/books/{id}")
    public Mono<Book> getBookById(@PathVariable("id") Long id) {
        return this.bookRepository.findOne(id);
    }

    @PostMapping("/books")
    public Mono<ResponseEntity<Book>> save(@RequestBody Book book) {
        return this.bookRepository.save(Mono.justOrEmpty(book))
                .map(savedBook -> new ResponseEntity<>(savedBook, HttpStatus.CREATED));
    }
}
