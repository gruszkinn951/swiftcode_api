package com.tasks.swiftcode_api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address",
        "bankName",
        "countryISO2",
        "countryName",
        "isHeadquarter",
        "swiftCode"
})
public class BankEntity {
    @Id
    @NonNull
    @CsvBindByName(column = "SWIFT CODE")
    private String swiftCode;

    @CsvBindByName(column = "ADDRESS")
    private String address;

    @CsvBindByName(column = "NAME")
    private String bankName;

    @CsvBindByName(column = "COUNTRY ISO2 CODE")
    private String countryISO2;

    @CsvBindByName(column = "COUNTRY NAME")
    private String countryName;

    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;

    @OneToMany
    @Transient
    private List<BankEntity> branches;
}