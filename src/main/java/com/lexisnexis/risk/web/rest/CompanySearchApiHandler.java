package com.lexisnexis.risk.web.rest;

import com.lexisnexis.risk.dto.CompanySearchBody;
import com.lexisnexis.risk.web.rest.dto.companies.Companies;

public interface CompanySearchApiHandler {

    Companies getCompanyData(CompanySearchBody companySearchBody, QueryType queryType, String apiKey, boolean returnOnlyActiveCompanies);
}
