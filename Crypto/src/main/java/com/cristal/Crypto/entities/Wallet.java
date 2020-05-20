package com.cristal.Crypto.entities;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;


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
    private String walletName;
    @Column
    private HashMap<String, Double> balance;

    public Wallet() {
        this.balance = new HashMap<>();
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
