package com.lexisnexis.risk.web.rest.dto.officers;

import lombok.Data;

import java.util.List;

@Data
public class Officers {
    private String etag;
    private Links links;
    private String kind;
    private Integer itemsPerPage;
    private List<Item> items;
}
