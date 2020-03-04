package com.cristal.Crypto.controllers;


import com.cristal.Crypto.entities.Wallet;
import com.cristal.Crypto.services.WalletService;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WalletController {

    WalletService walletService;
    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getAllWallet() throws NotFoundException {
        List<Wallet> list = walletService.getAll();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/wallets/add/{name}")
    public ResponseEntity<Wallet> addWallet(@PathVariable("name")String name) throws NotFoundException {
        Wallet newWallet = walletService.createWallet(name);
        return new ResponseEntity<>(newWallet, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/wallets/{id}/{coin}/balance")
    public ResponseEntity<String> getBalance(@PathVariable("id")Long id, @PathVariable("coin") String coin) throws NotFoundException {
        if(walletService.getAll().contains(id)){
            String total = "Your total balance of " + coin + " is: " + walletService.getCoinBalance(id, coin);

            return new ResponseEntity<>(total, new HttpHeaders(), HttpStatus.OK);

        }else {
            throw new NotFoundException("Wallet not found");
        }


    }
}
