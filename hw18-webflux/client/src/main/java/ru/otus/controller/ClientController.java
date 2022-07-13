package ru.otus.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;


@RestController
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final WebClient client;

    public ClientController(WebClient.Builder builder) {
        client = builder
                .baseUrl("http://localhost:8081/source/api/v1/clients")
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> findAll(@PathVariable("seed") long seed) {
        log.info("(눈_눈) Request for all clients in client app");

        return client.get()
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(Client.class)
                .doOnNext(val -> log.info("val:{}", val));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Client> save(@RequestParam("name") String name) {
        log.info("(눈_눈) Request to save client with name:{} in client app", name);

        return client.get().uri(String.format("?name=%s", name))
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToMono(Client.class)
                .doOnNext(val -> log.info("val:{}", val));
    }
}
