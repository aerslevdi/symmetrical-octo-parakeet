package com.cristal.crypto.controllers;


import com.cristal.crypto.dto.CoinDTO;
import com.cristal.crypto.dto.ExchangeDTO;
import com.cristal.crypto.dto.TransferDTO;
import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.CryptoCoin;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.services.WalletService;
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


@Api(description = "Exchange pertaining operations")
@Controller
@RequestMapping("/api")
public class OperationController {
    @Autowired
    WalletService walletService;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(OperationController.class);

    /**
     *
     * @param exchangeDTO = body with information for exchange
     * @requestparam quantity = Quantity of initial currency you want to trade
     * @return String with resulting balance of target currency
     * @throws NotFoundException
     */
    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Buy cryptocoins", response=String.class)
    @PostMapping("/wallets/buy/{id}")
    public ResponseEntity<String> buyCoin(@RequestBody ExchangeDTO exchangeDTO) throws Exception {
        walletService.buyCryptoCurrency(exchangeDTO);
        Wallet wallet = walletService.getWalletById(exchangeDTO.getWalletID());
        String response = "Your " + exchangeDTO.getExchangeTo().toUpperCase() + " balance: " + wallet.getBalance().get(exchangeDTO.getExchangeTo());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }
    /**
     *
     *
     * @return String with new balance of the wallet receiving the transfer
     * @throws NotFoundException
     */

    @ApiResponses(
            value = {@ApiResponse(code = 201, message = "The request was successful"),
                    @ApiResponse(code = 400, message = "There is missing or wrong information"),
                    @ApiResponse(code = 404, message = "Requested information does not exist")
            }
    )
    @ApiOperation(value = "Transfer from one wallet to another", response=String.class)
    @PostMapping("/wallets/transfer/{idFrom}/{idTo}/{coin}")
    public ResponseEntity<String > transfer(@RequestBody TransferDTO transferDTO) throws NotFoundException {
        String response = walletService.transferCoins(transferDTO);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

    }
}
