package com.cristal.crypto.controllers;


import com.cristal.crypto.services.CryptoCompareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(description = "Exchange values operation")
@Controller
@RequestMapping("/api")
public class CoinController {
    @Autowired
    CryptoCompareService cryptoService;

    /**
     *
     * @param convert = optional parameter to convert cryptocoin to a real currency different than USD
     * @return List of cryptocoin and their rate either in default USD or requested by parameter exchange
     * @throws NotFoundException
     */
    @ApiOperation(value = "Return all Crytocoins value in USD by default", response= List.class)
    @GetMapping("/cryptocoins")
    public ResponseEntity<?> getAll(@RequestParam Optional<String>  convert)
            throws NotFoundException {
        return new ResponseEntity<>(cryptoService.getAll(convert.orElse("USD")), new HttpHeaders(), HttpStatus.OK);
    }


}
