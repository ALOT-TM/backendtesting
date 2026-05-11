package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.commands.CreateCompanyCommand;
import com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.*;
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
class CompanyControllerIntegrationTest {

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
    void createCompany_andGetById() throws Exception {
        // Arrange
        CreateCompanyCommand command = new CreateCompanyCommand("Company A", "HQ A");
        String json = objectMapper.writeValueAsString(command);

        // Act & Assert
        String response = mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Company created = objectMapper.readValue(response, Company.class);
        assertThat(created.getName()).isEqualTo("Company A");

        // Get by id
        mockMvc.perform(get("/api/companies/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company A"));
    }

    @Test
    void listCompanies_returnsAll() throws Exception {
        companyRepository.save(new Company("Company 1", "HQ 1"));
        companyRepository.save(new Company("Company 2", "HQ 2"));
        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Company 1"))
                .andExpect(jsonPath("$[1].name").value("Company 2"));
    }
}

