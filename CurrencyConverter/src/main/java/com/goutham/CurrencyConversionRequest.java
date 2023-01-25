package com.goutham;

public class CurrencyConversionRequest {
    private String curr_code;
    private Double amount;
    private String language;
    private String target_code;

    public String getCurr_code() {
        return curr_code;
    }

    public void setCurr_code(String curr_code) {
        this.curr_code = curr_code;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTarget_code() {
        return target_code;
    }

    public void setTarget_code(String target_code) {
        this.target_code = target_code;
    }

    public Object getCurrCode() {
        return null;
    }

    public String getTargetCode() {
        return null;
    }
}
