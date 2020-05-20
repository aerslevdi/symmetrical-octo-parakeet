package com.cristal.Crypto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
public class WalletDTO implements Serializable {
    private Long id;
    private String name;
    private HashMap<String, Double> balance;

}
