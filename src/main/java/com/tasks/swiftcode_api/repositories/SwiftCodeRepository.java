package com.tasks.swiftcode_api.repositories;

import com.tasks.swiftcode_api.models.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwiftCodeRepository extends JpaRepository<BankEntity, String> {
}