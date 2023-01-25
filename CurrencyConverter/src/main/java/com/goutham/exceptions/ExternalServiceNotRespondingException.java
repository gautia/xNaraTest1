package com.goutham.exceptions;

import com.goutham.common.CurrencyConverterConstants;

public class ExternalServiceNotRespondingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3159011240160233998L;

	public ExternalServiceNotRespondingException(String serviceName) {
		super(new StringBuilder(serviceName).append(CurrencyConversionService.UNAVAILABLE).toString());
	}

	public String toString() {
		return "External Service error. Please try again.";
	}

}
