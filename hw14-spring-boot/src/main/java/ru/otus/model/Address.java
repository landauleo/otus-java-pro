package ru.otus.model;


import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class Address implements Cloneable, Persistable<Long> {

    @Id
    @Nonnull
    private final Long id;

    @Nonnull
    private final String street;

    @Transient
    private final boolean isNew;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
        this.isNew = id == null;
    }


    @Override
    protected Address clone() {
        return new Address(this.id, this.street);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

}
