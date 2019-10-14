package com.profit.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.profit.exception.ExchangeProfitCalculatorException;
import com.profit.model.Currency;
import com.profit.model.DayExchangeRate;
import com.profit.model.ExchangeRate;
import com.profit.service.ExchangeRateHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class ExchangeRateHistoryServiceImpl implements ExchangeRateHistoryService {

    @Value("${fixer.accessToken}")
    private String accessToken;

    @Value("${fixer.baseUrl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DayExchangeRate getDayExchangeRate(Currency base, LocalDate date) {
        log.debug(">> getDayExchangeRate : {}, {}", base, date);

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriComponents = createUriComponents(date, base);

        ResponseEntity<JsonNode> fixerResponse = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity,
                JsonNode.class);

        DayExchangeRate response = handleResponse(fixerResponse);
        log.debug("<< getDayExchangeRate : {}", response);
        return response;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private UriComponents createUriComponents(LocalDate date, Currency base) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(String.format("%s/%s", baseUrl, "api/{date}"));
        uriComponentsBuilder.queryParam("access_key", accessToken);
        uriComponentsBuilder.queryParam("base", base.name());
        return uriComponentsBuilder.buildAndExpand(date);
    }

    private DayExchangeRate handleResponse(ResponseEntity<JsonNode> response) {
        checkOnError(response);
        JsonNode body = response.getBody();
        Currency base = Currency.get(body.get("base").textValue())
                                .orElseThrow(() -> new ExchangeProfitCalculatorException("Unexpected base currency"));

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        DayExchangeRate ans = new DayExchangeRate()
                .setDate(LocalDate.parse(body.get("date").textValue()))
                .setBaseCurrency(base)
                .setExchangeRates(exchangeRates);

        Iterator<Map.Entry<String, JsonNode>> rates = body.get("rates").fields();
        while (rates.hasNext()) {
            Map.Entry<String, JsonNode> rate = rates.next();

            Optional<Currency> target = Currency.get(rate.getKey());
            target.ifPresent(currency -> exchangeRates.add(new ExchangeRate(base, currency, BigDecimal.valueOf(rate.getValue().doubleValue()))));
        }

        return ans;
    }

    private void checkOnError(ResponseEntity<JsonNode> response) {
        if (response.getStatusCode() != HttpStatus.OK
                || !response.getBody().get("success").booleanValue()) {
            throw new ExchangeProfitCalculatorException("Exception during call Fixer API");
        }
    }
}