package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.otus.jdbc.repository.DataTemplate;
import ru.otus.jdbc.repository.DataTemplateException;
import ru.otus.jdbc.repository.executor.DbExecutor;

import static ru.otus.jdbc.mapper.SQLQuery.INSERT;
import static ru.otus.jdbc.mapper.SQLQuery.SELECT_ALL;
import static ru.otus.jdbc.mapper.SQLQuery.SELECT_BY_ID;
import static ru.otus.jdbc.mapper.SQLQuery.UPDATE;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, SELECT_BY_ID, List.of(id), resultSet -> {
            try (resultSet) {
                if (resultSet.next()) {
                    return mapToEntity(resultSet);
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, SELECT_ALL, Collections.emptyList(), resultSet -> {
            try (resultSet) {
                List<T> instances = new ArrayList<>();
                while (resultSet.next()) {
                    instances.add(mapToEntity(resultSet));
                }
                return instances;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unable to execute findAll()"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(connection, INSERT, getValuesToInsert(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            dbExecutor.executeStatement(connection, UPDATE, getValuesToUpdate(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T mapToEntity(ResultSet resultSet) throws InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T newInstance = entityClassMetaData.getConstructor().newInstance();

        for (Field field : entityClassMetaData.getAllFields()) {
            String fieldName = field.getName();
            boolean canAccess = field.canAccess(newInstance);
            field.setAccessible(true);
            field.set(newInstance, resultSet.getObject(fieldName));
            field.setAccessible(canAccess);
        }

        return newInstance;
    }

    private List<Object> getValuesToInsert(T object) throws IllegalAccessException {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> values= new ArrayList<>(fields.size());

        for (Field field : fields) {
            boolean canAccess = field.canAccess(object);
            field.setAccessible(true);
            values.add(field.get(object));
            field.setAccessible(canAccess);
        }

        return values;
    }

    private List<Object> getValuesToUpdate(T object) throws IllegalAccessException {
        List<Object> values = new ArrayList<>(getValuesToInsert(object));

        Field idField = entityClassMetaData.getIdField();
        boolean canAccess = idField.canAccess(object);
        idField.setAccessible(true);
        values.add(idField.get(object));
        idField.setAccessible(canAccess);

        return values;
    }

}
