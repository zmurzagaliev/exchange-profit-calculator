package com.profit.service.impl;

import com.profit.service.ProfitCalculator;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProfitCalculatorTest {

    @Test
    public void testCalculateProfit() {
        ProfitCalculator profitCalculator = new ProfitCalculatorImpl();

        BigDecimal profit = profitCalculator.calculate(BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(10), BigDecimal.valueOf(0.5));
        assertThat(profit, Matchers.comparesEqualTo(BigDecimal.valueOf(47.50)));
    }
}
