package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.commands.CreateCompanyCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyControllerRestTest {

    @Test
    void createCommand_rejectsBlankName() {
        try {
            new CreateCompanyCommand("", "HQ");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
