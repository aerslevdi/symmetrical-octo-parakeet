package com.cristal.crypto;
import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.exception.ElementNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WalletServiceTest {
    private static final String LOCALHOST = "http://localhost:";
    private static final String CONTEXT_PATH = "/api";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllWallets()
            throws ElementNotFoundException {
        final String url = LOCALHOST + port + CONTEXT_PATH;
        ResponseEntity<List> httpResponse = testRestTemplate.getForEntity(url+"/wallets",List.class);
        List responseList =  httpResponse.getBody();
        assertTrue(responseList.size() >= 0);
        LinkedHashMap wallet = (LinkedHashMap) responseList.get(0);
        assertTrue(wallet.get("name").equals("First wallet"));
        assertTrue((Integer) wallet.get("id")==1);
        assertTrue(httpResponse.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void getWalletById(){
        final String url = LOCALHOST + port + CONTEXT_PATH;
        ResponseEntity<LinkedHashMap> httpResponse = testRestTemplate.getForEntity(url+"/wallets/2",LinkedHashMap.class);

        LinkedHashMap walletDTO = httpResponse.getBody();
        assertTrue((Integer) walletDTO.get("id")==2);
        assertTrue(walletDTO.get("name").equals("Second wallet"));
        assertTrue(httpResponse.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void getNonExistentWallet() {
        final String url = LOCALHOST + port + CONTEXT_PATH;
        ResponseEntity<String> thrown = testRestTemplate.getForEntity(url + "/wallets/12",  String.class);
        assertTrue(thrown.getBody().contains("No wallet record exist for given id"));
    }
    @Test
    public void create(){
        final String url = LOCALHOST + port + CONTEXT_PATH;
        WalletDTO testWallet = new WalletDTO();
        testWallet.setName("Test wallet");
        HttpEntity<WalletDTO> request = new HttpEntity<>(testWallet);
        ResponseEntity httpResponsePost = testRestTemplate.postForEntity(url+"/wallets", request,WalletDTO.class);
        WalletDTO newWallet = (WalletDTO) httpResponsePost.getBody();
        assertTrue(newWallet.getName().equals(testWallet.getName()));
        assertTrue(newWallet.getId()==(testWallet.getId()));
    }

   @Test
    public void updateWallet(){
        final String url = LOCALHOST + port + CONTEXT_PATH;
        ResponseEntity<WalletDTO> httpResponse = testRestTemplate.getForEntity(url+"/wallets/3",WalletDTO.class);

        WalletDTO walletDTO = httpResponse.getBody();
        Long walletDTOId = walletDTO.getId();

        String walletNameUpdate = "New wallet name";

        WalletDTO walletDTOUpdate = new WalletDTO();
        walletDTOUpdate.setName(walletNameUpdate);
        walletDTOUpdate.setId(walletDTOId);
        walletDTOUpdate.setBalance(walletDTO.getBalance());
        HttpEntity<WalletDTO> updateRequest = new HttpEntity<>(walletDTOUpdate);

        testRestTemplate.exchange(url+"/wallets/"+walletDTOId, HttpMethod.PUT,updateRequest,Void.class);

        ResponseEntity<WalletDTO> httpResponseGet = testRestTemplate.getForEntity(url+"/wallets/"+walletDTOId,WalletDTO.class);

        WalletDTO updatedWalletDTO = httpResponseGet.getBody();
        assertTrue(walletDTOUpdate.getName().equals(updatedWalletDTO.getName()));
        assertTrue(walletDTOUpdate.getId()==updatedWalletDTO.getId());
    }

    @Test
    public void deleteWallet(){
        final String url = LOCALHOST + port + CONTEXT_PATH;
        testRestTemplate.delete(url+"/wallets/1", String.class);
        ResponseEntity<LinkedHashMap> thrown = testRestTemplate.getForEntity(url + "/wallets/1",  LinkedHashMap.class);
        assertEquals("No wallet record exist for given id", thrown.getBody().get("message"));
    }

    @Test
    public void deleteFalseWallet(){
        final String url = LOCALHOST + port + CONTEXT_PATH;
        testRestTemplate.delete(url+"/wallets/13", String.class);

    }



}
