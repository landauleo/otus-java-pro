package ru.otus.jdbc.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import static ru.otus.jdbc.mapper.SQLQuery.INSERT;
import static ru.otus.jdbc.mapper.SQLQuery.SELECT_ALL;
import static ru.otus.jdbc.mapper.SQLQuery.SELECT_BY_ID;
import static ru.otus.jdbc.mapper.SQLQuery.UPDATE;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;
    private final String entityName;
    private final String idField;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.entityName = entityClassMetaData.getName().toLowerCase();
        this.idField = entityClassMetaData.getIdField().getName().toLowerCase();
    }


    @Override
    public String getSelectAllSql() {
        return String.format(SELECT_ALL, entityName);
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(SELECT_BY_ID, entityName, idField);
    }

    @Override
    public String getInsertSql() {
        return String.format(INSERT, entityName, getInsertableFields(), getInsertableParams());
    }

    @Override
    public String getUpdateSql() {
        return String.format(UPDATE, entityName, getUpdatableFields(), idField);
    }

    private String getInsertableFields() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName().toLowerCase())
                .collect(Collectors.joining(", "));
    }

    private String getInsertableParams() {
        return String.join(", ", Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?"));
    }

    private String getUpdatableFields() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName().toLowerCase() + " = ?")
                .collect(Collectors.joining(", "));
    }

}
