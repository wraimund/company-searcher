package com.lexisnexis.risk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"total_results", "items"})
public class Companies {
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("items")
    private List<Item> items;
}
