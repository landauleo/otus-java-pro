package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();


    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyResponsible(key, value, Action.PUT.name());
    }

    @Override
    public void remove(K key) {
        V removed = cache.remove(key);
        notifyResponsible(key, removed, Action.REMOVE.name());
    }

    @Override
    public V get(K key) {
        V found = cache.get(key);
        notifyResponsible(key, found, Action.GET.name());
        return found;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyResponsible(K key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }

}
