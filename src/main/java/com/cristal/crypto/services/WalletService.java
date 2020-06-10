package com.cristal.crypto.services;

import com.cristal.crypto.entities.ExchangeCoin;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.repositories.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CryptoCompareService cryptoService;
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Wallet> getAll() throws NotFoundException{
        List<Wallet> walletList = walletRepository.findAll();
        if (walletList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return walletList;
        }
    }
    @Transactional(readOnly = true)
    public Wallet getWalletById(Long id) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return wallet.get();
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            walletRepository.deleteById(id);
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }
    @Transactional
    public Wallet createWallet(Wallet wallet) throws NotFoundException {
            walletRepository.save(wallet);
            return wallet;
    }

    @Transactional
    public Wallet withdrawCoin(Long id, String coin, Double quantity) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            if(wallet1.getAllCoin(coin) != null){
                Double base = wallet1.getAllCoin(coin);
                quantity = base - quantity;
            }
            wallet1.getBalance().put(coin, quantity);
            entityManager.merge(wallet1);
            return wallet1;
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }
    @Transactional
    public Wallet depositCoin(Long id, String coin, Double quantity) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            if(wallet1.getAllCoin(coin) != null){
                Double base = wallet1.getAllCoin(coin);
                quantity += base;
            }
            wallet1.getBalance().put(coin, quantity);
            entityManager.merge(wallet1);
            return wallet1;
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }
    @Transactional
    public void updateWallet(Wallet wallet) throws NotFoundException{
        Optional<Wallet> walletOpt = Optional.ofNullable(wallet);
        if(walletOpt.isPresent() && getWalletById(wallet.getId())!=null){
            entityManager.merge(walletOpt.get());
        }

    }

    @Transactional(readOnly = true)
    public String getCoinBalance(Long id, String coin) throws NotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();

            return coin.toUpperCase() + " : " + wallet1.getBalance().get(coin);
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }

    @Transactional(readOnly = true)
    public String getFullBalance(Long id) throws NotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            return wallet1.getBalance().toString();
        } else {
            throw new NotFoundException("No wallet record exist for given id");
        }
    }
    @Transactional
    public void buyCryptoCurrency(Long id, Double quantity, String to, String from) throws NotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        Double total = 0.0;
        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            ExchangeCoin cCoin = cryptoService.getById(from, to);
            Double ratio = cCoin.getExchangeRate();
            total = quantity * ratio;
            if(wallet1.getAllCoin(to) != null){
            Double coinPrv = wallet1.getAllCoin(to);
            total = coinPrv + total;}
            wallet1.getBalance().put(to, total);
            this.updateWallet(wallet1);
        } else{
            throw new NotFoundException("Wallet not registered");
        }
    }
    @Transactional
    public String transferCoins(Long idFrom, Long idTo, Double quantity, String coin) throws NotFoundException {
        Optional<Wallet> wallet1 = walletRepository.findById(idFrom);
        Optional<Wallet> wallet2 = walletRepository.findById(idTo);
        try{
        if(wallet1.isPresent() && wallet2.isPresent()){
            Wallet walletFrom = wallet1.get();
            if(walletFrom.getAllCoin(coin)>= quantity){
                depositCoin(idTo, coin, quantity);
                withdrawCoin(idFrom, coin, quantity);
            }
        }
        return wallet2.get().getWalletName()+" new balance=> "+coin.toUpperCase()+ "= " + wallet2.get().getAllCoin(coin);
        }catch(NotFoundException e){
            throw new NotFoundException("Wallet not found");
        }


    }
    @Transactional
    public Wallet applyPatchToXP(JsonPatch patch, Wallet wallet) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(wallet, JsonNode.class));
        return objectMapper.treeToValue(patched, Wallet.class);
    }

}
