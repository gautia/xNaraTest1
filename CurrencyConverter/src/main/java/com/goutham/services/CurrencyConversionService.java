package com.goutham.services;

import com.goutham.CurrencyConversionRequest;
import com.goutham.CurrencyConversionResponse;
import com.goutham.repository.CurrencyPropsRepository;
import com.goutham.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionService {

    @Autowired
    private CurrencyRateRepository rateRepository;

    @Autowired
    private CurrencyPropsRepository propsRepository;

    public <CurrencyProps> CurrencyConversionResponse convertCurrency(CurrencyConversionRequest request) {
        Double exchangeRate = rateRepository.finalize(request.getCurrCode(), request.getTargetCode());
        Double convertedAmount = request.getAmount() * exchangeRate;

        CurrencyProps props = propsRepository.findByCurrCode(request.getTargetCode());
        String result = props.getClass() + " " + convertedAmount.toString();

        String statement = getLocalizedStatement(request.getLanguage());

        return new CurrencyConversionResponse(result, statement);
    }

    private String getLocalizedStatement(String language) {
        // Use resource bundles to get the localized statement
    }
}

