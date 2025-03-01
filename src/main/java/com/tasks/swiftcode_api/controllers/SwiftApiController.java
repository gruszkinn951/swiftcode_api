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

}
