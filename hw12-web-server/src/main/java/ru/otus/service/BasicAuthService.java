package ru.otus.service;

public interface BasicAuthService {
    boolean authenticate(String login, String password);
}
