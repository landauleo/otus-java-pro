package ru.otus.model;


import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
public class Phone implements Cloneable, Persistable<Long> {

    @Id
    @Nonnull
    private final Long id;

    @Nonnull
    private final String number;

    @Transient
    private final boolean isNew;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
        this.isNew = id == null;
    }

    @Override
    protected Phone clone() {
        return new Phone(this.id, this.number);
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
