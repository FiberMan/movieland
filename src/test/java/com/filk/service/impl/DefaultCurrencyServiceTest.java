package com.filk.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filk.dto.CurrencyRateNbuDto;
import com.filk.util.CurrencyCode;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCurrencyServiceTest {

    @Test
    public void convert() throws IOException, NoSuchFieldException, IllegalAccessException {
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        DefaultCurrencyService defaultCurrencyService = new DefaultCurrencyService(objectMapperMock);

        Field currencyRatesUrl = defaultCurrencyService.getClass().getDeclaredField("currencyRatesUrl");
        currencyRatesUrl.setAccessible(true);
        currencyRatesUrl.set(defaultCurrencyService, "https://bank.gov.ua");
        currencyRatesUrl.setAccessible(false);

        List<CurrencyRateNbuDto> currencyRates = new ArrayList<>();
        currencyRates.add(new CurrencyRateNbuDto("USD", 27.5));
        currencyRates.add(new CurrencyRateNbuDto("EUR", 31.2));

        when(objectMapperMock.readValue(any(URL.class), any(TypeReference.class))).thenReturn(currencyRates);

        defaultCurrencyService.refreshCurrencyPrice();

        assertEquals(3.94, defaultCurrencyService.convert(123, CurrencyCode.EUR), 0.01);
        assertEquals(4.47, defaultCurrencyService.convert(123, CurrencyCode.USD), 0.01);
        assertEquals(123, defaultCurrencyService.convert(123, CurrencyCode.UAH), 0.01);
    }
}