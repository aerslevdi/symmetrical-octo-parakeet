package com.cristal.Crypto.entities;


import lombok.NonNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Iterator;


@Entity
@Table(name="wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NonNull
    private String walletName;
    @Column
    private HashMap<String, Double> prices;

    public Wallet(String name) {
        this.walletName = name;
        this.prices = new HashMap<>();
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public void setQuantity(String coin, Double quantity) {
       prices.put(coin, quantity);
    }
    public Boolean getCurrency(String coin){
        return prices.containsKey(coin);
    }

    public Long getId() {
        return id;
    }
    public Double getTotalCoin(String coin){
        return prices.get(coin);
    }

    public HashMap<String, Double> getPrices() {
        return prices;
    }
    public String stringPrices(){

        return "sdf";
    }
    @Override
    public String toString() {
        return "Wallet{" +
                "walletName ='" + walletName + '\'' +
                ", prices=" + prices +
                '}';
    }
}
