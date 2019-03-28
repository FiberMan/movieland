package com.filk.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filk.dto.CurrencyRateNbuDto;
import com.filk.util.CurrencyCode;
import com.filk.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DefaultCurrencyService implements CurrencyService {
    @Value("${currency.ratesUrl}")
    private String currencyRatesUrl;
    private CurrencyCode defaultCurrency = CurrencyCode.UAH;
    private volatile Map<CurrencyCode, Double> currencyRatesCache = new HashMap<>();
    private ObjectMapper objectMapper;

    @Autowired
    public DefaultCurrencyService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public double convert(double amount, CurrencyCode currencyCode) {
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(currencyRatesCache.get(currencyCode)), 2, RoundingMode.HALF_UP).doubleValue();
    }

    @PostConstruct
    @Scheduled(cron = "${currency.ratesUpdateCron}")
    public void refreshCurrencyPrice() {
        List<CurrencyRateNbuDto> currencies;
        String formattedDate = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String requestUrl = String.format(currencyRatesUrl, formattedDate);

        try {
            currencies = objectMapper.readValue(new URL(requestUrl), new TypeReference<List<CurrencyRateNbuDto>>() {
            });
        } catch (IOException e) {
            log.info("Not able to get currency rate list from {}", requestUrl);
            return;
        }

        Map<CurrencyCode, Double> currencyRates = new HashMap<>();
        currencyRates.put(defaultCurrency, 1.0);

        for (CurrencyRateNbuDto currency : currencies) {
            try {
                currencyRates.put(CurrencyCode.valueOf(currency.getCurrencyCode()), currency.getRate());
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (currencyRates.size() != CurrencyCode.values().length) {
            log.info("Some currency rates have not been loaded.");
            return;
        }

        currencyRatesCache = currencyRates;

        log.info("Currency rates has been successfully refreshed.");
    }
}
