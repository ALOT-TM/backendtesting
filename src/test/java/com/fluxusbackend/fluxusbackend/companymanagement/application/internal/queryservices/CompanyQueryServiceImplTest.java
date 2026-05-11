package com.fluxusbackend.fluxusbackend.companymanagement.application.internal.queryservices;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.queries.GetCompanyByIdQuery;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.queries.ListCompaniesQuery;
import com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyQueryServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyQueryServiceImpl companyQueryService;

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company("Test Company", "HQ Test");
    }

    @Test
    void handle_GetCompanyByIdQuery_ReturnsCompany() {
        // Arrange
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        GetCompanyByIdQuery query = new GetCompanyByIdQuery(new com.fluxusbackend.fluxusbackend.shared.domain.model.valueobjects.CompanyId(1L));

        // Act
        Optional<Company> result = companyQueryService.handle(query);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Company");
    }

    @Test
    void handle_ListCompaniesQuery_ReturnsCompanies() {
        // Arrange
        when(companyRepository.findAll()).thenReturn(List.of(company));
        ListCompaniesQuery query = new ListCompaniesQuery();

        // Act
        List<Company> result = companyQueryService.handle(query);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Company");
    }
}

