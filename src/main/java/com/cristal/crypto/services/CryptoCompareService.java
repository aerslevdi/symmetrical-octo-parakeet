package com.cristal.crypto.services;


import com.cristal.crypto.dto.ExchangeDTO;
import com.cristal.crypto.dto.CoinDTO;
import com.cristal.crypto.exception.ElementNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CryptoCompareService{


    @Autowired
    RestTemplate restTemplate;
    public static final String API_URL = "https://min-api.cryptocompare.com/data/";

    /**
     *
     * @param convert = ID of currency for exchange
     * @return List of cryptocurrency and its value in the provided by parameter currency
     * @throws NotFoundException
     */
    @Cacheable("allCoins")
    public LinkedHashMap getAll(String convert)  {
        LinkedHashMap cryptoCoin = restTemplate.getForObject(
                API_URL + "all/coinlist" , LinkedHashMap.class);
            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String >>> dict = (LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String >>>) cryptoCoin;
            LinkedHashMap<String, LinkedHashMap<String, String >> coins = dict.get("Data");
            ArrayList<String> coinSymbols = new ArrayList<>(coins.size());
            coins.forEach((key, value) -> coinSymbols.add(key));

        String totalUrl = API_URL + "price?fsym=" + convert +"&tsyms=";
            int start = 0;
            int end = 100;
            CoinDTO coinDTO = new CoinDTO();
            LinkedHashMap relationPrice = coinDTO.getCoinsPrice();
            for(String coin: coinSymbols){
                for(String x: coinSymbols.subList(start,end)){

                    totalUrl = totalUrl + x + ",";

                }
                LinkedHashMap priceCoin1 = restTemplate.getForObject(totalUrl, LinkedHashMap.class);
                relationPrice.putAll(priceCoin1);
                totalUrl = API_URL + "price?fsym=" + convert +"&tsyms=";
                start+=100;
                end+=100;
                if(end>coinSymbols.size()){end = coinSymbols.size();}
                if(start>coinSymbols.size()){break;}
            }
            return relationPrice;
    }

    /**
     *
     *
     * @return ExchangeDTO
     * @throws NotFoundException
     */
    public ExchangeDTO getById(ExchangeDTO cBDTO)  throws ElementNotFoundException {
        String fromUC = cBDTO.getExchangeFrom().toUpperCase();
        String toUC = cBDTO.getExchangeTo().toUpperCase();
        ExchangeDTO cryptoCoins = new ExchangeDTO();
        LinkedHashMap<String, Object> coin;
        try {
             coin = restTemplate.getForObject(
                    API_URL+ "price?fsym=" +fromUC+"&tsyms=" + toUC, LinkedHashMap.class);
             cryptoCoins.setPrice((Double)coin.get(toUC));
             cryptoCoins.setExchangeFrom(fromUC);
             cryptoCoins.setExchangeTo(toUC);
             cryptoCoins.setWalletID(cBDTO.getWalletID());
             return cryptoCoins;
        }catch(ElementNotFoundException ex){
            throw new ElementNotFoundException(ex.getMessage());
        }


    }



}


