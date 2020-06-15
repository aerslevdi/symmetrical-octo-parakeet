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
public class TransferDTO implements Serializable {
    @ApiModelProperty(notes = "transferring wallet ID")
    private Long fromWalletID;
    @ApiModelProperty(notes = "Receiving wallet ID")
    private Long toWalletID;
    @ApiModelProperty(notes = "Currency")
    private String coin;
    @ApiModelProperty(notes = "Quantity of currency to transfer")
    private Double quantity;
}
