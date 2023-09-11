package com.lexisnexis.risk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"name", "officer_role", "appointed_on", "address"})
public class Officer {
    @JsonProperty("name")
    private String name;
    @JsonProperty("officer_role")
    private String role;
    @JsonProperty("appointed_on")
    private String appointedDate;
    @JsonProperty("address")
    private Address address;
}
