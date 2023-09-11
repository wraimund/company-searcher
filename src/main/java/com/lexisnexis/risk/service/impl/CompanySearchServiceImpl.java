package com.lexisnexis.risk.service.impl;

import com.lexisnexis.risk.dto.Companies;
import com.lexisnexis.risk.dto.CompanySearchBody;
import com.lexisnexis.risk.dto.Item;
import com.lexisnexis.risk.mapper.ItemMapper;
import com.lexisnexis.risk.service.CompanySearchService;
import com.lexisnexis.risk.web.rest.CompanySearchApiHandler;
import com.lexisnexis.risk.web.rest.OfficerSearchApiHandler;
import com.lexisnexis.risk.web.rest.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompanySearchServiceImpl implements CompanySearchService {

    private final CompanySearchApiHandler companySearchApiHandler;

    private final OfficerSearchApiHandler officerSearchApiHandler;

    private final ItemMapper itemMapper;

    @Autowired
    public CompanySearchServiceImpl(CompanySearchApiHandler companySearchApiHandler, OfficerSearchApiHandler officerSearchApiHandler, ItemMapper itemMapper) {
        this.companySearchApiHandler = companySearchApiHandler;
        this.officerSearchApiHandler = officerSearchApiHandler;
        this.itemMapper = itemMapper;
    }

    @Override
    public Companies getCompanyData(CompanySearchBody companySearchBody, String apiKey, Boolean activeCompanies) {
        Companies companies = new Companies();
        String companyNumber = companySearchBody.getCompanyNumber();
        String companyName = companySearchBody.getCompanyName();
        QueryType queryType;
        if ((StringUtils.isNotBlank(companyName) && StringUtils.isNotBlank(companyNumber)) || (StringUtils.isBlank(companyName) && StringUtils.isNotBlank(companyNumber))) {
            queryType = QueryType.COMPANYNUMBER;
        } else if (StringUtils.isNotBlank(companyName)) {
            queryType = QueryType.COMPANYNAME;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing search criteria");
        }
        com.lexisnexis.risk.web.rest.dto.companies.Companies matchingCompanies = companySearchApiHandler.getCompanyData(companySearchBody, queryType, apiKey, activeCompanies);
        if (CollectionUtils.isEmpty(matchingCompanies.getItems())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No companies found matching search criteria");
        } else {
            List<Item> items = new ArrayList<>();
            matchingCompanies.getItems().forEach(item -> {
                com.lexisnexis.risk.web.rest.dto.officers.Officers matchingOfficers = officerSearchApiHandler.getCompanyOfficersByCompanyNumber(item.getCompanyNumber(), apiKey);
                Item itemResponse = itemMapper.convert(item, matchingOfficers);
                items.add(itemResponse);
            });
            companies.setItems(items);
            companies.setTotalResults(items.size());
        }
        return companies;
    }
}
