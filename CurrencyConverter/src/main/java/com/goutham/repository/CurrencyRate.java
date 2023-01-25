package com.goutham.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencyrate")
public class CurrencyRate {
    @Id
    private String curr_code;
    private String target_code;
    private Double rate;

    // Getters and setters for the properties
}
