package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.otus.jdbc.annotation.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String className;

    private final Constructor<T> constructor;

    private final Field idField;

    private final List<Field> fields;

    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.className = clazz.getSimpleName(); //clazz.getName() returns ru.otus.jdbc.mapper.client

        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to get constructor of class " + className);
        }

        this.idField = Arrays.stream(clazz.getDeclaredFields()).
                filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Id found by class "+ className));

        this.fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());

        this.fieldsWithoutId = Arrays.stream(clazz.getDeclaredFields()).
                filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

}
