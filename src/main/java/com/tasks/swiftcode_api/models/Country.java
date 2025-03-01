package com.tasks.swiftcode_api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private String countryISO2;
    private String countryName;
    private List<BankEntity> swiftCodes;
}
