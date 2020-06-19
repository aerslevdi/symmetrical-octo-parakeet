package com.cristal.crypto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDTO implements Serializable {

    @ApiModelProperty(notes = "Wallet ID")
    private Long walletID;
    @ApiModelProperty(notes = "Target currency")
    private String exchangeTo;
    @ApiModelProperty(notes = "Intial currency")
    private String exchangeFrom;
    @ApiModelProperty(notes = "price", hidden=true)
    private Double price= 0.0;
    @ApiModelProperty(notes = "Quantity of initial currency to exchange")
    private Double quantity;
}
