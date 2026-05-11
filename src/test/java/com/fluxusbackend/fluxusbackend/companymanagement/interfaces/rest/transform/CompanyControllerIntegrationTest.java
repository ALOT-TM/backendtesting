package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.commands.CreateCompanyCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyControllerIntegrationTest {

    @Test
    void createCommand_validationAndCompanyMapping() {
        CreateCompanyCommand cmd = new CreateCompanyCommand("ACME","HQ");
        Company c = new Company(cmd.name(), cmd.headquarters());
        assertThat(c.getName()).isEqualTo("ACME");
        assertThat(c.getHeadquarters()).isEqualTo("HQ");
    }
}

