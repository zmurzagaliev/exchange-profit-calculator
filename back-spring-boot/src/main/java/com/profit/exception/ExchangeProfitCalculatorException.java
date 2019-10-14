package com.profit.exception;

public class ExchangeProfitCalculatorException extends RuntimeException {

    public ExchangeProfitCalculatorException() {
    }

    public ExchangeProfitCalculatorException(String message) {
        super(message);
    }

    public ExchangeProfitCalculatorException(Throwable cause) {
        super(cause);
    }
}
