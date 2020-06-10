package com.cristal.crypto;

import com.cristal.crypto.controllers.CoinController;
import com.cristal.crypto.controllers.OperationController;
import com.cristal.crypto.controllers.WalletController;
import com.cristal.crypto.entities.Wallet;
import javassist.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CryptoApplicationTests {
	@Autowired
	private OperationController operationController;
	@Autowired
	private WalletController walletController;
	@Autowired
	private CoinController coinController;
	@Test
	void contextLoads() throws NotFoundException {
		Wallet wallet = new Wallet();
		assertThat(walletController.addWallet(wallet)).isNotNull();
		wallet.setQuantity("USD", 50.0);
		assertThat(operationController.buyCoin(1L,"USD", "BTC", "25")).isNotNull();
	}

}
