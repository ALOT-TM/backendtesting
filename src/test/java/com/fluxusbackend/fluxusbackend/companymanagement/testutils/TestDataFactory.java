package com.fluxusbackend.fluxusbackend.companymanagement.testutils;

import com.fluxusbackend.fluxusbackend.companymanagement.domain.model.aggregates.Company;
import com.fluxusbackend.fluxusbackend.shared.domain.model.valueobjects.CompanyId;
import com.fluxusbackend.fluxusbackend.identityaccessmanagement.domain.model.aggregates.UserAccount;
import com.fluxusbackend.fluxusbackend.identityaccessmanagement.domain.model.enums.UserRole;
import com.fluxusbackend.fluxusbackend.identityaccessmanagement.domain.model.valueobjects.EmailAddress;
import com.fluxusbackend.fluxusbackend.identityaccessmanagement.domain.model.valueobjects.PasswordHash;

public class TestDataFactory {
    public static Company createCompany(String name, String headquarters) {
        return new Company(name, headquarters);
    }

    public static UserAccount createUserAccount(String email, String password, UserRole role, CompanyId companyId) {
        return new UserAccount(
                new EmailAddress(email),
                new PasswordHash(password),
                role,
                companyId
        );
    }
}

