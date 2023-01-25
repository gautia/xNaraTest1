package com.goutham.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {
    Double finalize(Object currCode, String targetCode);
}


