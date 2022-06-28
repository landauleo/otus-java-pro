package ru.otus.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(String name, String street, Collection<String> phoneNumbers) {
        Set<Phone> phones = phoneNumbers.stream().filter(StringUtils::hasText).distinct()
                .map(phoneNumber -> new Phone(null, phoneNumber.trim())).collect(Collectors.toSet());
        return clientRepository.save(new Client(null, name, new Address(null, street), phones));
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

}
