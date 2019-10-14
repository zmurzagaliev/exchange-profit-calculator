package com.profit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ExchangeRate {

    private Currency base;
    private Currency target;
    private BigDecimal rate;

}
