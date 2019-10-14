package com.profit.controller;

import com.profit.config.ApplicationProperties;
import com.profit.manager.ExchangeProfitCalculatorManager;
import com.profit.model.CalculateExchangeProfitRequest;
import com.profit.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(path = "/exchange/v1")
@EnableConfigurationProperties(ApplicationProperties.class)
@Validated
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class ExchangeProfitCalculatorController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ExchangeProfitCalculatorManager exchangeProfitCalculatorManager;

    @GetMapping(path = "/calculate")
    public HttpEntity<BigDecimal> analyze(
            @RequestParam(name = "amount") BigDecimal amount,
            @RequestParam(name = "buyDate") LocalDate buyDate,
            @RequestParam(required = false, name = "sellDate") LocalDate sellDate,
            @RequestParam(required = false, name = "spread") BigDecimal spread,
            @RequestParam(required = false, name = "baseCurrency") Currency baseCurrency,
            @RequestParam(required = false, name = "targetCurrency") Currency targetCurrency

    ) {
        ResponseEntity.BodyBuilder retResponseEntity = ResponseEntity.status(200);

        CalculateExchangeProfitRequest request = new CalculateExchangeProfitRequest()
                .setAmount(amount)
                .setBuyDate(buyDate)
                .setSellDate(Optional.ofNullable(sellDate).orElse(LocalDate.now()))
                .setSpread(Optional.ofNullable(spread).orElse(applicationProperties.getDefaultSpread()))
                .setBaseCurrency(Optional.ofNullable(baseCurrency).orElse(applicationProperties.getDefaultBaseCurrency()))
                .setTargetCurrency(Optional.ofNullable(targetCurrency).orElse(applicationProperties.getDefaultTargetCurrency()));

        return retResponseEntity.body(exchangeProfitCalculatorManager.calculateExchangeProfit(request));
    }
}