package com.profit.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class CalculateExchangeProfitRequest {

    private LocalDate buyDate;
    private LocalDate sellDate;

    private BigDecimal amount;
    private BigDecimal spread;
    private Currency baseCurrency;
    private Currency targetCurrency;

}
