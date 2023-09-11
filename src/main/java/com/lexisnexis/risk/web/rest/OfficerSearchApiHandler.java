package com.lexisnexis.risk.web.rest;

import com.lexisnexis.risk.web.rest.dto.officers.Officers;

public interface OfficerSearchApiHandler {

    Officers getCompanyOfficersByCompanyNumber(String companyNumber, String apiKey);
}
