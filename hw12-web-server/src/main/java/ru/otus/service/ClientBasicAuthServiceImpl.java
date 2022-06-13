package ru.otus.service;


import static ru.otus.helpers.HashHelper.doesPasswordsMatch;

public class ClientBasicAuthServiceImpl implements BasicAuthService{

    private final DBServiceClient dbServiceClient;

    public ClientBasicAuthServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceClient.findByLogin(login)
                .map(client -> doesPasswordsMatch(password, client.getPassword()))
                .orElse(false);
    }

}
