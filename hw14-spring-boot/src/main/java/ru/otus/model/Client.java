package ru.otus.model;


import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;


@Table("client")
public class Client implements Cloneable {

    @Id
    @Nonnull
    private final Long id;

    @Nonnull
    @Column("name")
    private final String name;

    //The @MappedCollection annotation can be used on a reference type (one-to-one relationship)
    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    //When using List and Map you must have an additional column for the position of a dataset in the List or the key value of the entity in the Map
    @Nonnull
    @MappedCollection(idColumn = "client_id", keyColumn = "id")
    private final Set<Phone> phones;

    //helps disambiguating multiple potentially available constructors taking parameters
    //and explicitly marking the one annotated as the one to be used to create entities.
    //With none or only a single constructor you can omit the annotation.
    @PersistenceConstructor
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    //именно Getter-ы нужны, чтобы резолвить переменные в шаблоне
    @Nonnull
    public Long getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Address getAddress() {
        return address;
    }

    @Nonnull
    public Set<Phone> getPhones() {
        return phones;
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return id.equals(client.id) && name.equals(client.name) && address.equals(client.address) && phones.equals(client.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }

}
