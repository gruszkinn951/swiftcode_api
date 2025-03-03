package com.tasks.swiftcode_api.controllers;

import com.tasks.swiftcode_api.models.BankEntity;
import com.tasks.swiftcode_api.models.Country;
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
    public ResponseEntity<BankEntity> getDetails(@PathVariable("swift-code") String swiftCode) {
        BankEntity found = swiftApiService.getBankEntityBySwiftCode(swiftCode.toUpperCase());
        return ResponseEntity.ok(found);
    }

    @GetMapping("/country/{countyISO2code}")
    @ResponseBody
    public ResponseEntity<Country> getBankEntitiesByCountryISO2code(@PathVariable("countyISO2code") String countyISO2code) {
        Country foundCountry = swiftApiService.findAllSwiftCodesWithDetailsByCountryISO2(countyISO2code.toUpperCase());
        return ResponseEntity.ok(foundCountry);
    }

    @DeleteMapping("/{swift-code}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteSwiftCode(@PathVariable("swift-code") String swiftCode,
                                                               @RequestParam("bankName") String bankName,
                                                               @RequestParam("countryISO2") String countryISO2) {
        Map<String, String> response = swiftApiService.deleteBankEntityFromDatabase(swiftCode.toUpperCase(), bankName, countryISO2.toUpperCase());
        return ResponseEntity.ok(response);
    }
}
