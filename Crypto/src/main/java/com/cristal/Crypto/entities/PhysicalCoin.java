package com.cristal.Crypto.entities;

import javax.persistence.Column;

public class PhysicalCoin extends Currency {
    @Column
    private Double sellValue;
}
