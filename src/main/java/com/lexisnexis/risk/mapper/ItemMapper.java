package com.lexisnexis.risk.mapper;

import com.lexisnexis.risk.dto.Address;
import com.lexisnexis.risk.dto.Item;
import com.lexisnexis.risk.dto.Officer;
import com.lexisnexis.risk.web.rest.dto.officers.Officers;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemMapper {
    private static Address getAddress(com.lexisnexis.risk.web.rest.dto.common.Address address) {
        Address addressResponse = new Address();
        if (address != null) {
            addressResponse.setPremises(address.getPremises());
            addressResponse.setAddressLine1(address.getAddressLine1());
            addressResponse.setLocality(address.getLocality());
            addressResponse.setCountry(address.getCountry());
            addressResponse.setPostalCode(address.getPostalCode());
        }
        return addressResponse;
    }

    public Item convert(com.lexisnexis.risk.web.rest.dto.companies.Item item, Officers officers) {
        Item itemResponse = new Item();
        itemResponse.setCompanyNumber(item.getCompanyNumber());
        itemResponse.setCompanyType(item.getCompanyType());
        itemResponse.setTitle(item.getTitle());
        itemResponse.setCompanyStatus(item.getCompanyStatus());
        itemResponse.setCreationDate(item.getCreationDate());
        itemResponse.setAddress(getAddress(item.getAddress()));
        if (!CollectionUtils.isEmpty(officers.getItems())) {
            List<Officer> officersList = new ArrayList<>();
            officers.getItems().forEach(officer -> {
                Officer officerResponse = new Officer();
                officerResponse.setName(officer.getName());
                officerResponse.setRole(officer.getOfficerRole());
                officerResponse.setAppointedDate(officer.getAppointedOn());
                officerResponse.setAddress(getAddress(officer.getAddress()));
                officersList.add(officerResponse);
            });
            itemResponse.setOfficers(officersList);
        }
        return itemResponse;
    }
}
