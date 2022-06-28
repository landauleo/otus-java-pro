package ru.otus.service;

import ru.otus.model.Client;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;

public interface ClientService {

    Client saveClient(String name, String street, Collection<String> phones);

    Iterable<Client> findAll();

}
