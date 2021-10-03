package com.repository;

import com.models.CottonPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CottonPartRepository extends JpaRepository<CottonPart, Long> {
    Optional<CottonPart> findOneByCottonPart(int cotton_part);
}
