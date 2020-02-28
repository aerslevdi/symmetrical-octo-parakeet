package com.cristal.Crypto.services;

import com.cristal.Crypto.entities.CryptoCoin;
import com.cristal.Crypto.entities.Wallet;
import com.cristal.Crypto.repositories.WalletRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    private CryptoCompareService cryptoService;

    public List<Wallet> getAll() {
        List<Wallet> walletList = walletRepository.findAll();
        if (walletList.isEmpty()) {
            return new ArrayList<Wallet>();
        } else {
            return walletList;
        }
    }

    public Wallet getWalletById(Long id) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    public void delete(Long id) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            walletRepository.deleteById(id);
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    public Wallet createWallet(Wallet wallet) {
        walletRepository.save(wallet);
        return wallet;
    }

    public Wallet updateWallet(Long id, String coin, Double quantity) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            wallet1.getPrices().put(coin, quantity);

            return wallet1;
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    public Double getCoinBalance(Long id, String coin) throws NotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();

            return wallet1.getPrices().get(coin);
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    public String getFullBalance(Long id) throws NotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();

            return wallet1.getPrices().toString();
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    public String buyCryptoCurrency(Long id, Double income, String exchange,String coin) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        Double total = 0.0;
        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
                    CryptoCoin cCoin = cryptoService.getById(coin);
                    Double coinTotal = 0.0;
                    if (exchange.equalsIgnoreCase("usd")) {
                        Double ratio = cCoin.getDollarValue();
                        coinTotal = income / ratio;

                    } else if (exchange.equalsIgnoreCase("eur")) {
                        Double ratio = cCoin.getEuroValue();
                        coinTotal = income / ratio;
                    }
                    Double coinPrv = wallet1.getTotalCoin(coin);
                    total = coinPrv + coinTotal;
                    this.updateWallet(id, coin, total);
        } else{
            throw new NotFoundException("Wallet not registered");
        }

        return "Yor final " + coin + " balance is: " + total;
    }
    public String sellCryptoCurrency(Long id, Double income, String exchange,String coin) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        Double total = 0.0;
        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            CryptoCoin cCoin = cryptoService.getById(exchange);
            Double coinTotal = 0.0;
            if (exchange.equalsIgnoreCase("eth")) {
                Double ratio = cCoin.getDollarValue();
                coinTotal = income * ratio;

            } else if (exchange.equalsIgnoreCase("btc")) {
                Double ratio = cCoin.getEuroValue();
                coinTotal = income * ratio;
            }
            Double coinPrv = wallet1.getTotalCoin(coin);
            total = coinPrv + coinTotal;
            this.updateWallet(id, coin, total);
        } else{
            throw new NotFoundException("Wallet not registered");
        }

        return "Yor final " + coin + " balance is: " + total;
    }




}
