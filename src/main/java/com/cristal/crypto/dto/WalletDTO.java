package com.cristal.crypto.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class WalletDTO implements Serializable {
    @ApiModelProperty(notes = "Wallet ID", required = true)
    private Long id;
    @ApiModelProperty(notes = "Wallet name")
    private String name;
    @ApiModelProperty(notes = "Balance of wallet")
    private HashMap<String, Double> balance;

}
