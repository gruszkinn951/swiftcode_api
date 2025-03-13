package com.tasks.swiftcode_api.models.DTO;

import com.tasks.swiftcode_api.models.BankEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperToDTO {
    public CountryDTO toCountryDTO(String countryISO2, String countryName, List<BankEntity> bankEntities) {
        List<ReducedBankDTO> swiftCodes = bankEntities.stream()
                .map(this::toReducedBankDTO)
                .collect(Collectors.toList());

        return CountryDTO.builder()
                .countryISO2(countryISO2)
                .countryName(countryName)
                .swiftCodes(swiftCodes)
                .build();
    }

    public ReducedBankDTO toReducedBankDTO(BankEntity bankEntity) {
        return ReducedBankDTO.builder()
                .swiftCode(bankEntity.getSwiftCode())
                .address(bankEntity.getAddress())
                .bankName(bankEntity.getBankName())
                .countryISO2(bankEntity.getCountryISO2())
                .isHeadquarter(bankEntity.isHeadquarter())
                .build();
    }

    public BankDTO toBankDTO(BankEntity bankEntity) {
        List<ReducedBankDTO> reducedBranches = bankEntity.getBranches().stream()
                .map(this::toReducedBankDTO)
                .toList();

        return BankDTO.builder()
                .address(bankEntity.getAddress())
                .bankName(bankEntity.getBankName())
                .countryISO2(bankEntity.getCountryISO2())
                .countryName(bankEntity.getCountryName())
                .isHeadquarter(bankEntity.isHeadquarter())
                .swiftCode(bankEntity.getSwiftCode())
                .branches(bankEntity.isHeadquarter() ? reducedBranches : null)
                .build();
    }
}
