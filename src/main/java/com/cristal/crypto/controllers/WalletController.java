package com.cristal.crypto.controllers;


import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.services.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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

@Api(description = "Wallet pertaining operations")
@Controller
@RequestMapping("/api")
public class WalletController {
    @Autowired
    WalletService walletService;


    /**
     *
     * @return List of all existing wallets
     * @throws NotFoundException
     */
    @ApiOperation(value = "Get list of all wallets")
    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getALlWallets() throws NotFoundException{
        List walletsMap = walletService.getAll();
        return new ResponseEntity<>(walletsMap, new HttpHeaders(), HttpStatus.OK);

    }

    /**
     *
     * @param id = ID from wallet
     * @return Wallet with same ID as passed by parameter
     * @throws NotFoundException
     */
    @ApiOperation(value = "Get wallet by its ID", response=Wallet.class)
    @GetMapping("/wallets/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) throws NotFoundException {
        Wallet wallet = walletService.getWalletById(id);

        return new ResponseEntity<>(wallet, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param wallet = new wallet to be created
     * @return created wallet with ID
     * @throws NotFoundException
     */
    @ApiOperation(value = "Create new wallet")
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> addWallet(@RequestBody Wallet wallet) throws NotFoundException {
        Wallet newWallet = walletService.createWallet(wallet);
        return new ResponseEntity<>(newWallet, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param id = ID from wallet
     * @return String indicating success in deleting wallet
     * @throws NotFoundException
     */
    @ApiOperation(value = "Delete an existing wallet")
    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<String> removeWallet(@PathVariable Long id) throws NotFoundException {
        if(walletService.getWalletById(id) != null){
         walletService.delete(id);}
         String response = "The wallet was deleted";
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     *
     * @param id = wallet id
     * @param patch =
     * [
     *   {
     *     "op":"replace",
     *     "path":"/name",
     *     "value":"new wallet name"
     *   }
     * ]
     * @return updated wallet
     */
    @ApiOperation(value = "Update an existing wallet", response=Wallet.class)
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
