package com.lexisnexis.risk.controller;

import com.lexisnexis.risk.dto.Companies;
import com.lexisnexis.risk.dto.CompanySearchBody;
import com.lexisnexis.risk.service.CompanySearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@Slf4j
public class CompanySearchController {

    private final CompanySearchService companySearchService;

    @Autowired
    public CompanySearchController(CompanySearchService companySearchService) {
        this.companySearchService = companySearchService;
    }

    @PostMapping(produces = "application/json")
    public @ResponseBody Companies searchCompanies(@RequestHeader("x-api-key") String apiKey, @RequestBody CompanySearchBody companySearchBody, @RequestParam(required = false) Boolean active) {
        log.debug("Return only active companies: {}", active);
        return companySearchService.getCompanyData(companySearchBody, apiKey, active);
    }
}
