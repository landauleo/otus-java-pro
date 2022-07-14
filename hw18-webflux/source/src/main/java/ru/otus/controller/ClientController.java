package ru.otus.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;
import ru.otus.service.ClientService;


@RestController//therefore, the @ResponseBody isn't required.
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "clients/all", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Client> findAll() {
        log.info("╰( ͡° ͜ʖ ͡° )つ──☆*:・ﾟ Request for all clients in source app");
        return clientService.findAll();
    }

    @PostMapping(value = "clients", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Client> save(@RequestParam("name") String name) {
        log.info("╰( ͡° ͜ʖ ͡° )つ──☆*:・ﾟ Request to save client with name:{} in source app", name);
        return clientService.saveClient(name);
    }
}
