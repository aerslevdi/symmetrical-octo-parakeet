package com.cristal.Crypto.controllers;

import com.cristal.Crypto.entities.CryptoCoin;
import com.cristal.Crypto.entities.Currency;
import com.cristal.Crypto.entities.ExchangeCoin;
import com.cristal.Crypto.services.CryptoCompareService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/api")
public class CoinController {
    @Autowired
    CryptoCompareService cryptoService;



    @GetMapping("/cryptocoins")
    public ResponseEntity<?> getAll(@RequestParam Optional<String>  convert)
            throws NotFoundException {


        return new ResponseEntity<>(cryptoService.getAll(convert.orElse("USD")), new HttpHeaders(), HttpStatus.OK);
    }


}
