package ru.otus.service;


public class ClientBasicAuthServiceImpl implements BasicAuthService{

    private final DBServiceClient dbServiceClient;

    public ClientBasicAuthServiceImpl(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceClient.findByLogin(login)
                .map(client -> client.getPassword().equals(password))
                .orElse(false);
    }

}
