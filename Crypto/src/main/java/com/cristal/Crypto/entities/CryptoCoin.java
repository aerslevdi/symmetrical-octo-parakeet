package com.cristal.Crypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCoin extends Currency {
    @Column
    private Double dollarValue;
    @Column
    private Double euroValue;


    @JsonProperty("USD")
    public void setDollarValue(Double dollarValue) {
        this.dollarValue = dollarValue;
    }

    @JsonProperty("EUR")
    public void setEuroValue(Double euroValue) {
        this.euroValue = euroValue;
    }
}
