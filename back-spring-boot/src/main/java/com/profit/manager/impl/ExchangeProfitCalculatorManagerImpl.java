package com.profit.manager.impl;

import com.profit.exception.ExchangeProfitCalculatorException;
import com.profit.manager.ExchangeProfitCalculatorManager;
import com.profit.model.CalculateExchangeProfitRequest;
import com.profit.model.DayExchangeRate;
import com.profit.model.ExchangeRate;
import com.profit.service.ExchangeRateHistoryService;
import com.profit.service.ProfitCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ExchangeProfitCalculatorManagerImpl implements ExchangeProfitCalculatorManager {

    private ExchangeRateHistoryService exchangeRateHistoryService;

    private ProfitCalculator profitCalculator;

    public ExchangeProfitCalculatorManagerImpl(ExchangeRateHistoryService exchangeRateHistoryService, ProfitCalculator profitCalculator) {
        this.exchangeRateHistoryService = exchangeRateHistoryService;
        this.profitCalculator = profitCalculator;
    }

    @Override
    public BigDecimal calculateExchangeProfit(CalculateExchangeProfitRequest request) {
        log.debug(">> calculateExchangeProfit : {}", request);

        DayExchangeRate dayBuyExchangeRate = exchangeRateHistoryService.getDayExchangeRate(request.getBaseCurrency(), request.getBuyDate());
        ExchangeRate buyExchangeRate = dayBuyExchangeRate.getExchangeRate(request.getBaseCurrency(), request.getTargetCurrency())
                                                         .orElseThrow(() -> new ExchangeProfitCalculatorException(
                                                                 "Unexpected exception. Failed to get buy exchange rates."));

        DayExchangeRate daySellExchangeRate = exchangeRateHistoryService.getDayExchangeRate(request.getBaseCurrency(), request.getSellDate());
        ExchangeRate sellExchangeRate = daySellExchangeRate.getExchangeRate(request.getBaseCurrency(), request.getTargetCurrency())
                                                           .orElseThrow(() -> new ExchangeProfitCalculatorException(
                                                                   "Unexpected exception. Failed to get sell exchange rates."));

        BigDecimal response = profitCalculator.calculate(buyExchangeRate.getRate(), sellExchangeRate.getRate(), request.getAmount(), request.getSpread());
        log.debug("<< calculateExchangeProfit: {}", response);
        return response;
    }
}
