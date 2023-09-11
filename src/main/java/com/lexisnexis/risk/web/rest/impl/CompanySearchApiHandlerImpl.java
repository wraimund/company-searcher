package com.lexisnexis.risk.web.rest.impl;

import com.lexisnexis.risk.dto.CompanySearchBody;
import com.lexisnexis.risk.web.rest.CompanySearchApiHandler;
import com.lexisnexis.risk.web.rest.QueryType;
import com.lexisnexis.risk.web.rest.dto.companies.Companies;
import com.lexisnexis.risk.web.rest.dto.companies.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@Slf4j
public class CompanySearchApiHandlerImpl extends TruProxyApiHandler implements CompanySearchApiHandler {

    @Value("${truProxyApi.companyUrl}")
    private String companyUrl;

    @Autowired
    public CompanySearchApiHandlerImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public Companies getCompanyData(CompanySearchBody companySearchBody, QueryType queryType, String apiKey, boolean returnOnlyActiveCompanies) {
        super.buildRequestEntity(apiKey);
        Companies matchingCompanies = new Companies();
        ResponseEntity<Companies> response = restTemplate.exchange(truProxyBaseUrl + companyUrl + "?Query={queryType}", HttpMethod.GET, requestEntity, Companies.class, queryType.toString());
        String searchParameter;
        if (response.getStatusCode().is2xxSuccessful()) {
            Companies companies = response.getBody();
            log.debug("All companies found: {}", companies);
            if (companies != null && !CollectionUtils.isEmpty(companies.getItems())) {
                Companies companiesToSearch = companies;
                if (returnOnlyActiveCompanies) {
                    List<Item> activeItems = companies.getItems().stream().filter(item -> item.getCompanyStatus().equalsIgnoreCase("active")).toList();
                    companiesToSearch = new Companies();
                    companiesToSearch.setItems(activeItems);
                }
                log.debug("Active companies found: {}", companiesToSearch);
                List<Item> matchingItems;
                if (queryType.equals(QueryType.COMPANYNUMBER)) {
                    searchParameter = companySearchBody.getCompanyNumber();
                    matchingItems = companiesToSearch.getItems().stream().filter(item -> item.getCompanyNumber().equalsIgnoreCase(searchParameter)).toList();
                } else {
                    searchParameter = companySearchBody.getCompanyName();
                    matchingItems = companiesToSearch.getItems().stream().filter(item -> item.getTitle().toLowerCase().contains(searchParameter.toLowerCase())).toList();
                }
                matchingCompanies.setItems(matchingItems);
                log.info("Query type: {} with search parameter: {} and active search enabled: {} matching companies: {}", queryType, searchParameter, returnOnlyActiveCompanies, matchingCompanies);
            }
        } else {
            throw new ResponseStatusException(response.getStatusCode(), "Unable to complete request");
        }
        return matchingCompanies;
    }
}
