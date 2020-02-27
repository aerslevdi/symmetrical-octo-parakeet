package com.cristal.Crypto.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Entity
public class Wallet {

    @Id
    @Column
    @NotNull
    private String name;
    @Column
    private HashMap<CryptoCoin, Double> values;

}
