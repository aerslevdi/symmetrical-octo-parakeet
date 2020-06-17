package com.cristal.crypto.controllers;


import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.exception.ElementNotFoundException;
import com.cristal.crypto.services.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);


    /**
     *
     * @return List of all existing wallets
     * @throws NotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Get list of all wallets")
    @GetMapping("/wallets")
    public ResponseEntity<List<WalletDTO>> getALlWallets() throws ElementNotFoundException{
        List<WalletDTO> walletsMap = walletService.getAll();

        logger.info("Retrieving all wallets");
        return new ResponseEntity<>(walletsMap, new HttpHeaders(), HttpStatus.OK);

    }

    /**
     *
     * @param id = ID from wallet
     * @return Wallet with same ID as passed by parameter
     * @throws ElementNotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Get wallet by its ID", response=Wallet.class)
    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id) throws ElementNotFoundException {
        WalletDTO wallet = walletService.getWalletById(id);
        logger.info("Getting wallet by ID");
        return new ResponseEntity<>(wallet, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param walletDTO = new wallet to be created
     * @return created wallet with ID
     * @throws ElementNotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Create new wallet")
    @PostMapping("/wallets")
    public ResponseEntity<WalletDTO> addWallet(@RequestBody WalletDTO walletDTO) throws ElementNotFoundException {
        walletService.createWallet(walletDTO);
        logger.info("Creating wallet");
        return new ResponseEntity<>(walletDTO, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param id = ID from wallet
     * @return String indicating success in deleting wallet
     * @throws ElementNotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Delete an existing wallet")
    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<String> removeWallet(@PathVariable Long id) throws ElementNotFoundException {
        if(walletService.getWalletById(id) != null){
         walletService.delete(id);}
         String response = "The wallet was deleted";
        logger.info("Removing wallet");
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }


    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Update an existing wallet")
    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@PathVariable Long id, @RequestBody WalletDTO walletDTO) throws ElementNotFoundException {
        walletDTO.setId(id);
        walletService.updateWallet(walletDTO);
        logger.info("Updating wallet by PUT");
        return new ResponseEntity<>(walletDTO, HttpStatus.CREATED);

    }





}
