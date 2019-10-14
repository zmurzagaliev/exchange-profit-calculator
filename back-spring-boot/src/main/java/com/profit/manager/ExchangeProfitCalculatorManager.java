package com.profit.manager;

import com.profit.model.CalculateExchangeProfitRequest;

import java.math.BigDecimal;

public interface ExchangeProfitCalculatorManager {

    BigDecimal calculateExchangeProfit(CalculateExchangeProfitRequest request);

}
