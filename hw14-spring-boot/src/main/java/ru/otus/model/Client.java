package ru.otus.model;


import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;


@Table("client")
public class Client implements Persistable<Long>, Cloneable {

    @Id
    @Nonnull
    private final Long id;

    @Nonnull
    @Column("name")
    private final String name;

    //The @MappedCollection annotation can be used on a reference type (one-to-one relationship)
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    //When using List and Map you must have an additional column for the position of a dataset in the List or the key value of the entity in the Map
    @MappedCollection(idColumn = "client_id", keyColumn = "number")
    private final List<Phone> phones;

    @Transient
    private final boolean isNew;

    //helps disambiguating multiple potentially available constructors taking parameters
    //and explicitly marking the one annotated as the one to be used to create entities.
    //With none or only a single constructor you can omit the annotation.
    @PersistenceConstructor
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.isNew = id == null;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return id.equals(client.id) && name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }


}
