package com.lexisnexis.risk.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class CompanySearchControllerIntegrationTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(
                    wireMockConfig()
                            .dynamicPort()
                            .usingFilesUnderClasspath("wiremock")
            )
            .build();

    @Autowired
    protected MockMvc mockMvc;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        log.debug("Adding base URL to wiremock server: {}", wireMock.baseUrl());
        registry.add("truProxyApi.baseUrl", wireMock::baseUrl);
    }

    @DisplayName("Should return status code of 200 with one company for company number")
    @Test
    void shouldReturnSuccessResponseWithOneCompanyForCompanyNumber() throws Exception {
        this.mockMvc.perform(post("/companies")
                        .header("x-api-key", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("active", "true")
                        .content("{ \"companyNumber\" : \"13901664\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_results").value("1"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].company_number").value("13901664"))
                .andExpect(jsonPath("$.items[0].company_status").value("active"))
                .andExpect(jsonPath("$.items[0].officers").isArray())
                .andExpect(jsonPath("$.items[0].officers", hasSize(2)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should return status code of 200 with one company for company name")
    @Test
    void shouldReturnSuccessResponseWithOneCompanyForCompanyName() throws Exception {
        this.mockMvc.perform(post("/companies")
                        .header("x-api-key", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("active", "true")
                        .content("{ \"companyName\" : \"PHILIP ROBINSON LIMITED\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_results").value("1"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].company_number").value("08009585"))
                .andExpect(jsonPath("$.items[0].company_status").value("active"))
                .andExpect(jsonPath("$.items[0].officers").isArray())
                .andExpect(jsonPath("$.items[0].officers", hasSize(1)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should return status code of 404")
    @Test
    void shouldReturn404WhenNoResultsFoundForCompanyNumber() throws Exception {
        this.mockMvc.perform(post("/companies")
                        .header("x-api-key", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("active", "true")
                        .content("{ \"companyNumber\" : \"9999999\" }"))
                .andExpect(status().isNotFound());
    }

    // Not implemented all tests but understand there would be more use cases needing tests
}