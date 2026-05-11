package com.fluxusbackend.fluxusbackend.companymanagement.interfaces.rest.transform;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Simplified unit test placeholder for multi-tenancy logic; pure model assertions.
 */
class CompanyMultiTenancyIsolationTest {

    @Test
    void companyModel_basicBehavior() {
        Company a = new Company("A","HQ A");
        Company b = new Company("B","HQ B");
        assertThat(a.getName()).isEqualTo("A");
        assertThat(b.getName()).isEqualTo("B");
        assertThat(a.getName()).isNotEqualTo(b.getName());
    }
}
