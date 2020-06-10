package com.cristal.crypto;

import javassist.NotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CryptoApplication{

	public static void main(String[] args) throws NotFoundException {
		SpringApplication.run(CryptoApplication.class, args);
		System.out.println("Crypto app successfully initiated");
	}
}
