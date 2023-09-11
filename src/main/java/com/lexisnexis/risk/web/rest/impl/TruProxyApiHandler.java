package com.lexisnexis.risk.web.rest.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TruProxyApiHandler {
    protected final RestTemplate restTemplate;
    protected HttpEntity<Object> requestEntity;

    @Value("${truProxyApi.baseUrl}")
    protected String truProxyBaseUrl;

    public TruProxyApiHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    void buildRequestEntity(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>(headers);
    }
}
