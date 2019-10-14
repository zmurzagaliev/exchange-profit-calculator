package com.profit.config;

import com.profit.model.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ConfigurationProperties(prefix = "application")
@Validated
@Getter
@Setter
public class ApplicationProperties {

    @NotNull
    private BigDecimal defaultSpread;

    @NotNull
    private Currency defaultBaseCurrency;

    @NotNull
    private Currency defaultTargetCurrency;

}
