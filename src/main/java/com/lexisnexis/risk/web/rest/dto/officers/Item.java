package com.lexisnexis.risk.web.rest.dto.officers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lexisnexis.risk.web.rest.dto.common.Address;
import lombok.Data;

@Data
public class Item {
    private Address address;
    private String name;
    @JsonProperty("appointed_on")
    private String appointedOn;
    @JsonProperty("resigned_on")
    private String resignedOn;
    @JsonProperty("officer_role")
    private String officerRole;
    @JsonIgnore
    private Links links;
    @JsonIgnore
    @JsonProperty("date_of_birth")
    private DateOfBirth dateOfBirth;
    @JsonIgnore
    private String occupation;
    @JsonIgnore
    @JsonProperty("country_of_residence")
    private String countryOfResidence;
    @JsonIgnore
    private String nationality;
}
