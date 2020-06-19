package com.cristal.crypto.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class APIConnection {

        public ResponseEntity connect(String url, HttpMethod httpMethod, Class entryClass, Object body){

            HttpHeaders headers = new HttpHeaders();
            HttpEntity entity;
            if(body == null) {
                entity = new HttpEntity(headers);
            }else{
                entity = new HttpEntity(body,headers);
            }

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List> httpResponse= restTemplate.exchange(url,
                    httpMethod, entity, entryClass);

            return httpResponse;
        }

}
