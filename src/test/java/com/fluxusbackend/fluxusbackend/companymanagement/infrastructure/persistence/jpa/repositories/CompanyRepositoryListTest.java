package com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyRepositoryListTest {

    @Test
    void companyModel_multipleInstances() {
        Company a = new Company("C1","HQ1");
        Company b = new Company("C2","HQ2");
        assertThat(a.getName()).isEqualTo("C1");
        assertThat(b.getName()).isEqualTo("C2");
    }
}
