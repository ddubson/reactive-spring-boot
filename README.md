# Reactive Spring Boot

## Concepts

`Flux<T>` is a publisher of 0 or more T where T is any given type

`Mono<T>` is a publisher of 0 or 1 of T where T is any given type

`Flux.just` - factory method of a discrete number of 0 or more elements to be emitted by 
a Flux (publisher)

`Flux.generate<T>` - generate a signal stream of T, append a `.take(n)` to discretize
number of T generated to be consumed

`Mono.just` - factory method of a discrete number of 0 or 1 elements to be emitted by 
a Mono (publisher)

