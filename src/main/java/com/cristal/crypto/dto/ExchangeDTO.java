package com.cristal.crypto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExchangeDTO {
    @ApiModelProperty(notes = "Target currency")
    private String name;
    @ApiModelProperty(notes = "Intial currency")
    private String exchange;
    @ApiModelProperty(notes = "price", hidden=true)
    private Double price;
    @ApiModelProperty(notes = "Quantity of initial currency to exchange")
    private Double quantity;
}
