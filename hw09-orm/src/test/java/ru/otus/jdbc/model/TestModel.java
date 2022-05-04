package ru.otus.jdbc.model;

import java.util.Objects;

import ru.otus.jdbc.annotation.Id;

public class TestModel {

    @Id
    private Long id;
    private String name;

    public TestModel() { //reflection doesn't work without default constructor
    }

    public TestModel(String name) {
        this.id = null;
        this.name = name;
    }

    public TestModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestModel testModel)) return false;
        return Objects.equals(id, testModel.id) && Objects.equals(name, testModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
