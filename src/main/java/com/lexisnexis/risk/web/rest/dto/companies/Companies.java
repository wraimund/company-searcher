package com.lexisnexis.risk.web.rest.dto.companies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Companies {
    @JsonProperty("page_number")
    private Integer pageNumber;
    private String kind;
    @JsonProperty("total_results")
    private Integer totalResults;
    private List<Item> items;
}
