package com.goutham.controllers;

import com.goutham.CurrencyConversionRequest;
import com.goutham.CurrencyConversionResponse;
import com.goutham.services.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class CurrencyConversionController {

	@Autowired
	private CurrencyConversionService conversionService;

	@PostMapping("/convert")
	public CurrencyConversionResponse convertCurrency(@RequestBody CurrencyConversionRequest request) {
		return conversionService.convertCurrency(request);
	}
}
