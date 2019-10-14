package com.profit.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class DayExchangeRate {

    private LocalDate date;
    private Currency baseCurrency;

    private List<ExchangeRate> exchangeRates;

    public List<ExchangeRate> getExchangeRates(Currency base) {
        return Optional.ofNullable(exchangeRates)
                       .orElse(Collections.emptyList())
                       .stream().filter(o -> o.getBase() == base)
                       .collect(Collectors.toList());
    }

    public Optional<ExchangeRate> getExchangeRate(Currency base, Currency target) {
        return Optional.ofNullable(exchangeRates)
                       .orElse(Collections.emptyList())
                       .stream().filter(o -> o.getBase() == base && o.getTarget() == target)
                       .findFirst();
    }
}
