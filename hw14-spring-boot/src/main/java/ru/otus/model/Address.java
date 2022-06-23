package ru.otus.model;


import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
public class Address implements Cloneable {

    @Id
    private final Long id;

    @Nonnull
    @Column("street")
    private final String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    protected Address clone() {
        return new Address(this.id, this.street);
    }

}
