package com.filk.util;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RequestParameters {
    private SortBy sortBy;
    private SortOrder sortOrder;
    private CurrencyCode currency;

    public enum SortBy {
        RATING,
        PRICE
    }

    public enum SortOrder {
        ASC,
        DESC
    }

    public void setSortBy(String sortBy) {
        this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = SortOrder.valueOf(sortOrder.toUpperCase());
    }

    public void setCurrency(String currency) {
        this.currency = CurrencyCode.valueOf(currency.toUpperCase());
    }

    private void validate() {
        if(sortBy == SortBy.RATING && sortOrder == SortOrder.ASC) {
            throw new IllegalArgumentException("Sort by rating ASC is not supported.");
        }
    }

    private void setDefaults() {
        if(sortBy == SortBy.RATING && sortOrder == null) {
            sortOrder = SortOrder.DESC;
        }
        if(sortBy == SortBy.PRICE && sortOrder == null) {
            sortOrder = SortOrder.ASC;
        }
        if(currency == null) {
            currency = CurrencyCode.UAH;
        }
    }

    public RequestParameters postProcess(){
        validate();
        setDefaults();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestParameters)) return false;
        RequestParameters that = (RequestParameters) o;
        return sortBy == that.sortBy &&
                sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy, sortOrder);
    }
}
