package com.cristal.crypto.services;

import com.cristal.crypto.controllers.WalletController;
import com.cristal.crypto.dto.ExchangeDTO;
import com.cristal.crypto.dto.TransferDTO;
import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.exception.ConflictException;
import com.cristal.crypto.exception.ElementNotFoundException;
import com.cristal.crypto.repositories.WalletRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CryptoCompareService cryptoService;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    /**
     *
     * @return List of all existing wallets
     * @throws ElementNotFoundException
     */
    @Transactional(readOnly = true)
    public List<WalletDTO> getAll() throws ElementNotFoundException{
        List<Wallet> walletList = walletRepository.findAll();
        if (walletList.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<WalletDTO> walletsDTO = walletList.stream().map(this::convertToDto).collect(Collectors.toList());
            return walletsDTO;
        }
    }
    /**
     *
     * @param id = ID of existing wallet
     * @return Wallet with ID matched with one supplied by parameter
     * @throws NotFoundException
     */
    @Transactional(readOnly = true)
    public WalletDTO getWalletById(Long id) throws ElementNotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            return convertToDto(wallet.get());
        } else {
            ElementNotFoundException ex =  new ElementNotFoundException("No wallet record exist for given id");
            logger.error(ex.getMessage());
            throw ex;
        }
    }
    /**
     *
     * @param id = ID of wallet to delte
     * @throws ElementNotFoundException
     */
    @Transactional
    public void delete(Long id) throws ElementNotFoundException {
        try{
            walletRepository.deleteById(id);
        } catch (ElementNotFoundException ex){
            logger.error(ex.getMessage());
            throw new ElementNotFoundException("No wallet record exist for given id");
        }
    }
    /**
     *
     * @param walletDTO = new wallet
     * @return new wallet with ID
     * @throws NotFoundException
     */
    @Transactional
    public WalletDTO createWallet(WalletDTO walletDTO) throws ElementNotFoundException {
        try{
            Wallet wallet = walletRepository.save(convertToEntity(walletDTO));
            walletDTO.setId(wallet.getId());
            return walletDTO;
        }
        catch (ElementNotFoundException ex){
            logger.error(ex.getMessage());
            throw new ElementNotFoundException("No wallet record exist for given id");
        }
    }
    /**
     *
     * @param id = ID of wallet
     * @param coin = ID of currency to withdraw
     * @param quantity = quantity of currency to withdraw
     * @return updated wallet
     * @throws ElementNotFoundException
     */
    @Transactional
    public Wallet withdrawCoin(Long id, String coin, Double quantity) throws ElementNotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            if(wallet1.getAllCoin(coin) != null){
                Double base = wallet1.getAllCoin(coin);
                quantity = base - quantity;
            }
            wallet1.getBalance().put(coin, quantity);
            walletRepository.save(wallet1);
            return wallet1;
        } else {
            ElementNotFoundException ex =  new ElementNotFoundException("No wallet record exist for given id");
            logger.error(ex.getMessage());
            throw ex;
        }
    }
    /**
     *
     * @param id = ID of wallet receiving the deposit
     * @param coin = ID of currency being deposited
     * @param quantity = quantity of currency being deposited
     * @return List of cryptocurrency and its value in the provided by parameter currency
     * @throws ElementNotFoundException
     */
    @Transactional
    public Wallet depositCoin(Long id, String coin, Double quantity) throws ElementNotFoundException {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            if(wallet1.getAllCoin(coin) != null){
                Double base = wallet1.getAllCoin(coin);
                quantity += base;
            }
            wallet1.getBalance().put(coin, quantity);
            walletRepository.save(wallet1);
            return wallet1;
        } else {
            throw new ElementNotFoundException("No wallet record exist for given id");
        }
    }
    /**
     *
     * @param walletDTO = wallet being updated
     * @throws ElementNotFoundException
     */
    @Transactional
    public void updateWallet(WalletDTO walletDTO) throws ElementNotFoundException{
        Optional<WalletDTO> walletOpt = Optional.ofNullable(walletDTO);
        if(walletOpt.isPresent() && getWalletById(walletDTO.getId())!=null){
            Wallet wallet = convertToEntity(walletOpt.get());
            walletRepository.findById(wallet.getId())
                    .map(walletTemp -> {
                        walletTemp.setWalletName(wallet.getWalletName());
                        walletTemp.setBalance(wallet.getBalance());
                        walletTemp.setId(wallet.getId());
                        return convertToDto(walletRepository.save(walletTemp));
                    });
        }

    }
    /**
     *
     * @param id = ID of wallet
     * @param coin = ID of currency being consulted
     * @return String informing balance of consulted currency
     * @throws ElementNotFoundException
     */
    @Transactional(readOnly = true)
    public String getCoinBalance(Long id, String coin) throws ElementNotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            if(wallet1.getCurrency(coin)!=null){
            return coin.toUpperCase() + " : " + wallet1.getBalance().get(coin);}
            else{
                return coin.toUpperCase() + ": 0.0";
            }
        } else {
            throw new ElementNotFoundException("No wallet record exist for given id");
        }
    }

    /**
     *
     * @param id = ID of wallet
     * @return full balance of consulted wallet
     * @throws ElementNotFoundException
     */
    @Transactional(readOnly = true)
    public String getFullBalance(Long id) throws ElementNotFoundException{
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            Wallet wallet1 = wallet.get();
            return wallet1.getBalance().toString();
        } else {
            throw new ElementNotFoundException("No wallet record exist for given id");
        }
    }

    /**
     *
     * @throws ConflictException
     */
    @Transactional
    public void buyCryptoCurrency(ExchangeDTO exchangeDTO) throws ConflictException {
        Optional<Wallet> walletOpt = walletRepository.findById(exchangeDTO.getWalletID());
        Double total = 0.0;
        if (walletOpt.isPresent()) {
            Wallet wallet = walletOpt.get();
            ExchangeDTO cCoin = cryptoService.getById(exchangeDTO);
            Double ratio = cCoin.getPrice();
            Boolean isCoin = wallet.getCurrency(exchangeDTO.getExchangeTo());
            Double available = wallet.getAllCoin(exchangeDTO.getExchangeFrom());
                if(available >= exchangeDTO.getQuantity()){
                    total = exchangeDTO.getQuantity() * ratio;
                    Double balance = available - exchangeDTO.getQuantity();
                    wallet.getBalance().put(exchangeDTO.getExchangeFrom(), balance);
                    if(isCoin){
                        Double coinPrv = wallet.getAllCoin(exchangeDTO.getExchangeTo());
                        total = coinPrv + total;}
                    wallet.getBalance().put(exchangeDTO.getExchangeTo(), total);
                    this.updateWallet(convertToDto(wallet));}
                else{
                    throw new ConflictException("Amount available insufficient for transaction");
            }
        } else{
            throw new ElementNotFoundException("Wallet not registered");
        }
    }

    /**
     *
     * @throws ElementNotFoundException
     */
    @Transactional
    public String transferCoins(TransferDTO transferDTO) throws ElementNotFoundException {
        Optional<Wallet> wallet1 = walletRepository.findById(transferDTO.getFromWalletID());
        Optional<Wallet> wallet2 = walletRepository.findById(transferDTO.getToWalletID());
        try{
        if(wallet1.isPresent() && wallet2.isPresent()){
            Wallet walletFrom = wallet1.get();
            if(walletFrom.getAllCoin(transferDTO.getCoin())>= transferDTO.getQuantity()){
                depositCoin(transferDTO.getToWalletID(), transferDTO.getCoin(), transferDTO.getQuantity());
                withdrawCoin(transferDTO.getFromWalletID(), transferDTO.getCoin(), transferDTO.getQuantity());
            }
        }
        return wallet2.get().getWalletName()+" new balance=> "+transferDTO.getCoin().toUpperCase()+ "= " + wallet2.get().getAllCoin(transferDTO.getCoin());
        }catch(ElementNotFoundException e){
            throw new ElementNotFoundException("Wallet not found");
        }


    }
    private WalletDTO convertToDto(Wallet wallet) {
        WalletDTO walletDTO = modelMapper.map(wallet, WalletDTO.class);
        walletDTO.setBalance(wallet.getBalance());
        walletDTO.setId(wallet.getId());
        walletDTO.setName(wallet.getWalletName());
        return walletDTO;
    }
    private Wallet convertToEntity(WalletDTO walletDTO) throws  ElementNotFoundException {
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setBalance(walletDTO.getBalance());
        wallet.setWalletName(walletDTO.getName());
        if(walletDTO.getId()!=null){
            wallet.setId(walletDTO.getId());
        }
        return wallet;
    }

}
