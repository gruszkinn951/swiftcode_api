package com.tasks.swiftcode_api.services;

import com.tasks.swiftcode_api.models.BankEntity;
import com.tasks.swiftcode_api.repositories.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftApiService {
    @Autowired
    SwiftCodeRepository repository;

    public List<BankEntity> saveAllBankEntitiesFromListToDatabase(List<BankEntity> bankEntities) {
        return bankEntities.stream()
                .peek(this::prepareBankEntityBeforeSave)
                .map(repository::save)
                .collect(Collectors.toList());
    }

    private void prepareBankEntityBeforeSave(BankEntity bankEntity) {
        uppercaseSwiftISO2CountryOfBankEntity(bankEntity);
        trimWhiteSpaces(bankEntity);
        bankEntity.setHeadquarter(isBranchAHeadquarters(bankEntity.getSwiftCode()));
    }

    private void uppercaseSwiftISO2CountryOfBankEntity(BankEntity bankEntity) {
        bankEntity.setSwiftCode(bankEntity.getSwiftCode().toUpperCase());
        bankEntity.setCountryName(bankEntity.getCountryName().toUpperCase());
        bankEntity.setCountryISO2(bankEntity.getCountryISO2().toUpperCase());
    }

    private void trimWhiteSpaces(BankEntity bankEntity) {
        if (bankEntity.getAddress() != null) {
            bankEntity.setAddress(bankEntity.getAddress().trim());
        }
    }

    private boolean isBranchAHeadquarters(String swiftCode) {
        if (swiftCode != null && swiftCode.length() >= 3) {
            String suffix = swiftCode.substring(swiftCode.length() - 3);
            return "XXX".equals(suffix);
        }
        return false;
    }
}