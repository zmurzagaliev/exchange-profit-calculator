package com.profit.service;

import java.math.BigDecimal;

public interface ProfitCalculator {

    BigDecimal calculate(BigDecimal buyPrice, BigDecimal sellPrice, BigDecimal amount, BigDecimal spread);

}
