package com.lexisnexis.risk.web.rest.impl;

import com.lexisnexis.risk.web.rest.OfficerSearchApiHandler;
import com.lexisnexis.risk.web.rest.dto.officers.Item;
import com.lexisnexis.risk.web.rest.dto.officers.Officers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class OfficerSearchApiHandlerImpl extends TruProxyApiHandler implements OfficerSearchApiHandler {

    @Value("${truProxyApi.officersUrl}")
    private String officersUrl;

    @Autowired
    public OfficerSearchApiHandlerImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public Officers getCompanyOfficersByCompanyNumber(String companyNumber, String apiKey) {
        super.buildRequestEntity(apiKey);
        Officers matchingOfficers = new Officers();
        ResponseEntity<Officers> response = restTemplate.exchange(truProxyBaseUrl + officersUrl + "?CompanyNumber={companyNumber}", HttpMethod.GET, requestEntity, Officers.class, companyNumber);
        if (response.getStatusCode().is2xxSuccessful()) {
            Officers officers = response.getBody();
            log.debug("Officers found: {}", officers);
            List<Item> matchingItems = null;
            if (officers != null && !CollectionUtils.isEmpty(officers.getItems())) {
                matchingItems = officers.getItems().stream().filter(item -> StringUtils.isBlank(item.getResignedOn())).toList();
                matchingOfficers.setItems(matchingItems);
            }
            log.info("Company number: {} has matching officers: {}", companyNumber, matchingItems);
        }
        return matchingOfficers;
    }
}
