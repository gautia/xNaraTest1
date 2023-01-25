package com.goutham;

public class CurrencyConversionResponse {
    private String result;
    private String statement;

    public CurrencyConversionResponse(String result, String statement) {
    }

    // Getters and setters for the properties

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}

