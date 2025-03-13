package com.tasks.swiftcode_api.models.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({
        "address",
        "bankName",
        "countryISO2",
        "isHeadquarter",
        "swiftCode",
})
public class ReducedBankDTO {
    private String swiftCode;
    private String address;
    private String bankName;
    private String countryISO2;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
}
