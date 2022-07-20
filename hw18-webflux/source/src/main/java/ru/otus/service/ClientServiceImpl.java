package ru.otus.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Mono<Client> saveClient(String name) {
        Client savedClient = clientRepository.save(new Client(null, name));
        return Mono.justOrEmpty(savedClient); //if I had more time I'd switch to r2dbc postgresql
    }

    @Override
    public Flux<Client> findAll() {
        return Flux.defer(() -> Flux.fromIterable(clientRepository.findAll())); //if I had more time I'd switch to r2dbc postgresql
    }

}
