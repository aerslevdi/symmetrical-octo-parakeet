package com.cristal.Crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.LinkedHashMap;



@Getter
@Setter
public class CoinDTO implements Serializable {
    private LinkedHashMap<String, Double> coinsPrice = new LinkedHashMap<>();


}
