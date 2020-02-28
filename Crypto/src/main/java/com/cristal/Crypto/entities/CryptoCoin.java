package com.cristal.Crypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class CryptoCoin extends Currency {
    @Column
    private double dollarValue;
    @Column
    private double euroValue;


    @JsonProperty("USD")
    public void setDollarValue(Double dollarValue) {
        this.dollarValue = dollarValue;
    }

    @JsonProperty("EUR")
    public void setEuroValue(Double euroValue) {
        this.euroValue = euroValue;
    }

    public Double getDollarValue() {
        return dollarValue;
    }

    public Double getEuroValue() {
        return euroValue;
    }

    @Override
    public String toString() {
        return "CryptoCoin{" +
                "dollarValue=" + dollarValue +
                ", euroValue=" + euroValue +
                '}';
    }
}
