package ru.otus.jdbc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.model.TestModel;
import ru.otus.jdbc.repository.executor.DbExecutorImpl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class DataTemplateJdbcTest {

    private final EntityClassMetaData<TestModel> entityClassMetaData = new EntityClassMetaDataImpl<>(TestModel.class);

    private final EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);

    private final DataTemplateJdbc<TestModel> dataTemplate = new DataTemplateJdbc<>(new DbExecutorImpl(), entitySQLMetaData, entityClassMetaData);

    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("demoDB")
            .withUsername("usr")
            .withPassword("pwd")
            .withClasspathResourceMapping("00_createTables.sql", "/docker-entrypoint-initdb.d/00_createTables.sql", BindMode.READ_ONLY)
            .withClasspathResourceMapping("01_insertData.sql", "/docker-entrypoint-initdb.d/01_insertData.sql", BindMode.READ_ONLY);

    @Test
    void findById() {
        assertAll(() -> {
            assertDoesNotThrow(() -> dataTemplate.findById(makeConnection(), 1L));
            assertThat(dataTemplate.findById(makeConnection(), 1L)).isPresent();
            assertEquals(new TestModel(1L, "name1"), dataTemplate.findById(makeConnection(), 1L).get());
            assertFalse(dataTemplate.findById(makeConnection(), Integer.MAX_VALUE).isPresent());
        });
    }

    @Test
    void findAll() {
        List<TestModel> list = List.of(new TestModel(1L, "name1"), new TestModel(2L, "name2"));

        assertAll(() -> {
            assertDoesNotThrow(() -> dataTemplate.findAll(makeConnection()));
            assertFalse(dataTemplate.findAll(makeConnection()).isEmpty());
            assertEquals(list, dataTemplate.findAll(makeConnection()));
        });
    }

    @Test
    void insert() throws SQLException {
        long testModelId = 3;

        TestModel testModel = new TestModel("кря-кря");
        TestModel testModelWithId = new TestModel(testModelId, "кря-кря");
        int size = dataTemplate.findAll(makeConnection()).size() + 1;

        assertAll(() -> {
            assertEquals(testModelId, dataTemplate.insert(makeConnection(), testModel));
            assertThat(dataTemplate.findById(makeConnection(), testModelId)).isPresent();
            assertEquals(testModelWithId, dataTemplate.findById(makeConnection(), testModelId).get());
            assertThat(dataTemplate.findAll(makeConnection()).size()).isEqualTo(size);
        });
    }

    @Test
    void update() throws SQLException {
        long testModelId = 1L;

        TestModel testModel = new TestModel("миииииааауууу");
        TestModel testModelWithId = new TestModel(testModelId, "миииииааауууу");
        int size = dataTemplate.findAll(makeConnection()).size();

        assertAll(() -> {
            assertThrows(DataTemplateException.class, () -> dataTemplate.update(makeConnection(), testModel));
            assertDoesNotThrow(() -> dataTemplate.update(makeConnection(), testModelWithId));
            assertThat(dataTemplate.findById(makeConnection(), testModelId)).isPresent();
            assertEquals(testModelWithId, dataTemplate.findById(makeConnection(), testModelId).get());
            assertThat(dataTemplate.findAll(makeConnection()).size()).isEqualTo(size);
        });
    }

    private Properties getConnectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", postgresqlContainer.getUsername());
        props.setProperty("password", postgresqlContainer.getPassword());
        props.setProperty("ssl", "false");
        return props;
    }

    private Connection makeConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(postgresqlContainer.getJdbcUrl(), getConnectionProperties());
        connection.setAutoCommit(true);
        return connection;
    }

}