package com.cristal.Crypto.controllers;


import com.cristal.Crypto.entities.Wallet;
import com.cristal.Crypto.services.CryptoCompareService;
import com.cristal.Crypto.services.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class WalletController {
    @Autowired
    WalletService walletService;

    @GetMapping("/wallets/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) throws NotFoundException {
        Wallet wallet = walletService.getWalletById(id);

        return new ResponseEntity<>(wallet, new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/wallets/")
    public ResponseEntity<Wallet> addWallet(@RequestBody Wallet wallet) throws NotFoundException {
        Wallet newWallet = walletService.createWallet(wallet);
        return new ResponseEntity<>(newWallet, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<String> removeWallet(@PathVariable Long id) throws NotFoundException {
        if(walletService.getWalletById(id) != null){
         walletService.delete(id);}
         String response = "The wallet was deleted";
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(path = "/wallets/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Wallet> patchWallet(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            Wallet walletById = walletService.getWalletById(id);
            Wallet walletPatched = walletService.applyPatchToXP(patch, walletById);
            walletPatched.setId(id);
            walletService.updateWallet(walletPatched);
            return ResponseEntity.ok(walletPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
