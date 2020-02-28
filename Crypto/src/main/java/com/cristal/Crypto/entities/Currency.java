package com.cristal.Crypto.entities;



import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Currency {
    @Id
    @Column(unique = true)
    @NonNull
    private String currency;

    public void setName(String name) {
        this.currency = name;
    }

    public String getCurrency() {
        return currency;
    }
}
