package com.tasks.swiftcode_api.models.DTO;

import com.tasks.swiftcode_api.models.BankEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    private String countryISO2;
    private String countryName;
    private List<ReducedBankDTO> swiftCodes;
}
