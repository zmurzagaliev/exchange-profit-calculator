package com.profit.service.impl;

import com.profit.service.ProfitCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
@Slf4j
public class ProfitCalculatorImpl implements ProfitCalculator {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

    @Override
    public BigDecimal calculate(BigDecimal buyPrice, BigDecimal sellPrice, BigDecimal amount, BigDecimal spread) {
        log.debug(">> calculate : {}, {}, {}, {}", buyPrice, sellPrice, amount, spread);

        BigDecimal response = getSellPriceWithSpread(sellPrice, spread)
                .subtract(getBuyPriceWithSpread(buyPrice, spread))
                .multiply(amount);

        log.debug("<< calculate : {}", response);
        return response;
    }

    private BigDecimal getBuyPriceWithSpread(BigDecimal buyPrice, BigDecimal spread) {
        return buyPrice.add(getSpreadValue(buyPrice, spread));
    }

    private BigDecimal getSellPriceWithSpread(BigDecimal sellPrice, BigDecimal spread) {
        return sellPrice.subtract(getSpreadValue(sellPrice, spread));
    }

    private BigDecimal getSpreadValue(BigDecimal value, BigDecimal spread) {
        return value.divide(spread, MATH_CONTEXT)
                    .divide(BigDecimal.valueOf(100), MATH_CONTEXT)
                    .divide(BigDecimal.valueOf(2), MATH_CONTEXT);
    }
}