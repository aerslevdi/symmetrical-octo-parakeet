package com.cristal.Crypto.entities;

import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
@RequiredArgsConstructor
public class PhysicalCoin extends Currency {
    @Column
    private Double sellValue;
}
