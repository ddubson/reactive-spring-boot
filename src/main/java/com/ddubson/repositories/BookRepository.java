package com.ddubson.repositories;

import com.ddubson.models.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository {
    Flux<Book> findAll();

    Mono<Book> findOne(Long id);

    Mono<Book> save(Mono<Book> book);
}
