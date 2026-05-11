package com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyRepositoryTest {

    @Test
    void companyModel_basicConstructor() {
        Company c = new Company("Repo Company","HQ");
        assertThat(c.getName()).isEqualTo("Repo Company");
        assertThat(c.getHeadquarters()).isEqualTo("HQ");
    }
}
