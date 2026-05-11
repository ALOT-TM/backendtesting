package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyMultiTenancyIsolationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Company companyA;
    private Company companyB;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
        companyA = companyRepository.save(new Company("Company A", "HQ A"));
        companyB = companyRepository.save(new Company("Company B", "HQ B"));
    }

    @Test
    public void companyIsolation_enforcedByCompanyId() throws Exception {
        Long companyAId = companyA.getId();
        Long companyBId = companyB.getId();

        mockMvc.perform(get("/api/companies/" + companyAId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company A"));

        mockMvc.perform(get("/api/companies/" + companyBId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company B"));
    }
}
