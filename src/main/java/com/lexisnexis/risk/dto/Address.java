package com.lexisnexis.risk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"premises", "address_line_1", "locality", "country", "postal_code"})
public class Address {
    @JsonProperty("premises")
    private String premises;
    @JsonProperty("address_line_1")
    private String addressLine1;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("country")
    private String country;
    @JsonProperty("postal_code")
    private String postalCode;
}
