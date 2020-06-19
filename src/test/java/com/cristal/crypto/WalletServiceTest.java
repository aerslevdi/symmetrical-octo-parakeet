package com.cristal.crypto;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.cristal.crypto.config.APIConnection;
import com.cristal.crypto.dto.WalletDTO;
import com.cristal.crypto.entities.Wallet;
import com.cristal.crypto.exception.ElementNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletServiceTest {

    private static String BASE_URL = "http://localhost:8080/api";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllWallets()
            throws ElementNotFoundException {

        APIConnection connectionToApi = new APIConnection();
        ResponseEntity<List> httpResponse = restTemplate.getForEntity(BASE_URL+"/wallets",List.class);
        List responseList =  httpResponse.getBody();

        Assert.assertTrue(responseList.size() >= 0);
        Wallet wallet = (Wallet) responseList.get(0);
        Assert.assertTrue(wallet.getWalletName().equals("First wallet"));
        Assert.assertTrue(wallet.getId()==1);
        Assert.assertTrue(httpResponse.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void getWalletById(){
        APIConnection connectionToApi = new APIConnection();
        ResponseEntity<WalletDTO> httpResponse = restTemplate.getForEntity(BASE_URL+"/wallets/2",WalletDTO.class);

        WalletDTO walletDTO = httpResponse.getBody();
        Assert.assertTrue(walletDTO.getId()==2);
        Assert.assertTrue(walletDTO.getName().equals("Second wallet"));
        Assert.assertTrue(httpResponse.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void getNonExistentWallet() {
        boolean isThrowingException = false;
        try {
            APIConnection connectionToApi = new APIConnection();
            ResponseEntity<WalletDTO> httpResponse = restTemplate.getForEntity(BASE_URL + "/wallets/10", WalletDTO.class);
        }catch(HttpClientErrorException.NotFound ex){
            isThrowingException = true;
        }
        Assert.assertTrue(isThrowingException);
    }

    @Test
    public void createAndDeleteWallet(){

        WalletDTO testWallet = new WalletDTO();
        testWallet.setName("Test wallet");
        HttpEntity<WalletDTO> request = new HttpEntity<>(testWallet);

        APIConnection connectionToApi = new APIConnection();
        ResponseEntity httpResponsePost = restTemplate.postForEntity(BASE_URL+"/wallets", request,Wallet.class);

        WalletDTO newWallet = (WalletDTO) httpResponsePost.getBody();


        Assert.assertTrue(newWallet.getName().equals(testWallet.getName()));
        Assert.assertTrue(newWallet.getId()==(testWallet.getId()));

        ResponseEntity httpResponseDelete = restTemplate.delete(BASE_URL+"/wallets/"+newWallet.getId());

        String deleteResponse = (String) httpResponseDelete.getBody();

        Assert.assertTrue(deleteResponse.equals("The wallet was deleted"));

    }

    @Test
    public void updateWallet(){

        APIConnection connectionToApi = new APIConnection();

        ResponseEntity<WalletDTO> httpResponse = connectionToApi.connect(BASE_URL+"/wallets/3", HttpMethod.GET,WalletDTO.class,null);

        WalletDTO walletDTO = httpResponse.getBody();
        String walletDTOName = walletDTO.getName();
        Long walletDTOId = walletDTO.getId();


        String walletNameUpdate = "New wallet name";

        WalletDTO walletDTOUpdate = new WalletDTO();
        walletDTOUpdate.setName(walletNameUpdate);
        walletDTOUpdate.setId(walletDTOId);
        walletDTOUpdate.setBalance(walletDTO.getBalance());

        ResponseEntity<WalletDTO> httpResponsePutWallet = connectionToApi.connect(BASE_URL+"/wallets/"+walletDTOId, HttpMethod.PUT,WalletDTO.class,walletDTOUpdate);

        WalletDTO updatedWallet = httpResponsePutWallet.getBody();

        Assert.assertTrue(updatedWallet.getId()==walletDTO.getId());
        Assert.assertTrue(updatedWallet.getName().equals(walletDTOName));

        ResponseEntity<WalletDTO> httpResponseGet = connectionToApi.connect(BASE_URL+"/wallets/"+updatedWallet.getId(), HttpMethod.GET,WalletDTO.class,null);

        WalletDTO updatedWalletDTO = httpResponseGet.getBody();
        Assert.assertTrue(walletDTOUpdate.getName().equals(updatedWalletDTO.getName()));
        Assert.assertTrue(walletDTOUpdate.getId()==updatedWalletDTO.getId());
    }

    @Test
    public void deleteFalseWallet(){
        boolean isThrowingException = false;
        APIConnection connectionToApi = new APIConnection();
        try {
            ResponseEntity httpResponseDelete = connectionToApi.connect(BASE_URL + "/wallets/999999", HttpMethod.DELETE, String.class, null);
        }catch(HttpClientErrorException.NotFound ex){
            isThrowingException = true;
        }
        Assert.assertTrue(isThrowingException);
    }



}
