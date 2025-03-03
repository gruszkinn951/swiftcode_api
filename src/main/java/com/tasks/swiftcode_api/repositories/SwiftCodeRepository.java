package com.tasks.swiftcode_api.repositories;

import com.tasks.swiftcode_api.models.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SwiftCodeRepository extends JpaRepository<BankEntity, String> {
    Optional<BankEntity> findFirstByCountryISO2(String countryISO2);
    List<BankEntity> findAllBanksByCountryISO2(String countryISO2);
}