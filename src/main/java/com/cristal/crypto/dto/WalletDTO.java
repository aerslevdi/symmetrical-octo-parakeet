package com.cristal.crypto.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class WalletDTO implements Serializable {
    @ApiModelProperty(notes = "Wallet ID", required = true)
    private Long id;
    @ApiModelProperty(notes = "Wallet name")
    private String name;
    @ApiModelProperty(notes = "Wallet balance: coin, quantity", example = "{BTC : 50.0, USD: 50.0 }")
    private HashMap<String, Double> balance;

    public WalletDTO() {
        this.balance = new HashMap<>();
    }

    public WalletDTO(@NonNull String walletName, HashMap<String, Double> balance) {
        this.name = walletName;
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
