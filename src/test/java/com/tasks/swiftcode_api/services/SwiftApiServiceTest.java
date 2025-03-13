package com.tasks.swiftcode_api.services;

import com.tasks.swiftcode_api.exceptions.BankNotFoundException;
import com.tasks.swiftcode_api.exceptions.InvalidDataException;
import com.tasks.swiftcode_api.exceptions.ResourceAlreadyExistsException;
import com.tasks.swiftcode_api.models.BankEntity;
import com.tasks.swiftcode_api.models.DTO.BankDTO;
import com.tasks.swiftcode_api.models.DTO.CountryDTO;
import com.tasks.swiftcode_api.models.DTO.MapperToDTO;
import com.tasks.swiftcode_api.repositories.SwiftCodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class SwiftApiServiceTest {

    @Autowired
    private SwiftApiService swiftApiService;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Autowired
    private MapperToDTO mapper;

    @BeforeEach
    public void setup() {
        swiftCodeRepository.deleteAll();
    }

    @Test
    public void SwiftApiServiceTest_GetBankEntityBySwiftCode() {

        BankEntity bankEntity1 = BankEntity.builder()
                .swiftCode("87654321123")
                .address("Berlin")
                .bankName("Bank A")
                .countryISO2("GE")
                .countryName("GERMANY")
                .build();

        BankEntity bankEntity2 = BankEntity.builder()
                .swiftCode("87654321321")
                .address("Warsaw")
                .bankName("Bank B")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        BankEntity bankEntity3 = BankEntity.builder()
                .swiftCode("87654321XXX")
                .address("Cracow")
                .bankName("Bank C")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        swiftCodeRepository.save(bankEntity1);
        swiftCodeRepository.save(bankEntity2);
        swiftCodeRepository.save(bankEntity3);

        List<BankEntity> branches = List.of(bankEntity1, bankEntity2);
        bankEntity3.setBranches(branches);

        BankDTO result = swiftApiService.getBankBySwiftCode("87654321XXX");
        Assertions.assertEquals("Bank C", result.getBankName());
        Assertions.assertEquals("Cracow", result.getAddress());

        BankDTO expectedBankDTO = mapper.toBankDTO(bankEntity3);
        Assertions.assertEquals(expectedBankDTO.getBankName(), result.getBankName());
        Assertions.assertEquals(expectedBankDTO.getAddress(), result.getAddress());
    }

    @Test
    public void SwiftApiServiceTest_FindAllSwiftCodesWithDetailsByCountryISO2() {

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

        swiftCodeRepository.save(bankEntity1);
        swiftCodeRepository.save(bankEntity2);
        swiftCodeRepository.save(bankEntity3);

        List<BankEntity> banksInZimbabwe = List.of(bankEntity2, bankEntity3);


        CountryDTO zimbabwe = mapper.toCountryDTO("ZE", "ZIMBABWE", banksInZimbabwe);

        CountryDTO resultZimbabwe = swiftApiService.findAllSwiftCodesWithDetailsByCountryISO2("ZE");

        Assertions.assertEquals(resultZimbabwe, zimbabwe);
    }

    @Test
    public void SwiftApiServiceTest_SwiftCodeValidationOfBank() {
        BankEntity existingBank = BankEntity.builder()
                .swiftCode("12345678XXX")
                .address("Test Address")
                .bankName("Test Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        swiftCodeRepository.save(existingBank);

        BankEntity newBank = BankEntity.builder()
                .swiftCode("12345678XXX")
                .address("New Address")
                .bankName("New Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> swiftApiService.swiftCodeValidationOfBank(newBank));

        BankEntity invalidBank = BankEntity.builder()
                .swiftCode("12345")
                .address("Test Address")
                .bankName("Test Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        Assertions.assertThrows(InvalidDataException.class, () -> swiftApiService.swiftCodeValidationOfBank(invalidBank));

        BankEntity invalidAlphanumericBank = BankEntity.builder()
                .swiftCode("1234@5678")
                .address("Test Address")
                .bankName("Test Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .build();

        Assertions.assertThrows(InvalidDataException.class, () -> swiftApiService.swiftCodeValidationOfBank(invalidAlphanumericBank));

        BankEntity validHeadquartersBank = BankEntity.builder()
                .swiftCode("12345678")
                .address("Headquarters Address")
                .bankName("Headquarters Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .isHeadquarter(true)
                .build();

        Assertions.assertDoesNotThrow(() -> swiftApiService.swiftCodeValidationOfBank(validHeadquartersBank));
        Assertions.assertEquals("12345678XXX", validHeadquartersBank.getSwiftCode());

        BankEntity invalidBranchBank = BankEntity.builder()
                .swiftCode("12345678")
                .address("Branch Address")
                .bankName("Branch Bank")
                .countryISO2("PL")
                .countryName("POLAND")
                .isHeadquarter(false)
                .build();

        Assertions.assertThrows(InvalidDataException.class, () -> swiftApiService.swiftCodeValidationOfBank(invalidBranchBank));
    }

    @Test
    public void SwiftApiServiceTest_AddBankEntityToDatabase() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("78945612XXX")
                .address("New York")
                .bankName("Bank X")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .build();

        ResponseEntity<Map<String, String>> response = swiftApiService.addBankEntityToDatabase(bankEntity);

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().containsKey("message"));
        Assertions.assertEquals("SWIFT Code added successfully", response.getBody().get("message"));
    }

    @Test
    public void SwiftApiServiceTest_DeleteBankEntityFromDatabase() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("87654321XXX")
                .address("Los Angeles")
                .bankName("Bank Y")
                .countryISO2("US")
                .countryName("UNITED STATES")
                .isHeadquarter(true)
                .build();

        swiftCodeRepository.save(bankEntity);

        Map<String, String> result = swiftApiService.deleteBankEntityFromDatabase("87654321XXX");

        Assertions.assertEquals("SWIFT Code deleted successfully", result.get("message"));
        Optional<BankEntity> deletedBank = swiftCodeRepository.findById("87654321XXX");
        Assertions.assertFalse(deletedBank.isPresent());
    }

    @Test
    public void SwiftApiServiceTest_GetBankEntityBySwiftCode_NotFound() {
        BankEntity bankEntity = BankEntity.builder()
                .swiftCode("99999999XXX")
                .address("NonExistent")
                .bankName("NonExistentBank")
                .countryISO2("XX")
                .countryName("NOWHERE")
                .isHeadquarter(true)
                .build();

        swiftCodeRepository.save(bankEntity);

        try {
            swiftApiService.getBankBySwiftCode("11111111XXX");
        } catch (BankNotFoundException e) {
            Assertions.assertEquals("No bank found with SWIFT code: 11111111XXX", e.getMessage());
        }
    }
}
