package com.filk.entity;

import lombok.Getter;

import java.util.Objects;

@Getter
public class RequestParameters {
    private SortBy sortBy;
    private SortOrder sortOrder;

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
        validate();
        setDefaults();
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = SortOrder.valueOf(sortOrder.toUpperCase());
        validate();
        setDefaults();
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
