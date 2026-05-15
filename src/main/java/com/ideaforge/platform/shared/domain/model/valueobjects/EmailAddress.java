package com.ideaforge.platform.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record EmailAddress(String value) {
    public EmailAddress {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Email cannot be blank");
        if (!value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) throw new IllegalArgumentException("Invalid email format");
        value = value.trim().toLowerCase();
    }
}
