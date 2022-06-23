package ru.otus.model;


import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone implements Cloneable {

    @Id
    @Nonnull
    private final Long id;

    @Column("number")
    @Nonnull
    private final String number;

    @PersistenceConstructor
    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Nonnull
    public Long getId() {
        return id;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                ", number='" + getNumber() + '\'' +
                '}';
    }

    @Override
    protected Phone clone() {
        return new Phone(getId(), getNumber());
    }

}
