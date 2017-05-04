package com.ddubson.repositories;

import com.ddubson.models.Book;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReactiveBookRepository implements BookRepository {
    @Override
    public Flux<Book> findAll() {
        Book book1 = new Book("Book Title", "Book Author");

        return Flux.just(book1);
    }

    @Override
    public Mono<Book> findOne(Long id) {
        return null;
    }

    @Override
    public Mono<Book> save(Mono<Book> book) {
        return null;
    }
}
