package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;

public interface ClientService {

    Mono<Client> saveClient(String name);

    Flux<Client> findAll();
}
