package com.tasks.swiftcode_api.controllers;

import com.tasks.swiftcode_api.models.BankEntity;
import com.tasks.swiftcode_api.models.DTO.BankDTO;
import com.tasks.swiftcode_api.models.DTO.CountryDTO;
import com.tasks.swiftcode_api.services.SwiftApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftApiController {
    @Autowired
    private SwiftApiService swiftApiService;

    @GetMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<BankDTO> getDetails(@PathVariable("swift-code") String swiftCode) {
        BankDTO found = swiftApiService.getBankBySwiftCode(swiftCode.toUpperCase());
        return ResponseEntity.ok(found);
    }

    @GetMapping("/country/{countyISO2code}")
    @ResponseBody
    public ResponseEntity<CountryDTO> getBankEntitiesByCountryISO2code(@PathVariable("countyISO2code") String countyISO2code) {
        CountryDTO foundCountryDTO = swiftApiService.findAllSwiftCodesWithDetailsByCountryISO2(countyISO2code.toUpperCase());
        return ResponseEntity.ok(foundCountryDTO);
    }

    @DeleteMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSwiftCode(@PathVariable("swift-code") String swiftCode,
                                                               @RequestParam("bankName") String bankName,
                                                               @RequestParam("countryISO2") String countryISO2) {
        Map<String, String> response = swiftApiService.deleteBankEntityFromDatabase(swiftCode.toUpperCase(), bankName, countryISO2.toUpperCase());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSwiftCode(@RequestBody BankEntity bankEntity) {
        return swiftApiService.addBankEntityToDatabase(bankEntity);
    }
}
