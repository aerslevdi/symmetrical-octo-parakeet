package com.cristal.crypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExchangeCoin extends Currency {
    @Column
    private Double exchangeRate;
    @Column
    private String exchangeCoin;
}
