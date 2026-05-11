package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.commands.CreateCompanyCommand;
import com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompanyControllerIntegrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
    }

    @Test
    public void createCompany_andGetById() throws Exception {
        CreateCompanyCommand command = new CreateCompanyCommand("Company A", "HQ A");
        String json = objectMapper.writeValueAsString(command);

        String response = mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Company created = objectMapper.readValue(response, Company.class);
        assertThat(created.getName()).isEqualTo("Company A");

        mockMvc.perform(get("/api/companies/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company A"));
    }
}
