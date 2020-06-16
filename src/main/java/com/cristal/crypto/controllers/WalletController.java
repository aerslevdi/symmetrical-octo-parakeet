package com.cristal.crypto.controllers;


import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.services.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(description = "Wallet pertaining operations")
@Controller
@RequestMapping("/api")
public class WalletController {
    @Autowired
    WalletService walletService;
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<List<WalletDTO>> getALlWallets() throws NotFoundException{
        List<Wallet> walletsMap = walletService.getAll();
        List<WalletDTO> walletsDTO = walletsMap.stream().map(this::convertToDto)
                .collect(Collectors.toList());
        logger.info("Retrieving all wallets");
        return new ResponseEntity<>(walletsDTO, new HttpHeaders(), HttpStatus.OK);

    }

    /**
     *
     * @param id = ID from wallet
     * @return Wallet with same ID as passed by parameter
     * @throws NotFoundException
     */
    //TODO change to DTO
    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Get wallet by its ID", response=Wallet.class)
    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@PathVariable Long id) throws NotFoundException {
        WalletDTO wallet = convertToDto(walletService.getWalletById(id));
        logger.info("Getting wallet by ID");
        return new ResponseEntity<>(wallet, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param wallet = new wallet to be created
     * @return created wallet with ID
     * @throws NotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Create new wallet")
    @PostMapping("/wallets")
    public ResponseEntity<WalletDTO> addWallet(@RequestBody WalletDTO wallet) throws NotFoundException {
        Wallet newWallet = walletService.createWallet(convertToEntity(wallet));
        logger.info("Creating wallet");
        WalletDTO walletDTO = convertToDto(newWallet);
        return new ResponseEntity<>(walletDTO, new HttpHeaders(), HttpStatus.OK);
    }
    /**
     *
     * @param id = ID from wallet
     * @return String indicating success in deleting wallet
     * @throws NotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Delete an existing wallet")
    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<String> removeWallet(@PathVariable Long id) throws NotFoundException {
        if(walletService.getWalletById(id) != null){
         walletService.delete(id);}
         String response = "The wallet was deleted";
        logger.info("Removing wallet");
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
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Update by field an existing wallet", response=Wallet.class)
    @PatchMapping(path = "/wallets/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Wallet> patchWallet(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {
            Wallet walletById = walletService.getWalletById(id);
            Wallet walletPatched = walletService.applyPatchToXP(patch, walletById);
            walletPatched.setId(id);
            walletService.updateWallet(walletPatched);
            logger.info("Updating wallet");
            return ResponseEntity.ok(walletPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Full update an existing wallet")
    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@RequestBody WalletDTO walletDTO) throws NotFoundException {
        Wallet wallet = walletService.getWalletById(walletDTO.getId());

        Wallet newWallet = new Wallet();
        newWallet.setId(walletDTO.getId());
        newWallet.setWalletName(walletDTO.getName());
        newWallet.setBalance(walletDTO.getBalance());
        walletService.updateWallet(newWallet);
        logger.info("Updating wallet by PUT");
        return new ResponseEntity<>(walletDTO, HttpStatus.CREATED);

    }



    private WalletDTO convertToDto(Wallet wallet) {
        WalletDTO walletDTO = modelMapper.map(wallet, WalletDTO.class);
        walletDTO.setBalance(wallet.getBalance());
        walletDTO.setId(wallet.getId());
        walletDTO.setName(wallet.getWalletName());
        return walletDTO;
    }
    private Wallet convertToEntity(WalletDTO walletDTO) throws  NotFoundException {
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setBalance(walletDTO.getBalance());
        wallet.setWalletName(walletDTO.getName());
        if(walletDTO.getId()!=null){
            wallet.setId(walletDTO.getId());
        }
        return wallet;
    }

}
