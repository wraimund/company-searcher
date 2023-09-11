package com.lexisnexis.risk.mapper;

import com.lexisnexis.risk.dto.Item;
import com.lexisnexis.risk.web.rest.dto.officers.Officers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemMapperTest {

    private final ItemMapper itemMapper = new ItemMapper();

    @DisplayName("Should return an Item response from Tru Proxy API DTOs")
    @Test
    void shouldReturnAnItemResponseFromTruProxyItemAndOfficers() {
        com.lexisnexis.risk.web.rest.dto.companies.Item companyItem = new com.lexisnexis.risk.web.rest.dto.companies.Item();
        companyItem.setCompanyNumber("12345678");
        companyItem.setCompanyStatus("active");
        companyItem.setCreationDate("2017-01-09");
        companyItem.setCompanyType("ltd");
        companyItem.setTitle("TESTING TIMES LTD");
        Officers officers = new Officers();
        officers.setEtag("0d1fbf531c3fa83d127b8ba96fc8b4bfe0c6bf1f");
        officers.setItemsPerPage(35);
        officers.setKind("officer-list");
        List<com.lexisnexis.risk.web.rest.dto.officers.Item> officerItems = new ArrayList<>();
        com.lexisnexis.risk.web.rest.dto.officers.Item officerItem = new com.lexisnexis.risk.web.rest.dto.officers.Item();
        officerItem.setName("Test Testerson");
        officerItem.setAppointedOn("2017-01-09");
        officerItem.setOfficerRole("director");
        officerItems.add(officerItem);
        officers.setItems(officerItems);

        Item responseItem = itemMapper.convert(companyItem, officers);
        assertNotNull(responseItem);
        assertEquals(companyItem.getCompanyNumber(), responseItem.getCompanyNumber());
        assertEquals(1, responseItem.getOfficers().size());

        // Could write more here
    }
}
