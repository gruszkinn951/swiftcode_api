package com.tasks.swiftcode_api.mappers;

import com.tasks.swiftcode_api.models.BankEntity;
import com.tasks.swiftcode_api.models.DTO.BankDTO;
import com.tasks.swiftcode_api.models.DTO.CountryDTO;
import com.tasks.swiftcode_api.models.DTO.MapperToDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperToDTOTest {

    @Autowired
    private MapperToDTO mapper;

    @Test
    public void MapperToDTOTest_toCountryDTO() {

        BankEntity bankEntity1 = BankEntity.builder()
                .swiftCode("15935745XXX")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .build();

        BankEntity bankEntity2 = BankEntity.builder()
                .swiftCode("12345678XXX")
                .address("Zaza")
                .bankName("Bank B")
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .build();

        BankEntity bankEntity3 = BankEntity.builder()
                .swiftCode("87654321BBB")
                .address("Waza")
                .bankName("Bank C")
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .build();

        List<BankEntity> bankEntities = List.of(bankEntity1, bankEntity2, bankEntity3);

        CountryDTO expectedCountryDTO = CountryDTO.builder()
                .countryISO2("ZE")
                .countryName("ZIMBABWE")
                .swiftCodes(List.of(mapper.toReducedBankDTO(bankEntity2), mapper.toReducedBankDTO(bankEntity3)))
                .build();

        CountryDTO result = mapper.toCountryDTO("ZE", "ZIMBABWE", bankEntities);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedCountryDTO.getCountryISO2(), result.getCountryISO2());
        Assertions.assertEquals(expectedCountryDTO.getCountryName(), result.getCountryName());
        Assertions.assertEquals(expectedCountryDTO.getSwiftCodes().size(), result.getSwiftCodes().size());
        Assertions.assertEquals(expectedCountryDTO.getSwiftCodes().get(0).getSwiftCode(), result.getSwiftCodes().get(0).getSwiftCode());
    }

    @Test
    public void MapperToDTOTest_toBankDTO() {

        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("87654321123")
                .address("Cracow")
                .bankName("Bank C")
                .countryISO2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .branches(List.of())  // Empty list of branches for this test case
                .build();

        BankDTO expectedBankDTO = BankDTO.builder()
                .swiftCode("87654321123")
                .address("Cracow")
                .bankName("Bank C")
                .countryISO2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .branches(null)  // No branches for headquarter
                .build();

        BankDTO result = mapper.toBankDTO(bankEntity);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedBankDTO.getSwiftCode(), result.getSwiftCode());
        Assertions.assertEquals(expectedBankDTO.getAddress(), result.getAddress());
        Assertions.assertEquals(expectedBankDTO.getBankName(), result.getBankName());
        Assertions.assertEquals(expectedBankDTO.getCountryISO2(), result.getCountryISO2());
        Assertions.assertEquals(expectedBankDTO.getCountryName(), result.getCountryName());
        Assertions.assertEquals(expectedBankDTO.isHeadquarter(), result.isHeadquarter());
        Assertions.assertNull(result.getBranches());
    }
}
