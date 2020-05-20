package com.cristal.Crypto.controllers;


import com.cristal.Crypto.entities.Wallet;
import com.cristal.Crypto.services.WalletService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api")
public class OperationController {
    @Autowired
    WalletService walletService;

    @PostMapping("/wallets/buy/{id}/{from}/{to}")
    public ResponseEntity<String > buyCoin(@PathVariable Long id, @PathVariable String from, @PathVariable String to, @RequestParam String quantity) throws NotFoundException {
        Double q = Double.parseDouble(quantity);
        walletService.buyCryptoCurrency((id), q, from, to);
        Wallet wallet = walletService.getWalletById(id);
        String response = "Your " + to.toUpperCase() + " balance: " + wallet.getBalance().get(to);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }
    @PostMapping("/wallets/transfer/{idFrom}/{idTo}/{coin}")
    public ResponseEntity<String > transfer(@PathVariable Long idFrom, @PathVariable Long idTo, @PathVariable String coin, @RequestParam String quantity) throws NotFoundException {
        Double q = Double.parseDouble(quantity);
        String response = walletService.transferCoins(idFrom, idTo, q, coin);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }
}
