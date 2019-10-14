package com.profit.service;

import com.profit.model.Currency;
import com.profit.model.DayExchangeRate;

import java.time.LocalDate;

public interface ExchangeRateHistoryService {

    DayExchangeRate getDayExchangeRate(Currency base, LocalDate date);

}
