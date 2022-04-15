package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        if (msg == null) {
            throw new NullPointerException("Message should not be null");
        }
        map.put(msg.getId(), msg.clone());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id)); //if non-null, otherwise returns an empty
    }
}
