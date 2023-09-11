package com.lexisnexis.risk.web.rest.dto.companies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lexisnexis.risk.web.rest.dto.common.Address;
import lombok.Data;

import java.util.List;

@Data
public class Item {
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonIgnore
    private String addressSnippet;
    @JsonProperty("date_of_creation")
    private String creationDate;
    @JsonIgnore
    private Matches matches;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private Links links;
    @JsonProperty("company_number")
    private String companyNumber;
    private String title;
    @JsonProperty("company_type")
    private String companyType;
    private Address address;
    @JsonIgnore
    private String kind;
    @JsonIgnore
    private List<String> descriptionIdentifier;
}
