package com.cristal.Crypto.services;


import com.cristal.Crypto.dto.CoinDTO;
import com.cristal.Crypto.entities.CryptoCoin;
import com.cristal.Crypto.entities.Currency;
import com.cristal.Crypto.entities.ExchangeCoin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CryptoCompareService{



    RestTemplate restTemplate = new RestTemplate();
    public static final String API_URL = "https://min-api.cryptocompare.com/data/";

    @Cacheable("allCoins")
    public LinkedHashMap getAll(String convert)  {
        LinkedHashMap cryptoCoin = restTemplate.getForObject(
                API_URL + "all/coinlist" , LinkedHashMap.class);
            LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String >>> dict = (LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, String >>>) cryptoCoin;
            LinkedHashMap<String, LinkedHashMap<String, String >> coins = dict.get("Data");
            Set<String> keys = coins.keySet();
            ArrayList<String> coinSymbols = new ArrayList<>();
            for (String key : keys){
                coinSymbols.add(key);
            }
            System.out.println(coinSymbols.size());

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
    public ExchangeCoin getById(String from, String to)  {
        ExchangeCoin cryptoCoins = new ExchangeCoin();
        to.toUpperCase();
        from.toUpperCase();
        LinkedHashMap<String, Double> coin = restTemplate.getForObject(
                API_URL+ "price?fsym=" +from+"&tsyms=" + to, LinkedHashMap.class);
        cryptoCoins.setExchangeRate(coin.get(from.toUpperCase()));
        cryptoCoins.setName(to);
        cryptoCoins.setExchangeCoin(from);
        return cryptoCoins;
    }



}


