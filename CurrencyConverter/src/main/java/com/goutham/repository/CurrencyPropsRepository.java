package com.goutham.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPropsRepository<CurrencyProps> extends JpaRepository<CurrencyProps, Long> {
    CurrencyProps findByCurrCode(String currCode);
}

