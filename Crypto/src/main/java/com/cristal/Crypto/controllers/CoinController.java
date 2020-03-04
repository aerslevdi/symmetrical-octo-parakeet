package com.cristal.Crypto.controllers;

import com.cristal.Crypto.entities.CryptoCoin;
import com.cristal.Crypto.entities.Currency;
import com.cristal.Crypto.services.CryptoCompareService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CoinController {
    @Autowired
    CryptoCompareService cryptoService;



    @GetMapping("/{id}")
    public ResponseEntity<CryptoCoin> getCoin(@PathVariable("id") String id)
            throws NotFoundException {
        CryptoCoin entity = cryptoService.getById(id);

        return new ResponseEntity<CryptoCoin>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/cryptocoins")
    public ResponseEntity<List<Currency>> getAll()
            throws NotFoundException {
        List<Currency> currencies = cryptoService.getAll();

        return new ResponseEntity<>(currencies, new HttpHeaders(), HttpStatus.OK);
    }

}
