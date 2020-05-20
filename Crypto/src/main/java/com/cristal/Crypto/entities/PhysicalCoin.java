package com.cristal.Crypto.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@RequiredArgsConstructor
@Entity
@Table(name = "physicalcoin")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PhysicalCoin extends Currency {
    @Column
    private Double sellValue;
}
