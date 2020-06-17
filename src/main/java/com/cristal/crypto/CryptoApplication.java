package com.cristal.crypto;

import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.repositories.WalletRepository;
import javassist.NotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;


@SpringBootApplication
@EnableCaching
public class CryptoApplication{

	public static void main(String[] args) throws NotFoundException {
		SpringApplication.run(CryptoApplication.class, args);
		System.out.println("Crypto app successfully initiated");
	}
	@Bean
	public CommandLineRunner demo(WalletRepository walletRepository) {
		return (args) -> {
			LinkedHashMap<String, Double> balance = new LinkedHashMap<String, Double>();
			balance.put("BTC", 50.0);
			balance.put("USD", 50.0);
			balance.put("ETH", 50.0);
			balance.put("EUR", 50.0);
			walletRepository.save(new Wallet("First wallet", balance));
			walletRepository.save(new Wallet("Second wallet", balance));
			walletRepository.save(new Wallet("Third wallet", balance));
			walletRepository.save(new Wallet("Fourth wallet", balance));
			walletRepository.save(new Wallet("Fifth wallet", balance));
		};
	}
}
