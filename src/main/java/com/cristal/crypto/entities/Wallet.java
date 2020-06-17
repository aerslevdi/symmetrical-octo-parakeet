package com.cristal.crypto.entities;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;


@Entity
@Table(name="wallets")
@Getter
@Setter
public class Wallet {

    @Id
    @ApiModelProperty(notes = "Wallet identifier automatically generated", required = true,hidden = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NonNull
    @ApiModelProperty(notes = "Wallet name")
    private String walletName;
    @Column
    @ApiModelProperty(notes = "Wallet balance: coin, quantity", example = "{BTC : 50.0, USD: 50.0 }")
    @Lob
    private HashMap<String, Double> balance;

    public Wallet() {
        this.balance = new HashMap<>();
    }

    public Wallet(@NonNull String walletName, HashMap<String, Double> balance) {
        this.walletName = walletName;
        this.balance = balance;
    }

    public void setQuantity(String coin, Double quantity) {
       balance.put(coin, quantity);
    }
    public Boolean getCurrency(String coin){
        return balance.containsKey(coin);
    }
    public Double getAllCoin(String coin){
        return balance.get(coin);
    }
}
