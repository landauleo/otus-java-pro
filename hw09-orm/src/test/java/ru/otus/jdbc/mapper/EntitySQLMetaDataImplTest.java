package ru.otus.jdbc.mapper;


import org.junit.jupiter.api.Test;
import ru.otus.jdbc.model.TestModel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EntitySQLMetaDataImplTest {

    private static final EntityClassMetaData<TestModel> entityClassMetaData = new EntityClassMetaDataImpl<>(TestModel.class);
    private static final EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);

    @Test
    void testSelectAllSql() {
        assertAll(() -> {
            assertDoesNotThrow(entitySQLMetaData::getSelectAllSql);
            assertEquals("SELECT * FROM testmodel", entitySQLMetaData.getSelectAllSql());
        });
    }

    @Test
    void testSelectByIdSql() {
        assertAll(() -> {
            assertDoesNotThrow(entitySQLMetaData::getSelectByIdSql);
            assertEquals("SELECT * FROM testmodel WHERE id = ?", entitySQLMetaData.getSelectByIdSql());
        });
    }

    @Test
    void testInsertSql() {
        assertAll(() -> {
            assertDoesNotThrow(entitySQLMetaData::getInsertSql);
            assertEquals("INSERT INTO testmodel (name) VALUES (?)", entitySQLMetaData.getInsertSql());
        });
    }

    @Test
    void testUpdateSql() {
        assertAll(() -> {
            assertDoesNotThrow(entitySQLMetaData::getUpdateSql);
            assertEquals("UPDATE testmodel set name = ? WHERE id = ?", entitySQLMetaData.getUpdateSql());
        });
    }

}