package com.filk.service;

import com.filk.util.CurrencyCode;

public interface CurrencyService {
    double convert(double amount, CurrencyCode currencyCode);
}
