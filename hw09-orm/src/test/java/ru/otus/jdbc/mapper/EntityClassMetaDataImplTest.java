package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.jdbc.annotation.Id;
import ru.otus.jdbc.model.TestModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntityClassMetaDataImplTest {

    private static final EntityClassMetaData<TestModel> entityClassMetaData = new EntityClassMetaDataImpl<>(TestModel.class);

    @Test
    void testGetFieldsWithoutId() {
        assertEquals(1, entityClassMetaData.getFieldsWithoutId().size());
        assertEquals("name", entityClassMetaData.getFieldsWithoutId().get(0).getName());
    }

    @Test
    void testGetAllFields() {
        assertEquals(2, entityClassMetaData.getAllFields().size());
        assertEquals("id", entityClassMetaData.getAllFields().get(0).getName());
        assertNotNull(entityClassMetaData.getAllFields().get(0).getAnnotation(Id.class));
        assertEquals("name", entityClassMetaData.getAllFields().get(1).getName());
        assertNull(entityClassMetaData.getAllFields().get(1).getAnnotation(Id.class));
    }

    @Test
    void testGetConstructor() {
        assertNotNull(entityClassMetaData.getConstructor());
        assertEquals(TestModel.class, entityClassMetaData.getConstructor().getDeclaringClass());
    }

}