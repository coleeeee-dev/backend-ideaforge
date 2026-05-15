package com.ideaforge.platform.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PhoneNumber(String value) {
    public PhoneNumber {
        if (value != null && !value.isBlank() && !value.matches("^[0-9+()\\s-]{6,20}$")) throw new IllegalArgumentException("Invalid phone number");
    }
}
