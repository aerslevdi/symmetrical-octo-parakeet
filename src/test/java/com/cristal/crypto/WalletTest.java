package com.cristal.crypto;

import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

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
    @Test
    public void getWalletsTest (){
        assertThat(this.restTemplate.getForObject("localhost:"+ port+"/api/wallets", Wallet.class)).isNotNull();
    }
}
