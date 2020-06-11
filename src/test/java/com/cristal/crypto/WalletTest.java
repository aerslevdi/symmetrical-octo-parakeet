package com.cristal.crypto;

import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.repositories.WalletRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Mock
    private WalletRepository walletRepository;
    private LinkedHashMap<String, Double> balance = new LinkedHashMap<>();

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        balance.put("BTC", 50.0);
        balance.put("USD", 50.0);
        walletRepository.save(new Wallet("test wallet 1", balance));
        walletRepository.save(new Wallet("test wallet 2", balance));
        walletRepository.save(new Wallet("test wallet 3", balance));
        walletRepository.save(new Wallet("test wallet 4", balance));
        walletRepository.save(new Wallet("test wallet 5", balance));
        walletRepository.save(new Wallet("test wallet 6", balance));
    }
    @Test
    public void getWalletsTest (){
        assertThat(this.restTemplate.getForObject("localhost:"+ port+"/api/wallets", Wallet.class)).isNotNull();
    }
    @Test
    public void addWalletsTest (){
        balance.put("BTC", 50.0);
        balance.put("USD", 50.0);
        Wallet walletTest = new Wallet("test wallet 1", balance);
        walletRepository.save(walletTest);
        Assert.assertTrue(walletTest.getWalletName().equals(walletRepository.findById(walletTest.getId())));
    }


}
