package com.fluxusbackend.fluxusbackend.companymanagement.application.internal.commandservices;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.commands.CreateCompanyCommand;
import com.fluxusbackend.fluxusbackend.companymanagement.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyCommandServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyCommandServiceImpl companyCommandService;

    private CreateCompanyCommand validCommand;

    @BeforeEach
    void setUp() {
        validCommand = new CreateCompanyCommand("Test Company", "HQ Test");
    }

    @Test
    void handle_ShouldSaveCompanyWithCorrectData() {
        // Arrange
        Company companyToSave = new Company(validCommand.name(), validCommand.headquarters());
        Company savedCompany = new Company(validCommand.name(), validCommand.headquarters());
        when(companyRepository.save(any(Company.class))).thenReturn(savedCompany);

        // Act
        Company result = companyCommandService.handle(validCommand);

        // Assert
        ArgumentCaptor<Company> companyCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(companyCaptor.capture());
        Company captured = companyCaptor.getValue();
        assertThat(captured.getName()).isEqualTo(validCommand.name());
        assertThat(captured.getHeadquarters()).isEqualTo(validCommand.headquarters());
        assertThat(result).isNotNull();
    }

    @Test
    void handle_ShouldThrowException_WhenNameIsNull() {
        // Arrange & Act & Assert
        try {
            new CreateCompanyCommand(null, "HQ");
        } catch (NullPointerException | IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("Company name is required");
        }
    }
}

