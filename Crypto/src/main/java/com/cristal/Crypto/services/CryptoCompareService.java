package com.cristal.Crypto.services;


import com.cristal.Crypto.entities.CryptoCoin;
import com.cristal.Crypto.entities.Currency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CryptoCompareService{

    /*public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/


    public List<Currency> getAll()  {
            List<Currency> cryptoCoins;
            RestTemplate restTemplate = new RestTemplate();
            Object cryptoCoin = restTemplate.getForObject(
                    "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR", Object.class);
            LinkedHashMap<String, LinkedHashMap<String, Double>> dict = (LinkedHashMap<String, LinkedHashMap<String, Double>>) cryptoCoin;
            CryptoCoin ethereum = new CryptoCoin();
            ethereum.setDollarValue(dict.get("ETH").get("USD"));
            ethereum.setEuroValue(dict.get("ETH").get("EUR"));
            ethereum.setName("Ethereum");
            CryptoCoin bitcoin = new CryptoCoin();
            bitcoin.setDollarValue(dict.get("BTC").get("USD"));
            bitcoin.setEuroValue(dict.get("BTC").get("EUR"));
            bitcoin.setName("Bitcoin");
            cryptoCoins = new ArrayList<>();
            cryptoCoins.add(bitcoin);
            cryptoCoins.add(ethereum);
            return cryptoCoins;
    }

        public CryptoCoin getEthereum()  {
                CryptoCoin cryptoCoins;
                RestTemplate restTemplate = new RestTemplate();
                 cryptoCoins = restTemplate.getForObject(
                        "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,EUR", CryptoCoin.class);
                 cryptoCoins.setName("Ethereum");
                System.out.println(cryptoCoins.toString());
                return cryptoCoins;
        }
        public CryptoCoin getBitcoin()  {
                CryptoCoin cryptoCoins;
                RestTemplate restTemplate = new RestTemplate();
                cryptoCoins = restTemplate.getForObject(
                        "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,EUR", CryptoCoin.class);
                cryptoCoins.setName("Bitcoin");
                return cryptoCoins;
        }
    public CryptoCoin getById(String id)  {
        CryptoCoin cryptoCoins;
        RestTemplate restTemplate = new RestTemplate();
        cryptoCoins = restTemplate.getForObject(
                "https://min-api.cryptocompare.com/data/price?fsym="+id+"&tsyms=USD,EUR", CryptoCoin.class);
        cryptoCoins.setName(id);
        return cryptoCoins;
    }



}


