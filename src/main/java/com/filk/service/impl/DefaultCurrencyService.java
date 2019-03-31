package com.filk.service.impl;

import com.filk.dto.CurrencyRateNbuDto;
import com.filk.util.CurrencyCode;
import com.filk.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class DefaultCurrencyService implements CurrencyService {
    private String currencyRatesUrl;
    private CurrencyCode defaultCurrency = CurrencyCode.UAH;
    private Map<CurrencyCode, Double> currencyRatesCache = new HashMap<>();
    private RestTemplate restTemplate;
    private ReentrantLock lock;

    @Value("${currency.ratesUrl}")
    public void setCurrencyRatesUrl(String currencyRatesUrl) {
        this.currencyRatesUrl = currencyRatesUrl;
    }

    @Autowired
    public DefaultCurrencyService(RestTemplate restTemplate, ReentrantLock lock) {
        this.restTemplate = restTemplate;
        this.lock = lock;
    }

    @Override
    public double convert(double price, CurrencyCode currencyCode) {
        try {
            lock.lock();
            BigDecimal rate = BigDecimal.valueOf(currencyRatesCache.get(currencyCode));
            BigDecimal amount = BigDecimal.valueOf(price);
            BigDecimal result = amount.divide(rate, 2, RoundingMode.HALF_UP);
            return result.doubleValue();
        } finally {
            lock.unlock();
        }

    }

    @PostConstruct
    @Scheduled(cron = "${currency.ratesUpdateCron}")
    public void refreshCurrencyPrice() {
        String formattedDate = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String requestUrl = String.format(currencyRatesUrl, formattedDate);

        CurrencyRateNbuDto[] rates = restTemplate.getForObject(requestUrl, CurrencyRateNbuDto[].class);

        try {
            lock.lock();

            List<String> unprocessedCodes = CurrencyCode.getStringList();
            unprocessedCodes.remove(defaultCurrency.toString());
            currencyRatesCache.put(defaultCurrency, 1.0);

            if (rates == null) {
                terminateIfEmptyCache("Not able to get currency rate list from {}", requestUrl);
            }

            for (CurrencyRateNbuDto rate : rates) {
                if (CurrencyCode.contains(rate.getCurrencyCode())) {
                    currencyRatesCache.put(CurrencyCode.valueOf(rate.getCurrencyCode()), rate.getRate());
                    unprocessedCodes.remove(rate.getCurrencyCode());
                }
            }

            if (!unprocessedCodes.isEmpty()) {
                terminateIfEmptyCache("Some currency rates have not been loaded {}", unprocessedCodes.toString());
            }

            log.info("Currency rates has been successfully refreshed.");
        } finally {
            lock.unlock();
        }
    }

    private void terminateIfEmptyCache(String logMessage, String logParam) {
        log.error(logMessage, logParam);

        if (currencyRatesCache.isEmpty()) {
            throw new RuntimeException("Not able to get currency rate list from URL");
        } else {
            log.info("Continue using cached currency rates.");
        }
    }
}
