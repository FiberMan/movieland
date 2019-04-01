package com.filk.service.impl;

import com.filk.dto.CurrencyRateNbuDto;
import com.filk.util.CurrencyCode;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCurrencyServiceTest {

    @Test
    public void convert() {
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        DefaultCurrencyService defaultCurrencyService = new DefaultCurrencyService(restTemplateMock);

        defaultCurrencyService.setCurrencyRatesUrl("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json&date=%s");

        CurrencyRateNbuDto[] currencyRates = new CurrencyRateNbuDto[2];
        currencyRates[0] = new CurrencyRateNbuDto("USD", 27.5);
        currencyRates[1] = new CurrencyRateNbuDto("EUR", 31.2);

        when(restTemplateMock.getForObject(any(String.class), any(Class.class))).thenReturn(currencyRates);

        defaultCurrencyService.refreshCurrencyPrice();

        assertEquals(3.94, defaultCurrencyService.convert(123, CurrencyCode.EUR), 0.01);
        assertEquals(4.47, defaultCurrencyService.convert(123, CurrencyCode.USD), 0.01);
        assertEquals(123, defaultCurrencyService.convert(123, CurrencyCode.UAH), 0.01);
    }
}