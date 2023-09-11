package com.lexisnexis.risk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"company_number", "company_type", "title", "company_status", "date_of_creation", "address"})
public class Item {
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty("date_of_creation")
    private String creationDate;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("officers")
    private List<Officer> officers;
}