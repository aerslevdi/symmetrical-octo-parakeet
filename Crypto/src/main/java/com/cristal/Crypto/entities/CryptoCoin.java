package com.cristal.Crypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cryptocoin")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class CryptoCoin extends Currency {
    @Column
    @JsonProperty("Symbol")
    private String symbol;
    @Column
    @JsonProperty("Name")
    private String name;
    @Column
    private Double price;
}
