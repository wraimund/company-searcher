package com.lexisnexis.risk.service;

import com.lexisnexis.risk.dto.Companies;
import com.lexisnexis.risk.dto.CompanySearchBody;

public interface CompanySearchService {

    Companies getCompanyData(CompanySearchBody companySearchBody, String apiKey, Boolean activeCompanies);
}
