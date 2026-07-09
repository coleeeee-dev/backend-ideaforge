package com.ideaforge.platform.iam.domain.model.valueobjects;

public record LoginResult(
        Long accountId,
        String email,
        AccountRole role,
        String token,
        String tokenType,
        long expiresInMinutes) { }
