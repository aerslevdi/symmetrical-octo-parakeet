package com.cristal.crypto.dto;

import com.cristal.crypto.entities.CryptoCoin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.LinkedHashMap;



@Getter
@Setter
public class CoinDTO implements Serializable {
    @ApiModelProperty(notes = "List of coin and its price")
    private LinkedHashMap<String, Double> coinsPrice = new LinkedHashMap<>();


}
